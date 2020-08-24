package com.example.encuesta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class login extends AppCompatActivity {

    Button btnAcceder;
    RadioButton   rdAdolescente,rdEncuestador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAcceder=(Button)findViewById(R.id.btnAcceder);
        rdAdolescente=(RadioButton)findViewById(R.id.rdIdAdolescente);
        rdEncuestador=(RadioButton)findViewById(R.id.rdIdEncuestador);
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaPrincipal = new Intent(login.this, MainActivity.class);
                if(rdAdolescente.isChecked()==true) {

                    Bundle ParametrosLogin = new Bundle();

                    ParametrosLogin.putString("Usuario","Adolescente");
                    pantallaPrincipal.putExtras(ParametrosLogin);
                    startActivity(pantallaPrincipal);

                }else if(rdEncuestador.isChecked()==true) {

                    Bundle ParametrosLogin = new Bundle();

                    ParametrosLogin.putString("Usuario","Enceustador");
                    pantallaPrincipal.putExtras(ParametrosLogin);
                    startActivity(pantallaPrincipal);
                }
            }
        });

    }

}
