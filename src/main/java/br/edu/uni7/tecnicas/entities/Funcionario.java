package br.edu.uni7.tecnicas.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Funcionario {
    @Id
    private Integer matricula;
    private String nome;
    @OneToMany
    List<Item> itens = new ArrayList<Item>();

    public Integer gerarNovaMatricula(){
        return (int)Math.floor(Math.random() * (999 - 100) + 100);
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
    public void addItem(Item item){
        itens.add(item);
    }
    public void rmItem(Integer identificador){
        for(int i = 0; i < itens.size(); i++){
            if(itens.get(i).getIdentificador() == identificador){
                itens.remove(itens.get(i));
                break;
            }
        }

    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }


    @Override
    public String toString() {
        return "Funcionario{" +
                "matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", itens=" + itens +
                '}';
    }
}
