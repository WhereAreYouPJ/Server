package way.application.data.location;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import way.application.data.member.MemberEntity;

@Entity
@Table(name = "location")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
public class LocationEntity {
	@Id
	@Column(name = "location_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationSeq;

	private String location;
	private String streetName;
	private Double x;
	private Double y;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberEntity member;
}
