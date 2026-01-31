package lk.icbt.ovr.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lk.icbt.ovr.dto.LoginRequestDTO;
import lk.icbt.ovr.dto.LoginResponseDTO;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.exception.ApiError;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final lk.icbt.ovr.service.AuthService authService;

    public AuthController(lk.icbt.ovr.service.AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO requestDto, HttpServletRequest req) {
        try {
            LoginResponseDTO response = authService.login(requestDto);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {

            // Return 401 for login failure (as your requirement)
            ApiError err = new ApiError(
                    LocalDateTime.now(),
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized",
                    ex.getMessage(),
                    req.getRequestURI()
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }
    }
}