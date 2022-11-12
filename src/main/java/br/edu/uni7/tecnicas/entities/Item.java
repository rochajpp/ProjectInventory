package br.edu.uni7.tecnicas.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.OneToMany;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Item {
    @Id
    private Integer identificador;
    private LocalDate dataDeInclusao;
    private String fabricante;
    private String modelo;
    private Integer anoDeFabricacao;
    private LocalDate ultimaAtualizacao;
    @ManyToOne
    private Funcionario funcionario = null;



    public Item(){

    }

    public Integer gerarNovoIdentificador(){
        return (int)Math.floor(Math.random() * (9999 - 1000) + 1000);
    }
    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public LocalDate getDataDeInclusao() {
        return dataDeInclusao;
    }

    public void setDataDeInclusao(LocalDate dataDeInclusao) {
        this.dataDeInclusao = dataDeInclusao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void setAnoDeFabricacao(Integer anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
    public Funcionario getFuncionario(){
        return this.funcionario;
    }
    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
    }


    @Override
    public String toString() {
        return "Item{" +
                "identificador='" + identificador + '\'' +
                ", dataDeInclusao=" + dataDeInclusao +
                ", fabricante='" + fabricante + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anoDeFabricacao=" + anoDeFabricacao +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                '}';
    }
}
