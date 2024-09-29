package com.janioofi.curriculumgenerator.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Educacao {
    private String descricao;
    private String lugar;
    private String mesAnoInicio;
    private String mesAnoFinal;
}
