package vn.kien.event.eventbe.model.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:31
 */
@Getter
@Setter
public class WrapResponse<T> {

    private boolean success;
    private T data;

    private String errorCode;

    private List<String> message;

    private Boolean violations;

    private String refCode;

    private String statusCode;

    public static <T> WrapResponse<T> error(String msg) {
        WrapResponse baseResponse = new WrapResponse();
        baseResponse.setData(null);
        baseResponse.setSuccess(false);
        baseResponse.setStatusCode("500");
        baseResponse.setMessage(Collections.singletonList(msg));
        return baseResponse;
    }

    public static <T> WrapResponse<T> ok(T data) {
        WrapResponse baseResponse = new WrapResponse();
        baseResponse.setData(data);
        baseResponse.setSuccess(true);
        baseResponse.setStatusCode("200");
        return baseResponse;
    }

    public static <T> CompletableFuture<WrapResponse<T>> okFuture(CompletableFuture<T> data) {
        return data.thenApply(rs -> WrapResponse.ok(rs));
    }

    @SneakyThrows
    public static <T> Class<? extends WrapResponse> getClass(Class<T> clazz) {
        return WrapResponse.ok(clazz.newInstance()).getClass();
    }
}
