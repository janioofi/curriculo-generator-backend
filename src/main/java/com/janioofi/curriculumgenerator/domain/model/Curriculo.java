package com.janioofi.curriculumgenerator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curriculo {
    private String nomeCompleto;
    private String celular;
    private String bairro;
    private String cidade;
    private String estado;
    private String email;
    private String linkedinUsername;
    private String githubUsername;
    private String titulo;
    private String resumo;
    private Habilidades habilidades;
    private List<Experiencia> experiencias;
    private List<Educacao> educacao;
}
