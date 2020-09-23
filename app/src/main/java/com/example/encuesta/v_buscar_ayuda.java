package com.example.encuesta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link v_buscar_ayuda.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_buscar_ayuda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_buscar_ayuda extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    RadioButton rdAyudaSi, rdAyudaNo, rdMiedoSi, rdMiedoNo, rdCreenciasSi, rdCreenciasNo, rdRepresaliasSi, rdRepresaliasNo, rdotroSi, rdOtroNo, rdNoSabeSi, rdNoSabeNO;
    String idEncuesta, otroBuscarAyuda, dondeBuscasteAyuda, MencionarOrganizacion, respuestanoayuda;
    EditText txtOtro, txtDondeBuscasteAyuda, txtMencionaOrganizacion, txtrespuestanoayuda;
    Integer miedo, creencias, represalias, noSabe, otro, ayuda, enamorado;
    Integer infiel, celos, revisarCelular, limitarFamilia,insultarPublico, amenazaAbandonarte, quitarHijos, amenazaEconomico, rompeObjetos, otroSufrirViolencia;
    LinearLayout display, display76, display77, display75;

    //volley

    ProgressDialog progreso;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    private OnFragmentInteractionListener mListener;

    public v_buscar_ayuda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_buscar_ayuda.
     */
    // TODO: Rename and change types and number of parameters
    public static v_buscar_ayuda newInstance(String param1, String param2) {
        v_buscar_ayuda fragment = new v_buscar_ayuda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_v_buscar_ayuda, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente36);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras36);
        idFragment=(TextView) vista.findViewById(R.id.idBuscarAyuda);
        rdMiedoSi=(RadioButton) vista.findViewById(R.id.miedoSi);
        rdMiedoNo=(RadioButton) vista.findViewById(R.id.miedoNo);
        rdCreenciasSi=(RadioButton) vista.findViewById(R.id.creenciasSi);
        rdCreenciasNo=(RadioButton) vista.findViewById(R.id.creenciasNo);
        rdRepresaliasSi=(RadioButton) vista.findViewById(R.id.represaliasSi);
        rdRepresaliasNo=(RadioButton) vista.findViewById(R.id.represaliasNo);
        rdotroSi=(RadioButton) vista.findViewById(R.id.otroNoBuscarAyudaSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroNoBuscarAyudaNo);
        rdNoSabeSi=(RadioButton) vista.findViewById(R.id.noBuscarAyudaNoSabeSi);
        rdNoSabeNO=(RadioButton) vista.findViewById(R.id.noBuscarAyudaNoSabeNo);
        rdAyudaSi=(RadioButton) vista.findViewById(R.id.buscasteAyudaSi);
        rdAyudaNo=(RadioButton) vista.findViewById(R.id.buscasteAyudaNo);

        txtDondeBuscasteAyuda=(EditText) vista.findViewById(R.id.txtDondeAyuda);
        txtrespuestanoayuda=(EditText) vista.findViewById(R.id.txtRespuestaNoayuda);
        txtOtro=(EditText) vista.findViewById(R.id.txtotroNoBuscarAyuda);
        txtMencionaOrganizacion=(EditText) vista.findViewById(R.id.txtrespuestaConocerOrganizacion);
        display=(LinearLayout) vista.findViewById(R.id.layounoayuda);
        display75=(LinearLayout) vista.findViewById(R.id.layout75);
        display76=(LinearLayout) vista.findViewById(R.id.layout76);
        display77=(LinearLayout) vista.findViewById(R.id.layout77);

        display.setVisibility(View.INVISIBLE);
        display.setVisibility(View.GONE);

        rdAyudaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display76.setVisibility(View.VISIBLE);

                display77.setVisibility(View.INVISIBLE);
                display77.setVisibility(View.GONE);

            }
        });

        rdAyudaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display77.setVisibility(View.VISIBLE);

                display76.setVisibility(View.INVISIBLE);
                display76.setVisibility(View.GONE);

            }
        });

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        //request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {
            String pantalla="Siguiente";
            actualizar(pantalla);

        });

        btnAtras.setOnClickListener(v -> {
            String pantalla="Atras";
            actualizar(pantalla);

        });
        return vista;
    }

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualizarBuscarAyuda.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta40(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    if(enamorado==1 || enamorado==0){

                        if(infiel==4 && celos==4 && revisarCelular==4 && limitarFamilia==4 && insultarPublico==4 && amenazaAbandonarte==4 && quitarHijos==4 && amenazaEconomico==4  && rompeObjetos==4 && otroSufrirViolencia==4){

                            interfaceComunicaFragments.enviarEncuesta37(idFragment.getText().toString());

                        }else{

                            interfaceComunicaFragments.enviarEncuesta38(idFragment.getText().toString());

                        }


                    }else{


                            interfaceComunicaFragments.enviarEncuesta36(idFragment.getText().toString());

                    }

                }

            } else {

                Toast.makeText(getContext(), "Error en la actualizacion" + response.toString() , Toast.LENGTH_SHORT).show();

            }

        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id = idFragment.getText().toString();
                String ayuda = "0";
                if (rdAyudaSi.isChecked()) {
                    ayuda = "1";
                } else if (rdAyudaNo.isChecked()) {
                    ayuda = "2";
                }


                String miedo = "0";
                if (rdMiedoSi.isChecked()) {
                    miedo = "1";
                } else if (rdMiedoNo.isChecked()) {
                    miedo = "2";
                }


                String creencia = "0";
                if (rdCreenciasSi.isChecked()) {
                    creencia = "1";
                } else if (rdCreenciasNo.isChecked()) {
                    creencia = "2";
                }

                String represalia = "0";
                if (rdRepresaliasSi.isChecked()) {
                    represalia = "1";
                } else if (rdRepresaliasNo.isChecked()) {
                    represalia = "2";
                }


                String noSabe = "0";
                if (rdNoSabeSi.isChecked()) {
                    noSabe = "1";
                } else if (rdNoSabeNO.isChecked()) {
                    noSabe = "2";
                }


                String otro = "0";
                if (rdotroSi.isChecked()) {
                    otro = "1";
                } else if (rdOtroNo.isChecked()) {
                    otro = "2";
                }


                String dondeAyuda= txtDondeBuscasteAyuda.getText().toString();
                String otroBuscarAyuda= txtOtro.getText().toString();
                String conocerOrganizacion= txtMencionaOrganizacion.getText().toString();
                String respuestanoayuda= txtrespuestanoayuda.getText().toString();
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("ayuda", ayuda);
                parametros.put("miedo", miedo);
                parametros.put("creencia", creencia);
                parametros.put("represalia", represalia);
                parametros.put("noSabe", noSabe);
                parametros.put("dondeAyuda", dondeAyuda);
                parametros.put("otroBuscarAyuda", otroBuscarAyuda);
                parametros.put("otro", otro);
                parametros.put("conocerOrganizacion", conocerOrganizacion);
                parametros.put("respuestanoayuda", respuestanoayuda);




                return parametros;
            }
        };
       // request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                ayuda= jsonObject.optInt("buscaste_ayuda");
                miedo= jsonObject.optInt("no_buscar_ayuda_miedo");
                creencias= jsonObject.optInt("no_buscar_ayuda_creencias");
                represalias= jsonObject.optInt("no_buscar_ayuda_represalias");
                otro= jsonObject.optInt("no_buscar_ayuda_otro");
                noSabe= jsonObject.optInt("no_buscar_ayuda_nosabe");
                respuestanoayuda= jsonObject.optString("respuesta_no_buscar_ayuda");
                otroBuscarAyuda= jsonObject.optString("no_buscar_ayuda_otro_nombre");
                MencionarOrganizacion= jsonObject.optString("conoces_organizacion");
                dondeBuscasteAyuda= jsonObject.optString("donde_buscaste_ayuda");

                //otro fragment
                enamorado = jsonObject.optInt("tuviste_enamorado");

                infiel= jsonObject.optInt("acusaba_infiel");
                celos= jsonObject.optInt("celaba_amigo");
                revisarCelular= jsonObject.optInt("revisa_celular");
                limitarFamilia= jsonObject.optInt("limitar_familia");
                insultarPublico= jsonObject.optInt("insulta_publico");
                amenazaAbandonarte= jsonObject.optInt("amenaza_abandono");
                quitarHijos= jsonObject.optInt("amenaza_hijos");
                amenazaEconomico= jsonObject.optInt("amenaza_economico");
                rompeObjetos= jsonObject.optInt("rompe_objetos");
                otroSufrirViolencia= jsonObject.optInt("otro_violencia_pareja");


                if(infiel==4 && celos==4 && revisarCelular==4 && limitarFamilia==4 && insultarPublico==4 && amenazaAbandonarte==4 && quitarHijos==4 && amenazaEconomico==4  && rompeObjetos==4 && otroSufrirViolencia==4){

                    display75.setVisibility(View.INVISIBLE);
                    display75.setVisibility(View.GONE);

                    display76.setVisibility(View.INVISIBLE);
                    display76.setVisibility(View.GONE);

                    display77.setVisibility(View.INVISIBLE);
                    display77.setVisibility(View.GONE);

                }else{

                    display75.setVisibility(View.VISIBLE);

                    display76.setVisibility(View.VISIBLE);

                    display77.setVisibility(View.VISIBLE);

                }

                if(ayuda==1){
                    rdAyudaSi.setChecked(true);
                }else if(ayuda==2){
                    rdAyudaNo.setChecked(true);
                }

                if(miedo==1){
                    rdMiedoSi.setChecked(true);
                }else if(miedo==2){
                    rdMiedoNo.setChecked(true);
                }

                if(creencias==1){
                    rdCreenciasSi.setChecked(true);
                }else if(creencias==2){
                    rdCreenciasNo.setChecked(true);
                }

                if(represalias==1){
                    rdRepresaliasSi.setChecked(true);
                }else if(represalias==2){
                    rdRepresaliasNo.setChecked(true);
                }

                if(noSabe==1){
                    rdNoSabeSi.setChecked(true);
                }else if(noSabe==2){
                    rdNoSabeNO.setChecked(true);
                }


                if(otro==1){
                    rdotroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }
                txtOtro.setText(otroBuscarAyuda.toString());
                txtDondeBuscasteAyuda.setText(dondeBuscasteAyuda.toString());
                txtMencionaOrganizacion.setText(MencionarOrganizacion.toString());
                txtrespuestanoayuda.setText(respuestanoayuda.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        //request.add(jsonObjectRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        //navegar entre fragments
        if(context instanceof Activity){
            this.actividad= (Activity) context;
            interfaceComunicaFragments= (IComunicacionFragments) this.actividad;
        }
        ////
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
