package org.example.services;

import org.example.models.Aluno;
import org.example.models.Curso;

public interface NotificacaoService {
    void enviarConfirmacao(Aluno aluno, Curso curso);
}
