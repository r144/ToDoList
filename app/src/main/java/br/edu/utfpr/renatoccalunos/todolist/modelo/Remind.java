package br.edu.utfpr.renatoccalunos.todolist.modelo;

import java.util.ArrayList;

public class Remind {
    private static Remind uniqueInstance;
    private ArrayList<Tarefa> tarefas;
    private Remind(){
        this.tarefas = new ArrayList<Tarefa>();
    }

    public ArrayList<Tarefa> getTarefas() {
        return tarefas;
    }

    public static Remind getInstance() {
        return uniqueInstance == null ? uniqueInstance = new Remind() : uniqueInstance;
    }
}
