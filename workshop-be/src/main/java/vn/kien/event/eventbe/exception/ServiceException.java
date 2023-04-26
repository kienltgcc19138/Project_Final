package vn.kien.event.eventbe.exception;

import vn.kien.event.eventbe.base.BaseException;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:48
 */
public class ServiceException extends BaseException {
    public ServiceException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    public static BaseException build(String errorCode, String msgDetail) {
        ServiceException exp = new ServiceException(errorCode);
        exp.setMessage(msgDetail);
        return exp;
    }
}
