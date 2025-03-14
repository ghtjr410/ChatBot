package com.chatbot.api.auth.api;

import com.chatbot.api.auth.api.dto.request.LoginRequest;
import com.chatbot.api.auth.api.dto.request.SignupRequest;
import com.chatbot.api.auth.application.AuthService;
import com.chatbot.api.auth.application.dto.TokenInfoData;
import com.chatbot.api.auth.domain.UserRoleType;
import com.chatbot.api.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/signup/user")
    public ApiResponse<?> signupUser(
            @RequestBody SignupRequest request
    ) {
        authService.signup(request, UserRoleType.ROLE_USER);

        return ApiResponse.success();
    }

    @PostMapping("/signup/admin")
    public ApiResponse<?> signupAdmin(
            @RequestBody SignupRequest request
    ) {
        authService.signup(request, UserRoleType.ROLE_ADMIN);

        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<TokenInfoData> login(
            @RequestBody LoginRequest request
    ) {
        TokenInfoData data = authService.login(request);
        return ApiResponse.success(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminTest() {
        return "어드민통과";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userTest() {
        return "유저통과";
    }

}
