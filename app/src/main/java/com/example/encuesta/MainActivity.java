package com.example.encuesta;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements IComunicacionFragments, Response.Listener<JSONObject>, Response.ErrorListener,
        v_violencia_pareja_actual.OnFragmentInteractionListener,
        v_violencia_pareja.OnFragmentInteractionListener,
        v_tipo_violencia.OnFragmentInteractionListener,
        v_sufrir_violencia.OnFragmentInteractionListener,
        v_buscar_ayuda.OnFragmentInteractionListener,
        pl_problemas_defensoria.OnFragmentInteractionListener,
        pl_causa_detenido.OnFragmentInteractionListener,
        pl_apresado.OnFragmentInteractionListener,
        ssr_embarazo_plan_vida.OnFragmentInteractionListener,
        ssr_prevenir_its.OnFragmentInteractionListener,
        ssr_situacion_violencia.OnFragmentInteractionListener,
        ssr_edad_quisieras_hijos.OnFragmentInteractionListener,
        ssr_decidir_embarazo.OnFragmentInteractionListener,
        ssr_desicion_uso_anticonceptivos.OnFragmentInteractionListener, ssr_no_anticonceptivo.OnFragmentInteractionListener, ssr_usar_anticonceptivo_actualmente.OnFragmentInteractionListener, ssr_decidir_relaciones.OnFragmentInteractionListener, ssr_enamorado.OnFragmentInteractionListener, ssr_metodos_anticonceptivos.OnFragmentInteractionListener, ssr_oir_anticonceptivo.OnFragmentInteractionListener, ssc_consumir_subtancias.OnFragmentInteractionListener, ssc_problemas_salud_actual.OnFragmentInteractionListener, ssc_probleas_salud_recien.OnFragmentInteractionListener,dp_estado_civil.OnFragmentInteractionListener, ssc_alimento.OnFragmentInteractionListener, db_combustible.OnFragmentInteractionListener, sb_agua.OnFragmentInteractionListener, sb_servicio_sanitario.OnFragmentInteractionListener, sb_cuartos.OnFragmentInteractionListener, sb_tipo_vivienda.OnFragmentInteractionListener, sb_lugar_vives.OnFragmentInteractionListener, ddef_gastar_dinero.OnFragmentInteractionListener, ddef_familia.OnFragmentInteractionListener, dp_direccion.OnFragmentInteractionListener, dp_telefono.OnFragmentInteractionListener, documento.OnFragmentInteractionListener, dp_genero.OnFragmentInteractionListener, Identificacion_geografica.OnFragmentInteractionListener, dp_nombre_completo.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener ,buscar_encuestas.OnFragmentInteractionListener,principal.OnFragmentInteractionListener,emp_pers_8_0_1.OnFragmentInteractionListener,emp_pers_8_0_2.OnFragmentInteractionListener,
        emp_pers_8_0_3.OnFragmentInteractionListener,
        emp_pers_8_0_5.OnFragmentInteractionListener,
        emp_pers_8_0_7.OnFragmentInteractionListener,
        emp_pers_8_0_9.OnFragmentInteractionListener,
        emp_pers_8_0_10.OnFragmentInteractionListener,
        emp_pers_8_0_11.OnFragmentInteractionListener,
        emp_pers_8_0_12.OnFragmentInteractionListener,
        org_9_0_1.OnFragmentInteractionListener,
        org_9_0_4.OnFragmentInteractionListener,
        org_9_0_6.OnFragmentInteractionListener,
        emp_econ_10_0_1.OnFragmentInteractionListener,
        emp_econ_10_0_4.OnFragmentInteractionListener,
        emp_econ_10_0_7.OnFragmentInteractionListener,
        emp_econ_10_0_11.OnFragmentInteractionListener,
        emp_econ_10_0_14.OnFragmentInteractionListener,
        emp_econ_10_0_17.OnFragmentInteractionListener,
        emp_econ_10_0_19.OnFragmentInteractionListener,
        emp_econ_10_0_21.OnFragmentInteractionListener,
        emp_lab_11_0_1.OnFragmentInteractionListener,
        emp_lab_11_0_3.OnFragmentInteractionListener,
        emp_lab_11_0_6.OnFragmentInteractionListener,
        emp_lab_11_0_9.OnFragmentInteractionListener,
        emp_lab_11_0_14.OnFragmentInteractionListener,
        emp_lab_11_0_18.OnFragmentInteractionListener,
        emp_lab_11_0_21.OnFragmentInteractionListener,
        emp_lab_11_0_23.OnFragmentInteractionListener,
        emp_lab_11_0_27.OnFragmentInteractionListener,
        emp_lab_11_0_29.OnFragmentInteractionListener,
        emp_lab_11_0_31.OnFragmentInteractionListener,
        emp_lab_11_0_34.OnFragmentInteractionListener,
        sat_muni_12_0_1.OnFragmentInteractionListener,
        sat_muni_12_0_4.OnFragmentInteractionListener,
        sat_muni_12_0_7.OnFragmentInteractionListener,
        preg_eval_13_0_1.OnFragmentInteractionListener,
        preg_eval_13_0_3.OnFragmentInteractionListener,obs_encuestador.
        OnFragmentInteractionListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //volley
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    //navegarFragments
    String idEncuesta;
    IComunicacionFragments ICF;
    Identificacion_geografica identificacion_geografica;
    dp_nombre_completo dp_nombre_completo;
    dp_genero dp_genero;
    documento dp_documento;
    dp_telefono dp_telefono;
    dp_estado_civil dp_estado_civil;
    dp_direccion dp_direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);
        Bundle parametros = this.getIntent().getExtras();

            String tipo_usuario = parametros.getString("Usuario");
            EsconderItem(tipo_usuario);




        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        request= Volley.newRequestQueue(this);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,new principal());
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void EsconderItem(String Usuario)
    {
        
        switch (Usuario){
            case "Adolescente":
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.lista_encuesta).setVisible(false);
            break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId()==R.id.nueva_encuesta) {
            cargarWebservices();

        }   if (menuItem.getItemId()==R.id.lista_encuesta) {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new buscar_encuestas());
            fragmentTransaction.commit();

        }
        return false;
    }

    private void cargarWebservices() {

        progreso=new ProgressDialog(this);
        progreso.setMessage("cargando...");
        progreso.show();
        String url="http://192.168.0.13/encuestasWS/registroEncuesta.php";
        url=url.replace(" ", "%20");

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this, "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR: ", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try{
            jsonObject=json.getJSONObject(0);
            idEncuesta=jsonObject.optString("id");

        }catch (JSONException e){
            e.printStackTrace();
        }

        progreso.hide();
        Toast.makeText(this, "Encuesta" + idEncuesta, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();

        bundle.putString("idEncuesta", idEncuesta );
        Identificacion_geografica identificacion_geografica = new Identificacion_geografica();
        identificacion_geografica.setArguments(bundle);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, identificacion_geografica).addToBackStack(null).commit();
    }




    @Override
    public void enviarEncuesta2(String idEncuesta) {
        dp_nombre_completo=new dp_nombre_completo();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        dp_nombre_completo.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, dp_nombre_completo).addToBackStack(null).commit();
    }


    public void enviarEncuesta(String idEncuesta){
        identificacion_geografica=new Identificacion_geografica();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        identificacion_geografica.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, identificacion_geografica).addToBackStack(null).commit();
    }

    public void enviarEncuesta3(String idEncuesta){
        dp_genero=new dp_genero();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        dp_genero.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, dp_genero).addToBackStack(null).commit();
    }

    public void enviarEncuesta4(String idEncuesta){
        dp_documento=new documento();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        dp_documento.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, dp_documento).addToBackStack(null).commit();
    }


    public void enviarEncuesta5(String idEncuesta){
        dp_telefono=new dp_telefono();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        dp_telefono.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, dp_telefono).addToBackStack(null).commit();
    }


    public void enviarEncuesta6(String idEncuesta){
        dp_estado_civil=new dp_estado_civil();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        dp_estado_civil.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, dp_estado_civil).addToBackStack(null).commit();
    }

    public void enviarEncuesta7(String idEncuesta){
        dp_direccion=new dp_direccion();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        dp_direccion.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, dp_direccion).addToBackStack(null).commit();
    }
}
