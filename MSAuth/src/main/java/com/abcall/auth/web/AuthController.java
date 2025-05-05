package com.abcall.auth.web;

import com.abcall.auth.domain.dto.request.LoginRequest;
import com.abcall.auth.domain.dto.request.TokenRefreshRequest;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.domain.service.IAuthService;
import com.abcall.auth.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
@RequestMapping
public class AuthController {

    private final IAuthService authService;
    private final ApiUtils apiUtils;

    @Operation(summary = "Autentica a un usuario y devuelve un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(examples = {
                            @ExampleObject(name = "Solicitud incorrecta",
                                    summary = "Ejemplo de respuesta exitosa",
                                    value = "{\"statusCode\":200,\"statusDescription\":\"Consulta exitosa.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                    content = @Content(examples = {
                            @ExampleObject(name = "Solicitud incorrecta",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":400,\"statusDescription\":\"Valores nulos o incorrectos en los parámetros de entrada.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":401,\"statusDescription\":\"Autenticación requerida.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseServiceDto> login(@Valid @RequestBody LoginRequest loginRequest,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = authService.authenticateUser(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Renueva el token JWT de un usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(examples = {
                            @ExampleObject(name = "Solicitud incorrecta",
                                    summary = "Ejemplo de respuesta exitosa",
                                    value = "{\"statusCode\":200,\"statusDescription\":\"Consulta exitosa.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                    content = @Content(examples = {
                            @ExampleObject(name = "Solicitud incorrecta",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":400,\"statusDescription\":\"Valores nulos o incorrectos en los parámetros de entrada.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":401,\"statusDescription\":\"Autenticación requerida.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    }, mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @PostMapping("/refrescar")
    public ResponseEntity<ResponseServiceDto> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = authService.refreshToken(tokenRefreshRequest);
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
