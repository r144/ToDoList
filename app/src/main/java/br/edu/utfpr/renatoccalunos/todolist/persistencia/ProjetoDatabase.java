package br.edu.utfpr.renatoccalunos.todolist.persistencia;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;


@Database(entities = {Projeto.class}, version = 1, exportSchema = false)
public abstract class ProjetoDatabase extends RoomDatabase {

    public abstract ProjetoDao projetoDao();

    private static ProjetoDatabase instance;

    public static ProjetoDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (ProjetoDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                                                    ProjetoDatabase.class,
                                                    "projeto.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
