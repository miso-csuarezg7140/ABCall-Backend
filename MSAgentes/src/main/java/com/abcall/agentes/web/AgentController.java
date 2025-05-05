package com.abcall.agentes.web;

import com.abcall.agentes.domain.dto.request.AgentAuthRequest;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.domain.service.IAgentService;
import com.abcall.agentes.util.ApiUtils;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AgentController {

    private final IAgentService agentService;
    private final ApiUtils apiUtils;

    @Operation(summary = "Método que permite realizar el ingreso a la plataforma de los agentes registrados.")
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
    public ResponseEntity<ResponseServiceDto> authenticate(@Valid @RequestBody AgentAuthRequest agentAuthRequest,
                                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = agentService.authenticate(agentAuthRequest.getDocumentType(),
                agentAuthRequest.getDocumentNumber(), agentAuthRequest.getPassword());
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
        ResponseServiceDto response = agentService.documentTypeList();
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
