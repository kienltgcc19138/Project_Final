package vn.kien.event.eventbe.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 09:32
 */
@Entity
@Table(name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String objectId;
    private String objectType;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdAt;
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
}
