package com.janioofi.curriculumgenerator.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Habilidades {
    private List<Habilidade> idiomas;
    private List<Habilidade> frontEnd;
    private List<Habilidade> backEnd;
    private List<Habilidade> bancoDados;
    private List<Habilidade> pessoais;
    private List<Habilidade> outros;
}

