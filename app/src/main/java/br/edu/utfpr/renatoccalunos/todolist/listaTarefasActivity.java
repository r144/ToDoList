package br.edu.utfpr.renatoccalunos.todolist;

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

import java.util.ArrayList;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Remind;
import br.edu.utfpr.renatoccalunos.todolist.modelo.Tarefa;

public class listaTarefasActivity extends AppCompatActivity {
    public static final int PEDIRTAREFA = 1;
    private ArrayList<Tarefa> tarefas;
    private ListView listaTarefas;
    private boolean switchHelp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);
        listaTarefas = (ListView) findViewById(R.id.listaTarefas);
        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa t = (Tarefa) listaTarefas.getItemAtPosition(position);
                t.setChecked(!t.getChecked());
                listaTarefas.setItemChecked(position, t.getChecked());
            }
        });
        if(!getOption()){
            Tarefa tarefaHelp = new Tarefa();
            tarefaHelp.setNome("Tarefa Exemplo");
            tarefaHelp.setDescricao("descrição de obsservações desta tarefa");
            tarefaHelp.setProjeto("Projeto que relaciona esta tarefa");
            tarefaHelp.setPrioridade(5);
            tarefas.add(tarefaHelp);
        }
        tarefas = Remind.getInstance().getTarefas();

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
        carregaLista(tarefas);

    }

    private void carregaLista(ArrayList<Tarefa> list) {

        ArrayAdapter<Tarefa> adapter = new ArrayAdapter<Tarefa>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listaTarefas.setAdapter(adapter);
        listaTarefas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editar = menu.add(Menu.NONE, 0, Menu.NONE, "Edit");
        MenuItem deletar = menu.add(Menu.NONE, 1, Menu.NONE, "Delete");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Tarefa tarefa = (Tarefa) listaTarefas.getItemAtPosition(info.position);
                tarefas.remove(tarefa);
                carregaLista(tarefas);
                return false;
            }

        });
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Intent intentVaiPraFormulario = new Intent(listaTarefasActivity.this, FormularioActivity.class);
                intentVaiPraFormulario.putExtra("pos", info.position);
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
            case R.id.menu_lista_sobre:
                Intent intentVaiPraSobre = new Intent(listaTarefasActivity.this, SobreActivity.class);
                startActivity(intentVaiPraSobre);
                break;
            case R.id.menu_switch_help:
                switchHelp = !switchHelp;
        }
        return super.onOptionsItemSelected(item);
    }

    private void storaOptionHelp(boolean option) {
        SharedPreferences shared = getSharedPreferences("help", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean("switchHelp", option);
        editor.apply();
    }

    private boolean getOption(){
        SharedPreferences shared = getSharedPreferences("help", MODE_PRIVATE);
        boolean selectedOption = shared.getBoolean("help", switchHelp);
        return selectedOption;
    }
}
