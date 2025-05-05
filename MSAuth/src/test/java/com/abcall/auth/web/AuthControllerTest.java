package com.abcall.auth.web;

import com.abcall.auth.domain.dto.request.LoginRequest;
import com.abcall.auth.domain.dto.request.TokenRefreshRequest;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.domain.service.IAuthService;
import com.abcall.auth.util.ApiUtils;
import com.abcall.auth.util.enums.HttpResponseCodes;
import com.abcall.auth.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private IAuthService authService;

    @Mock
    private ApiUtils apiUtils;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginShouldReturnBadRequestWhenBindingResultHasErrors() {
        FieldError fieldError = new FieldError("objectName", "field", "error");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        ResponseServiceDto responseDto = new ResponseServiceDto(HttpResponseCodes.BAD_REQUEST.getCode(),
                HttpResponseMessages.BAD_REQUEST.getMessage(), "error");
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseDto);

        ResponseEntity<ResponseServiceDto> response = authController.login(new LoginRequest(), bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void loginShouldReturnResponseFromAuthServiceWhenBindingResultHasNoErrors() {
        LoginRequest loginRequest = new LoginRequest("1", "123456",
                "password", "userType");
        ResponseServiceDto responseDto = new ResponseServiceDto(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), "data");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authService.authenticateUser(loginRequest))
                .thenReturn(responseDto);

        ResponseEntity<ResponseServiceDto> response = authController.login(loginRequest, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void refreshTokenShouldReturnBadRequestWhenBindingResultHasErrors() {
        TokenRefreshRequest request = new TokenRefreshRequest();
        FieldError fieldError = new FieldError("objectName", "field", "error");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        ResponseServiceDto responseDto = new ResponseServiceDto(HttpResponseCodes.BAD_REQUEST.getCode(),
                HttpResponseMessages.BAD_REQUEST.getMessage(), "error");
        when(apiUtils.badRequestResponse(bindingResult)).thenReturn(responseDto);

        ResponseEntity<ResponseServiceDto> response = authController.refreshToken(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void refreshTokenShouldReturnResponseFromAuthServiceWhenBindingResultHasNoErrors() {
        TokenRefreshRequest request = new TokenRefreshRequest();
        ResponseServiceDto responseDto = new ResponseServiceDto(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), "data");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authService.refreshToken(request)).thenReturn(responseDto);

        ResponseEntity<ResponseServiceDto> response = authController.refreshToken(request, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void pingShouldReturnPong() {
        ResponseEntity<String> response = authController.ping();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }
}