package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.kien.event.eventbe.base.BasePageAndSortRequest;

/**
 * Le-Hong-Quan
 * Date: 27/02/2023
 * Time: 21:03
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchMajorRequest extends BasePageAndSortRequest {
    private String keyword;
}
