package way.application.data.friendRequest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import way.application.data.member.MemberEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "FriendRequest")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class FriendRequestEntity {

    @Id
    @Column(name = "friendRequest_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendRequestSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_seq")
    private MemberEntity senderSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_seq")
    private MemberEntity receiverSeq;

    private LocalDateTime createTime;
}
