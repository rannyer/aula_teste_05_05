package org.example.repositories.interfaces;

import org.example.models.Aluno;

import java.util.Optional;

public interface AlunoRepository {
    Optional<Aluno> buscarPorId(Long id);
    void salvar(Aluno aluno);
}
