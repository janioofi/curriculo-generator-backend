package com.janioofi.curriculumgenerator.controllers;

import com.janioofi.curriculumgenerator.domain.model.Curriculo;
import com.janioofi.curriculumgenerator.domain.services.CurriculoDocService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/curriculo")
@RestController
@RequiredArgsConstructor
@Tag(name = "Curriculo", description = "API Curriculo")
public class CurriculoController {
    private final CurriculoDocService curriculoDocService;

    @PostMapping("/gerar")
    public ResponseEntity<byte[]> gerarCurriculo(@RequestBody Curriculo curriculo) throws IOException {
        // Gera o arquivo .docx com base no template e no currículo recebido
        byte[] documento = curriculoDocService.gerarCurriculoDoc(curriculo);

        // Configura os cabeçalhos para o download do arquivo
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", curriculo.getNomeCompleto()+"-curriculo.docx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(documento);
    }
}
