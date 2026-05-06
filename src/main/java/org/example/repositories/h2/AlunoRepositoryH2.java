package org.example.repositories.h2;

import org.example.models.Aluno;
import org.example.repositories.db_connection.ConexaoH2;
import org.example.repositories.interfaces.AlunoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class AlunoRepositoryH2 implements AlunoRepository {
    @Override
    public Optional<Aluno> buscarPorId(Long id) {

        String sql = "SELECT id, nome, email FROM aluno WHERE id = ? ";
        try(Connection connection = ConexaoH2.conectar()){
            PreparedStatement statement =  connection.prepareStatement(sql);

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                Aluno aluno = new Aluno(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email")
                );
                return Optional.of(aluno);
            }
            return Optional.empty();

        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar aluno", e);
        }

    }

    @Override
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (id, nome, email) VALUES (?, ? ,?)";

        try(Connection connection = ConexaoH2.conectar();
            PreparedStatement statement =  connection.prepareStatement(sql)) {

            statement.setLong(1, aluno.getId());
            statement.setString(2, aluno.getNome());
            statement.setString(3, aluno.getEmail());

            statement.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException("Erro ao salvar aluno", e);
        }






    }
}
