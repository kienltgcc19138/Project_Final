package vn.kien.event.eventbe.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 10:39
 */
@Getter
@Setter
public class CreateTicketRequest {
    @NotEmpty(message = "Title is not empty")
    private String name;
    @NotEmpty(message = "Description is not empty")
    private String description;
    private Long eventId;
}
