package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 13:56
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VerifyEmailResponse {
    private String messages;
    private String token;
}
