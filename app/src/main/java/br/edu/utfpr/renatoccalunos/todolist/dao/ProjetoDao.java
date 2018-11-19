package br.edu.utfpr.renatoccalunos.todolist.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

public interface ProjetoDao {

    @Query("SELECT * from projeto")
    List<Tarefa> getAll();

    @Query( "SELECT * FROM  projeto where nome LIKE :nome")
    Tarefa findByName(String nome);

    @Query("SELECT COUNT(*) from projeto")
    int countUsers();

    @Insert
    void insertAll(Projeto... projetos);

    @Delete
    void delete(Projeto projeto);
}

