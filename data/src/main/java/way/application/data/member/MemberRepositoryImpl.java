package way.application.data.member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import way.application.core.exception.BadRequestException;
import way.application.core.utils.ErrorResult;
import way.application.core.utils.S3Util;
import way.application.data.utils.ValidateUtils;
import way.application.domain.jwt.JwtRepository;
import way.application.domain.member.Member;
import way.application.domain.member.MemberRepository;

import java.io.IOException;
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
	private final S3Util s3Util;

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
		String accessToken = jwtRepository.generateAccessToken(member.getUserId());
		String refreshToken = jwtRepository.generateRefreshToken(member.getUserId());

		return new Member.MemberLoginResponse(accessToken, refreshToken, member.getMemberSeq());
	}

	@Override
	public void sendMail(Member.MailSendRequest request) {

		// authKey 생성
		Random random = new Random();
		String authKey = String.valueOf(random.nextInt(888888) + 111111);

		//이메일 발송
		sendAuthEmail(request.email(), authKey);
	}

	private void sendAuthEmail(String email, String authKey) {
		String subject = "지금어디 인증번호 입니다.";
		String text = "인증번호는 " + authKey + "입니다. <br/>";

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
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
		validateUtils.validateUserMismatch(member.getUserId(), request.userId());

		// 인증코드 검사
		String verifyCode = redisTemplate.opsForValue().get(request.email());
		validateUtils.validateCode(verifyCode, request.code());

		//코드 삭제
		redisTemplate.delete(request.email());

	}

	@Override
	public void resetPassword(Member.PasswordResetRequest request) {

		//유저 검증
		MemberEntity member = validateUtils.validateUserId(request.userId());
		validateUtils.validatePassword(request.password(), member.getEncodedPassword());

		// 비밀번호 검증
		validateUtils.validateResetPassword(request.password(), request.checkPassword());

		// 비밀번호 업데이트 및 저장
		member.updatePassword(encoder.encode(request.checkPassword()));
		memberJpaRepository.save(member);

	}

	@Override
	public Member.FindIdResponse findId(Member.FindIdRequest request) {

		String verifyCode = redisTemplate.opsForValue().get(request.email());
		validateUtils.validateCode(verifyCode, request.code());

		MemberEntity member = validateUtils.validateEmail(request.email());

		return new Member.FindIdResponse(member.getUserId());
	}

	@Override
	public Member.GetMemberDetailResponse getMemberDetail(Long memberSeq) {

		MemberEntity member = validateUtils.validateMemberEntity(memberSeq);

		return new Member.GetMemberDetailResponse(member.getUserName(), member.getUserId(), member.getEmail(),
			member.getProfileImage());
	}

	@Override
	@Transactional
	public void modifyUserInfo(Long memberId, MultipartFile multipartFile, String newUserId, String newUserName) throws
		IOException {

		//멤버 조회
		MemberEntity member = validateUtils.validateMemberEntity(memberId);

		//아이디 변경
		if (newUserId != null) {
			validateUtils.checkUserIdDuplication(newUserId);

			member.updateUserId(newUserId);
		}

		//프로필 사진 변경
		if (multipartFile != null) {
			String upload = s3Util.uploadMultipartFile(multipartFile);
			member.updateProfileImage(upload);
		}

		//이름 변경
		if (newUserName != null) {
			member.updateUserName(newUserName);
		}

		//저장
		memberJpaRepository.save(member);
	}

	@Override
	public Member.GetMemberDetailByUserIdResponse getMemberDetailByUserId(String userId, HttpServletRequest request) {

		// 토큰 추출
		String token = jwtRepository.extractToken(request);
		//userId 추출
		String tokenUserId = jwtRepository.extractUserId(token);
		//userId 검사
		MemberEntity member = validateUtils.validateUserId(userId);

		if (tokenUserId.equals(member.getUserId())) {
			throw new BadRequestException(ErrorResult.SELF_SEARCH_BAD_REQUEST_EXCEPTION);
		}

		return new Member.GetMemberDetailByUserIdResponse(member.getUserName(), member.getProfileImage(),
			member.getMemberSeq());
	}

	@Override
	@Transactional
	public void logout(Member.LogoutRequest request) {

		MemberEntity member = validateUtils.validateMemberEntity(request.memberSeq());

		member.deleteFireBaseTargetToken();
		redisTemplate.delete(member.getUserId());

	}
}
