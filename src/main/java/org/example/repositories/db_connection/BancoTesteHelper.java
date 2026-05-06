package org.example.repositories.db_connection;

import java.sql.Connection;
import java.sql.Statement;

public class BancoTesteHelper {
    public static void criarTabelas(){
        try (Connection connection = ConexaoH2.conectar()) {
            var statement = connection.createStatement();

            String sqlAluno = """
                    CREATE TABLE IF NOT EXISTS aluno (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE
                    )
            """;
            String sqlCurso = """
                    CREATE TABLE IF NOT EXISTS curso (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        vagas_disponiveis INT NOT NULL
                    )
            """;
            String sqlMatricula = """
                    CREATE TABLE IF NOT EXISTS matricula (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        aluno_id BIGINT NOT NULL,
                        curso_id BIGINT NOT NULL,
                        FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                        FOREIGN KEY (curso_id) REFERENCES curso(id)
                    )
            """;
            statement.execute(sqlAluno);
            statement.execute(sqlCurso);
            statement.execute(sqlMatricula);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar tabelas", e);
        }
    }

    public static void limparTabelas() {
        try (Connection connection = ConexaoH2.conectar();
             Statement statement = connection.createStatement()) {

            statement.execute("DELETE FROM matricula");
            statement.execute("DELETE FROM aluno");
            statement.execute("DELETE FROM curso");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao limpar tabelas", e);
        }

    }

}
