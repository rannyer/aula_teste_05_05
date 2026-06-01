package org.example.services;

import lombok.AllArgsConstructor;
import org.example.models.Aluno;
import org.example.models.Curso;
import org.example.models.Matricula;
import org.example.repositories.interfaces.AlunoRepository;
import org.example.repositories.interfaces.CursoRepository;
import org.example.repositories.interfaces.MatriculaRepository;

@AllArgsConstructor
public class MatriculaService {

    private AlunoRepository alunoRepository;
    private CursoRepository cursoRepository;
    private MatriculaRepository matriculaRepository;
    private NotificacaoService notificacaoService;

    public Matricula matricular(Long alunoId, Long cursoId){
        Aluno aluno = alunoRepository
                .buscarPorId(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno nao encontrado"));

        Curso curso =  cursoRepository
                .buscarPorId(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso nao encontrados"));

        curso.ocuparVagas();

        Matricula matriculaSalva = matriculaRepository.salvar(new Matricula(null, aluno, curso));

        notificacaoService.enviarConfirmacao(aluno, curso);

        return matriculaSalva;
    }

}
