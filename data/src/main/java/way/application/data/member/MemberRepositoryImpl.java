package way.application.data.member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import way.application.core.exception.BadRequestException;
import way.application.core.utils.ErrorResult;
import way.application.data.utils.ValidateUtils;
import way.application.domain.jwt.JwtRepository;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder encoder;
    private final ValidateUtils validateUtils;
    private final JwtRepository jwtRepository;
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    @Transactional
    public void save(Member.SaveMemberRequest request) {
        memberJpaRepository.save(
                memberMapper.toMemberEntity(request, encoder.encode(request.password()))
        );
    }

    @Override
    public Member.CheckIdResponse checkId(String userId) {

        //예외처리
        validateUtils.checkUserIdDuplication(userId);

        return new Member.CheckIdResponse(userId);
    }

    @Override
    public Member.CheckEmailResponse checkEmail(String email) {

        validateUtils.checkEmailDuplication(email);

        return new Member.CheckEmailResponse(email);
    }

    @Override
    @Transactional
    public Member.MemberLoginResponse login(Member.MemberLoginRequest request) {

        // 검증
        MemberEntity member = validateUtils.validateUserId(request.userId());
        validateUtils.validatePassword(request.password(), member.getEncodedPassword());

        // fcm 토큰 저장
        memberJpaRepository.updateByFireBaseTargetToken(request.targetToken());

        // jwt 생성
        String accessToken = jwtRepository.generateAccessToken(member.getId());
        String refreshToken = jwtRepository.generateRefreshToken(member.getId());

        return new Member.MemberLoginResponse(accessToken,refreshToken, member.getId());
    }

    @Override
    public void sendMail(Member.MailSendRequest request) {

        // authKey 생성
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(888888) +  111111);

        //이메일 발송
        sendAuthEmail(request.email(),authKey);
    }

    private void sendAuthEmail(String email, String authKey){
        String subject = "지금어디 인증번호 입니다.";
        String text = "인증번호는 " + authKey + "입니다. <br/>";

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text,true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            throw new BadRequestException(ErrorResult.EMAIL_BAD_REQUEST_EXCEPTION);
        }

        saveCode(email, authKey);
    }

    private void saveCode(String email, String authKey) {
        redisTemplate.opsForValue().set(
                email,
                authKey,
                300000,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void verifyCode(Member.CodeVerifyRequest request) {

        //이메일 검증
        validateUtils.validateEmail(request.email());

        // 인증코드 검사
        String verifyCode = redisTemplate.opsForValue().get(request.email());
        validateUtils.validateCode(verifyCode, request.code());

        //코드 삭제
        redisTemplate.delete(request.email());

    }

    @Override
    public void verifyPasswordCode(Member.VerifyPasswordCodeRequest request) {

        // 멤버 조회
        MemberEntity member = validateUtils.validateEmail(request.email());

        // 이메일, 멤버 유효성 검사
        validateUtils.validateUserMismatch(member.getUserId(),request.userId());

        // 인증코드 검사
        String verifyCode = redisTemplate.opsForValue().get(request.email());
        validateUtils.validateCode(verifyCode,request.code());

        //코드 삭제
        redisTemplate.delete(request.email());

    }

    @Override
    public void resetPassword(Member.PasswordResetRequest request) {

        //유저 검증
        MemberEntity member = validateUtils.validateUserId(request.userId());
        validateUtils.validatePassword(request.password(), member.getEncodedPassword());

        // 비밀번호 검증
        validateUtils.validateResetPassword(request.password(),request.checkPassword());

        // 비밀번호 업데이트 및 저장
        member.updatePassword(encoder.encode(request.checkPassword()));
        memberJpaRepository.save(member);

    }

    // TODO 로그인 시 MemberEntity firebaseTargetToken 저장 로직 구현 필요
}
