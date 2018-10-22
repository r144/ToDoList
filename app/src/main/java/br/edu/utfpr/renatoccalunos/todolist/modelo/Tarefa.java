package br.edu.utfpr.renatoccalunos.todolist.modelo;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private long id;
    private String nome;
    private String descricao;
    private String projeto;
    private int prioridade;
    private Boolean checked = false;

    public Tarefa() {
        Helper helper = Helper.SingletoonHelper();
        this.id = Helper.getId();
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setId() {
        this.id++;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return getNome();

    }
}
