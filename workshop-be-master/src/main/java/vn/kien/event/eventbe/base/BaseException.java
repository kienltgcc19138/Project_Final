package vn.kien.event.eventbe.base;

import lombok.Getter;
import lombok.Setter;
import vn.kien.event.eventbe.utils.MessageSourceUtils;


@Getter
@Setter
public class BaseException extends RuntimeException {

    protected String errorCode;

    protected String message;

    public BaseException() {
    }

    protected BaseException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.message = MessageSourceUtils.getMessage(errorCode, args);
    }
}

