package com.abcall.agentes.web;

import com.abcall.agentes.domain.dto.ResponseServiceDto;
import com.abcall.agentes.domain.service.AgenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.abcall.agentes.util.Constant.CODIGO_200;
import static com.abcall.agentes.util.Constant.CODIGO_206;
import static com.abcall.agentes.util.Constant.CODIGO_400;
import static com.abcall.agentes.util.Constant.VALIDACION_CONTRASENA;
import static com.abcall.agentes.util.Constant.VALIDACION_NUMERICO;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService agenteService;

    @Operation(summary = "Método que permite realizar el ingreso a la plataforma de los agentes registrados.")
    @GetMapping("/login")
    public ResponseEntity<ResponseServiceDto> login(
            @Parameter(description = "Tipo de documento del agente", example = "CC")
            @NotBlank(message = "El parámetro tipoDocAgente no puede ser nulo.")
            @RequestParam(value = "tipoDocumentoAgente") String tipoDocumentoAgente,

            @Parameter(description = "Número de documento del agente", example = "1010259487")
            @Pattern(regexp = VALIDACION_NUMERICO, message = "El parámetro numDocumentoAgente no cumple con el formato requerido.")
            @NotBlank(message = "El parámetro numDocumentoAgente no puede ser nulo.")
            @RequestParam(value = "numDocumentoAgente") String numDocumentoAgente,

            @Parameter(description = "Contraseña del cliente", example = "Contrasena123!")
            @Pattern(regexp = VALIDACION_CONTRASENA, message = "La contrasena no cumple con el formato requerido.")
            @NotBlank(message = "El parámetro contrasena no puede ser nulo.")
            @RequestParam(value = "contrasena") String contrasena) {

        ResponseServiceDto response = agenteService.login(tipoDocumentoAgente, numDocumentoAgente, contrasena);

        return switch (response.getStatusCode()) {
            case CODIGO_200 -> ResponseEntity.ok(response);
            case CODIGO_206 -> ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
            case CODIGO_400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            case null, default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @Operation(summary = "Permite monitorear el estado del MS en el despliegue.")
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }
}
