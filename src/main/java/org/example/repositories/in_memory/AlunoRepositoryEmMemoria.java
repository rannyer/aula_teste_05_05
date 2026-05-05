package org.example.repositories.in_memory;

import org.example.models.Aluno;
import org.example.repositories.interfaces.AlunoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AlunoRepositoryEmMemoria implements AlunoRepository {

    private Map<Long, Aluno> alunos =  new HashMap<>();

    @Override
    public Optional<Aluno> buscarPorId(Long id) {
        return Optional.ofNullable(alunos.get(id));
    }

    @Override
    public void salvar(Aluno aluno) {
        alunos.put(aluno.getId(), aluno);
    }
}
