package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

/**
 * Le-Hong-Quan
 * Date: 13/03/2023
 * Time: 20:55
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchUserChatRequest extends BasePageAndSortRequest {

}
