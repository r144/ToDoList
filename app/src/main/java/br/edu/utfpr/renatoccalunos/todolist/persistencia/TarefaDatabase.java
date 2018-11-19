package br.edu.utfpr.renatoccalunos.todolist.persistencia;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;


@Database(entities = {Tarefa.class}, version = 1, exportSchema = false)
public abstract class TarefaDatabase extends RoomDatabase {

    public abstract TarefaDao tarefaDao();

    private static TarefaDatabase instance;

    public static TarefaDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (TarefaDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                                                    TarefaDatabase.class,
                                                    "tarefa.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
