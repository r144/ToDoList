package br.edu.utfpr.renatoccalunos.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Remind;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

public class FormularioActivity extends AppCompatActivity {

    FormularioHelper helper;
    public static final String TAREFA = "TAREFA";
    private ArrayList<Tarefa> tarefas;
    private int idTarefaEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new FormularioHelper(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            EditText campoNome = this.findViewById(R.id.formulario_nome);
            EditText campoProjeto = this.findViewById(R.id.formulario_projeto);
            EditText campoDescricao = this.findViewById(R.id.formulario_descricao);
            RatingBar campoPrioridade = this.findViewById(R.id.formulario_ratingBar);

            idTarefaEdit = bundle.getInt("pos");

            Tarefa t = Remind.getInstance().getTarefas().get(idTarefaEdit);

            campoNome.setText(t.getNome());
            campoProjeto.setText(t.getProjeto());
            campoDescricao.setText(t.getDescricao());
            campoPrioridade.setProgress(t.getPrioridade());


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fomulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                salvar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {
        Tarefa tarefa = helper.pegaTarefa();

        if (validaCampos(tarefa)) {
            Toast.makeText(FormularioActivity.this, "Tarefa " + tarefa.getNome() + " Criada!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, listaTarefasActivity.class);
            tarefas = Remind.getInstance().getTarefas();
            if (idTarefaEdit != -1) {
                tarefas.remove(idTarefaEdit);
            }
            tarefas.add(tarefa);
            finish();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Erro");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage("Preencha os dados corretamente");

            builder.setNeutralButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    private boolean validaCampos(Tarefa tarefa) {
        return !tarefa.getNome().equals("") && !tarefa.getProjeto().equals("") && !tarefa.getDescricao().equals("");
    }
}
