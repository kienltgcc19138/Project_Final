package vn.kien.event.eventbe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "recommend")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "recommend-users-joined",
        includeAllAttributes = true,
        attributeNodes = @NamedAttributeNode("users")
)
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommend_generator")
    private Long recommendId;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    private Date createdAt;
    private String createdBy;
}
