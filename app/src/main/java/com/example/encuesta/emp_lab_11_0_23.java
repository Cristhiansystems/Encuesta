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
 * {@link emp_lab_11_0_23.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_23#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_23 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_23() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_23.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    EditText txtotro, txtnumtrabajos, txttrabajo1, txttrabajo2;
    String  idEncuesta, otronom, numtrabajos, trabajo1, trabajo2;
    Integer modalidadContrato, capacitacion, taller;
    RadioButton rdContratoFirmado, rdAcuerdoHablado, rdOtro, rdSi, rdNo;
    LinearLayout display1123;
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
    public static emp_lab_11_0_23 newInstance(String param1, String param2) {
        emp_lab_11_0_23 fragment = new emp_lab_11_0_23();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_23, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente64);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras64);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1123);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1124);
        txtnumtrabajos=(EditText) vista.findViewById(R.id.txtResp1125);
        txttrabajo1=(EditText) vista.findViewById(R.id.txtTrabajo1);
        txttrabajo2=(EditText) vista.findViewById(R.id.txtTrabajo2);


        rdContratoFirmado=(RadioButton) vista.findViewById(R.id.RdContratoFirmado);
        rdAcuerdoHablado=(RadioButton) vista.findViewById(R.id.RdAcuerdoHablado);
        rdOtro=(RadioButton) vista.findViewById(R.id.RdOtro1124);
        rdSi=(RadioButton) vista.findViewById(R.id.SiMeSirvioLaCapacitacion);
        rdNo=(RadioButton) vista.findViewById(R.id.NoMeSirvioLaCapacitacion);

        display1123=(LinearLayout) vista.findViewById(R.id.layout1123);
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

                capacitacion = jsonObject.optInt("sirvio_capacitacion");
                modalidadContrato = jsonObject.optInt("modalidad_contrato");
                otronom = jsonObject.optString("modalidad_contrato_otro");
                numtrabajos = jsonObject.optString("numero_trabajo");
                trabajo1 = jsonObject.optString("trabajo_1");
                trabajo2 = jsonObject.optString("trabajo_2");

                taller = jsonObject.optInt("realizo_capacitacion");

                if(taller==2){
                    display1123.setVisibility(View.INVISIBLE);
                    display1123.setVisibility(View.GONE);
                }
                txtotro.setText(otronom.toString());
                txtnumtrabajos.setText(numtrabajos.toString());
                txttrabajo1.setText(trabajo1.toString());
                txttrabajo2.setText(trabajo2.toString());


                if(modalidadContrato==1){
                    rdContratoFirmado.setChecked(true);
                }else if(modalidadContrato==2){
                    rdAcuerdoHablado.setChecked(true);
                }else if(modalidadContrato==3){
                    rdOtro.setChecked(true);
                }

                if(capacitacion==1){
                    rdSi.setChecked(true);
                }else if(capacitacion==2){
                    rdNo.setChecked(true);
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
        String url=ip+"actualiza1123.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta68(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    if(taller==2){
                        interfaceComunicaFragments.enviarEncuesta65(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta66(idFragment.getText().toString());
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
                String numTrabajo=txtnumtrabajos.getText().toString();
                String trabajo1=txttrabajo1.getText().toString();
                String trabajo2=txttrabajo2.getText().toString();


                String capacitacion="0";
                if(rdSi.isChecked()){
                    capacitacion="1";
                }else if(rdNo.isChecked()){

                    capacitacion="2";
                }

                String modalidadContrato="0";
                if(rdContratoFirmado.isChecked()){
                    modalidadContrato="1";
                }else if(rdAcuerdoHablado.isChecked()){

                    modalidadContrato="2";
                }else if(rdOtro.isChecked()){

                    modalidadContrato="3";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);


                parametros.put("numTrabajo", numTrabajo);
                parametros.put("trabajo1", trabajo1);
                parametros.put("trabajo2", trabajo2);
                parametros.put("capacitacion", capacitacion);
                parametros.put("modalidadContrato", modalidadContrato);
                parametros.put("otronom", otronom);


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
