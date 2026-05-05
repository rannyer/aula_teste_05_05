package services;

import org.example.models.Aluno;
import org.example.models.Curso;
import org.example.models.Matricula;
import org.example.repositories.in_memory.AlunoRepositoryEmMemoria;
import org.example.repositories.in_memory.CursoRepositoryEmMemoria;
import org.example.repositories.in_memory.MatriculaRepositoryEmMemoria;
import org.example.services.MatriculaService;
import org.example.services.NotificacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void naoDeveMatricularAlunoQuandoCursoNaoTemVaga(){
        Aluno aluno =  new Aluno(1L, "Joao Java", "jojo@oracle.com");
        Curso curso =  new Curso(10L, "Java com Testes", 0);
        alunoRepository.salvar(aluno);
        cursoRepository.salvar(curso);

        RuntimeException exception =  assertThrows(RuntimeException.class, () -> matriculaService.matricular(1L,10L));

        assertEquals("Curso sem vagas", exception.getMessage());
        assertEquals(0, matriculaRepository.listarTodas().size());

        verify(notificacaoService, never()).enviarConfirmacao(aluno, curso);
    }

    @Nested
    class aluno_curso_inexistente{
        @Test
        void naoDeveMatricularQuandoAlunoNaoExiste(){
            Curso curso =  new Curso(10L, "Java com Testes", 0);
            cursoRepository.salvar(curso);

            RuntimeException exception =  assertThrows(RuntimeException.class, () ->
                    matriculaService.matricular(99L,10L));

            assertEquals("Aluno nao encontrado", exception.getMessage());
            assertEquals(0, matriculaRepository.listarTodas().size());

            verify(notificacaoService, never()).enviarConfirmacao(any(), any());
        }

        @Test
        void naoDeveMatricularQuandoCursoNaoExiste(){
            Aluno aluno =  new Aluno(1L, "Joao Java", "jojo@oracle.com");

            alunoRepository.salvar(aluno);

            RuntimeException exception =  assertThrows(RuntimeException.class, () ->
                    matriculaService.matricular(1L,99L));

            assertEquals("Curso nao encontrado", exception.getMessage());
            assertEquals(0, matriculaRepository.listarTodas().size());

            verify(notificacaoService, never()).enviarConfirmacao(any(), any());
        }
    }

}
