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
 * {@link dp_estado_civil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dp_estado_civil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dp_estado_civil extends Fragment{
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
    String idEncuesta;
    Integer estado_civil;
    RadioButton rdSoltero, rdCasada, rdConcubino, rdDivorcada, rdViuda, rdSeparada, rdPadreSoltero, rdMadreSoltera, rdotro;
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

    public dp_estado_civil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dp_estado_civil.
     */
    // TODO: Rename and change types and number of parameters
    public static dp_estado_civil newInstance(String param1, String param2) {
        dp_estado_civil fragment = new dp_estado_civil();
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
        vista=inflater.inflate(R.layout.fragment_dp_estado_civil, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente6);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras6);
        idFragment= (TextView) vista.findViewById(R.id.idEstadoCivil);

        rdSoltero=(RadioButton) vista.findViewById(R.id.soltera);
        rdCasada=(RadioButton) vista.findViewById(R.id.casada);
        rdConcubino=(RadioButton) vista.findViewById(R.id.concubinato);
        rdDivorcada=(RadioButton) vista.findViewById(R.id.divorciada);
        rdViuda=(RadioButton) vista.findViewById(R.id.viuda);
        rdSeparada=(RadioButton) vista.findViewById(R.id.separada);
        rdPadreSoltero=(RadioButton) vista.findViewById(R.id.padreSoltero);
        rdMadreSoltera=(RadioButton) vista.findViewById(R.id.madreSoltera);
        rdotro=(RadioButton) vista.findViewById(R.id.otroEstadoCivil);

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
        String url=ip+"actualizarEstadoCivil.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta7(idFragment.getText().toString());
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
                String estadoCivil="0";
                if(rdSoltero.isChecked()){
                    estadoCivil="1";
                }else if(rdCasada.isChecked()){
                    estadoCivil="2";
                }else if(rdConcubino.isChecked()){
                    estadoCivil="3";
                }else if(rdDivorcada.isChecked()){
                    estadoCivil="4";
                }else if(rdViuda.isChecked()){
                    estadoCivil="5";
                }else if(rdSeparada.isChecked()){
                    estadoCivil="6";
                }else if(rdPadreSoltero.isChecked()){
                    estadoCivil="7";
                }else if(rdMadreSoltera.isChecked()){
                    estadoCivil="8";
                }else if(rdotro.isChecked()){
                    estadoCivil="88";
                }

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("estadoCivil",estadoCivil);


                return parametros;
            }
        };
        request.add(stringRequest);
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
                estado_civil=jsonObject.optInt("estado_civil");



                if(estado_civil==1){
                    rdSoltero.setChecked(true);
                }else if(estado_civil==2){
                    rdCasada.setChecked(true);
                }else if(estado_civil==3){
                    rdConcubino.setChecked(true);
                }else if(estado_civil==4){
                    rdDivorcada.setChecked(true);
                }else if(estado_civil==5){
                    rdViuda.setChecked(true);
                }else if(estado_civil==6){
                    rdSeparada.setChecked(true);
                }else if(estado_civil==7){
                    rdPadreSoltero.setChecked(true);
                }else if(estado_civil==8){
                    rdMadreSoltera.setChecked(true);
                }else if(estado_civil==88){
                    rdotro.setChecked(true);
                }


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
