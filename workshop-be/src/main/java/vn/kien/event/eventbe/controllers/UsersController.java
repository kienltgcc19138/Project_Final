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
import vn.kien.event.eventbe.request.*;
import vn.kien.event.eventbe.services.UsersService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 21:13
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Users API")
@RequiredArgsConstructor
public class UsersController {
    private final ExecutorService executorService;
    private final UsersService usersService;


    @RequiredHeaderToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<WrapResponse<Object>> createUsers(@Valid @RequestBody CreateUserRequest createUserRequest,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.createUser(createUserRequest, users.getUsersId(), false)), executorService);
    }

    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public CompletableFuture<WrapResponse<Object>> updateUsers(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.updateUser(updateUserRequest)), executorService);
    }

    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}/update-password", method = RequestMethod.PUT)
    public CompletableFuture<Object> updatePassword(@PathVariable("id") String id,
                                                    @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return CompletableFuture.supplyAsync(() -> usersService.updatePassword(id, updatePasswordRequest));
    }

    @RequiredHeaderToken
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/update-password-user", method = RequestMethod.PUT)
    public CompletableFuture<Object> updatePasswordUser(@RequestBody UpdatePasswordUserRequest updatePasswordUserRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.updatePasswordUser(updatePasswordUserRequest)));
    }


    @RequiredHeaderToken
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public CompletableFuture<WrapResponse<Object>> createAdmin(@Valid @RequestBody CreateUserRequest createUserRequest,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.createUser(createUserRequest, users.getUsersId(), true)), executorService);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequiredHeaderToken
    @RequestMapping(value = "/search-user", method = RequestMethod.POST)
    public CompletableFuture<WrapResponse<Object>> getAllUsers(@RequestBody SearchUserRequest searchUserRequest) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.getAllUsers(searchUserRequest)), executorService);
    }

    @RequiredHeaderToken
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CompletableFuture<WrapResponse<Object>> getUserById(@PathVariable("id") String id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.getUserById(id)), executorService);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequiredHeaderToken
    @RequestMapping(value = "/{usersId}", method = RequestMethod.DELETE)
    public CompletableFuture<WrapResponse<Object>> deleteUser(@PathVariable("usersId") String id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(usersService.deleteUser(id)), executorService);
    }
}
