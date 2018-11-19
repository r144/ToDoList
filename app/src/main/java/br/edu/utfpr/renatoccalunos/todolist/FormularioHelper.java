package br.edu.utfpr.renatoccalunos.todolist;

import android.widget.EditText;
import android.widget.RatingBar;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Helper;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

public class FormularioHelper {

    private final EditText campoNome;
//    private final Spinner campoProjeto;
    private final EditText campoDescricao;
    private final RatingBar campoPrioridade;

    public FormularioHelper(FormularioActivity activity){
        campoNome = activity.findViewById(R.id.formulario_nome);
//        campoProjeto = activity.findViewById(R.id.formulario_projeto);
        campoDescricao = activity.findViewById(R.id.formulario_descricao);
        campoPrioridade = activity.findViewById(R.id.formulario_ratingBar);
    }

    public Tarefa pegaTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(campoNome.getText().toString());
        tarefa.setDescricao(campoDescricao.getText().toString());
//        tarefa.setProjeto(campoProjeto.getText().toString());
        tarefa.setPrioridade((int) campoPrioridade.getProgress());
        return tarefa;
    }
}
