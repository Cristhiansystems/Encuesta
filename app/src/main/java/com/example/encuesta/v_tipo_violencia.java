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
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link v_tipo_violencia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_tipo_violencia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_tipo_violencia extends Fragment{
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
    RadioButton rdViolenciaFisicaSi, rdViolenciaFisicaNo, rdViolenciaPsicologicaSi, rdViolenciaPsicologicaNo, rdViolenciaSexualSi, rdViolenciaSexualNo, rdOtroSi, rdOtroNo, rdNosabeSi, rdNosabeNo;
    String idEncuesta, otroTipoViolencia;
    EditText txtOtro;
    Integer violenciaFisica, violenciaPsicologica, violenciaSexual, otro, noSabe;


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

    public v_tipo_violencia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_tipo_violencia.
     */
    // TODO: Rename and change types and number of parameters
    public static v_tipo_violencia newInstance(String param1, String param2) {
        v_tipo_violencia fragment = new v_tipo_violencia();
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
        vista=inflater.inflate(R.layout.fragment_v_tipo_violencia, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente320);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras320);
        idFragment= (TextView) vista.findViewById(R.id.idTipoViolencia);
        rdViolenciaFisicaSi=(RadioButton) vista.findViewById(R.id.violenciaFisicaSi);
        rdViolenciaFisicaNo=(RadioButton) vista.findViewById(R.id.violenciaFisicaNo);
        rdViolenciaPsicologicaSi=(RadioButton) vista.findViewById(R.id.violenciaPsicologicaSi);
        rdViolenciaPsicologicaNo=(RadioButton) vista.findViewById(R.id.violenciaPsicologicaNo);
        rdViolenciaSexualSi=(RadioButton) vista.findViewById(R.id.violenciaSexualSi);
        rdViolenciaSexualNo=(RadioButton) vista.findViewById(R.id.violenciaSexualNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroTipoViolenciaSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroTipoViolenciaNo);
        rdNosabeSi=(RadioButton) vista.findViewById(R.id.noSabeTipoViolenciaSi);
        rdNosabeNo=(RadioButton) vista.findViewById(R.id.noSabeTipoViolenciaNo);

        txtOtro=(EditText) vista.findViewById(R.id.txtotroTipoViolencia);

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
        String url=ip+"actualizarTipoViolencia.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta36(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta34(idFragment.getText().toString());

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
                String fisica = "0";
                if (rdViolenciaFisicaSi.isChecked()) {
                    fisica = "1";
                } else if (rdViolenciaFisicaNo.isChecked()) {
                    fisica = "2";
                }

                String psicologica = "0";
                if (rdViolenciaPsicologicaSi.isChecked()) {
                    psicologica = "1";
                } else if (rdViolenciaPsicologicaNo.isChecked()) {
                    psicologica = "2";
                }

                String sexual = "0";
                if (rdViolenciaSexualSi.isChecked()) {
                    sexual = "1";
                } else if (rdViolenciaSexualNo.isChecked()) {
                    sexual = "2";
                }

                String otro = "0";
                if (rdOtroSi.isChecked()) {
                    otro = "1";
                } else if (rdOtroNo.isChecked()) {
                    otro = "2";
                }


                String noSabe = "0";
                if (rdNosabeSi.isChecked()) {
                    noSabe = "1";
                } else if (rdNosabeNo.isChecked()) {
                    noSabe = "2";
                }
                String otroTipoViolencia= txtOtro.getText().toString();
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("fisica", fisica);
                parametros.put("psicologica", psicologica);
                parametros.put("sexual", sexual);
                parametros.put("otro", otro);
                parametros.put("noSabe", noSabe);
                parametros.put("otroTipoViolencia", otroTipoViolencia);



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
                violenciaFisica= jsonObject.optInt("fisica");
                violenciaPsicologica= jsonObject.optInt("psicologica");
                violenciaSexual= jsonObject.optInt("sexual");
                otro= jsonObject.optInt("otro_violencia");
                noSabe= jsonObject.optInt("violencia_no_sabe");

                otroTipoViolencia= jsonObject.optString("otro_violencia_nombre");


                if(violenciaFisica==1){
                    rdViolenciaFisicaSi.setChecked(true);
                }else if(violenciaFisica==2){
                    rdViolenciaFisicaNo.setChecked(true);
                }

                if(violenciaPsicologica==1){
                    rdViolenciaPsicologicaSi.setChecked(true);
                }else if(violenciaPsicologica==2){
                    rdViolenciaPsicologicaNo.setChecked(true);
                }

                if(violenciaSexual==1){
                    rdViolenciaSexualSi.setChecked(true);
                }else if(violenciaSexual==2){
                    rdViolenciaSexualNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }

                if(noSabe==1){
                    rdNosabeSi.setChecked(true);
                }else if(noSabe==2){
                    rdNosabeNo.setChecked(true);
                }
                txtOtro.setText(otroTipoViolencia.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
       // request.add(jsonObjectRequest);
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
