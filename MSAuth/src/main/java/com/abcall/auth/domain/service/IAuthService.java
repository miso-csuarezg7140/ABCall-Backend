package com.abcall.auth.domain.service;

import com.abcall.auth.domain.dto.response.ResponseServiceDto;

public interface IAuthService {

    ResponseServiceDto authenticateUser(String username, String password, String userType);
}
