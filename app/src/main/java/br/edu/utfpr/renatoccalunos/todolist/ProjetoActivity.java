package br.edu.utfpr.renatoccalunos.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import br.edu.utfpr.renatoccalunos.todolist.Utils.ProjetoHelper;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;
import br.edu.utfpr.renatoccalunos.todolist.persistencia.ToDoListDatabase;

public class ProjetoActivity extends AppCompatActivity {

    ProjetoHelper helper;
    private int idProjetoEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new ProjetoHelper(this);


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
        Projeto projeto = helper.pegaProjeto();

        if (validaCampos(projeto)) {
            Toast.makeText(ProjetoActivity.this, "Projeto " + projeto.getNome() + " Criado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, listaTarefasActivity.class);
//            taskList = Remind.getInstance().getTarefas();
            if (idProjetoEdit != -1) {
//                taskList.remove(idTarefaEdit);
            }

            ToDoListDatabase database = ToDoListDatabase.getDatabase(this);
            database.projetoDao().insertAll(projeto);
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

    private boolean validaCampos(Projeto tarefa) {
        return !tarefa.getNome().equals("") && !tarefa.getDescricao().equals("");
    }


}
