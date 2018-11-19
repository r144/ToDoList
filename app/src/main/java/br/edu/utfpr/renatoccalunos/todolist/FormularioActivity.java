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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Remind;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;
import br.edu.utfpr.renatoccalunos.todolist.persistencia.ProjetoDatabase;
import br.edu.utfpr.renatoccalunos.todolist.persistencia.TarefaDatabase;

public class FormularioActivity extends AppCompatActivity {

    FormularioHelper helper;
    public static final String TAREFA = "TAREFA";
    public static final int PEDIRPROJETO = 2;

    //    private ArrayList<Tarefa> taskList;
    private int idTarefaEdit = -1;
    private Tarefa tarefa;
    Spinner campoProjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new FormularioHelper(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Button buttonNovoProjeto = (Button) findViewById(R.id.novo_projeto);
        buttonNovoProjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(FormularioActivity.this, ProjetoActivity.class);
                startActivityForResult(intentVaiProFormulario, PEDIRPROJETO);
            }
        });
        if (bundle != null) {
            EditText campoNome = this.findViewById(R.id.formulario_nome);
            campoProjeto = this.findViewById(R.id.formulario_projeto);
            EditText campoDescricao = this.findViewById(R.id.formulario_descricao);
            RatingBar campoPrioridade = this.findViewById(R.id.formulario_ratingBar);
            campoProjeto.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

            idTarefaEdit = bundle.getInt("pos");

            Tarefa t = Remind.getInstance().getTarefas().get(idTarefaEdit);

            campoNome.setText(t.getNome());
            campoDescricao.setText(t.getDescricao());
            campoPrioridade.setProgress(t.getPrioridade());


        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        ProjetoDatabase database = ProjetoDatabase.getDatabase(this);
        List<String> list = database.projetoDao().getAllNames();
        populaSpinner(list);
    }

    private void populaSpinner(List<String> list) {
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        if (validaCampos(tarefa)) {
            Toast.makeText(FormularioActivity.this, "Tarefa " + tarefa.getNome() + " Criada!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, listaTarefasActivity.class);
//            taskList = Remind.getInstance().getTarefas();
            if (idTarefaEdit != -1) {
//                taskList.remove(idTarefaEdit);
            }

            TarefaDatabase database = TarefaDatabase.getDatabase(this);
            database.tarefaDao().insertAll(tarefa);
//            taskList.add(tarefa);
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
