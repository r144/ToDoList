package br.edu.utfpr.renatoccalunos.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.utfpr.renatoccalunos.todolist.modelo.Projeto;
import br.edu.utfpr.renatoccalunos.todolist.persistencia.ToDoListDatabase;

public class listaProjetosActivity extends AppCompatActivity {
    private ListView listaProjetos;
    private ActionMode actionMode;
    private int pos = -1;
    private ToDoListDatabase database = ToDoListDatabase.getDatabase(this);
    private ActionMode.Callback actionCb = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.menu_lista_projetos, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_projeto_edit:
                    edit();
                    mode.finish();
                    return true;
                case R.id.menu_projeto_delete:
                    delete();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_projetos);
        listaProjetos = (ListView) findViewById(R.id.listaProjetos);
        carregaLista();

        listaProjetos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (actionMode != null) {
                            return false;
                        }
                        pos = position;
                        listaProjetos.setEnabled(false);
                        actionMode = startSupportActionMode(actionCb);

                        return true;
                    }
                });
    }

    private void edit(){

    }
    private void delete(){
        Projeto projeto = (Projeto) listaProjetos.getItemAtPosition(pos);
        database.projetoDao().delete(projeto);
        carregaLista();

    }
    private void carregaLista() {
        ToDoListDatabase database = ToDoListDatabase.getDatabase(this);
        listaProjetos.setAdapter(null);
        List<Projeto> list = database.projetoDao().getAll();
        ArrayAdapter<Projeto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listaProjetos.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();

    }
}
