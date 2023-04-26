package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 10:37
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private String objectId;
    private String objectType;
    private String name;
    private String description;
    private String createdAt;
    private EventResponse eventResponse;

}
