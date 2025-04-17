package com.example.bookshop.controller;


import com.example.bookshop.dto.PasswordChangeRequest;
import com.example.bookshop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/profile/security/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) {


        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Пароли не совпадают");
            return ResponseEntity.badRequest().body(error);
        }

        boolean result = userService.changePassword(userDetails.getUsername(),
                request.getOldPassword(),
                request.getNewPassword());

        if (result) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Пароль успешно изменён!");
            return ResponseEntity.ok(response);

        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "Старый пароль введён неверно");
        return ResponseEntity.badRequest().body(error);
    }
}
