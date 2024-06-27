package way.application.data.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import way.application.core.exception.ServerException;
import way.application.core.utils.ErrorResult;
import way.application.data.friendRequest.FriendRequestEntity;
import way.application.data.friendRequest.FriendRequestJpaRepository;
import way.application.data.friendRequest.FriendRequestMapper;
import way.application.data.member.MemberEntity;
import way.application.data.utils.ValidateUtils;
import way.application.domain.firebase.FireBaseRepository;
import way.application.domain.friend.Friend;
import way.application.domain.friend.FriendRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class FriendRepositoryImpl implements FriendRepository {

    private final ValidateUtils validateUtils;
    private final FriendRequestMapper friendRequestMapper;
    private final FireBaseRepository fireBaseRepository;
    private final FriendRequestJpaRepository friendRequestJpaRepository;

    @Override
    @Transactional
    public void friendRequest(Friend.FriendRequest request) {

        // 보낸 아이디
        MemberEntity owner = validateUtils.validateMemberEntity(request.memberSeq());

        // 보낼 아이디
        MemberEntity friends = validateUtils.validateMemberEntity(request.friendSeq());

        // 본인 한테 친구 요청 보낼 수 없음
        validateUtils.validateMemberAndFriendId(owner,friends);

        // 이미 친구 요청 전송됨
        validateUtils.validateAlreadyFriendRequest(owner, friends);

        // 친구가 친구 요청을 보냄
        validateUtils.validateAlreadyFriendRequest(friends,owner);

        // 이미 친구
        validateUtils.validateAlreadyFriend(owner,friends);

        // 친구 요청 저장
        FriendRequestEntity friendRequestEntity = friendRequestMapper.toFriendRequestEntity(owner,friends, LocalDateTime.now());
        friendRequestJpaRepository.save(friendRequestEntity);
        // 알림
        sendNotification(friends, owner);

    }
    private void sendNotification(MemberEntity invitedMember, MemberEntity createMember) {
        try {
            String body = createMember.getUserName() + "이(가) 친구 요청을 보냈습니다.";
            fireBaseRepository.sendMessageTo(invitedMember.getFireBaseTargetToken(), "친구 요청이 들어왔습니다.", body);
        } catch (IOException e) {
            log.info("Exception = {}", e.getMessage());
            throw new ServerException(ErrorResult.FIREBASE_CLOUD_MESSAGING_EXCEPTION);
        }
    }

}
