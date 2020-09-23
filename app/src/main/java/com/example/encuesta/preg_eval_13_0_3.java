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
 * {@link preg_eval_13_0_3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link preg_eval_13_0_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class preg_eval_13_0_3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public preg_eval_13_0_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment preg_eval_13_0_3.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    String idEncuesta, programa, comentarios;
    EditText txtprograma, txtcomentarios;
    Integer antesEmpleo, empleo, planDesarrollo;
    RadioButton rdEmpleoSi, rdEmpleoNo, rdantesEmpleoSi, rdantesEmpleoNo, rdPlanDesarrolloSi, rdPlanDesarrolloNo;

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
    // TODO: Rename and change types and number of parameters
    public static preg_eval_13_0_3 newInstance(String param1, String param2) {
        preg_eval_13_0_3 fragment = new preg_eval_13_0_3();
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
        vista= inflater.inflate(R.layout.fragment_preg_eval_13_0_3, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente73);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras73);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1303);


        txtprograma=(EditText) vista.findViewById(R.id.txtResp1310);
        txtcomentarios=(EditText) vista.findViewById(R.id.txtComentarios);

        rdantesEmpleoSi=(RadioButton) vista.findViewById(R.id.RdSiantesEmpleo);
        rdantesEmpleoNo=(RadioButton) vista.findViewById(R.id.RdNoantesEmpleo);
        rdEmpleoSi=(RadioButton) vista.findViewById(R.id.RdSiActualEstoyConEmpleo);
        rdEmpleoNo=(RadioButton) vista.findViewById(R.id.RdNoActualEstoyConEmpleo);
        rdPlanDesarrolloSi=(RadioButton) vista.findViewById(R.id.RdSiCreo);
        rdPlanDesarrolloNo=(RadioButton) vista.findViewById(R.id.RdNoCreo);


        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));


        }

        //Aqui empieza el volley
       // request= Volley.newRequestQueue(getContext());
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

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                programa = jsonObject.optString("mas_gusto_programa");
                comentarios = jsonObject.optString("comentarios");

                antesEmpleo= jsonObject.optInt("empleado_antes");
                empleo = jsonObject.optInt("tiene_empleo_actual");
                planDesarrollo = jsonObject.optInt("hubiera_tenido_plan_desarrollo_sin_programa");


                if(empleo==1){
                    rdEmpleoSi.setChecked(true);
                }else if(empleo==2){
                    rdEmpleoNo.setChecked(true);
                }

                if(antesEmpleo==1){
                    rdantesEmpleoSi.setChecked(true);
                }else if(antesEmpleo==2){
                    rdantesEmpleoNo.setChecked(true);
                }


                if(planDesarrollo==1){
                    rdPlanDesarrolloSi.setChecked(true);
                }else if(planDesarrollo==2){
                    rdPlanDesarrolloNo.setChecked(true);
                }

                txtprograma.setText(programa.toString());
                txtcomentarios.setText(comentarios.toString());



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

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualiza1304.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta77(idFragment.getText().toString());

                }else if(pantalla=="Atras"){

                    interfaceComunicaFragments.enviarEncuesta75(idFragment.getText().toString());

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
                String programa= txtprograma.getText().toString();
                String comentarios= txtcomentarios.getText().toString();

                String antesEmpleo="0";
                if(rdantesEmpleoSi.isChecked()){
                    antesEmpleo="1";
                }else if(rdantesEmpleoNo.isChecked()){
                    antesEmpleo="2";
                }

                String empleo="0";
                if(rdEmpleoSi.isChecked()){
                    empleo="1";
                }else if(rdEmpleoNo.isChecked()){
                    empleo="2";
                }

                String planDesarrollo="0";
                if(rdPlanDesarrolloSi.isChecked()){
                    planDesarrollo="1";
                }else if(rdPlanDesarrolloNo.isChecked()){
                    planDesarrollo="2";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("programa", programa);
                parametros.put("comentarios", comentarios);
                parametros.put("empleo", empleo);
                parametros.put("antesEmpleo", antesEmpleo);
                parametros.put("planDesarrollo", planDesarrollo);


                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);

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
