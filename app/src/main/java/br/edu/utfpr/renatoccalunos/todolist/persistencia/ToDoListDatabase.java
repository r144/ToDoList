package br.edu.utfpr.renatoccalunos.todolist.persistencia;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;


@Database(entities = {Projeto.class, Tarefa.class}, version = 1, exportSchema = false)
public abstract class ToDoListDatabase extends RoomDatabase {

    public abstract ProjetoDao projetoDao();
    public abstract TarefaDao tarefaDao();

    private static ToDoListDatabase instance;

    public static ToDoListDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (ToDoListDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                                                    ToDoListDatabase.class,
                                                    "todolist.db").allowMainThreadQueries()
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
