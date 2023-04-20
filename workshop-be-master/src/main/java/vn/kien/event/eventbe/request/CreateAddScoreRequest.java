package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 02:06
 */
@Getter
@Setter
public class CreateAddScoreRequest {
    private String usersId;
    private Long eventId;
    private Float score;

    private Boolean isJoin;
}
