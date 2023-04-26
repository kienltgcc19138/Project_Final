package vn.kien.event.eventbe.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
        name = "event-joined",
        includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode(value = "users", subgraph = "users-subgraph"),
                @NamedAttributeNode(value = "ticket"),
                @NamedAttributeNode(value = "files"),
        },
        subgraphs = @NamedSubgraph(name = "users-subgraph", attributeNodes = {
                @NamedAttributeNode("users"),
        })

)

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_generator")
    private Long eventId;
    private String name;
    private String topic;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;
    private Date timeStart;
    private Date timeEnd;
    private String status;
    private Float score;
    private String createdBy;
    private Date createdAt;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private Set<UsersEvent> users;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> ticket;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FileData> files;

}
