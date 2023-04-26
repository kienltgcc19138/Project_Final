package vn.kien.event.eventbe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 17:39
 */

@Entity
@Table(name = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraphs({
        @NamedEntityGraph(name = "notification-joined",
                attributeNodes = @NamedAttributeNode(value = "users"))
})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_generator")
    private Long notificationId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    private String description;
    private boolean isRead;
    private Long eventId;
    private Date createdAt;

}
