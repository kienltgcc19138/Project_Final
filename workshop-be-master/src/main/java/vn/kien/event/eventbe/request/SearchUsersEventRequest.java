package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

/**
 * Le-Hong-Quan
 * Date: 05/03/2023
 * Time: 02:08
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchUsersEventRequest extends BasePageAndSortRequest {
//    private String keyword;
    private String usersId;
    private Long eventId;

}
