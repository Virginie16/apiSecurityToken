package curium.api.services;

import curium.api.dto.LoginDto;

public interface AuthService {
	String login(LoginDto loginDto);
}
