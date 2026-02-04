package lk.icbt.ovr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lk.icbt.ovr.dto.LoginRequestDTO;
import lk.icbt.ovr.dto.LoginResponseDTO;
import lk.icbt.ovr.entity.User;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.repository.UserRepository;

public class AuthServiceImplTest {

    private UserRepository userRepository;
    private AuthServiceImpl authService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        authService = new AuthServiceImpl(userRepository);
    }

    @Test
    void login_shouldSucceed_whenCredentialsCorrect() {
        User u = new User();
        u.setUsername("admin");
        u.setPassword("admin123");
        u.setRole("ADMIN");
        u.setFullName("System Admin");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(u));

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("admin");
        req.setPassword("admin123");

        LoginResponseDTO resp = authService.login(req);

        assertTrue(resp.isSuccess());
        assertEquals("admin", resp.getUsername());
        assertEquals("ADMIN", resp.getRole());
    }

    @Test
    void login_shouldFail_whenUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("unknown");
        req.setPassword("x");

        assertThrows(BadRequestException.class, () -> authService.login(req));
    }

    @Test
    void login_shouldFail_whenPasswordWrong() {
        User u = new User();
        u.setUsername("admin");
        u.setPassword("admin123");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(u));

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("admin");
        req.setPassword("wrong");

        assertThrows(BadRequestException.class, () -> authService.login(req));
    }
}
