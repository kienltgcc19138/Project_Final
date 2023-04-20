package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Le-Hong-Quan
 * Date: 06/03/2023
 * Time: 19:17
 */
@Getter
@Setter
public class AddUsersCourseRequest {
    private List<String> usersIds;
}
