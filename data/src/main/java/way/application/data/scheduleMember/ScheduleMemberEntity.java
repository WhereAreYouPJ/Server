package way.application.data.scheduleMember;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import way.application.data.member.MemberEntity;
import way.application.data.schedule.ScheduleEntity;

@Entity
@Table(name = "schedule_member")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
public class ScheduleMemberEntity {
    @Id
    @Column(name = "schedule_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity invitedMember;

    @Column(name = "is_creator", nullable = false)
    private Boolean isCreator = false;

    @Column(name = "accept_schedule", nullable = false)
    private Boolean acceptSchedule = false;
}
