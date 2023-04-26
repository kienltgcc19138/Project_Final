package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 02:00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersEventDetailResponse {
    private UsersResponse usersResponse;
    private EventResponse eventResponse;
    private Float score;
}
