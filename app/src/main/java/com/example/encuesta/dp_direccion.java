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
 * {@link dp_direccion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dp_direccion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dp_direccion extends Fragment {
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
    EditText txtRespuesta, txtotro;
    String idEncuesta, Respuesta, otroDireccion;
    Integer tipoVivienda;
    RadioButton rdcasa, rdalquiler, rdalojamiento, rdalbergue, rdcentro, rdhogar, rdcalle, rdotro, rdTrabajo;
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

    public dp_direccion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dp_direccion.
     */
    // TODO: Rename and change types and number of parameters
    public static dp_direccion newInstance(String param1, String param2) {
        dp_direccion fragment = new dp_direccion();
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
        vista=inflater.inflate(R.layout.fragment_dp_direccion, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente7);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras7);
        idFragment= (TextView) vista.findViewById(R.id.idDireccion);

        rdcasa=(RadioButton) vista.findViewById(R.id.casaFamiliar);
        rdalquiler=(RadioButton) vista.findViewById(R.id.alquiler);
        rdalojamiento=(RadioButton) vista.findViewById(R.id.alojamiento);
        rdalbergue=(RadioButton) vista.findViewById(R.id.albergue);
        rdcentro=(RadioButton) vista.findViewById(R.id.centroJusticia);
        rdhogar=(RadioButton) vista.findViewById(R.id.hogar);
        rdcalle=(RadioButton) vista.findViewById(R.id.calle);
        rdTrabajo=(RadioButton) vista.findViewById(R.id.trabajo);
        rdotro=(RadioButton) vista.findViewById(R.id.otroDireccion);
        txtRespuesta=(EditText) vista.findViewById(R.id.txtdireccion);
        txtotro=(EditText) vista.findViewById(R.id.txtNombreDireccion);

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
        String url=ip+"actualizarDireccion.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta8(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta5(idFragment.getText().toString());
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
                String id=idFragment.getText().toString();
                String direccion="0";
                if(rdcasa.isChecked()){
                    direccion="1";
                }else if(rdalquiler.isChecked()){
                    direccion="2";
                }else if(rdalojamiento.isChecked()){
                    direccion="3";
                }else if(rdalbergue.isChecked()){
                    direccion="4";
                }else if(rdcentro.isChecked()){
                    direccion="5";
                }else if(rdhogar.isChecked()){
                    direccion="6";
                }else if(rdcalle.isChecked()){
                    direccion="7";
                }else if(rdTrabajo.isChecked()){
                    direccion="8";
                }else if(rdotro.isChecked()){
                    direccion="88";
                }

                String respuesta=txtRespuesta.getText().toString();
                String otro=txtotro.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("direccion",direccion);
                parametros.put("respuesta",respuesta);
                parametros.put("otro",otro);


                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json=response.optJSONArray("usuario");
            JSONObject jsonObject=null;

            try{
                jsonObject=json.getJSONObject(0);
                idEncuesta=jsonObject.optString("encuesta_emt");
                tipoVivienda=jsonObject.optInt("tipo_vivienda");
                Respuesta=jsonObject.optString("direccion");
                otroDireccion=jsonObject.optString("tipo_vivienda_otro");


                txtRespuesta.setText(Respuesta.toString());
                txtotro.setText(otroDireccion.toString());

                if(tipoVivienda==1){
                    rdcasa.setChecked(true);
                }else if(tipoVivienda==2){
                    rdalquiler.setChecked(true);
                }else if(tipoVivienda==3){
                    rdalojamiento.setChecked(true);
                }else if(tipoVivienda==4){
                    rdalbergue.setChecked(true);
                }else if(tipoVivienda==5){
                    rdcentro.setChecked(true);
                }else if(tipoVivienda==6){
                    rdhogar.setChecked(true);
                }else if(tipoVivienda==7){
                    rdcalle.setChecked(true);
                }else if(tipoVivienda==8){
                    rdTrabajo.setChecked(true);
                }else if(tipoVivienda==88){
                    rdotro.setChecked(true);
                }


            }catch (JSONException e){
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
