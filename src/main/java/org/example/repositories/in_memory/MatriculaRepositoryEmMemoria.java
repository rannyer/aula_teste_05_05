package org.example.repositories.in_memory;

import org.example.models.Matricula;
import org.example.repositories.interfaces.MatriculaRepository;

import java.util.ArrayList;
import java.util.List;

public class MatriculaRepositoryEmMemoria implements MatriculaRepository {
    private List<Matricula> matriculas = new ArrayList<>();
    private Long proximoId = 1L;

    @Override
    public Matricula salvar(Matricula matricula) {
        Matricula novaMatricula = new Matricula(
                proximoId++,
                matricula.getAluno(),
                matricula.getCurso()
        );

        matriculas.add(novaMatricula);
        return novaMatricula;
    }

    @Override
    public List<Matricula> listarTodas() {
        return matriculas;
    }
}
