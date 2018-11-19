package br.edu.utfpr.renatoccalunos.todolist.controller;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.edu.utfpr.renatoccalunos.todolist.dao.TarefaDao;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

@Database(entities = {Tarefa.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCIA;

    public abstract TarefaDao tarefaDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCIA;
    }

    public static void destroyInstance() {
        INSTANCIA = null;
    }
}
