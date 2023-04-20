package vn.kien.event.eventbe.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.kien.event.eventbe.annotations.swagger.RequiredHeaderToken;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.model.wrapper.WrapResponse;
import vn.kien.event.eventbe.services.FileDataService;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/file-data")
@Tag(name = "File Data", description = "File Data API")
@RequiredArgsConstructor
public class FileDataController {
    private final ExecutorService executorService;
    private final FileDataService fileDataService;

    @RequiredHeaderToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Object> createEvent(@RequestParam("type") String type,
                                                 @RequestParam("file") MultipartFile file,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal Users users) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(fileDataService.createFileData(file, type, users.getUsersId())), executorService);
    }

    @RequiredHeaderToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CompletableFuture<Object> getEvent(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> WrapResponse.ok(fileDataService.getFileData(id)), executorService);
    }

}
