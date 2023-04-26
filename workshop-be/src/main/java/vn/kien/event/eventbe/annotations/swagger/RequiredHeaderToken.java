package vn.kien.event.eventbe.annotations.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import vn.kien.event.eventbe.common.Constants;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Parameter(
        in = ParameterIn.HEADER,
        name = Constants.HEADER_TOKEN_NAME,
        required = true,
        schema = @Schema(
                type = "string"
        )
)
public @interface RequiredHeaderToken {

}