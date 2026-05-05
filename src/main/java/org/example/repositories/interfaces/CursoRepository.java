package org.example.repositories.interfaces;

import org.example.models.Curso;

import java.util.Optional;

public interface CursoRepository {
    Optional<Curso> buscarPorId(Long id);
    void salvar(Curso curso);
}
