package way.application.data.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class MemberEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String encodedPassword;

    private String email;
    private String profileImage;

    private String fireBaseTargetToken;

    public void updatePassword(String password) {
        this.encodedPassword = password;
    }

    public void updateUserId(String newUserId) {
        this.userId = newUserId;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateUserName(String newUserName) {
        this.userName = newUserName;
    }


    public void deleteFireBaseTargetToken() {
        this.fireBaseTargetToken = null;
    }
}
