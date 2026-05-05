package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    private Long id;
    private String nome;
    private int vagasDisponiveis;

    public void ocuparVagas(){
        if(vagasDisponiveis <=0){
            throw new RuntimeException("Curso sem vagas");
        }
        vagasDisponiveis--;
    }
}
