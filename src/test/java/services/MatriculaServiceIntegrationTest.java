package services;

import org.example.models.Aluno;
import org.example.models.Curso;
import org.example.models.Matricula;
import org.example.repositories.impl.AlunoRepositoryEmMemoria;
import org.example.repositories.impl.CursoRepositoryEmMemoria;
import org.example.repositories.impl.MatriculaRepositoryEmMemoria;
import org.example.repositories.interfaces.CursoRepository;
import org.example.repositories.interfaces.MatriculaRepository;
import org.example.services.MatriculaService;
import org.example.services.NotificacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MatriculaServiceIntegrationTest {
    private AlunoRepositoryEmMemoria alunoRepository;
    private CursoRepositoryEmMemoria cursoRepository;
    private MatriculaRepositoryEmMemoria matriculaRepository;

    private NotificacaoService notificacaoService;

    private MatriculaService matriculaService;

    @BeforeEach
    void setup(){
        alunoRepository = new AlunoRepositoryEmMemoria();
        cursoRepository =  new CursoRepositoryEmMemoria();
        matriculaRepository = new MatriculaRepositoryEmMemoria();

        notificacaoService = mock(NotificacaoService.class);

        matriculaService = new MatriculaService(
                alunoRepository,
                cursoRepository,
                matriculaRepository,
                notificacaoService
        );
    }

    @Test
    void deveMatricularAlunoEmCursoComVaga(){
        Aluno aluno =  new Aluno(1L, "Joao Java", "jojo@oracle.com");
        Curso curso =  new Curso(10L, "Java com Testes", 3);

        alunoRepository.salvar(aluno);
        cursoRepository.salvar(curso);

        Matricula matricula = matriculaService.matricular(1L, 10L);

        assertNotNull(matricula.getId());
        assertEquals("Joao Java", matricula.getAluno().getNome());
        assertEquals("Java com Testes", matricula.getCurso().getNome());
        assertEquals(2, curso.getVagasDisponiveis());
        assertEquals(1, matriculaRepository.listarTodas().size());

        verify(notificacaoService).enviarConfirmacao(aluno, curso);

    }

}
