package way.application.data.friend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import way.application.data.member.MemberEntity;

@Entity
@Table(name = "friend")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class FriendEntity {

    @Id
    @Column(name = "friend_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friends_seq")
    private MemberEntity friends;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_seq")
    private MemberEntity owner;



}
