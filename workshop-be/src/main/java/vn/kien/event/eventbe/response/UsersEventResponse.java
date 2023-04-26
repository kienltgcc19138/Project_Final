package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 01/03/2023
 * Time: 00:17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersEventResponse {
    private String usersId;
    private Long eventId;
    private UsersResponse usersResponse;
    private Float score;

}
