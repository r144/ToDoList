package br.edu.utfpr.renatoccalunos.todolist.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import br.edu.utfpr.renatoccalunos.todolist.persistencia.ProjetoDao;

@Entity(tableName = "tarefa")
public class Tarefa {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "descricao")
    private String descricao;

    @ColumnInfo(name = "projetoId")
    private int projetoId;

    @ColumnInfo(name = "prioridade")
    private int prioridade;

    @ColumnInfo(name = "checked")
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

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
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
