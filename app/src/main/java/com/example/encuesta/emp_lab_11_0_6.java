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
 * {@link emp_lab_11_0_6.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_6 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_6.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtcurso1, txtcurso2, txtcurso3, txtrespuesta;
    String  idEncuesta, otronom, curso1, curso2, curso3, respuesta;
    Integer recursos, tiempo, interes, otro, capacitacion;
    RadioButton rdCapacitacionSi, rdCapacitacionNo, rdRecursosSi, rdRecursosNo, rdTiempoSi, rdTiempoNo, rdInteresSi, rdInteresNo, rdOtroSI, rdOtroNO;
    LinearLayout display1106, display1107, display1108, display1107ops;
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
    // TODO: Rename and change types and number of parameters
    public static emp_lab_11_0_6 newInstance(String param1, String param2) {
        emp_lab_11_0_6 fragment = new emp_lab_11_0_6();
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
      vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_6, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente59);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras59);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1106);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro117);
        txtcurso1=(EditText) vista.findViewById(R.id.txtCurso1);
        txtcurso2=(EditText) vista.findViewById(R.id.txtCurso2);
        txtcurso3=(EditText) vista.findViewById(R.id.txtCurso3);
        txtrespuesta=(EditText) vista.findViewById(R.id.respuesta1107);

        rdCapacitacionSi=(RadioButton) vista.findViewById(R.id.SiCapacitacion);
        rdCapacitacionNo=(RadioButton) vista.findViewById(R.id.NoCapacitacion);

        rdTiempoSi=(RadioButton) vista.findViewById(R.id.SiFaltaDeTiempo);
        rdTiempoNo=(RadioButton) vista.findViewById(R.id.NoFaltaDeTiempo);

        rdRecursosSi=(RadioButton) vista.findViewById(R.id.SiFaltaRecursos);
        rdRecursosNo=(RadioButton) vista.findViewById(R.id.NoFaltaRecursos);

        rdInteresSi=(RadioButton) vista.findViewById(R.id.SiFaltaDeCursos);
        rdInteresNo=(RadioButton) vista.findViewById(R.id.NoFaltaDeCursos);

        rdOtroSI=(RadioButton) vista.findViewById(R.id.OtroSi117);
        rdOtroNO=(RadioButton) vista.findViewById(R.id.OtroNo117);

        display1106=(LinearLayout) vista.findViewById(R.id.layout1106);
        display1107=(LinearLayout) vista.findViewById(R.id.layout1107);
        display1107ops=(LinearLayout) vista.findViewById(R.id.layout1107ops);
        display1108=(LinearLayout) vista.findViewById(R.id.layout1108);

        display1107ops.setVisibility(View.INVISIBLE);
        display1107ops.setVisibility(View.GONE);

        rdCapacitacionSi.setOnClickListener(v -> {

            display1107.setVisibility(View.INVISIBLE);
            display1107.setVisibility(View.GONE);
            display1108.setVisibility(View.VISIBLE);

        });

        rdCapacitacionNo.setOnClickListener(v -> {

            display1108.setVisibility(View.INVISIBLE);
            display1108.setVisibility(View.GONE);
            display1107.setVisibility(View.VISIBLE);

        });
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

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                otronom = jsonObject.optString("falta_capacitacion_otro_nombre");
                curso1 = jsonObject.optString("curso_realizo1");
                curso2 = jsonObject.optString("curso_realizo2");
                curso3 = jsonObject.optString("curso_realizo3");

                capacitacion = jsonObject.optInt("realizate_capacitacion_trabajo");
                recursos = jsonObject.optInt("falta_recursos");
                tiempo = jsonObject.optInt("falta_tiempo");
                interes = jsonObject.optInt("falta_interes");
                otro = jsonObject.optInt("falta_capacitacion_otro");
                respuesta = jsonObject.optString("respuesta_capacitacion");

                txtotro.setText(otronom.toString());
                txtcurso1.setText(curso1.toString());
                txtcurso2.setText(curso2.toString());
                txtcurso3.setText(curso3.toString());
                txtrespuesta.setText(respuesta.toString());

                if(capacitacion==1){
                    display1107.setVisibility(View.INVISIBLE);
                    display1107.setVisibility(View.GONE);
                }else if(capacitacion==2){
                    display1108.setVisibility(View.INVISIBLE);
                    display1108.setVisibility(View.GONE);
                }

                if(capacitacion==1){
                    rdCapacitacionSi.setChecked(true);
                }else if(capacitacion==2){
                    rdCapacitacionNo.setChecked(true);
                }

                if(recursos==1){
                    rdRecursosSi.setChecked(true);
                }else if(recursos==2){
                    rdRecursosNo.setChecked(true);
                }
                if(tiempo==1){
                    rdTiempoSi.setChecked(true);
                }else if(tiempo==2){
                    rdTiempoNo.setChecked(true);
                }

                if(interes==1){
                    rdInteresSi.setChecked(true);
                }else if(interes==2){
                    rdInteresNo.setChecked(true);
                }
                if(otro==1){
                    rdOtroSI.setChecked(true);
                }else if(otro==2){
                    rdOtroNO.setChecked(true);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        request.add(jsonObjectRequest);
    }

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualiza1106.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta63(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta61(idFragment.getText().toString());

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

                String otronom=txtotro.getText().toString();
                String curso1=txtcurso1.getText().toString();
                String curso2=txtcurso2.getText().toString();
                String curso3=txtcurso3.getText().toString();
                String respuesta=txtrespuesta.getText().toString();

                String capacitacion="0";
                if(rdCapacitacionSi.isChecked()){
                    capacitacion="1";
                }else if(rdCapacitacionNo.isChecked()){

                    capacitacion="2";
                }

                String recursos="0";
                if(rdRecursosSi.isChecked()){
                    recursos="1";
                }else if(rdRecursosNo.isChecked()){

                    recursos="2";
                }

                String tiempo="0";
                if(rdTiempoSi.isChecked()){
                    tiempo="1";
                }else if(rdTiempoNo.isChecked()){

                    tiempo="2";
                }

                String interes="0";
                if(rdInteresSi.isChecked()){
                    interes="1";
                }else if(rdInteresNo.isChecked()){

                    interes="2";
                }

                String otro="0";
                if(rdOtroSI.isChecked()){
                    otro="1";
                }else if(rdOtroNO.isChecked()){

                    otro="2";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("curso1", curso1);
                parametros.put("curso2", curso2);
                parametros.put("curso3", curso3);
                parametros.put("otronom", otronom);
                parametros.put("capacitacion", capacitacion);
                parametros.put("recursos", recursos);
                parametros.put("tiempo", tiempo);
                parametros.put("interes", interes);
                parametros.put("otro", otro);
                parametros.put("respuesta", respuesta);


                return parametros;
            }
        };
        request.add(stringRequest);
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
