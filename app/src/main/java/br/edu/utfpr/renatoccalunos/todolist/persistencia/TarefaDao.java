package br.edu.utfpr.renatoccalunos.todolist.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

@Dao
public interface TarefaDao {

    @Query("SELECT * from tarefa")
    List<Tarefa> getAll();

    @Query( "SELECT * FROM  tarefa where nome LIKE :nome")
    Tarefa findByName(String nome);

    @Query( "SELECT * FROM  tarefa where id = :id LIMIT 1")
    Tarefa findById(long id);

    @Query("SELECT COUNT(*) from tarefa")
    int countUsers();

    @Insert
    void insertAll(Tarefa... tarefas);

    @Delete
    void delete(Tarefa tarefa);

    @Update
    void update(Tarefa tarefa);
}
