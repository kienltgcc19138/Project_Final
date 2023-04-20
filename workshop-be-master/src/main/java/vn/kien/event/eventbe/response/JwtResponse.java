package vn.kien.event.eventbe.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.kien.event.eventbe.entity.Users;

import java.io.Serializable;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 13:18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

//    @JsonIgnore
    private UsersResponse users;

    private String tokenType;

    private String accessToken;

//    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken, Users user) {
        this.accessToken = accessToken;
        this.users = new UsersResponse(user);
        tokenType = "Bearer";
//        this.refreshToken = refreshToken;
    }

}