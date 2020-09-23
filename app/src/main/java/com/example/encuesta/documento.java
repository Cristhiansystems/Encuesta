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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link documento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link documento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class documento extends Fragment{
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
    EditText txtnumCi, txtRun, txtOtro;
    String idEncuesta,  numCI, numRun, nomOtro;
    Integer CI, Run, cn, lsm, otro, ninguno;
    RadioButton rdSiCI, rdNoCI, rdSiRun, rdNoRun, rdSiCn, rdNoCn, rdSilibreta, rdNolibreta, rdSiOtro, rdNoOtro, rdSiNinguno, rdNoNinguno;
    //volley

    ProgressDialog progreso;
   // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    private OnFragmentInteractionListener mListener;

    public documento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment documento.
     */
    // TODO: Rename and change types and number of parameters
    public static documento newInstance(String param1, String param2) {
        documento fragment = new documento();
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
        vista=inflater.inflate(R.layout.fragment_documento, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente4);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras4);
        idFragment= (TextView) vista.findViewById(R.id.idDocumento);
        txtnumCi=(EditText) vista.findViewById(R.id.txtnumCi);
        txtRun=(EditText) vista.findViewById(R.id.txtnumRun);
        txtOtro=(EditText) vista.findViewById(R.id.txtotroDocumento);
        rdSiCI=(RadioButton) vista.findViewById(R.id.SiCI);
        rdNoCI=(RadioButton) vista.findViewById(R.id.NoCI);
        rdNoRun=(RadioButton) vista.findViewById(R.id.NoRun);
        rdSiRun=(RadioButton) vista.findViewById(R.id.SiRun);
        rdSilibreta=(RadioButton) vista.findViewById(R.id.SiLibreta);
        rdNolibreta=(RadioButton) vista.findViewById(R.id.NoLibreta);
        rdNoCn=(RadioButton) vista.findViewById(R.id.NoCN);
        rdSiCn=(RadioButton) vista.findViewById(R.id.SiCN);
        rdSiOtro=(RadioButton) vista.findViewById(R.id.SiOtroDocumento);
        rdNoOtro=(RadioButton) vista.findViewById(R.id.NoOtroDocumento);
        rdNoNinguno=(RadioButton) vista.findViewById(R.id.NoNingunoDocumento);
        rdSiNinguno=(RadioButton) vista.findViewById(R.id.SiNingunoDocumento);


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
        String url=ip+"actualizarDocumento.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta5(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta3(idFragment.getText().toString());
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
                String ci="0";
                if(rdSiCI.isChecked()){
                    ci="1";
                }else if(rdNoCI.isChecked()){
                    ci="2";
                }

                String run="0";
                if(rdSiRun.isChecked()){
                    run="1";
                }else if(rdNoRun.isChecked()){
                    run="2";
                }


                String cn="0";
                if(rdSiCn.isChecked()){
                    cn="1";
                }else if(rdNoCn.isChecked()){
                    cn="2";
                }

                String lsb="0";
                if(rdSilibreta.isChecked()){
                    lsb="1";
                }else if(rdNolibreta.isChecked()){
                    lsb="2";
                }

                String otro="0";
                if(rdSiOtro.isChecked()){
                    otro="1";
                }else if(rdNoOtro.isChecked()){
                    otro="2";
                }


                String ninguno="0";
                if(rdSiNinguno.isChecked()){
                    ninguno="1";
                }else if(rdNoNinguno.isChecked()){
                    ninguno="2";
                }

                String numCi=txtnumCi.getText().toString();
                String numRude=txtRun.getText().toString();
                String otroDocumento= txtOtro.getText().toString();



                Map<String,String> parametros=new HashMap<>();
                parametros.put("ci",ci);
                parametros.put("id",id);
                parametros.put("run",run);
                parametros.put("cn",cn);
                parametros.put("lsb",lsb);
                parametros.put("otro",otro);
                parametros.put("ninguno",ninguno);
                parametros.put("numCi",numCi);
                parametros.put("numRun",numRude);
                parametros.put("otroDocumento",otroDocumento);


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
                numCI=jsonObject.optString("numero_ci");
                numRun=jsonObject.optString("numero_run");
                CI=jsonObject.optInt("ci");
                Run=jsonObject.optInt("run");
                cn=jsonObject.optInt("certificado_nacimiento");
                lsm=jsonObject.optInt("libreta_militar");
                otro=jsonObject.optInt("documento_otro");
                ninguno=jsonObject.optInt("documento_ninguno");
                nomOtro=jsonObject.optString("documento_otro_nombre");


                txtnumCi.setText(numCI.toString());
                txtRun.setText(numRun.toString());
                txtOtro.setText(nomOtro.toString());


                if(CI==1){
                    rdSiCI.setChecked(true);
                }else if(CI==2){
                    rdNoCI.setChecked(true);
                }



                if(Run==1){
                    rdSiRun.setChecked(true);
                }else if(Run==2){
                    rdNoRun.setChecked(true);
                }



                if(cn==1){
                    rdSiCn.setChecked(true);
                }else if(cn==2){
                    rdNoCn.setChecked(true);
                }


                if(lsm==1){
                    rdSilibreta.setChecked(true);
                }else if(lsm==2){
                    rdNolibreta.setChecked(true);
                }



                if(otro==1){
                    rdSiOtro.setChecked(true);
                }else if(otro==2){
                    rdNoOtro.setChecked(true);
                }


                if(ninguno==1){
                    rdSiNinguno.setChecked(true);
                }else if(ninguno==2){
                    rdNoNinguno.setChecked(true);
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
