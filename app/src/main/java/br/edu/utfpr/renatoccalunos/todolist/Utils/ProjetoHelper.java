package br.edu.utfpr.renatoccalunos.todolist.Utils;

import android.widget.EditText;

import br.edu.utfpr.renatoccalunos.todolist.ProjetoActivity;
import br.edu.utfpr.renatoccalunos.todolist.R;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;

public class ProjetoHelper {

    private final EditText campoNome;
    private final EditText campoDescricao;

    public ProjetoHelper(ProjetoActivity activity){
        campoNome = activity.findViewById(R.id.projeto_nome);
        campoDescricao = activity.findViewById(R.id.projeto_nome);
    }

    public Projeto pegaProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome(campoNome.getText().toString());
        projeto.setDescricao(campoDescricao.getText().toString());
        return projeto;
    }
}
