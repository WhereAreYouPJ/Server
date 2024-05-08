package way.application.data.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import way.application.core.exception.BadRequestException;
import way.application.core.utils.ErrorResult;
import way.application.data.member.MemberEntity;
import way.application.data.member.MemberJpaRepository;
import way.application.data.schedule.ScheduleEntity;
import way.application.data.schedule.ScheduleJpaRepository;
import way.application.data.scheduleMember.ScheduleMemberEntity;
import way.application.data.scheduleMember.ScheduleMemberJpaRepository;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidateUtils {
    private final MemberJpaRepository memberJpaRepository;
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final ScheduleMemberJpaRepository scheduleMemberJpaRepository;

    // TODO **ID 값으로만 Entity별 validate 진행
    public MemberEntity validateMemberEntity(Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorResult.USER_ID_BAD_REQUEST_EXCEPTION));
    }

    public List<MemberEntity> validateMemberEntityIn(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<MemberEntity> memberEntities = memberJpaRepository.findAllById(ids);
        if (memberEntities.size() != ids.size()) {
            throw new BadRequestException(ErrorResult.USER_ID_BAD_REQUEST_EXCEPTION);
        }

        return memberEntities;
    }


    public ScheduleEntity validateScheduleEntity(Long id) {
        return scheduleJpaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorResult.SCHEDULE_ID_BAD_REQUEST_EXCEPTION));
    }

    public ScheduleMemberEntity validateMemberInScheduleMemberEntity(Long memberId, Long scheduleId) {
        return scheduleMemberJpaRepository.findAcceptedScheduleMemberByScheduleIdAndMemberId(scheduleId, memberId)
                .orElseThrow(() -> new BadRequestException(ErrorResult.MEMBER_ID_NOT_IN_SCHEDULE_BAD_EXCEPTION));
    }
}
