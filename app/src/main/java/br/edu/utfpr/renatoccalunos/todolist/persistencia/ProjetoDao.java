package br.edu.utfpr.renatoccalunos.todolist.persistencia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;

@Dao
public interface ProjetoDao {

    @Query("SELECT * from projeto")
    List<Projeto> getAll();

    @Query("SELECT projeto.nome from projeto")
    List<String> getAllNames();

    @Query( "SELECT * FROM  projeto where nome LIKE :nome")
    Projeto findByName(String nome);

    @Query("SELECT COUNT(*) from projeto")
    int countUsers();

    @Insert
    void insertAll(Projeto... projetos);

    @Delete
    void delete(Projeto projeto);
}

