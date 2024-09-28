package com.janioofi.curriculumgenerator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Experiencia {
    private String cargo;
    private String empresa;
    private String mesAnoInicio;
    private String mesAnoFinal;
    private String descricao;
}
