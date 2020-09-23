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
 * {@link emp_econ_10_0_14.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_14#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_14 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_14() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_14.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro14, txtotro15;
    String idEncuesta, otronom14, otronom15;
    Integer frecuenciaVender, contratos, acuerdoVerbal, pedidoVerbal, importanteDocumento, otro15;
    RadioButton rdCadaDia, rdUnaSemana, rdUnaMes, rdotro14, rdContratoSi, rdContratoNo, rdacuerdosVerbalesSi, rdacuerdosVerbalesNo, rdOtro15Si, rdOtro15No, rdPedidosSi, rdPedidosNo, rdImportanteSi, rdImportanteNo;

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
    // TODO: Rename and change types and number of parameters
    public static emp_econ_10_0_14 newInstance(String param1, String param2) {
        emp_econ_10_0_14 fragment = new emp_econ_10_0_14();
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
        vista= inflater.inflate(R.layout.fragment_emp_econ_10_0_14, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente53);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras53);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1014);
        txtotro14=(EditText) vista.findViewById(R.id.txtOtro1014);
        txtotro15=(EditText) vista.findViewById(R.id.txtOtro1015);


        rdCadaDia=(RadioButton) vista.findViewById(R.id.RdCadaDia);
        rdUnaSemana=(RadioButton) vista.findViewById(R.id.RdUnaVezPorSemana);
        rdUnaMes=(RadioButton) vista.findViewById(R.id.RdUnaVezAlMes);
        rdotro14=(RadioButton) vista.findViewById(R.id.RdOtro1014);


        rdContratoSi=(RadioButton) vista.findViewById(R.id.SiContratos);
        rdContratoNo=(RadioButton) vista.findViewById(R.id.NoContratos);

        rdacuerdosVerbalesSi=(RadioButton) vista.findViewById(R.id.SiAcuerdosVerbales);
        rdacuerdosVerbalesNo=(RadioButton) vista.findViewById(R.id.NoAcuerdosVerbales);

        rdPedidosSi=(RadioButton) vista.findViewById(R.id.SiPedidosVerbales);
        rdPedidosNo=(RadioButton) vista.findViewById(R.id.NoPedidosVerbales);

        rdOtro15Si=(RadioButton) vista.findViewById(R.id.OtroSi1015);
        rdOtro15No=(RadioButton) vista.findViewById(R.id.OtroNo1015);

        rdImportanteSi=(RadioButton) vista.findViewById(R.id.SiEsImportVentConDocum);
        rdImportanteNo=(RadioButton) vista.findViewById(R.id.NoEsImportVentConDocum);

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
                frecuenciaVender = jsonObject.optInt("frecuencia_vender");
                otronom14 = jsonObject.optString("frecuencia_vender_otro_nombre");
                contratos= jsonObject.optInt("contratos");
                acuerdoVerbal = jsonObject.optInt("acuerdos_verbales");
                pedidoVerbal = jsonObject.optInt("pedidos_verbales");
                otro15 = jsonObject.optInt("venta_atraves_otro");
                otronom15 = jsonObject.optString("venta_atraves_otro_nombre");
                importanteDocumento = jsonObject.optInt("importante_vender_formal");



                if(frecuenciaVender==1){
                    rdCadaDia.setChecked(true);
                }else if(frecuenciaVender==2){
                    rdUnaSemana.setChecked(true);
                }else if(frecuenciaVender==3){
                    rdUnaMes.setChecked(true);
                }else if(frecuenciaVender==4){
                    rdotro14.setChecked(true);
                }

                if(contratos==1){
                    rdContratoSi.setChecked(true);
                }else if(contratos==2){
                    rdContratoNo.setChecked(true);
                }

                if(acuerdoVerbal==1){
                    rdacuerdosVerbalesSi.setChecked(true);
                }else if(acuerdoVerbal==2){
                    rdacuerdosVerbalesNo.setChecked(true);
                }

                if(pedidoVerbal==1){
                    rdPedidosSi.setChecked(true);
                }else if(pedidoVerbal==2){
                    rdPedidosNo.setChecked(true);
                }

                if(otro15==1){
                    rdOtro15Si.setChecked(true);
                }else if(otro15==2){
                    rdOtro15No.setChecked(true);
                }

                if(importanteDocumento==1){
                    rdImportanteSi.setChecked(true);
                }else if(importanteDocumento==2){
                    rdImportanteNo.setChecked(true);
                }


                txtotro15.setText(otronom15.toString());
                txtotro14.setText(otronom14.toString());


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
        String url=ip+"actualiza1014.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta57(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta55(idFragment.getText().toString());

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

                String otronom14=txtotro14.getText().toString();
                String otronom15=txtotro15.getText().toString();

                String frecuencia="0";
                if(rdCadaDia.isChecked()){
                    frecuencia="1";
                }else if(rdUnaSemana.isChecked()){
                    frecuencia="2";
                }else if(rdUnaMes.isChecked()){
                    frecuencia="3";
                }else if(rdotro14.isChecked()){
                    frecuencia="4";
                }


                String contrato="0";
                if(rdContratoSi.isChecked()){
                    contrato="1";
                }else if(rdContratoNo.isChecked()){
                    contrato="2";
                }


                String acuerdoVerbal="0";
                if(rdacuerdosVerbalesSi.isChecked()){
                    acuerdoVerbal="1";
                }else if(rdacuerdosVerbalesNo.isChecked()){
                    acuerdoVerbal="2";
                }

                String pedidoVerbal="0";
                if(rdPedidosSi.isChecked()){
                    pedidoVerbal="1";
                }else if(rdPedidosNo.isChecked()){
                    pedidoVerbal="2";
                }

                String otro15="0";
                if(rdOtro15Si.isChecked()){
                    otro15="1";
                }else if(rdOtro15No.isChecked()){
                    otro15="2";
                }

                String importanteDccumento="0";
                if(rdImportanteSi.isChecked()){
                    importanteDccumento="1";
                }else if(rdImportanteNo.isChecked()){
                    importanteDccumento="2";
                }



                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom14", otronom14);
                parametros.put("otronom15", otronom15);
                parametros.put("frecuencia", frecuencia);
                parametros.put("contrato", contrato);
                parametros.put("acuerdoVerbal", acuerdoVerbal);
                parametros.put("pedidoVerbal", pedidoVerbal);
                parametros.put("otro15", otro15);
                parametros.put("importanteDccumento", importanteDccumento);

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
