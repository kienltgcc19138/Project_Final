package vn.kien.event.eventbe.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:34
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7600966520679662596L;
    @NotBlank(message = "Email is not empty")
    private String email;
    @NotBlank(message = "Password is not empty")
    private String password;

    @NotBlank(message = "Fullname is not empty")
    private String fullName;
    private String phone;





}
