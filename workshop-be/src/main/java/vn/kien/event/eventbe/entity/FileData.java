package vn.kien.event.eventbe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_data_generator")
    private Long fileDataId;
    private String fileName;
    private String type;
    private String externalId;
    private String link;
    private String createdBy;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
}
