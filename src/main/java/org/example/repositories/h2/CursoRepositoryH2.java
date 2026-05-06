package org.example.repositories.h2;

import org.example.models.Curso;
import org.example.repositories.db_connection.ConexaoH2;
import org.example.repositories.interfaces.CursoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class CursoRepositoryH2 implements CursoRepository {

    @Override
    public Optional<Curso> buscarPorId(Long id) {
        String sql = "SELECT id, nome, vagas_disponiveis FROM curso WHERE id = ?";

        try (Connection connection = ConexaoH2.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Curso curso = new Curso(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("vagas_disponiveis")
                );

                return Optional.of(curso);
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar curso", e);
        }
    }

    @Override
    public void salvar(Curso curso) {
        String sql = """
            MERGE INTO curso (id, nome, vagas_disponiveis)
            KEY(id)
            VALUES (?, ?, ?)
        """;

        try (Connection connection = ConexaoH2.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, curso.getId());
            statement.setString(2, curso.getNome());
            statement.setInt(3, curso.getVagasDisponiveis());

            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar curso", e);
        }
    }
}