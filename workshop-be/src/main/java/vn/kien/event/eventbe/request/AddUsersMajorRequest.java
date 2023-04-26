package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Le-Hong-Quan
 * Date: 06/03/2023
 * Time: 19:03
 */
@Getter
@Setter
public class AddUsersMajorRequest {
    private List<String> usersIds;
}
