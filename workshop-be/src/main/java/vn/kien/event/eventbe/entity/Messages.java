package vn.kien.event.eventbe.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "messages-joined",
        includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode(value = "users"),
        }
)
public class Messages {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    private Users users;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String createdBy;
    private Date createdAt;
}
