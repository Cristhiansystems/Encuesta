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
 * {@link sb_tipo_vivienda.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sb_tipo_vivienda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sb_tipo_vivienda extends Fragment{
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
    RadioButton rdDepartamento, rdIndependiente, rdVecindad, rdPrecaria, rdImporvisada,  rdOtro;
    EditText txtnomOtro;
    String nomOtro, idEncuesta;
    Integer tipo_vivienda;
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

    public sb_tipo_vivienda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sb_tipo_vivienda.
     */
    // TODO: Rename and change types and number of parameters
    public static sb_tipo_vivienda newInstance(String param1, String param2) {
        sb_tipo_vivienda fragment = new sb_tipo_vivienda();
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
        vista=inflater.inflate(R.layout.fragment_sb_tipo_vivienda, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente11);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras11);
        idFragment= (TextView) vista.findViewById(R.id.idTipoVivienda);
        rdDepartamento=(RadioButton) vista.findViewById(R.id.tipoVDepartamento);
        rdIndependiente=(RadioButton) vista.findViewById(R.id.tipoVIndependiente);
        rdVecindad=(RadioButton) vista.findViewById(R.id.tipoVVecindad);
        rdPrecaria=(RadioButton) vista.findViewById(R.id.tipoVPrecaria);
        rdImporvisada=(RadioButton) vista.findViewById(R.id.tipoVImprovisada);
        rdOtro=(RadioButton) vista.findViewById(R.id.tipoVOtro);
        txtnomOtro=(EditText) vista.findViewById(R.id.txtTipoVOtro);


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
        String url="http://192.168.0.13/encuestasWS/actualizarTipoVivienda.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta12(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta10(idFragment.getText().toString());

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
                String tipoVivienda="0";
                if(rdDepartamento.isChecked()){
                    tipoVivienda="1";
                }else if(rdIndependiente.isChecked()){
                    tipoVivienda="2";
                }else if(rdVecindad.isChecked()){
                    tipoVivienda="3";
                }else if(rdPrecaria.isChecked()){
                    tipoVivienda="4";

                }else if(rdPrecaria.isChecked()){
                    tipoVivienda="4";

                }else if(rdOtro.isChecked()){
                    tipoVivienda="88";
                }
                String otro=txtnomOtro.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("tipoVivienda",tipoVivienda);
                parametros.put("otro",otro);




                return parametros;
            }
        };
        request.add(stringRequest);
    }


    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json=response.optJSONArray("usuario");
            JSONObject jsonObject=null;

            try{
                jsonObject=json.getJSONObject(0);
                idEncuesta=jsonObject.optString("encuesta_emt");
                tipo_vivienda=jsonObject.optInt("tipo_lugar_vivienda");
                nomOtro=jsonObject.optString("tipo_lugar_vivienda_otro");

                if(tipo_vivienda==1){
                    rdDepartamento.setChecked(true);
                }else if(tipo_vivienda==2){
                    rdIndependiente.setChecked(true);
                }else if(tipo_vivienda==3){
                    rdVecindad.setChecked(true);
                }else if(tipo_vivienda==4){
                    rdPrecaria.setChecked(true);
                }else if(tipo_vivienda==5){
                    rdImporvisada.setChecked(true);
                }else if(tipo_vivienda==88){
                    rdOtro.setChecked(true);
                }

                txtnomOtro.setText(nomOtro.toString());




            }catch (JSONException e){
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
