package br.edu.utfpr.renatoccalunos.todolist.modelo;

import java.util.Random;
import java.util.UUID;

public class Helper {
    private static Helper instancia;
    private static int id = 0;
    public static Helper SingletoonHelper() {
        if (instancia == null) {
            instancia = new Helper();
        }
        return instancia;
    }

    public static int getId() {
        return id++;
    }

    public static void setId(int id) {
        Helper.id = id;
    }
}
