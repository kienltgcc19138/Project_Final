package vn.kien.event.eventbe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 20:31
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {
    String message;
}
