package lk.icbt.ovr.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.icbt.ovr.dto.LoginRequestDTO;
import lk.icbt.ovr.dto.LoginResponseDTO;
import lk.icbt.ovr.entity.User;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        String username = request.getUsername().trim();
        String password = request.getPassword().trim();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        // Basic password check (plain text)
        if (!user.getPassword().equals(password)) {
            throw new BadRequestException("Invalid username or password");
        }

        LoginResponseDTO resp = new LoginResponseDTO();
        resp.setSuccess(true);
        resp.setMessage("Login successful");
        resp.setUsername(user.getUsername());
        resp.setRole(user.getRole());
        resp.setFullName(user.getFullName());

        return resp;
    }
}