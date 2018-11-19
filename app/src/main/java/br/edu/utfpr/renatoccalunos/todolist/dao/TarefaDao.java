package br.edu.utfpr.renatoccalunos.todolist.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

@Dao
public interface TarefaDao {

    @Query("SELECT * from tarefa")
    List<Tarefa> getAll();

    @Query( "SELECT * FROM  tarefa where nome LIKE :nome")
    Tarefa findByName(String nome);

    @Query("SELECT COUNT(*) from tarefa")
    int countUsers();

    @Insert
    void insertAll(Tarefa... tarefas);

    @Delete
    void delete(Tarefa tarefa);
}
