package br.edu.utfpr.renatoccalunos.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.edu.utfpr.renatoccalunos.todolist.Utils.FormularioHelper;

public class SobreActivity extends AppCompatActivity {

    FormularioHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
