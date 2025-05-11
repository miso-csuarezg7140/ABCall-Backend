package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.request.ConsultIncidentRequest;
import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IIncidentService;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.Constants;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class IncidentController {

    private final IIncidentService incidentesService;
    private final ApiUtils apiUtils;

    @Operation(summary = "Obtiene la lista de incidentes de un usuario dado.")
    @PostMapping("/consultar")
    public ResponseEntity<ResponseServiceDto> consultar(
            @Valid @RequestBody ConsultIncidentRequest consultIncidentRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        ResponseServiceDto response = incidentesService.consultar(consultIncidentRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Método que permite la creación de un incidente nuevo.")
    @PostMapping("/crear")
    public ResponseEntity<ResponseServiceDto> crear(@Valid @RequestBody CreateIncidentRequest createIncidentRequest,
                                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } else {
            ResponseServiceDto response = incidentesService.crear(createIncidentRequest);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    @Operation(summary = "Método que permite la consulta del detalle de un incidente.")
    @GetMapping("/consultarDetalle")
    public ResponseEntity<ResponseServiceDto> consultarDetalle(
            @Parameter(description = "Id de la incidencia a consultar detalle", example = "1")
            @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El parámetro idIncidente debe ser numérico")
            @NotBlank(message = "El parámetro idIncidente no puede ser nulo")
            @RequestParam(required = false) String idIncidente) {

        ResponseServiceDto response = incidentesService.consultarDetalle(idIncidente);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Método que permite la actualización de un incidente.")
    @PutMapping("/actualizar")
    public ResponseEntity<ResponseServiceDto> actualizar(
            @Valid @RequestBody UpdateIncidentRequest updateIncidentRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseServiceDto response = apiUtils.badRequestResponse(bindingResult);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } else {
            ResponseServiceDto response = incidentesService.actualizar(updateIncidentRequest);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    @Operation(summary = "Permite monitorear el estado del MS en el despliegue.")
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("pong");
    }
}
