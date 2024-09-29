package com.janioofi.curriculumgenerator.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Curso {
    private String descricao;
    private String instituicao;
    private String anoConclusao;
}
