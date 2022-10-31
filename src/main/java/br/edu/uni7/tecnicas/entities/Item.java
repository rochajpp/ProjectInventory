package br.edu.uni7.tecnicas.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Item {
    @Id
    private Integer indetificador;
    private Date dataDeInclusao;
    private String fabricante;
    private String modelo;
    private Integer anoDeFabricacao;
    private Date ultimaAtualizacao;



    public Integer getIndetificador() {
        return indetificador;
    }

    public void setIndetificador(Integer indetificador) {
        this.indetificador = indetificador;
    }

    public Date getDataDeInclusao() {
        return dataDeInclusao;
    }

    public void setDataDeInclusao(Date dataDeInclusao) {
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

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}
