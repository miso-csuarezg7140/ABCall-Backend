package com.abcall.clientes.web;

import com.abcall.clientes.domain.dto.ClienteDto;
import com.abcall.clientes.domain.dto.ResponseServiceDto;
import com.abcall.clientes.domain.service.ClienteService;
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

import static com.abcall.clientes.util.ApiUtils.buildResponseServiceDto;
import static com.abcall.clientes.util.ApiUtils.requestHandleErrors;
import static com.abcall.clientes.util.Constant.CODIGO_200;
import static com.abcall.clientes.util.Constant.CODIGO_201;
import static com.abcall.clientes.util.Constant.CODIGO_206;
import static com.abcall.clientes.util.Constant.CODIGO_400;
import static com.abcall.clientes.util.Constant.MENSAJE_400;
import static com.abcall.clientes.util.Constant.VALIDACION_CONTRASENA;
import static com.abcall.clientes.util.Constant.VALIDACION_NUMERICO;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Método que permite el registro de un cliente nuevo.")
    @PostMapping("/registrar")
    public ResponseEntity<ResponseServiceDto> registrar(@Valid @RequestBody ClienteDto clienteDto,
                                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = requestHandleErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    buildResponseServiceDto(CODIGO_400, MENSAJE_400, errors));
        } else {
            ResponseServiceDto response = clienteService.registrar(clienteDto);

            return switch (response.getStatusCode()) {
                case CODIGO_201 -> ResponseEntity.status(HttpStatus.CREATED).body(response);
                case CODIGO_206 -> ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
                case CODIGO_400 -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                case null, default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            };
        }
    }

    @Operation(summary = "Método que permite realizar el ingreso a la plataforma de los clientes registrados.")
    @GetMapping("/login")
    public ResponseEntity<ResponseServiceDto> login(
            @Parameter(description = "Tipo de documento del cliente", example = "CC")
            @Pattern(regexp = VALIDACION_NUMERICO, message = "El parámetro numDocumentoCliente no cumple con el formato requerido.")
            @NotBlank(message = "El parámetro numDocumentoCliente no puede ser nulo.")
            @RequestParam(value = "numDocumentoCliente") String numDocumentoCliente,

            @Parameter(description = "Contraseña del cliente", example = "Contrasena123!")
            @Pattern(regexp = VALIDACION_CONTRASENA, message = "La contrasena no cumple con el formato requerido.")
            @NotBlank(message = "El parámetro contrasena no puede ser nulo.")
            @RequestParam(value = "contrasena") String contrasena) {

        ResponseServiceDto response = clienteService.login(numDocumentoCliente, contrasena);

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
