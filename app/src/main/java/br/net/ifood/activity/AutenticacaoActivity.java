package br.net.ifood.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.net.ifood.R;
import br.net.ifood.helper.ConfiguracaoFirebase;

public class AutenticacaoActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();

        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        // VERIFICAR USUÁRIO LOGADO
        verificaUsuarioLogado();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                if( !email.isEmpty() ) {
                    if( !senha.isEmpty() ) {

                        // VERIFICANDO O ESTADO DO SWITCH
                        if( tipoAcesso.isChecked() ) {
                            // FAZENDO O CADASTRO DO USUÁRIO
                            autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        // INFORMANDO AO USUÁRIO QUE O CADASTRO OCORREU DA FORMA CORRETA
                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Cadastro realizado com Sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        // LEVANDO O USUÁRIO PARA A TELA HOME
                                        abrirTelaPrincipal();
                                    } else {
                                        String erroExcecao = "";

                                        // AS MENSAGENS DE ERRO NO MOMENTO DE CRIAR O CADASTRO DO USUÁRIO
                                        try{
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e) {
                                            erroExcecao = "Digite uma senha mais forte";
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Por favor, digite um e-mail válido!";
                                        }catch (FirebaseAuthUserCollisionException e) {
                                            erroExcecao = "Essa conta já foi criada!";
                                        } catch (Exception e) {
                                            erroExcecao = "ao cadastrar o usuário " + e.getMessage();
                                            e.printStackTrace();
                                        }
                                        // MOSTRANDO A MENSAGEM DE ERRO PARA O NOSSO USUÁRIO
                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Erro: " + erroExcecao,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // FAZENDO O LOGIN DO USUÁRIO
                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Logado com Sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        abrirTelaPrincipal();
                                    } else {
                                        Toast.makeText(AutenticacaoActivity.this,
                                                "Erro ao fazer login!" + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(AutenticacaoActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AutenticacaoActivity.this,
                            "Preencha o e-mail!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // VERIFICANDO USUÁRIO LOGADO
    private void verificaUsuarioLogado(){
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if( usuarioAtual != null ) {
            abrirTelaPrincipal();
        }
    }

    // MÉTODO PARA ABRIR A TELA HOME DO APP
    private void abrirTelaPrincipal(){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    private void inicializarComponentes() {
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);
    }
}