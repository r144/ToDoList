package br.edu.utfpr.renatoccalunos.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.Utils.FormularioHelper;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;
import br.edu.utfpr.renatoccalunos.todolist.persistencia.ToDoListDatabase;

public class FormularioActivity extends AppCompatActivity {

    FormularioHelper helper;
    public static final String TAREFA = "TAREFA";
//    public static final in = 2;

    //    private ArrayList<Tarefa> taskList;
    private long idTarefaEdit = -1;
    private Tarefa tarefa;
    private ToDoListDatabase database;
    private Spinner campoProjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new FormularioHelper(this);
        Intent intent = getIntent();
        idTarefaEdit = intent.getLongExtra("id", -1);
        database = ToDoListDatabase.getDatabase(this);
        campoProjeto = this.findViewById(R.id.formulario_projeto);

        Button buttonNovoProjeto = (Button) findViewById(R.id.novo_projeto);
        buttonNovoProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(FormularioActivity.this, ProjetoActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });
        if (idTarefaEdit != -1) {

            EditText campoNome = this.findViewById(R.id.formulario_nome);
            EditText campoDescricao = this.findViewById(R.id.formulario_descricao);
            RatingBar campoPrioridade = this.findViewById(R.id.formulario_ratingBar);
            Spinner campoProjeto = this.findViewById(R.id.formulario_projeto);


            List<Tarefa> tarefas = database.tarefaDao().getAll();
            tarefa = recuperaObjTarefa(tarefas, idTarefaEdit);

            campoNome.setText(tarefa.getNome());
            campoDescricao.setText(tarefa.getDescricao());

            campoPrioridade.setProgress(tarefa.getPrioridade());

//            Tarefa t = Remind.getInstance().getTarefas().get(idTarefaEdit);
//            Tarefa t = database.tarefaDao().findById(idTarefaEdit);


        }
    }

    private Tarefa recuperaObjTarefa(List<Tarefa> tarefas, long id) {
        for (int i = 0; i < tarefas.size(); i++) {
            if (tarefas.get(i).getId() == id) {
                return tarefas.get(i);
            }
        }
        return new Tarefa();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Projeto> list = database.projetoDao().getAll();
        populaSpinner(list);
        int idselect = 0;
        if (tarefa != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == tarefa.getProjetoId()) {
                    campoProjeto.setSelection(i);
                }
            }
        }
    }

    private void populaSpinner(List<Projeto> list) {
        ArrayAdapter<Projeto> adapter = new
                ArrayAdapter<Projeto>(this,
                android.R.layout.simple_spinner_item,
                list);
        campoProjeto.setAdapter(adapter);
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
        ToDoListDatabase database = ToDoListDatabase.getDatabase(this);

        if (validaCampos(tarefa)) {
            Toast.makeText(FormularioActivity.this, "Tarefa " + tarefa.getNome() + " Criada!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, listaTarefasActivity.class);
//            taskList = Remind.getInstance().getTarefas();
            if (idTarefaEdit != -1) {
                List<Tarefa> listTasks = database.tarefaDao().getAll();
                database.tarefaDao().delete(recuperaObjTarefa(listTasks, idTarefaEdit));
            }

            database.tarefaDao().insertAll(tarefa);
//            taskList.add(tarefa);
            finish();
        } else {
            ErrorCampos();
        }

    }

    private void ErrorCampos() {
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

    private boolean validaCampos(Tarefa tarefa) {
        return !tarefa.getNome().equals("") && tarefa.getProjetoId() >= 0 && !tarefa.getDescricao().equals("");
    }
}
