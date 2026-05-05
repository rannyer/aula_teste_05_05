package org.example.repositories.interfaces;

import org.example.models.Matricula;

import java.util.List;

public interface MatriculaRepository {
    Matricula salvar(Matricula matricula);
    List<Matricula> listarTodas();
}
