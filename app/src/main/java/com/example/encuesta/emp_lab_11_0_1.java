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
 * {@link emp_lab_11_0_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_1.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtrespuesta;
    String otronom, idEncuesta, respuesta;
    Integer trabajaste, estudios, edad, enfermedad, cuidaFamiliar, noTrabajo, noNecesito, otro, oportunidadEmprendimiento, emprendimiento, actividadActual;
    RadioButton rdTrabajasteSi, rdTrabajasteNo, rdEstudioSi, rdEstudioNo, rdEdadSi, rdEdadNo, rdEnfermedadSi, rdEnfermedadNo, rdCuidaFamiliarSi, rdCuidaFamiliarNo, rdNoTrabajoSi, rdNoTrabajoNo, rdNoNecesitoSi, rdNoNecesitoNo, rdOtroSi, rdOtrono;
    LinearLayout display1102, display1102ops;
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
    public static emp_lab_11_0_1 newInstance(String param1, String param2) {
        emp_lab_11_0_1 fragment = new emp_lab_11_0_1();
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
        vista = inflater.inflate(R.layout.fragment_emp_lab_11_0_1, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente57);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras57);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1101);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro112);
        txtrespuesta=(EditText) vista.findViewById(R.id.respuesta1102);

        rdTrabajasteSi=(RadioButton) vista.findViewById(R.id.SiTrabaje);
        rdTrabajasteNo=(RadioButton) vista.findViewById(R.id.NoTrabaje);

        rdEstudioSi=(RadioButton) vista.findViewById(R.id.SiEstudios);
        rdEstudioNo=(RadioButton) vista.findViewById(R.id.NoEstudios);

        rdEdadSi=(RadioButton) vista.findViewById(R.id.SiEdad);
        rdEdadNo=(RadioButton) vista.findViewById(R.id.NoEdad);

        rdEnfermedadSi=(RadioButton) vista.findViewById(R.id.SiEnfermedad);
        rdEnfermedadNo=(RadioButton) vista.findViewById(R.id.NoEnfermedad);

        rdCuidaFamiliarSi=(RadioButton) vista.findViewById(R.id.SiCuidAUnFamili);
        rdCuidaFamiliarNo=(RadioButton) vista.findViewById(R.id.NoCuidAUnFamili);

        rdNoTrabajoSi=(RadioButton) vista.findViewById(R.id.SiEncontreTrabajo);
        rdNoTrabajoNo=(RadioButton) vista.findViewById(R.id.NoEncontreTrabajo);

        rdNoNecesitoSi=(RadioButton) vista.findViewById(R.id.SiNecesito);
        rdNoNecesitoNo=(RadioButton) vista.findViewById(R.id.NoNecesito);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi112);
        rdOtrono=(RadioButton) vista.findViewById(R.id.OtroNo112);

        display1102=(LinearLayout) vista.findViewById(R.id.layout1102);
        display1102ops=(LinearLayout) vista.findViewById(R.id.layout1102ops);

        display1102ops.setVisibility(View.INVISIBLE);
        display1102ops.setVisibility(View.GONE);

        rdTrabajasteSi.setOnClickListener(v -> {

            display1102.setVisibility(View.INVISIBLE);
            display1102.setVisibility(View.GONE);


        });

        rdTrabajasteNo.setOnClickListener(v -> {

            display1102.setVisibility(View.VISIBLE);

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
                trabajaste = jsonObject.optInt("alguna_vez_trabajaste");
                estudios = jsonObject.optInt("no_trabajo_estudios");
                edad = jsonObject.optInt("no_trabajo_edad");
                enfermedad = jsonObject.optInt("no_trabajo_enfermedad");
                cuidaFamiliar = jsonObject.optInt("no_trabajo_cuidado_familiar");
                noTrabajo = jsonObject.optInt("no_trabajo_encontrar");
                noNecesito= jsonObject.optInt("no_trabajo_no_necesito");
                otro = jsonObject.optInt("no_trabajo_otro");
                respuesta= jsonObject.optString("respuesta_trabajaste");
                otronom = jsonObject.optString("no_trabajo_otro_nombre");

                //otrofragment
                emprendimiento = jsonObject.optInt("contunia_emprendimiento");
                oportunidadEmprendimiento = jsonObject.optInt("opotunidad_realizar_emprendimiento");
                actividadActual = jsonObject.optInt("actividad_economica");


                txtotro.setText(otronom.toString());
                txtrespuesta.setText(respuesta.toString());

                if(trabajaste==1){

                    display1102.setVisibility(View.INVISIBLE);
                    display1102.setVisibility(View.GONE);

                }

               if(trabajaste==1){
                   rdTrabajasteSi.setChecked(true);
               }else if(trabajaste==2){
                   rdTrabajasteNo.setChecked(true);
               }

                if(estudios==1){
                    rdEstudioSi.setChecked(true);
                }else if(estudios==2){
                    rdEstudioNo.setChecked(true);
                }

                if(edad==1){
                    rdEdadSi.setChecked(true);
                }else if(edad==2){
                    rdEdadNo.setChecked(true);
                }

                if(enfermedad==1){
                    rdEnfermedadSi.setChecked(true);
                }else if(enfermedad==2){
                    rdEnfermedadNo.setChecked(true);
                }

                if(cuidaFamiliar==1){
                    rdCuidaFamiliarSi.setChecked(true);
                }else if(cuidaFamiliar==2){
                    rdCuidaFamiliarNo.setChecked(true);
                }

                if(noTrabajo==1){
                    rdNoTrabajoSi.setChecked(true);
                }else if(noTrabajo==2){
                    rdNoTrabajoNo.setChecked(true);
                }

                if(noNecesito==1){
                    rdNoNecesitoSi.setChecked(true);
                }else if(noNecesito==2){
                    rdNoNecesitoNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtrono.setChecked(true);
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
        String url=ip+"actualiza1101.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta61(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    if(oportunidadEmprendimiento==2){

                        interfaceComunicaFragments.enviarEncuesta52(idFragment.getText().toString());
                    }else {
                        if (emprendimiento == 1) {
                            interfaceComunicaFragments.enviarEncuesta53(idFragment.getText().toString());
                        } else {
                            if(actividadActual==2){
                                interfaceComunicaFragments.enviarEncuesta54(idFragment.getText().toString());
                            }else{
                                interfaceComunicaFragments.enviarEncuesta59(idFragment.getText().toString());
                            }

                        }
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

                String otronom=txtotro.getText().toString();
                String respuesta=txtrespuesta.getText().toString();

              String trabajaste="0";
              if(rdTrabajasteSi.isChecked()){
                  trabajaste="1";
              }else if(rdTrabajasteNo.isChecked()){

                  trabajaste="2";
              }

                String estudios="0";
                if(rdEstudioSi.isChecked()){
                    estudios="1";
                }else if(rdEstudioNo.isChecked()){

                    estudios="2";
                }

                String edad="0";
                if(rdEdadSi.isChecked()){
                    edad="1";
                }else if(rdEdadNo.isChecked()){

                    edad="2";
                }

                String enfermedad="0";
                if(rdEnfermedadSi.isChecked()){
                    enfermedad="1";
                }else if(rdEnfermedadNo.isChecked()){

                    enfermedad="2";
                }


                String cuidaFamiliar="0";
                if(rdCuidaFamiliarSi.isChecked()){
                    cuidaFamiliar="1";
                }else if(rdCuidaFamiliarNo.isChecked()){

                    cuidaFamiliar="2";
                }


                String noTrabajo="0";
                if(rdNoTrabajoSi.isChecked()){
                    noTrabajo="1";
                }else if(rdNoTrabajoNo.isChecked()){

                    noTrabajo="2";
                }

                String noNecesito="0";
                if(rdNoNecesitoSi.isChecked()){
                    noNecesito="1";
                }else if(rdNoNecesitoNo.isChecked()){

                    noNecesito="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtrono.isChecked()){

                    otro="2";
                }

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom", otronom);
                parametros.put("trabajaste", trabajaste);
                parametros.put("estudios", estudios);
                parametros.put("edad", edad);
                parametros.put("enfermedad", enfermedad);
                parametros.put("cuidaFamiliar", cuidaFamiliar);
                parametros.put("noTrabajo", noTrabajo);
                parametros.put("noNecesito", noNecesito);
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
