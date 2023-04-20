package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 23:42
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchNotificationRequest extends BasePageAndSortRequest {

}
