package vn.kien.event.eventbe.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 20:01
 */
@Getter
@Setter
public class RoomEventChatResponse {
   private Long roomId;
   private EventResponse eventResponse;
   private UsersResponse usersResponse;
}
