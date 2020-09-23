package com.example.encuesta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    Button btnAcceder;
    RadioButton rdAdolescente,rdEncuestador;
    EditText txtUsuario, txtcontra;
    TextView txtusuarioAlert, txtContraAlert;
    LinearLayout displayUsuario, displayContra;
    TextWatcher text=null, textContra=null;
    String contra, idUsuario="0";
    //volley

    ProgressDialog progreso;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAcceder=(Button)findViewById(R.id.btnAcceder);
        rdAdolescente=(RadioButton)findViewById(R.id.rdIdAdolescente);
        rdEncuestador=(RadioButton)findViewById(R.id.rdIdEncuestador);
        txtUsuario=(EditText) findViewById(R.id.txtusuario);
        txtcontra=(EditText) findViewById(R.id.txtcontra);

        txtusuarioAlert=(TextView) findViewById(R.id.txtusuarioalert);
        txtContraAlert=(TextView) findViewById(R.id.txtcontraalert);

        displayUsuario=(LinearLayout) findViewById(R.id.layoutUsuario);
        displayContra=(LinearLayout) findViewById(R.id.layoutContra);
        btnAcceder.setEnabled(false);

        displayContra.setVisibility(View.INVISIBLE);
        displayContra.setVisibility(View.GONE);

        displayUsuario.setVisibility(View.INVISIBLE);
        displayUsuario.setVisibility(View.GONE);

        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<8){
                    txtusuarioAlert.setText("Debe tener 8 caracteres minimo.");
                    btnAcceder.setEnabled(false);
                }else{
                    btnAcceder.setEnabled(true);
                    txtusuarioAlert.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        txtUsuario.addTextChangedListener(text);


        textContra = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contra=txtcontra.getText().toString();
                if(contra.equals("aacbar2020")){
                    btnAcceder.setEnabled(true);
                    txtContraAlert.setText("");

                }else{
                    txtContraAlert.setText("¡Contraseña Incorrecta!");
                    btnAcceder.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        txtcontra.addTextChangedListener(textContra);

        rdAdolescente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayContra.setVisibility(View.INVISIBLE);
                displayContra.setVisibility(View.GONE);

                displayUsuario.setVisibility(View.VISIBLE);
                btnAcceder.setEnabled(false);
                txtContraAlert.setText("");
            }
        });

        rdEncuestador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayUsuario.setVisibility(View.INVISIBLE);
                displayUsuario.setVisibility(View.GONE);

                displayContra.setVisibility(View.VISIBLE);
                btnAcceder.setEnabled(false);
                txtusuarioAlert.setText("");
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usuario();

            }
        });



    }
    private void usuario(){
        progreso=new ProgressDialog(this);
        progreso.setMessage("cargando...");
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"registroUsuario.php?usuario="+txtUsuario.getText().toString();
        url=url.replace(" ", "%20");

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json=response.optJSONArray("usuario");
            JSONObject jsonObject=null;

            try{
                jsonObject=json.getJSONObject(0);
                idUsuario=jsonObject.optString("id");
                progreso.hide();
                Toast.makeText(this, "Usuario: " + idUsuario, Toast.LENGTH_SHORT).show();
                Intent pantallaPrincipal = new Intent(login.this, MainActivity.class);

                if(rdAdolescente.isChecked()) {

                    Bundle ParametrosLogin = new Bundle();

                    ParametrosLogin.putString("Usuario","Adolescente");
                    ParametrosLogin.putString("usu",idUsuario);
                    pantallaPrincipal.putExtras(ParametrosLogin);
                    startActivity(pantallaPrincipal);

                }else if(rdEncuestador.isChecked()) {

                    Bundle ParametrosLogin = new Bundle();

                    ParametrosLogin.putString("Usuario","Enceustador");
                    ParametrosLogin.putString("usu","0");
                    pantallaPrincipal.putExtras(ParametrosLogin);
                    startActivity(pantallaPrincipal);
                }

            }catch (JSONException e){
                e.printStackTrace();
            }



        }, error -> {
            Toast.makeText(this, "No se pudo registrar usuario " + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });

        volleySingleton.getInstanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }
}
