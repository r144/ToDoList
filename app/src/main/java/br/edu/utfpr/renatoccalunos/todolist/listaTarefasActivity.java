package br.edu.utfpr.renatoccalunos.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.Utils.UtilsGUI;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;
import br.edu.utfpr.renatoccalunos.todolist.persistencia.ToDoListDatabase;

public class listaTarefasActivity extends AppCompatActivity {
    public static final int PEDIRTAREFA = 1;
    private List<Tarefa> tasks;
    private ListView listaTarefas;
    private boolean switchHelp = true;
    private ToDoListDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = ToDoListDatabase.getDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);
        listaTarefas = (ListView) findViewById(R.id.listaTarefas);
        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa t = (Tarefa) listaTarefas.getItemAtPosition(position);
                t.setChecked(!t.getChecked());
                database.tarefaDao().update(t);
                listaTarefas.setItemChecked(position, t.getChecked());
            }
        });
//        if(!getOption()){
//            Tarefa tarefaHelp = new Tarefa();
//            tarefaHelp.setNome("Tarefa Exemplo");
//            tarefaHelp.setDescricao("descrição de obsservações desta tarefa");
//            tarefaHelp.setProjeto("Projeto que relaciona esta tarefa");
//            tarefaHelp.setPrioridade(5);
//            tarefas.add(tarefaHelp);
//        }
//        tarefas = Remind.getInstance().getTarefas();

        Button buttonNovaTarefa = (Button) findViewById(R.id.nova_tarefa);
        buttonNovaTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(listaTarefasActivity.this, FormularioActivity.class);
                startActivityForResult(intentVaiProFormulario, PEDIRTAREFA);
            }
        });
        registerForContextMenu(listaTarefas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();

    }

    private void carregaLista() {
        ToDoListDatabase database = ToDoListDatabase.getDatabase(this);

        List<Tarefa> list = database.tarefaDao().getAll();
        ArrayAdapter<Tarefa> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listaTarefas.setAdapter(adapter);
        listaTarefas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        for (int i = 0; i < list.size(); i++) {
            listaTarefas.setItemChecked(i, list.get(i).getChecked());
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editar = menu.add(Menu.NONE, 0, Menu.NONE, "Edit");
        MenuItem deletar = menu.add(Menu.NONE, 1, Menu.NONE, "Delete");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                final Tarefa tarefa = (Tarefa) listaTarefas.getItemAtPosition(info.position);

                String mensagem = getString(R.string.delete_message)
                        + "\n" + getString(R.string.tarefa) + ": " + tarefa.getNome();

                DialogInterface.OnClickListener listener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:

                                        ToDoListDatabase database =
                                                ToDoListDatabase.getDatabase(listaTarefasActivity.this);
                                        database.tarefaDao().delete(tarefa);
                                        carregaLista();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:

                                        break;
                                }
                            }
                        };

                UtilsGUI.confirmaAcao(listaTarefasActivity.this, mensagem, listener);


                tasks = database.tarefaDao().getAll();
//                tarefas.remove(tarefa);
                carregaLista();
                return false;
            }

        });
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Intent intentVaiPraFormulario = new Intent(listaTarefasActivity.this, FormularioActivity.class);
                tasks = database.tarefaDao().getAll();
                long id = tasks.get((int) info.id).getId();
                intentVaiPraFormulario.putExtra("id", id);
                startActivity(intentVaiPraFormulario);
                Toast.makeText(listaTarefasActivity.this, "edit", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_tarefas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_lista_projeto:
                Intent intentVaiPraProjetos = new Intent(listaTarefasActivity.this, listaProjetosActivity.class);
                startActivity(intentVaiPraProjetos);
                break;
            case R.id.menu_lista_sobre:
                Intent intentVaiPraSobre = new Intent(listaTarefasActivity.this, SobreActivity.class);
                startActivity(intentVaiPraSobre);
                break;
            case R.id.menu_switch_help:
                switchHelp = !switchHelp;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void storaOptionHelp(boolean option) {
        SharedPreferences shared = getSharedPreferences("help", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean("switchHelp", option);
        editor.apply();
    }

    private boolean getOption() {
        SharedPreferences shared = getSharedPreferences("help", MODE_PRIVATE);
        boolean selectedOption = shared.getBoolean("help", switchHelp);
        return selectedOption;
    }
}
