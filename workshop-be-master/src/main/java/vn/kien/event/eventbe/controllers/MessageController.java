package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.kien.event.eventbe.annotations.swagger.RequiredHeaderToken;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.request.CreateMessageRequest;
import vn.kien.event.eventbe.request.SearchMessageRequest;
import vn.kien.event.eventbe.services.MessageService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Chat", description = "Chat API")
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MessageController {
    private final ExecutorService executorService;

    private final MessageService messageService;


    @RequestMapping(value = "/user-send", method = RequestMethod.POST)
    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> sendMessage(@RequestBody CreateMessageRequest createMessageRequest, @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(messageService.sendUserMessage(users.getUsersId(), createMessageRequest)));
    }

    @RequestMapping(value = "/admin-send/{usersId}", method = RequestMethod.POST)
    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> sendAdminMessage(@PathVariable("usersId") String usersId, @RequestBody CreateMessageRequest createMessageRequest, @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(messageService.sendAdminMessage(usersId, createMessageRequest, users.getUsersId())));
    }

    @RequestMapping(value = "/search/{usersId}", method = RequestMethod.POST)
    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchMessages(@PathVariable("usersId") String usersIds, @RequestBody SearchMessageRequest searchMessageRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(messageService.searchMessages(usersIds, searchMessageRequest)), executorService);
    }

    @RequestMapping(value = "/search-user-chat-with-admin", method = RequestMethod.POST)
    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Object> searchUserAdmin(@RequestBody SearchMessageRequest searchMessageRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(messageService.searchUserChatWithAdmin(searchMessageRequest)), executorService);
    }

}