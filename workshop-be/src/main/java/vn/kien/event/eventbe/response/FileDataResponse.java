package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 04/03/2023
 * Time: 21:57
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDataResponse {
    private Long fileDataId;
    private String fileDataName;
    private String link;
    private String type;

}
