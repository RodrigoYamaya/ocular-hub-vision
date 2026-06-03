package com.RodSolution.ocularhub.controller;

import com.RodSolution.ocularhub.model.dto.PacienteRequestDto;
import com.RodSolution.ocularhub.model.dto.PacienteResponseDto;
import com.RodSolution.ocularhub.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping()
    public ResponseEntity<List<PacienteResponseDto>> findAll() {
        List<PacienteResponseDto> pacienteList = pacienteService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(pacienteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> findById(@PathVariable(value = "id") Long id) {
        PacienteResponseDto paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(paciente);
    }

    @PostMapping()
    public ResponseEntity<PacienteResponseDto> save(@RequestBody @Valid PacienteRequestDto pacienteDto) {
        PacienteResponseDto pacienteSaved = pacienteService.cadastrar(pacienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        pacienteService.excluir(id);
        return ResponseEntity.status(HttpStatus.OK).body("Paciente com o ID " + id + " deletado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> updatePaciente(@PathVariable(value = "id") Long id, @RequestBody @Valid PacienteRequestDto pacienteDto) {
        PacienteResponseDto pacienteUpdate = pacienteService.atualizar(id, pacienteDto);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteUpdate);
    }


}
