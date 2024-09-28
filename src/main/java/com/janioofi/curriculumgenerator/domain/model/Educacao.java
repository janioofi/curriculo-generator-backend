package com.janioofi.curriculumgenerator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Educacao {
    private String descricao;
    private String lugar;
    private String mesAnoInicio;
    private String mesAnoFinal;
}
