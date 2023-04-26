package vn.kien.event.eventbe.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users_event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
        name = "users-event-joined",
        includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode(value = "users", subgraph = "users-subgraph"),
                @NamedAttributeNode(value = "event"),
        },
        subgraphs = @NamedSubgraph(name = "users-subgraph", attributeNodes = {
                @NamedAttributeNode("email"),
                @NamedAttributeNode("fullName"),
                @NamedAttributeNode("phone"),
                @NamedAttributeNode("course"),
                @NamedAttributeNode("major"),

        })
)

public class UsersEvent implements Serializable {
    @EmbeddedId
    private UsersEventId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usersId")
    @JoinColumn(name = "usersId")
    private Users users;
    private Boolean isJoin;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "eventId")
    private Event event;
    private Float score;
    private Date createdAt;
}
