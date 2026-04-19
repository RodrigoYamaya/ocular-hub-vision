package com.RodSolution.ocularhub.service;

import com.RodSolution.ocularhub.model.UploadExame;
import com.RodSolution.ocularhub.repository.UploadExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${upload.path}")
    private String diretorioUpload;

    @Autowired
    private UploadExameRepository uploadExameRepository;

    public UploadExame salvarArquivo(MultipartFile arquivo) throws IOException {

        String contentType = arquivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("O arquivo enviado não é uma imagem válida.");
        }

        Path caminhoDiretorio = Paths.get(diretorioUpload).toAbsolutePath().normalize();

        if (!Files.exists(caminhoDiretorio)) {
            Files.createDirectories(caminhoDiretorio);
        }

        String nomeOriginal = arquivo.getOriginalFilename();
        String extensao = "";

        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf(".")).toLowerCase();
        }

        String nomeArmazenado = UUID.randomUUID().toString() + extensao;

        Path caminhoCompleto = caminhoDiretorio.resolve(nomeArmazenado);

        Files.copy(arquivo.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

        UploadExame upload = new UploadExame();
        upload.setNomeOriginalArquivo(nomeOriginal);
        upload.setNomeArmazenado(nomeArmazenado);


        upload.setCaminhoArquivo(nomeArmazenado);

        upload.setTipoArquivo(contentType);
        upload.setTamanhoArquivo(arquivo.getSize());

        return uploadExameRepository.save(upload);
    }

    
}
