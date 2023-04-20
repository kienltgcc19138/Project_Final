package vn.kien.event.eventbe.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 09:16
 */
@Entity
@Table(name = "major")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "major-joined",
        includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode(value = "users"),
        }
)
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "major_generator")
    private Long majorId;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdAt;
    private String createdBy;
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private Set<Users> users;
}
