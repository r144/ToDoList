package br.edu.utfpr.renatoccalunos.todolist.Utils;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import br.edu.utfpr.renatoccalunos.todolist.FormularioActivity;
import br.edu.utfpr.renatoccalunos.todolist.R;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Helper;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

public class FormularioHelper {

    private final EditText campoNome;
    private final Spinner campoProjeto;
    private final EditText campoDescricao;
    private final RatingBar campoPrioridade;

    public FormularioHelper(FormularioActivity activity){
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoProjeto = activity.findViewById(R.id.formulario_projeto);
        campoDescricao = activity.findViewById(R.id.formulario_descricao);
        campoPrioridade = activity.findViewById(R.id.formulario_ratingBar);
    }

    public Tarefa pegaTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(campoNome.getText().toString());
        tarefa.setDescricao(campoDescricao.getText().toString());
        Projeto proj = (Projeto)campoProjeto.getSelectedItem();
        tarefa.setProjetoId(proj.getId());
        tarefa.setPrioridade((int) campoPrioridade.getProgress());
        return tarefa;
    }
}
