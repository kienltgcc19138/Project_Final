package vn.kien.event.eventbe.response;

import java.io.Serializable;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 13:28
 */
public class MessageResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String message;

    public MessageResponse(String message) {
        super();
        this.message = message;
    }

    public MessageResponse() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageResponse [message=" + message + "]";
    }


}