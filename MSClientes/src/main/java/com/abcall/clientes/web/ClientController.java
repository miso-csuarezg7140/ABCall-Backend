package com.abcall.clientes.web;

import com.abcall.clientes.domain.dto.request.ClientAuthRequest;
import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.domain.service.IClientService;
import com.abcall.clientes.util.ApiUtils;
import com.abcall.clientes.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;
    private final ApiUtils apiUtils;

    @Operation(summary = "Permite autenticar un cliente mediante número de documento y contraseña.")
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
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @PostMapping("/autenticar")
    public ResponseEntity<ResponseServiceDto> authenticate(
            @Valid @RequestBody ClientAuthRequest clientAuthRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = clientService.authenticate(
                clientAuthRequest.getDocumentNumber(), clientAuthRequest.getPassword());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Permite registrar un cliente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro exitoso",
                    content = @Content(examples = {
                            @ExampleObject(name = "Solicitud incorrecta",
                                    summary = "Ejemplo de respuesta exitosa",
                                    value = "{\"statusCode\":201,\"statusDescription\":\"Registro exitoso.\",\"data\":{}}")
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
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ResponseServiceDto> register(
            @Valid @RequestBody ClientRegisterRequest clientRegisterRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = clientService.register(clientRegisterRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Permite validar si un usuario existe y pertenece a un cliente.")
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
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @GetMapping("/validarUsuario")
    public ResponseEntity<ResponseServiceDto> validateUser(
            @Valid
            @Parameter(description = "Documento del cliente", example = "1010258471")
            @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_CLIENTE, message = "El parámetro numeroDocumentoCliente no cumple las validaciones.")
            @NotBlank(message = "El parámetro numeroDocumentoCliente no cumple las validaciones.")
            @RequestParam(required = false) String numeroDocumentoCliente,

            @Valid
            @Parameter(description = "Tipo de documento del usuario", example = "1")
            @Pattern(regexp = Constants.VALIDACION_TIPO_DOCUMENTO, message = "El parámetro tipoDocumentoUsuario no cumple las validaciones.")
            @NotBlank(message = "El parámetro tipoDocumentoUsuario no cumple las validaciones.")
            @RequestParam(required = false) String tipoDocumentoUsuario,

            @Valid
            @Parameter(description = "Número de documento del usuario", example = "1010478914")
            @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_USUARIO, message = "El parámetro numeroDocumentoUsuario no cumple las validaciones.")
            @NotBlank(message = "El parámetro numeroDocumentoUsuario no cumple las validaciones.")
            @RequestParam(required = false) String numeroDocumentoUsuario) {

        ResponseServiceDto response = clientService.validateUser(
                numeroDocumentoCliente, tipoDocumentoUsuario, numeroDocumentoUsuario);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Permite realizar la consulta de la lista de clientes.")
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
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @GetMapping("/listar")
    public ResponseEntity<ResponseServiceDto> list() {
        ResponseServiceDto response = clientService.list();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Método que retorna la lista de tipos de documento.")
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
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(examples = {
                            @ExampleObject(name = "Credenciales inválidas",
                                    summary = "Ejemplo de respuesta de error",
                                    value = "{\"statusCode\":500,\"statusDescription\":\"Error interno del servidor.\",\"data\":{}}")
                    },mediaType = "application/json", schema = @Schema(implementation = ResponseServiceDto.class)))
    })
    @GetMapping("/tiposDocumento")
    public ResponseEntity<ResponseServiceDto> documentList() {
        ResponseServiceDto response = clientService.documentTypeList();
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