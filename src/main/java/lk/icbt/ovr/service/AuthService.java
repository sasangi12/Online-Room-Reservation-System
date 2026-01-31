package lk.icbt.ovr.service;

import lk.icbt.ovr.dto.LoginRequestDTO;
import lk.icbt.ovr.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}