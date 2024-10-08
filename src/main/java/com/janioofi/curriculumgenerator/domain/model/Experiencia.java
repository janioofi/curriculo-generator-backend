package com.janioofi.curriculumgenerator.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Experiencia {
    private String cargo;
    private String empresa;
    private String mesAnoInicio;
    private String mesAnoFinal;
    private String descricao;
}
