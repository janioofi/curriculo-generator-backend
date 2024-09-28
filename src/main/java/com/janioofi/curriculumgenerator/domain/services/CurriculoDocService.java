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
        // Carrega o arquivo .docx existente como modelo a partir de resources
        ClassPathResource resource = new ClassPathResource("base.docx");
        InputStream fis = resource.getInputStream();
        XWPFDocument document = new XWPFDocument(fis);

        // Substitui os placeholders no documento
        substituirTexto(document, curriculo);

        // Converte o documento modificado em bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private void adicionarHabilidades(XWPFDocument document, Habilidades habilidades) {
        XWPFRun run;

        // Alinhamento à esquerda para habilidades
        XWPFParagraph habilidadesPara = document.createParagraph();
        habilidadesPara.setAlignment(ParagraphAlignment.LEFT);

        run = habilidadesPara.createRun();
        run.setText("HABILIDADES");
        run.setBold(true);
        run.addBreak();

        // Idiomas
        if (!habilidades.getIdiomas().isEmpty()) {
            run.addBreak();
            run.setText("Idiomas:");
            run.addBreak();
            for (Habilidade idioma : habilidades.getIdiomas()) {
                run.setText(idioma.getNome());
                run.addBreak();
            }
        }

        // Front-end
        if (!habilidades.getFrontEnd().isEmpty()) {
            run.addBreak();
            run.setText("Front-end:");
            run.addBreak();
            for (Habilidade frontEnd : habilidades.getFrontEnd()) {
                run.setText(frontEnd.getNome());
                run.addBreak();
            }
        }

        // Back-end
        if (!habilidades.getBackEnd().isEmpty()) {
            run.addBreak();
            run.setText("Back-end: ");
            run.addBreak();
            for (Habilidade backEnd : habilidades.getBackEnd()) {
                run.setText(backEnd.getNome());
                run.addBreak();
            }
        }

        // Banco de Dados
        if (!habilidades.getBancoDados().isEmpty()) {
            run.addBreak();
            run.setText("Banco de Dados:");
            run.addBreak();
            for (Habilidade banco : habilidades.getBancoDados()) {
                run.setText(banco.getNome());
                run.addBreak();
            }
        }

        // Habilidades Pessoais
        if (!habilidades.getPessoais().isEmpty()) {
            run.addBreak();
            run.setText("Habilidades Pessoais:");
            run.addBreak();
            for (Habilidade pessoal : habilidades.getPessoais()) {
                run.setText(pessoal.getNome());
                run.addBreak();
            }
        }

        // Outras Habilidades
        if (!habilidades.getOutros().isEmpty()) {
            run.addBreak();
            run.setText("Outras Habilidades:");
            run.addBreak();
            for (Habilidade outro : habilidades.getOutros()) { // Supondo que "Outros" seja uma lista de Habilidades
                run.setText(outro.getNome());
                run.addBreak();
            }
        }
    }

    private void adicionarExperiencias(XWPFDocument document, List<Experiencia> experiencias) {
        XWPFParagraph experienciasPara = document.createParagraph();
        experienciasPara.setAlignment(ParagraphAlignment.LEFT); // Alinhamento à esquerda
        XWPFRun run = experienciasPara.createRun();
        run.setText("PROJETOS E EXPERIÊNCIAS");
        run.setBold(true);
        run.addBreak();

        for (Experiencia experiencia : experiencias) {
            run.setText(experiencia.getCargo() + " - " + experiencia.getEmpresa() + " (" + experiencia.getMesAnoInicio() + " - " + experiencia.getMesAnoFinal() + ")");
            run.addBreak();
            run.setText("Descrição: " + experiencia.getDescricao());
            run.addBreak();
        }
    }

    private void adicionarEducacao(XWPFDocument document, List<Educacao> educacao) {
        XWPFParagraph educacaoPara = document.createParagraph();
        educacaoPara.setAlignment(ParagraphAlignment.LEFT); // Alinhamento à esquerda
        XWPFRun run = educacaoPara.createRun();
        run.setText("EDUCAÇÃO");
        run.setBold(true);
        run.addBreak();

        for (Educacao educ : educacao) {
            run.setText(educ.getDescricao() + " - " + educ.getLugar() + " (" + educ.getMesAnoInicio() + " - " + educ.getMesAnoFinal() + ")");
            run.addBreak();
        }
    }

    private void substituirTexto(XWPFDocument document, Curriculo curriculo) {
        // Itera sobre os parágrafos para substituir os textos
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            List<XWPFRun> runs = para.getRuns();
            if (runs != null) {
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        // Substitui {nomeCompleto}, {celular}, {email}, etc.
                        if (text.contains("{nomeCompleto}")) {
                            text = text.replace("{nomeCompleto}", curriculo.getNomeCompleto());
                        }
                        if (text.contains("{celular}")) {
                            text = text.replace("{celular}", curriculo.getCelular());
                        }
                        if (text.contains("{email}")) {
                            text = text.replace("{email}", curriculo.getEmail());
                        }
                        if (text.contains("{linkedinUsername}")) {
                            text = text.replace("{linkedinUsername}", curriculo.getLinkedinUsername());
                        }
                        if (text.contains("{githubUsername}")) {
                            text = text.replace("{githubUsername}", curriculo.getGithubUsername());
                        }
                        if (text.contains("{bairro}")) {
                            text = text.replace("{bairro}", curriculo.getBairro());
                        }
                        if (text.contains("{cidade}")) {
                            text = text.replace("{cidade}", curriculo.getCidade());
                        }
                        if (text.contains("{estado}")) {
                            text = text.replace("{estado}", curriculo.getEstado());
                        }
                        if (text.contains("{titulo}")) {
                            text = text.replace("{titulo}", curriculo.getTitulo());
                        }
                        if (text.contains("{resumo}")) {
                            text = text.replace("{resumo}", curriculo.getResumo());
                        }

                        // Substitui {habilidades}
                        if (text.contains("{habilidades}")) {
                            StringBuilder habilidadesText = new StringBuilder();

                            // Idiomas
                            if (!curriculo.getHabilidades().getIdiomas().isEmpty()) {
                                habilidadesText.append("Idiomas: ");
                                for (Habilidade idioma : curriculo.getHabilidades().getIdiomas()) {
                                    habilidadesText.append(idioma.getNome()).append(", "); // Adiciona vírgula
                                }
                                habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula
                                habilidadesText.append("\n");
                            }

                            // Front-end
                            if (!curriculo.getHabilidades().getFrontEnd().isEmpty()) {
                                habilidadesText.append("Front-end: ");
                                for (Habilidade frontEnd : curriculo.getHabilidades().getFrontEnd()) {
                                    habilidadesText.append(frontEnd.getNome()).append(", ");
                                }
                                habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula
                                habilidadesText.append("\n");
                            }

                            // Back-end
                            if (!curriculo.getHabilidades().getBackEnd().isEmpty()) {
                                habilidadesText.append("Back-end: ");
                                for (Habilidade backEnd : curriculo.getHabilidades().getBackEnd()) {
                                    habilidadesText.append(backEnd.getNome()).append(", ");
                                }
                                habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula
                                habilidadesText.append("\n");
                            }

                            // Banco de Dados
                            if (!curriculo.getHabilidades().getBancoDados().isEmpty()) {
                                habilidadesText.append("Banco de Dados: ");
                                for (Habilidade banco : curriculo.getHabilidades().getBancoDados()) {
                                    habilidadesText.append(banco.getNome()).append(", ");
                                }
                                habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula
                                habilidadesText.append("\n");
                            }

                            // Habilidades Pessoais
                            if (!curriculo.getHabilidades().getPessoais().isEmpty()) {
                                habilidadesText.append("Pessoais: ");
                                for (Habilidade pessoal : curriculo.getHabilidades().getPessoais()) {
                                    habilidadesText.append(pessoal.getNome()).append(", ");
                                }
                                habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula
                                habilidadesText.append("\n");
                            }

                            // Outras Habilidades
                            if (!curriculo.getHabilidades().getOutros().isEmpty()) {
                                habilidadesText.append("Outras: ");
                                for (Habilidade outro : curriculo.getHabilidades().getOutros()) {
                                    habilidadesText.append(outro.getNome()).append(", ");
                                }
                                habilidadesText.setLength(habilidadesText.length() - 2); // Remove a última vírgula
                                habilidadesText.append("\n");
                            }

                            // Substitui {habilidades} se houver conteúdo
                            if (habilidadesText.length() > 0) {
                                text = text.replace("{habilidades}", habilidadesText.toString());
                            } else {
                                text = text.replace("{habilidades}", ""); // Remove o placeholder se não houver habilidades
                            }
                        }

                        // Substitui {experiencias}
                        if (text.contains("{experiencias}")) {
                            StringBuilder experienciasText = new StringBuilder();
                            for (Experiencia experiencia : curriculo.getExperiencias()) {
                                experienciasText.append(experiencia.getCargo()).append(" - ")
                                        .append(experiencia.getEmpresa()).append(" (")
                                        .append(experiencia.getMesAnoInicio()).append(" - ")
                                        .append(experiencia.getMesAnoFinal()).append(")\n")
                                        .append("Descrição: ").append(experiencia.getDescricao()).append("\n\n");
                            }
                            text = text.replace("{experiencias}", experienciasText.toString());
                        }

                        // Substitui {educacao}
                        if (text.contains("{educacao}")) {
                            StringBuilder educacaoText = new StringBuilder();
                            for (Educacao educ : curriculo.getEducacao()) {
                                educacaoText.append(educ.getDescricao()).append(" - ")
                                        .append(educ.getLugar()).append(" (")
                                        .append(educ.getMesAnoInicio()).append(" - ")
                                        .append(educ.getMesAnoFinal()).append(")\n");
                            }
                            text = text.replace("{educacao}", educacaoText.toString());
                        }

                        // Atualiza o texto no run
                        run.setText(text, 0);
                    }
                }
            }
        }
    }
}
