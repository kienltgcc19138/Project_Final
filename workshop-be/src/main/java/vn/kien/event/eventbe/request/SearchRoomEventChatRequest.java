package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

import javax.validation.constraints.NotEmpty;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 20:04
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRoomEventChatRequest extends BasePageAndSortRequest {
    @NotEmpty(message = "RoomId is required")
    private Long roomId;
}
