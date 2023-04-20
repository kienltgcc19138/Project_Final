package vn.kien.event.eventbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.kien.event.eventbe.base.BaseException;
import vn.kien.event.eventbe.base.BaseObjectLoggable;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;

import java.util.Arrays;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 13:32
 */
@RestControllerAdvice
public class GlobalWebExceptionHandler extends BaseObjectLoggable {
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleBaseExceptions(BaseException baseException) {
        baseException.printStackTrace();
        WrapResponse baseResponse = new WrapResponse();
        baseResponse.setSuccess(false);
        baseResponse.setStatusCode("422");
        baseResponse.setErrorCode(baseException.getErrorCode());
        baseResponse.setMessage(Arrays.asList(baseException.getMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body(baseResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        WrapResponse baseResponse = new WrapResponse();
        baseResponse.setSuccess(false);
        baseResponse.setStatusCode("403");
        baseResponse.setErrorCode("FORBIDDEN");

        baseResponse.setMessage(Arrays.asList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                .body(baseResponse);
    }
}
