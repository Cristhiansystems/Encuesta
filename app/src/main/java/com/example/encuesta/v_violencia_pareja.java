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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link v_violencia_pareja.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_violencia_pareja#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_violencia_pareja extends Fragment {
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
    RadioButton rdinfielMM, rdinfielAV, rdinfielNC, rdinfielN, rdcelosMM, rdcelosAV, rdcelosNC, rdcelosN, rdrevisarCelularMM, rdrevisarCelularAV, rdrevisarCelularNC, rdrevisarCelularN, rdlimitarFamiliaMM, rdlimitarFamiliaAV, rdlimitarFamiliaNC, rdlimitarFamiliaN, rdinsultarPublicoMM, rdinsultarPublicoAV, rdinsultarPublicoNC, rdinsultarPublicoN, rdamenazaAbandonarteMM, rdamenazaAbandonarteAV, rdamenazaAbandonarteNC, rdamenazaAbandonarteN, rdquitarHijosMM, rdquitarHijosAV, rdquitarHijosNC, rdquitarHijosN, rdamenazaEconomicaMM, rdamenazaEconomicaAV, rdamenazaEconomicaNC, rdamenazaEconomicaN, rdotroMM, rdotroAV, rdotroNC, rdotroN, rdrompeObjetosMM, rdrompeObjetosAV, rdrompreObjetosNC, rdrompeObjetosN;
    String idEncuesta, otroViolenciaPareja;
    EditText txtOtro;
    Integer infiel, celos, revisarCelular, limitarFamilia,insultarPublico, amenazaAbandonarte, quitarHijos, amenazaEconomico, rompeObjetos, otro;


    //volley

    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    private OnFragmentInteractionListener mListener;

    public v_violencia_pareja() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_violencia_pareja.
     */
    // TODO: Rename and change types and number of parameters
    public static v_violencia_pareja newInstance(String param1, String param2) {
        v_violencia_pareja fragment = new v_violencia_pareja();
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
        vista=inflater.inflate(R.layout.fragment_v_violencia_pareja, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente34);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras34);
        idFragment= (TextView) vista.findViewById(R.id.idViolenciaPareja);
        rdinfielMM=(RadioButton) vista.findViewById(R.id.mmInfiel);
        rdinfielAV=(RadioButton) vista.findViewById(R.id.avInfiel);
        rdinfielN=(RadioButton) vista.findViewById(R.id.nInfiel);
        rdinfielNC=(RadioButton) vista.findViewById(R.id.ncInfiel);
        rdcelosMM=(RadioButton) vista.findViewById(R.id.mmCelos);
        rdcelosAV=(RadioButton) vista.findViewById(R.id.avCelos);
        rdcelosN=(RadioButton) vista.findViewById(R.id.nCelos);
        rdcelosNC=(RadioButton) vista.findViewById(R.id.ncCelos);
        rdrevisarCelularMM=(RadioButton) vista.findViewById(R.id.mmRevisarCelular);
        rdrevisarCelularAV=(RadioButton) vista.findViewById(R.id.avRevisarCelular);
        rdrevisarCelularN=(RadioButton) vista.findViewById(R.id.nRevisarCelular);
        rdrevisarCelularNC=(RadioButton) vista.findViewById(R.id.ncRevisarCelular);
        rdlimitarFamiliaMM=(RadioButton) vista.findViewById(R.id.mmLimitarFamilia);
        rdlimitarFamiliaAV=(RadioButton) vista.findViewById(R.id.avLimitarFamilia);
        rdlimitarFamiliaN=(RadioButton) vista.findViewById(R.id.nLimitarFamilia);
        rdlimitarFamiliaNC=(RadioButton) vista.findViewById(R.id.ncLimitarFamilia);
        rdinsultarPublicoMM=(RadioButton) vista.findViewById(R.id.mmInsultarPublico);
        rdinsultarPublicoAV=(RadioButton) vista.findViewById(R.id.avInsultarPublico);
        rdinsultarPublicoN=(RadioButton) vista.findViewById(R.id.nInsultarPublico);
        rdinsultarPublicoNC=(RadioButton) vista.findViewById(R.id.ncInsultarPublico);
        rdamenazaAbandonarteMM=(RadioButton) vista.findViewById(R.id.mmAmenazaAbandonarte);
        rdamenazaAbandonarteAV=(RadioButton) vista.findViewById(R.id.avAmenazaAbandonarte);
        rdamenazaAbandonarteN=(RadioButton) vista.findViewById(R.id.nAmenazaAbandonarte);
        rdamenazaAbandonarteNC=(RadioButton) vista.findViewById(R.id.ncAmenazaAbandonarte);
        rdquitarHijosMM=(RadioButton) vista.findViewById(R.id.mmQuitarHijos);
        rdquitarHijosAV=(RadioButton) vista.findViewById(R.id.avQuitarHijos);
        rdquitarHijosN=(RadioButton) vista.findViewById(R.id.nQuitarHijos);
        rdquitarHijosNC=(RadioButton) vista.findViewById(R.id.ncQuitarHijos);
        rdamenazaEconomicaMM=(RadioButton) vista.findViewById(R.id.mmAmenazaEconomico);
        rdamenazaEconomicaAV=(RadioButton) vista.findViewById(R.id.avAmenazaEconomico);
        rdamenazaEconomicaN=(RadioButton) vista.findViewById(R.id.nAmenazaEconomico);
        rdamenazaEconomicaNC=(RadioButton) vista.findViewById(R.id.ncAmenazaEconomico);
        rdrompeObjetosMM=(RadioButton) vista.findViewById(R.id.mmRompeObjetos);
        rdrompeObjetosAV=(RadioButton) vista.findViewById(R.id.avRompeObjetos);
        rdrompeObjetosN=(RadioButton) vista.findViewById(R.id.nRompeObjetos);
        rdrompreObjetosNC=(RadioButton) vista.findViewById(R.id.ncRompeObjetos);
        rdotroMM=(RadioButton) vista.findViewById(R.id.mmOtroViolenciaPareja);
        rdotroAV=(RadioButton) vista.findViewById(R.id.avOtroViolenciaPareja);
        rdotroN=(RadioButton) vista.findViewById(R.id.nOtroViolenciaPareja);
        rdotroNC=(RadioButton) vista.findViewById(R.id.ncOtroViolenciaPareja);


        txtOtro=(EditText) vista.findViewById(R.id.txtotroViolenciaPareja);


        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
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
        String url=ip+"actualizarViolenciaPareja.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    if(rdinfielNC.isChecked() && rdcelosNC.isChecked() && rdrevisarCelularNC.isChecked() && rdlimitarFamiliaNC.isChecked() && rdinsultarPublicoNC.isChecked() && rdamenazaAbandonarteNC.isChecked() && rdamenazaAbandonarteNC.isChecked() && rdquitarHijosNC.isChecked() && rdamenazaEconomicaNC.isChecked() && rdrompreObjetosNC.isChecked() && rdotroNC.isChecked()){

                        interfaceComunicaFragments.enviarEncuesta39(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta38(idFragment.getText().toString());
                    }


                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta36(idFragment.getText().toString());

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
                String infiel = "0";
                if (rdinfielMM.isChecked()) {
                    infiel = "1";
                } else if (rdinfielAV.isChecked()) {
                    infiel = "2";
                }else if (rdinfielN.isChecked()) {
                    infiel = "3";
                }else if (rdinfielNC.isChecked()) {
                    infiel = "4";
                }


                String celos = "0";
                if (rdcelosMM.isChecked()) {
                    celos = "1";
                } else if (rdcelosAV.isChecked()) {
                    celos = "2";
                }else if (rdcelosN.isChecked()) {
                    celos = "3";
                }else if (rdcelosNC.isChecked()) {
                    celos = "4";
                }


                String revisaCelular = "0";
                if (rdrevisarCelularMM.isChecked()) {
                    revisaCelular = "1";
                } else if (rdrevisarCelularAV.isChecked()) {
                    revisaCelular = "2";
                }else if (rdrevisarCelularN.isChecked()) {
                    revisaCelular = "3";
                }else if (rdrevisarCelularNC.isChecked()) {
                    revisaCelular = "4";
                }

                String limitarFamilia = "0";
                if (rdlimitarFamiliaMM.isChecked()) {
                    limitarFamilia = "1";
                } else if (rdlimitarFamiliaAV.isChecked()) {
                    limitarFamilia = "2";
                }else if (rdlimitarFamiliaN.isChecked()) {
                    limitarFamilia = "3";
                }else if (rdlimitarFamiliaNC.isChecked()) {
                    limitarFamilia = "4";
                }


                String insultaPublico = "0";
                if (rdinsultarPublicoMM.isChecked()) {
                    insultaPublico = "1";
                } else if (rdinsultarPublicoAV.isChecked()) {
                    insultaPublico = "2";
                }else if (rdinsultarPublicoN.isChecked()) {
                    insultaPublico = "3";
                }else if (rdinsultarPublicoNC.isChecked()) {
                    insultaPublico = "4";
                }

                String amenazaAbandono = "0";
                if (rdamenazaAbandonarteMM.isChecked()) {
                    amenazaAbandono = "1";
                } else if (rdamenazaAbandonarteAV.isChecked()) {
                    amenazaAbandono = "2";
                }else if (rdamenazaAbandonarteN.isChecked()) {
                    amenazaAbandono = "3";
                }else if (rdamenazaAbandonarteNC.isChecked()) {
                    amenazaAbandono = "4";
                }


                String quitarHijos = "0";
                if (rdquitarHijosMM.isChecked()) {
                    quitarHijos = "1";
                } else if (rdquitarHijosAV.isChecked()) {
                    quitarHijos = "2";
                }else if (rdquitarHijosN.isChecked()) {
                    quitarHijos = "3";
                }else if (rdquitarHijosNC.isChecked()) {
                    quitarHijos = "4";
                }

                String amenazaEconomico = "0";
                if (rdamenazaEconomicaMM.isChecked()) {
                    amenazaEconomico = "1";
                } else if (rdamenazaEconomicaAV.isChecked()) {
                    amenazaEconomico = "2";
                }else if (rdamenazaEconomicaN.isChecked()) {
                    amenazaEconomico = "3";
                }else if (rdamenazaEconomicaNC.isChecked()) {
                    amenazaEconomico = "4";
                }

                String romperObjetos = "0";
                if (rdrompeObjetosMM.isChecked()) {
                    romperObjetos = "1";
                } else if (rdrompeObjetosAV.isChecked()) {
                    romperObjetos = "2";
                }else if (rdrompeObjetosN.isChecked()) {
                    romperObjetos = "3";
                }else if (rdrompreObjetosNC.isChecked()) {
                    romperObjetos = "4";
                }

                String otro = "0";
                if (rdotroMM.isChecked()) {
                    otro = "1";
                } else if (rdotroAV.isChecked()) {
                    otro = "2";
                }else if (rdotroN.isChecked()) {
                    otro = "3";
                }else if (rdotroNC.isChecked()) {
                    otro = "4";
                }


                String otroViolenciaPareja= txtOtro.getText().toString();
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("infiel", infiel);
                parametros.put("celos", celos);
                parametros.put("revisaCelular", revisaCelular);
                parametros.put("limitarFamilia", limitarFamilia);
                parametros.put("insultaPublico", insultaPublico);
                parametros.put("amenazaAbandono", amenazaAbandono);
                parametros.put("quitarHijos", quitarHijos);
                parametros.put("amenazaEconomico", amenazaEconomico);
                parametros.put("romperObjetos", romperObjetos);
                parametros.put("otro", otro);
                parametros.put("otroViolenciaPareja", otroViolenciaPareja);




                return parametros;
            }
        };
        request.add(stringRequest);
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
                infiel= jsonObject.optInt("acusaba_infiel");
                celos= jsonObject.optInt("celaba_amigo");
                revisarCelular= jsonObject.optInt("revisa_celular");
                limitarFamilia= jsonObject.optInt("limitar_familia");
                insultarPublico= jsonObject.optInt("insulta_publico");
                amenazaAbandonarte= jsonObject.optInt("amenaza_abandono");
                quitarHijos= jsonObject.optInt("amenaza_hijos");
                amenazaEconomico= jsonObject.optInt("amenaza_economico");
                rompeObjetos= jsonObject.optInt("rompe_objetos");
                otro= jsonObject.optInt("otro_violencia_pareja");

                otroViolenciaPareja= jsonObject.optString("otro_violencia_pareja_nombre");


                if(infiel==1){
                    rdinfielMM.setChecked(true);
                }else if(infiel==2){
                    rdinfielAV.setChecked(true);
                }else if(infiel==3){
                    rdinfielN.setChecked(true);
                }else if(infiel==4){
                    rdinfielNC.setChecked(true);
                }

                if(celos==1){
                    rdcelosMM.setChecked(true);
                }else if(celos==2){
                    rdcelosAV.setChecked(true);
                }else if(celos==3){
                    rdcelosN.setChecked(true);
                }else if(celos==4){
                    rdcelosNC.setChecked(true);
                }


                if(revisarCelular==1){
                    rdrevisarCelularMM.setChecked(true);
                }else if(revisarCelular==2){
                    rdrevisarCelularAV.setChecked(true);
                }else if(revisarCelular==3){
                    rdrevisarCelularN.setChecked(true);
                }else if(revisarCelular==4){
                    rdrevisarCelularNC.setChecked(true);
                }

                if(limitarFamilia==1){
                    rdlimitarFamiliaMM.setChecked(true);
                }else if(limitarFamilia==2){
                    rdlimitarFamiliaAV.setChecked(true);
                }else if(limitarFamilia==3){
                    rdlimitarFamiliaN.setChecked(true);
                }else if(limitarFamilia==4){
                    rdlimitarFamiliaNC.setChecked(true);
                }


                if(insultarPublico==1){
                    rdinsultarPublicoMM.setChecked(true);
                }else if(insultarPublico==2){
                    rdinsultarPublicoAV.setChecked(true);
                }else if(insultarPublico==3){
                    rdinsultarPublicoN.setChecked(true);
                }else if(insultarPublico==4){
                    rdinsultarPublicoNC.setChecked(true);
                }



                if(amenazaAbandonarte==1){
                    rdamenazaAbandonarteMM.setChecked(true);
                }else if(amenazaAbandonarte==2){
                    rdamenazaAbandonarteAV.setChecked(true);
                }else if(amenazaAbandonarte==3){
                    rdamenazaAbandonarteN.setChecked(true);
                }else if(amenazaAbandonarte==4){
                    rdamenazaAbandonarteNC.setChecked(true);
                }


                if(quitarHijos==1){
                    rdquitarHijosMM.setChecked(true);
                }else if(quitarHijos==2){
                    rdquitarHijosAV.setChecked(true);
                }else if(quitarHijos==3){
                    rdquitarHijosN.setChecked(true);
                }else if(quitarHijos==4){
                    rdquitarHijosNC.setChecked(true);
                }

                if(amenazaEconomico==1){
                    rdamenazaEconomicaMM.setChecked(true);
                }else if(amenazaEconomico==2){
                    rdamenazaEconomicaAV.setChecked(true);
                }else if(amenazaEconomico==3){
                    rdamenazaEconomicaN.setChecked(true);
                }else if(amenazaEconomico==4){
                    rdamenazaEconomicaNC.setChecked(true);
                }

                if(rompeObjetos==1){
                    rdrompeObjetosMM.setChecked(true);
                }else if(rompeObjetos==2){
                    rdrompeObjetosAV.setChecked(true);
                }else if(rompeObjetos==3){
                    rdrompeObjetosN.setChecked(true);
                }else if(rompeObjetos==4){
                    rdrompreObjetosNC.setChecked(true);
                }

                if(otro==1){
                    rdotroMM.setChecked(true);
                }else if(otro==2){
                    rdotroAV.setChecked(true);
                }else if(otro==3){
                    rdotroN.setChecked(true);
                }else if(otro==4){
                    rdotroNC.setChecked(true);
                }
                txtOtro.setText(otroViolenciaPareja.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        request.add(jsonObjectRequest);
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
