package com.RodSolution.ocularhub.controller;

import com.RodSolution.ocularhub.model.UploadExame;
import com.RodSolution.ocularhub.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/exames")
@CrossOrigin(origins = "*")
public class UploadExameController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadArquivo(@RequestParam("file") MultipartFile file) {
        try {
            UploadExame novoUpload = uploadService.salvarArquivo(file);

            return ResponseEntity.status(201).body(novoUpload);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar arquivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro na requisição: " + e.getMessage());
        }
    }
}
