package com.abcall.auth.domain.service;

import com.abcall.auth.domain.dto.request.LoginRequest;
import com.abcall.auth.domain.dto.request.TokenRefreshRequest;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;

public interface IAuthService {

    ResponseServiceDto authenticateUser(LoginRequest loginRequest);

    ResponseServiceDto refreshToken(TokenRefreshRequest tokenRefreshRequest);
}
