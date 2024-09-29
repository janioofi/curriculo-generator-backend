package com.janioofi.curriculumgenerator.domain.services;

import com.janioofi.curriculumgenerator.domain.model.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CurriculoDocService {

    public byte[] gerarCurriculoDoc(Curriculo curriculo) throws IOException {
        ClassPathResource resource = new ClassPathResource("base.docx");
        InputStream fis = resource.getInputStream();
        XWPFDocument document = new XWPFDocument(fis);

        substituirTexto(document, curriculo);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private void substituirTexto(XWPFDocument document, Curriculo curriculo) {
        for (XWPFParagraph para : document.getParagraphs()) {
            List<XWPFRun> runs = para.getRuns();
            if (runs != null) {
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        text = substituirCamposPessoais(text, curriculo);
                        text = substituirResumo(text, curriculo);
                        text = substituirHabilidades(text, curriculo);
                        text = substituirExperiencias(text, curriculo);
                        text = substituirEducacao(text, curriculo);
                        text = substituirCursos(text, curriculo);

                        run.setText(text, 0);
                    }
                }
            }
        }
    }

    private String substituirCamposPessoais(String text, Curriculo curriculo) {
        text = text.replace("nomeCompleto", curriculo.getNomeCompleto());
        text = text.replace("celular", curriculo.getCelular());
        text = text.replace("email", curriculo.getEmail());
        text = text.replace("linkedinUsername", curriculo.getLinkedinUsername());
        text = text.replace("githubUsername", curriculo.getGithubUsername());

        text = text.replace("bairro", curriculo.getBairro().isEmpty() ? "" : curriculo.getBairro() + ", ");
        text = text.replace("cidade", curriculo.getCidade().isEmpty() ? "" : curriculo.getCidade() + " - ");
        text = text.replace("estado", curriculo.getEstado().isEmpty() ? "" : curriculo.getEstado());

        text = text.replace("titulo", curriculo.getTitulo());

        return text;
    }

    private String substituirResumo(String text, Curriculo curriculo) {
        if (!curriculo.getResumo().isEmpty()) {
            text = text.replace("resumoCorpo", curriculo.getResumo());
            text = text.replace("resumoTitulo", "RESUMO");
        } else {
            text = text.replace("resumoCorpo", "");
            text = text.replace("resumoTitulo", "");
        }
        return text;
    }

    private String substituirHabilidades(String text, Curriculo curriculo) {
        Habilidades habilidades = curriculo.getHabilidades();
        if (!habilidades.getBancoDados().isEmpty() ||
                !habilidades.getPessoais().isEmpty() ||
                !habilidades.getFrontEnd().isEmpty() ||
                !habilidades.getBackEnd().isEmpty() ||
                !habilidades.getOutros().isEmpty() ||
                !habilidades.getIdiomas().isEmpty()) {

            text = text.replace("habilidadesTitulo", "HABILIDADES");
            StringBuilder habilidadesText = new StringBuilder();

            // Adiciona cada categoria de habilidades em uma linha separada
            adicionarHabilidadesCategoria(habilidadesText, "Idiomas", habilidades.getIdiomas());
            adicionarHabilidadesCategoria(habilidadesText, "Front-end", habilidades.getFrontEnd());
            adicionarHabilidadesCategoria(habilidadesText, "Back-end", habilidades.getBackEnd());
            adicionarHabilidadesCategoria(habilidadesText, "Banco de Dados", habilidades.getBancoDados());
            adicionarHabilidadesCategoria(habilidadesText, "Pessoais", habilidades.getPessoais());
            adicionarHabilidadesCategoria(habilidadesText, "Outras", habilidades.getOutros());

            // Substitui o texto no documento
            text = text.replace("habilidadesCorpo", habilidadesText.toString().trim());
        } else {
            text = text.replace("habilidadesTitulo", "");
            text = text.replace("habilidadesCorpo", "");
        }
        return text;
    }

    private void adicionarHabilidadesCategoria(StringBuilder habilidadesText, String titulo, List<Habilidade> habilidades) {
        if (!habilidades.isEmpty()) {
            habilidadesText.append(titulo).append(": "); // Adiciona o título da categoria
            for (Habilidade habilidade : habilidades) {
                habilidadesText.append(habilidade.getNome()).append(", "); // Adiciona cada habilidade
            }
            habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula e espaço
        }
    }

    private String substituirExperiencias(String text, Curriculo curriculo) {
        if (!curriculo.getExperiencias().isEmpty()) {
            text = text.replace("projetosExperienciasTitulo", "PROJETOS E EXPERIÊNCIAS");
            StringBuilder experienciasText = new StringBuilder();
            for (Experiencia experiencia : curriculo.getExperiencias()) {
                experienciasText.append(experiencia.getCargo()).append(" - ")
                        .append(experiencia.getEmpresa()).append(" (")
                        .append(experiencia.getMesAnoInicio()).append(" - ")
                        .append(experiencia.getMesAnoFinal()).append(")\n")
                        .append("Descrição: ").append(experiencia.getDescricao()).append("\n\n");
            }
            text = text.replace("projetosExperienciasCorpo", experienciasText.toString());
        } else {
            text = text.replace("projetosExperienciasTitulo", "");
            text = text.replace("projetosExperienciasCorpo", "");
        }
        return text;
    }

    private String substituirEducacao(String text, Curriculo curriculo) {
        if (!curriculo.getEducacao().isEmpty()) {
            text = text.replace("educacaoTitulo", "EDUCAÇÃO");
            StringBuilder educacaoText = new StringBuilder();
            for (Educacao educ : curriculo.getEducacao()) {
                educacaoText.append(educ.getDescricao()).append(" - ")
                        .append(educ.getLugar()).append(" (")
                        .append(educ.getMesAnoInicio()).append(" - ")
                        .append(educ.getMesAnoFinal()).append(")\n");
            }
            text = text.replace("educacaoCorpo", educacaoText.toString());
        } else {
            text = text.replace("educacaoTitulo", "");
            text = text.replace("educacaoCorpo", "");
        }
        return text;
    }

    private String substituirCursos(String text, Curriculo curriculo) {
        if (!curriculo.getCursos().isEmpty()) {
            text = text.replace("cursosCertificacoesTitulo", "CURSOS E CERTIFICAÇÕES");
            StringBuilder cursoText = new StringBuilder();
            for (Curso curso : curriculo.getCursos()) {
                cursoText.append(curso.getDescricao()).append(" - ")
                        .append(curso.getInstituicao()).append(" (")
                        .append(curso.getAnoConclusao()).append(")\n");
            }
            text = text.replace("cursosCertificacoesCorpo", cursoText.toString());
        } else {
            text = text.replace("cursosCertificacoesTitulo", "");
            text = text.replace("cursosCertificacoesCorpo", "");
        }
        return text;
    }
}
