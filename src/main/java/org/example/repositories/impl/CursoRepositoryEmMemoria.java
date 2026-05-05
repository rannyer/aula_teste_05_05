package org.example.repositories.impl;

import org.example.models.Curso;
import org.example.repositories.interfaces.CursoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CursoRepositoryEmMemoria implements CursoRepository {
    private Map<Long, Curso> cursos =  new HashMap<>();

    @Override
    public Optional<Curso> buscarPorId(Long id) {
        return Optional.ofNullable(cursos.get(id));
    }

    @Override
    public void salvar(Curso curso) {
        cursos.put(curso.getId(), curso);
    }
}
