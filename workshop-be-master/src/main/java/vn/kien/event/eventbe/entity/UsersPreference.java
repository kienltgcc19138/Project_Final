package vn.kien.event.eventbe.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 14:26
 */
@Entity
@Table(name = "users_preference")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersPreference {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String usersPreferenceId;
    private String usersId;
    private String code;
    private String type;
    private Date createdAt;
}
