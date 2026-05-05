package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
public class Matricula {
    private Long id;
    private Aluno aluno;
    private Curso curso;
    private LocalDate dataEntrada;

    public Matricula(Long id, Aluno aluno, Curso curso) {
        this.id = id;
        this.aluno = aluno;
        this.curso = curso;
        this.dataEntrada = LocalDate.now();
    }
    public Matricula(){
        this.dataEntrada = LocalDate.now();
    }
}
