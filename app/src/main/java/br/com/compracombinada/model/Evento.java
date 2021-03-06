package br.com.compracombinada.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class Evento implements Serializable {

    private Integer id;

    private String nome;

    private String dataHora;

    private List<Local> locais;

    private List<Lista> listas;

    private List<Usuario> usuarioConvidados;

    private boolean temCotacao;

    private Usuario usuario;

    private boolean acabouCotacao;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Local> getLocais() {
        return locais;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public List<Usuario> getUsuarioConvidados() {
        return usuarioConvidados;
    }

    public void setUsuarioConvidados(List<Usuario> usuarioConvidados) {
        this.usuarioConvidados = usuarioConvidados;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isTemCotacao() {
        return temCotacao;
    }

    public void setTemCotacao(boolean temCotacao) {
        this.temCotacao = temCotacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isAcabouCotacao() {
        return acabouCotacao;
    }

    public void setAcabouCotacao(boolean acabouCotacao) {
        this.acabouCotacao = acabouCotacao;
    }
}
