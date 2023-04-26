package vn.kien.event.eventbe.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
        name = "course-joined",
        includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode(value = "users"),
        }
)

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
    private Long courseId;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdAt;
    private String createdBy;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Users> users;
}
