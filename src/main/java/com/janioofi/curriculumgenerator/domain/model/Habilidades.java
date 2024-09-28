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
public class Habilidades {
    private List<Habilidade> idiomas;
    private List<Habilidade> frontEnd;
    private List<Habilidade> backEnd;
    private List<Habilidade> bancoDados;
    private List<Habilidade> pessoais;
}

