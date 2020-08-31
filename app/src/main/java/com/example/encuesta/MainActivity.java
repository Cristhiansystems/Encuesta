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
    ddef_familia ddef_familia;
    ddef_gastar_dinero ddef_gastar_dinero;
    sb_lugar_vives sb_lugar_vives;
    sb_tipo_vivienda sb_tipo_vivienda;
    sb_cuartos sb_cuartos;
    sb_servicio_sanitario sb_servicio_sanitario;
    sb_agua sb_agua;
    db_combustible db_combustible;
    ssc_alimento ssc_alimento;
    ssc_problemas_salud_actual ssc_problemas_salud_actual;
    ssc_probleas_salud_recien ssc_probleas_salud_recien;
    ssc_consumir_subtancias ssc_consumir_subtancias;
    ssr_oir_anticonceptivo ssr_oir_anticonceptivo;
    ssr_metodos_anticonceptivos ssr_metodos_anticonceptivos;
    ssr_enamorado ssr_enamorado;
    ssr_decidir_relaciones ssr_decidir_relaciones;
    ssr_usar_anticonceptivo_actualmente ssr_usar_anticonceptivo_actualmente;
    ssr_no_anticonceptivo ssr_no_anticonceptivo;
    ssr_desicion_uso_anticonceptivos ssr_desicion_uso_anticonceptivos;
    ssr_decidir_embarazo ssr_decidir_embarazo;
    ssr_edad_quisieras_hijos ssr_edad_quisieras_hijos;
    ssr_situacion_violencia ssr_situacion_violencia;
    ssr_prevenir_its ssr_prevenir_its;
    ssr_embarazo_plan_vida ssr_embarazo_plan_vida;
    pl_apresado pl_apresado;
    pl_causa_detenido pl_causa_detenido;
    pl_problemas_defensoria pl_problemas_defensoria;
    v_tipo_violencia v_tipo_violencia;
    v_sufrir_violencia v_sufrir_violencia;
    v_violencia_pareja v_violencia_pareja;
    v_violencia_pareja_actual v_violencia_pareja_actual;
    v_buscar_ayuda v_buscar_ayuda;
    emp_pers_8_0_1 emp_pers_8_0_1;
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


    public void enviarEncuesta8(String idEncuesta){
        ddef_familia=new ddef_familia();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ddef_familia.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ddef_familia).addToBackStack(null).commit();
    }


    public void enviarEncuesta9(String idEncuesta){
        ddef_gastar_dinero=new ddef_gastar_dinero();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ddef_gastar_dinero.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ddef_gastar_dinero).addToBackStack(null).commit();
    }

    public void enviarEncuesta10(String idEncuesta){
        sb_lugar_vives=new sb_lugar_vives();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        sb_lugar_vives.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, sb_lugar_vives).addToBackStack(null).commit();
    }


    public void enviarEncuesta11(String idEncuesta){
        sb_tipo_vivienda=new sb_tipo_vivienda();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        sb_tipo_vivienda.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, sb_tipo_vivienda).addToBackStack(null).commit();
    }


    public void enviarEncuesta12(String idEncuesta){
        sb_cuartos=new sb_cuartos();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        sb_cuartos.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, sb_cuartos).addToBackStack(null).commit();
    }

    public void enviarEncuesta13(String idEncuesta){
        sb_servicio_sanitario=new sb_servicio_sanitario();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        sb_servicio_sanitario.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, sb_servicio_sanitario).addToBackStack(null).commit();
    }


    public void enviarEncuesta14(String idEncuesta){
        sb_agua=new sb_agua();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        sb_agua.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, sb_agua).addToBackStack(null).commit();
    }

    public void enviarEncuesta15(String idEncuesta){
        db_combustible=new db_combustible();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        db_combustible.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, db_combustible).addToBackStack(null).commit();
    }

    public void enviarEncuesta16(String idEncuesta){
        ssc_alimento=new ssc_alimento();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssc_alimento.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssc_alimento).addToBackStack(null).commit();
    }


    public void enviarEncuesta17(String idEncuesta){
        ssc_problemas_salud_actual=new ssc_problemas_salud_actual();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssc_problemas_salud_actual.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssc_problemas_salud_actual).addToBackStack(null).commit();
    }

    public void enviarEncuesta18(String idEncuesta){
        ssc_probleas_salud_recien=new ssc_probleas_salud_recien();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssc_probleas_salud_recien.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssc_probleas_salud_recien).addToBackStack(null).commit();
    }

    public void enviarEncuesta19(String idEncuesta){
        ssc_consumir_subtancias=new ssc_consumir_subtancias();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssc_consumir_subtancias.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssc_consumir_subtancias).addToBackStack(null).commit();
    }

    public void enviarEncuesta20(String idEncuesta){
        ssr_oir_anticonceptivo=new ssr_oir_anticonceptivo();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_oir_anticonceptivo.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_oir_anticonceptivo).addToBackStack(null).commit();
    }

    public void enviarEncuesta21(String idEncuesta){
        ssr_metodos_anticonceptivos=new ssr_metodos_anticonceptivos();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_metodos_anticonceptivos.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_metodos_anticonceptivos).addToBackStack(null).commit();
    }

    public void enviarEncuesta22(String idEncuesta){
        ssr_enamorado=new ssr_enamorado();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_enamorado.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_enamorado).addToBackStack(null).commit();
    }

    public void enviarEncuesta23(String idEncuesta){
        ssr_decidir_relaciones=new ssr_decidir_relaciones();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_decidir_relaciones.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_decidir_relaciones).addToBackStack(null).commit();
    }

    public void enviarEncuesta24(String idEncuesta){
        ssr_usar_anticonceptivo_actualmente=new ssr_usar_anticonceptivo_actualmente();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_usar_anticonceptivo_actualmente.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_usar_anticonceptivo_actualmente).addToBackStack(null).commit();
    }

    public void enviarEncuesta25(String idEncuesta){
        ssr_no_anticonceptivo=new ssr_no_anticonceptivo();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_no_anticonceptivo.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_no_anticonceptivo).addToBackStack(null).commit();
    }

    public void enviarEncuesta26(String idEncuesta){
        ssr_desicion_uso_anticonceptivos=new ssr_desicion_uso_anticonceptivos();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_desicion_uso_anticonceptivos.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_desicion_uso_anticonceptivos).addToBackStack(null).commit();
    }

    public void enviarEncuesta27(String idEncuesta){
        ssr_decidir_embarazo=new ssr_decidir_embarazo();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_decidir_embarazo.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_decidir_embarazo).addToBackStack(null).commit();
    }

    public void enviarEncuesta28(String idEncuesta){
        ssr_edad_quisieras_hijos=new ssr_edad_quisieras_hijos();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_edad_quisieras_hijos.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_edad_quisieras_hijos).addToBackStack(null).commit();
    }

    public void enviarEncuesta29(String idEncuesta){
        ssr_situacion_violencia=new ssr_situacion_violencia();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_situacion_violencia.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_situacion_violencia).addToBackStack(null).commit();
    }

    public void enviarEncuesta30(String idEncuesta){
        ssr_prevenir_its=new ssr_prevenir_its();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_prevenir_its.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_prevenir_its).addToBackStack(null).commit();
    }

    public void enviarEncuesta31(String idEncuesta){
        ssr_embarazo_plan_vida=new ssr_embarazo_plan_vida();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        ssr_embarazo_plan_vida.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ssr_embarazo_plan_vida).addToBackStack(null).commit();
    }

    public void enviarEncuesta32(String idEncuesta){
        pl_apresado=new pl_apresado();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        pl_apresado.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, pl_apresado).addToBackStack(null).commit();
    }

    public void enviarEncuesta33(String idEncuesta){
        pl_causa_detenido=new pl_causa_detenido();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        pl_causa_detenido.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, pl_causa_detenido).addToBackStack(null).commit();
    }

    public void enviarEncuesta34(String idEncuesta){
        pl_problemas_defensoria=new pl_problemas_defensoria();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        pl_problemas_defensoria.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, pl_problemas_defensoria).addToBackStack(null).commit();
    }

    public void enviarEncuesta35(String idEncuesta){
        v_tipo_violencia=new v_tipo_violencia();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        v_tipo_violencia.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, v_tipo_violencia).addToBackStack(null).commit();
    }

    public void enviarEncuesta36(String idEncuesta){
        v_sufrir_violencia=new v_sufrir_violencia();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        v_sufrir_violencia.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, v_sufrir_violencia).addToBackStack(null).commit();
    }

    public void enviarEncuesta37(String idEncuesta){
        v_violencia_pareja=new v_violencia_pareja();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        v_violencia_pareja.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, v_violencia_pareja).addToBackStack(null).commit();
    }

    public void enviarEncuesta38(String idEncuesta){
        v_violencia_pareja_actual=new v_violencia_pareja_actual();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        v_violencia_pareja_actual.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, v_violencia_pareja_actual).addToBackStack(null).commit();
    }

    public void enviarEncuesta39(String idEncuesta){
        v_buscar_ayuda=new v_buscar_ayuda();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        v_buscar_ayuda.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, v_buscar_ayuda).addToBackStack(null).commit();
    }

    public void enviarEncuesta40(String idEncuesta){
        emp_pers_8_0_1=new emp_pers_8_0_1();
        Bundle bundleEnvio=new Bundle();
        bundleEnvio.putString("idEncuesta", idEncuesta);

        emp_pers_8_0_1.setArguments(bundleEnvio);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, emp_pers_8_0_1).addToBackStack(null).commit();
    }
}
