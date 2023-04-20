package vn.kien.event.eventbe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 10:10
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersEventId implements Serializable {
    private String usersId;
    private Long eventId;
}
