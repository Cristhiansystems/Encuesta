package com.example.encuesta;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
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
           // EsconderItem(tipo_usuario);




        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,new principal());
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId()==R.id.nueva_encuesta) {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new Identificacion_geografica());
            fragmentTransaction.commit();
        }   if (menuItem.getItemId()==R.id.lista_encuesta) {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new buscar_encuestas());
            fragmentTransaction.commit();

        }
        return false;
    }
}
