package br.net.ifood.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.net.ifood.R;

public class AutenticacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();
    }
}