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
            @ApiResponse(responseCode = "200", description = "Cliente autenticado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseServiceDto.class)))}
    )
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseServiceDto> authenticateClient(
            @Valid @RequestBody ClientAuthRequest clientAuthRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = clientService.authenticateClient(
                clientAuthRequest.getUsername(), clientAuthRequest.getPassword());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseServiceDto> registerClient(
            @Valid @RequestBody ClientRegisterRequest clientRegisterRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = clientService.registerClient(clientRegisterRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/validateUserClient")
    public ResponseEntity<ResponseServiceDto> validateUserClient(
            @Valid
            @Parameter(description = "Documento del cliente", example = "1010258471")
            @Pattern(regexp = Constants.VALIDACION_NUMERICO)
            @NotBlank(message = "El parámetro documentClient no cumple las validaciones.")
            @RequestParam(required = false) String documentClient,

            @Valid
            @Parameter(description = "Tipo de documento del usuario", example = "CC")
            @Pattern(regexp = Constants.VALIDACION_TIPO_DOCUMENTO, message = "El parámetro documentTypeUser no cumple las validaciones.")
            @NotBlank(message = "El parámetro documentTypeUser no cumple las validaciones.")
            @RequestParam(required = false) String documentTypeUser,

            @Valid
            @Parameter(description = "Número de documento del usuario", example = "1010478914")
            @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El parámetro documentUser no cumple las validaciones.")
            @NotBlank(message = "El parámetro documentUser no cumple las validaciones.")
            @RequestParam(required = false) String documentUser) {

        ResponseServiceDto response = clientService.validateUserClient(
                documentClient, documentTypeUser, documentUser);
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