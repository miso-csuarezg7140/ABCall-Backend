package com.abcall.auth.web;

import com.abcall.auth.domain.dto.request.LoginRequest;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.domain.service.IAuthService;
import com.abcall.auth.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;
    private final ApiUtils apiUtils;

    @Operation(summary = "Autentica a un usuario y devuelve un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseServiceDto> login(@RequestBody LoginRequest loginRequest,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = authService.authenticateUser(
                loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getUserType());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Permite monitorear el estado del MS en el despliegue.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio disponible",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "pong")))
    })
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }
}
