package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.annotations.swagger.RequiredHeaderToken;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.request.SearchNotificationRequest;
import vn.kien.event.eventbe.services.NotificationService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/notification")
@Tag(name = "Notification", description = "Notification API")
@RequiredArgsConstructor
public class NotificationController {
    private final ExecutorService executorService;
    private final NotificationService notificationService;

    @RequiredHeaderToken
    @RequestMapping(value = "/{usersId}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchNotification(@PathVariable("usersId") String usersId, @RequestBody SearchNotificationRequest searchNotificationRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(notificationService.searchNotification(usersId, searchNotificationRequest)), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{usersId}/{notificationId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> readNotification(@PathVariable("usersId") String usersId, @PathVariable("notificationId") Long notificationId) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(notificationService.readNotification(usersId, notificationId)), executorService);
    }
}
