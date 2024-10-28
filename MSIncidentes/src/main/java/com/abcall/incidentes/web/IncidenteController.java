package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.domain.dto.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.Map;

import static com.abcall.incidentes.util.ApiUtils.buildResponseServiceDto;
import static com.abcall.incidentes.util.ApiUtils.requestHandleErrors;
import static com.abcall.incidentes.util.Constant.CODIGO_200;
import static com.abcall.incidentes.util.Constant.CODIGO_201;
import static com.abcall.incidentes.util.Constant.CODIGO_206;
import static com.abcall.incidentes.util.Constant.CODIGO_400;
import static com.abcall.incidentes.util.Constant.MENSAJE_400;
import static com.abcall.incidentes.util.Constant.VALIDACION_NUMERICO;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class IncidenteController {

    private final IncidenteService incidentesService;

    @Operation(summary = "Obtiene la lista de incidentes de un usuario dado.")
    @GetMapping("/consultar")
    public ResponseEntity<ResponseServiceDto> consultar(
            @Parameter(description = "Tipo de documento del usuario", example = "CC")
            @NotBlank(message = "El parámetro tipoDocUsuario no puede ser nulo.")
            @RequestParam(value = "tipoDocUsuario") String tipoDocUsuario,

            @Parameter(description = "Tipo de documento del usuario", example = "1010258471")
            @Pattern(regexp = VALIDACION_NUMERICO, message = "El parámetro numeroDocUsuario debe ser numérico")
            @NotBlank(message = "El parámetro numeroDocUsuario no puede ser nulo.")
            @RequestParam(value = "numeroDocUsuario") String numeroDocUsuarioStr) {
        ResponseServiceDto response = incidentesService.consultar(tipoDocUsuario, numeroDocUsuarioStr);
        return switch (response.getStatusCode()) {
            case CODIGO_200 -> ResponseEntity.ok(response);
            case CODIGO_206 -> ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
            case CODIGO_400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            case null, default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
    }

    @Operation(summary = "Método que permite la creación de un incidente nuevo.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseServiceDto> crear(@Valid @RequestBody IncidenteDto incidenteDto,
                                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = requestHandleErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    buildResponseServiceDto(CODIGO_400, MENSAJE_400, errors));
        } else {
            ResponseServiceDto response = incidentesService.crear(incidenteDto);
            return switch (response.getStatusCode()) {
                case CODIGO_201 -> ResponseEntity.status(HttpStatus.CREATED).body(response);
                case CODIGO_206 -> ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
                case CODIGO_400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                case null, default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            };
        }
    }

    @Operation(summary = "Permite monitorear el estado del MS en el despliegue.")
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }
}
