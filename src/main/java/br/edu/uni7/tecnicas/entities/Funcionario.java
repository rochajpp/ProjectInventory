package br.edu.uni7.tecnicas.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Funcionario {
    @Id
    private Integer matricula;
    private String nome;

    public Integer gerarNovaMatricula(){
        Integer novaMatricula = 0;
        for(int i = 0; i < 10; i++) {
            novaMatricula =+ (int)Math.floor(Math.random() * 10);
        }
        return novaMatricula;
    }


    public Integer getMatricula(){
        return this.matricula;
    }
    public void setMatricula(Integer matricula){
        this.matricula = matricula;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    @Override
    public String toString() {
        return "Funcionario{" +
                "matricula=" + matricula +
                ", nome='" + nome + '\'' +
                '}';
    }
}
