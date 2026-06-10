package com.RodSolution.ocularhub.controller;

import com.RodSolution.ocularhub.model.dto.ExameRequestDto;
import com.RodSolution.ocularhub.model.dto.ExameResponseDto;
import com.RodSolution.ocularhub.model.entities.Exame;
import com.RodSolution.ocularhub.service.ExameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
@CrossOrigin(origins = "*")
public class ExameController {

    @Autowired
    private ExameService exameService;


    @PostMapping
    public ResponseEntity<ExameResponseDto> criarExame(@RequestBody @Valid ExameRequestDto dto) {
        ExameResponseDto exameSaved = exameService.saveExame(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(exameSaved);
    }

    @GetMapping
    public ResponseEntity<List<ExameResponseDto>> listarTodos() {
        return ResponseEntity.ok(exameService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(exameService.findById(id));
    }


    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExameResponseDto>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(exameService.listarPorPaciente(pacienteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid ExameRequestDto dto) {
        return ResponseEntity.ok(exameService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
        exameService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).body("exame com o ID " + id + " deletado com sucesso.");
    }


}
