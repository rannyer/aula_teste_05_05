package org.example.repositories.h2;

import org.example.models.Aluno;
import org.example.models.Curso;
import org.example.models.Matricula;
import org.example.repositories.db_connection.ConexaoH2;
import org.example.repositories.interfaces.MatriculaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MatriculaRepositoryH2 implements MatriculaRepository {

    @Override
    public Matricula salvar(Matricula matricula) {
        String sql = "INSERT INTO matricula (aluno_id, curso_id) VALUES (?, ?)";

        try (Connection connection = ConexaoH2.conectar();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, matricula.getAluno().getId());
            statement.setLong(2, matricula.getCurso().getId());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return new Matricula(
                        generatedKeys.getLong(1),
                        matricula.getAluno(),
                        matricula.getCurso()
                );
            }

            throw new RuntimeException("Erro ao gerar ID da matrícula");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar matrícula", e);
        }
    }

    @Override
    public List<Matricula> listarTodas() {
        String sql = """
            SELECT 
                m.id AS matricula_id,
                a.id AS aluno_id,
                a.nome AS aluno_nome,
                a.email AS aluno_email,
                c.id AS curso_id,
                c.nome AS curso_nome,
                c.vagas_disponiveis AS curso_vagas
            FROM matricula m
            JOIN aluno a ON a.id = m.aluno_id
            JOIN curso c ON c.id = m.curso_id
        """;

        List<Matricula> matriculas = new ArrayList<>();

        try (Connection connection = ConexaoH2.conectar();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Aluno aluno = new Aluno(
                        resultSet.getLong("aluno_id"),
                        resultSet.getString("aluno_nome"),
                        resultSet.getString("aluno_email")
                );

                Curso curso = new Curso(
                        resultSet.getLong("curso_id"),
                        resultSet.getString("curso_nome"),
                        resultSet.getInt("curso_vagas")
                );

                Matricula matricula = new Matricula(
                        resultSet.getLong("matricula_id"),
                        aluno,
                        curso
                );

                matriculas.add(matricula);
            }

            return matriculas;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar matrículas", e);
        }
    }
}