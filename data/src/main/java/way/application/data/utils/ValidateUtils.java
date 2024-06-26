package way.application.data.utils;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import way.application.core.exception.BadRequestException;
import way.application.core.exception.ConflictException;
import way.application.core.utils.ErrorResult;
import way.application.data.friend.FriendEntity;
import way.application.data.friend.FriendJpaRepository;
import way.application.data.friendRequest.FriendRequestJpaRepository;
import way.application.data.location.LocationEntity;
import way.application.data.location.LocationJpaRepository;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberJpaRepository;
import way.application.data.schedule.ScheduleEntity;
import way.application.data.schedule.ScheduleJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberEntity;
import way.application.data.scheduleMember.ScheduleMemberJpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidateUtils {
	private final MemberJpaRepository memberJpaRepository;
	private final ScheduleJpaRepository scheduleJpaRepository;
	private final ScheduleMemberJpaRepository scheduleMemberJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final BCryptPasswordEncoder encoder;
	private final FriendJpaRepository friendJpaRepository;
	private final FriendRequestJpaRepository friendRequestJpaRepository;
	private final RedisTemplate<String, String> redisTemplate;


	public MemberEntity validateMemberEntity(Long memberSeq) {
		return memberJpaRepository.findById(memberSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.MEMBER_SEQ_BAD_REQUEST_EXCEPTION));
	}

	public List<MemberEntity> validateMemberEntityIn(List<Long> memberSeqs) {
		if (memberSeqs == null || memberSeqs.isEmpty()) {
			return Collections.emptyList();
		}

		List<MemberEntity> memberEntities = memberJpaRepository.findAllById(memberSeqs);
		if (memberEntities.size() != memberSeqs.size()) {
			throw new BadRequestException(ErrorResult.MEMBER_SEQ_BAD_REQUEST_EXCEPTION);
		}

		return memberEntities;
	}

	public ScheduleEntity validateScheduleEntity(Long scheduleSeq) {
		return scheduleJpaRepository.findById(scheduleSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.SCHEDULE_SEQ_BAD_REQUEST_EXCEPTION));
	}

	public ScheduleEntity validateScheduleEntityCreatedByMember(Long scheduleSeq, Long memberSeq) {
		return scheduleMemberJpaRepository.findAcceptedScheduleMemberByScheduleIdAndMemberId(scheduleSeq, memberSeq)
			.orElseThrow(
				() -> new BadRequestException(ErrorResult.SCHEDULE_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION))
			.getSchedule();
	}

	public ScheduleMemberEntity validateMemberInScheduleMemberEntity(Long memberSeq, Long scheduleSeq) {
		return scheduleMemberJpaRepository.findAcceptedScheduleMemberByScheduleIdAndMemberId(scheduleSeq, memberSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.MEMBER_SEQ_NOT_IN_SCHEDULE_BAD_REQUEST_EXCEPTION));
	}

	public ScheduleMemberEntity validateMemberInSchedule(Long memberSeq, Long scheduleSeq) {
		return scheduleMemberJpaRepository.findScheduleMemberEntityByMemberSeq(memberSeq, scheduleSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.MEMBER_SEQ_NOT_IN_SCHEDULE_BAD_REQUEST_EXCEPTION));
	}

	public List<LocationEntity> validateLocationEntityByCount(Long memberSeq) {
		List<LocationEntity> locationEntities = locationJpaRepository.findLocationEntityByMemberId(memberSeq);

		if (locationEntities.size() > 10)
			throw new BadRequestException(ErrorResult.LOCATION_OVER_TEN_BAD_REQUEST_EXCEPTION);

		return locationEntities;
	}

	public LocationEntity validateLocationEntityById(Long locationSeq) {
		return locationJpaRepository.findById(locationSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.LOCATION_SEQ_BAD_REQUEST_EXCEPTION));
	}

	public LocationEntity validateLocationEntityByMemberId(Long locationSeq, Long memberSeq) {
		return locationJpaRepository.findLocationEntityByMemberIdAndLocationId(memberSeq, locationSeq)
			.orElseThrow(
				() -> new BadRequestException(ErrorResult.LOCATION_DIDNT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION));
	}

	public void checkUserIdDuplication(String userId) {
		memberJpaRepository.findByUserId(userId)
			.ifPresent(user -> {
				throw new ConflictException(ErrorResult.USER_ID_DUPLICATION_EXCEPTION);
			});
	}

	public void checkEmailDuplication(String email) {
		memberJpaRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new ConflictException(ErrorResult.EMAIL_DUPLICATION_EXCEPTION);
			});

	}

	public MemberEntity validateUserId(String userId) {
		return memberJpaRepository.findByUserId(userId)
			.orElseThrow(() -> new BadRequestException(ErrorResult.USER_ID_BAD_REQUEST_EXCEPTION));
	}

	public void validatePassword(String password, String encodedPassword) {

		if (!encoder.matches(password, encodedPassword)) {
			throw new BadRequestException(ErrorResult.PASSWORD_BAD_REQUEST_EXCEPTION);
		}
	}

	public void validateEmail(String email) {
		if(redisTemplate.opsForValue().get(email) == null) {
			throw new BadRequestException(ErrorResult.EMAIL_BAD_REQUEST_EXCEPTION);
		}
	}

	public MemberEntity validateFindIdByEmail(String email) {
		return memberJpaRepository.findByEmail(email)
			.orElseThrow(() -> new BadRequestException(ErrorResult.EMAIL_BAD_REQUEST_EXCEPTION));
	}

	public void validateCode(String verifyCode, String code) {

		if (!verifyCode.equals(code)) {
			throw new BadRequestException(ErrorResult.CODE_BAD_REQUEST_EXCEPTION);
		}
	}

	public void validateUserMismatch(String userId, String requestUserId) {
		if (!userId.equals(requestUserId)) {
			throw new BadRequestException(ErrorResult.USER_MISMATCH_BAD_REQUEST_EXCEPTION);
		}
	}

	public void validateResetPassword(String password, String checkPassword) {

		if (!password.equals(checkPassword)) {
			throw new BadRequestException(ErrorResult.PASSWORD_MISMATCH_BAD_REQUEST_EXCEPTION);
		}
	}

	public void validateMemberAndFriendId(MemberEntity member, MemberEntity friend) {

		if(member.equals(friend)) {
			throw new BadRequestException(ErrorResult.SELF_FRIEND_REQUEST_BAD_REQUEST_EXCEPTION);
		}

	}

	public void validateAlreadyFriend(MemberEntity owner,MemberEntity friend) {

		Optional<FriendEntity> byOwnerAndFriends = friendJpaRepository.findByOwnerAndFriends(owner, friend);
		byOwnerAndFriends.ifPresent(friendSeq -> {
			throw new BadRequestException(ErrorResult.ALREADY_FRIEND_BAD_REQUEST_EXCEPTION);
		});

	}

	public void validateAlreadyFriendRequest(MemberEntity owner,MemberEntity friends) {

		if(friendRequestJpaRepository.existsByReceiverSeqAndSenderSeq(friends, owner)){
			throw new BadRequestException(ErrorResult.ALREADY_SENT_BAD_REQUEST_EXCEPTION);
		}
	}

	public void validateAlreadyFriendRequestByFriend(MemberEntity friends,MemberEntity owner) {

		if(friendRequestJpaRepository.existsByReceiverSeqAndSenderSeq(owner, friends)){
			throw new BadRequestException(ErrorResult.ALREADY_SENT_BY_FRIEND_BAD_REQUEST_EXCEPTION);
		}
	}
}
