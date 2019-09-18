package br.net.ifood.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.net.ifood.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirAutenticacao();
            }
        }, 3000);
    }
    private void abrirAutenticacao(){
        Intent abrirTelaLogin = new Intent(MainActivity.this, AutenticacaoActivity.class);
        startActivity(abrirTelaLogin);
        finish();
    }
}
