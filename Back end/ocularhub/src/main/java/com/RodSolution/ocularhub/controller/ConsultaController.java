package com.RodSolution.ocularhub.controller;

import com.RodSolution.ocularhub.model.dto.ConsultaRequestDto;
import com.RodSolution.ocularhub.model.dto.ConsultaResponseDto;
import com.RodSolution.ocularhub.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaResponseDto> agendar(@RequestBody @Valid ConsultaRequestDto dto) {
        ConsultaResponseDto consulta = consultaService.agendar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(consulta);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDto>> listarTodas() {
        List<ConsultaResponseDto> consultas = consultaService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(consultas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDto> buscarPorId(@PathVariable(value = "id") Long id) {
        ConsultaResponseDto consulta = consultaService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(consulta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ConsultaResponseDto> cancelar(
            @PathVariable(value = "id") Long id,
            @RequestParam(name = "motivo") String motivo) {

        ConsultaResponseDto consultaCancelada = consultaService.cancelar(id, motivo);
        return ResponseEntity.status(HttpStatus.OK).body(consultaCancelada);
    }
}
