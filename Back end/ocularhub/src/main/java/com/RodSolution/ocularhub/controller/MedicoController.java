package com.RodSolution.ocularhub.controller;

import com.RodSolution.ocularhub.model.dto.MedicoRequestDto;
import com.RodSolution.ocularhub.model.dto.MedicoResponseDto;
import com.RodSolution.ocularhub.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping()
    public ResponseEntity<List<MedicoResponseDto>> findAll() {
        List<MedicoResponseDto> medicoList = medicoService.listarTodosMedicos();
        return ResponseEntity.status(HttpStatus.OK).body(medicoList);
    }


    @PostMapping()
    public ResponseEntity<MedicoResponseDto> save(@RequestBody @Valid MedicoRequestDto medicoDto) {
        MedicoResponseDto medicoSaved = medicoService.saveMedico(medicoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
        medicoService.deletarMedico(id);
        return ResponseEntity.status(HttpStatus.OK).body("medico com o ID " + id + " deletado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> updateMedico(@PathVariable(value = "id") long id, @RequestBody @Valid MedicoRequestDto medicoDto) {
        MedicoResponseDto medicoUpdate = medicoService.updateMedico(medicoDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(medicoUpdate);

    }

}
