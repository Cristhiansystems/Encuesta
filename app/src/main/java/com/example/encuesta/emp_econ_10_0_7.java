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
 * {@link emp_econ_10_0_7.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_7#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_7 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_7() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_7.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtemprendimiento;
    String idEncuesta, emprendimiento, otronom;
    Integer actividadActual, planNegocio, productivo, servicios, otro;
    RadioButton rdactividadSi, rdactividadNo, rdplanNegocioSi, rdplanNegocioNo, rdproductivoSi, rdproductivoNo, rdServiciosSi, rdServiciosNo, rdOtroSi, rdOtroNo;
    LinearLayout display108;
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
    public static emp_econ_10_0_7 newInstance(String param1, String param2) {
        emp_econ_10_0_7 fragment = new emp_econ_10_0_7();
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
        vista= inflater.inflate(R.layout.fragment_emp_econ_10_0_7, container, false);

        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente51);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras51);

        idFragment=(TextView) vista.findViewById(R.id.idemppers107);
        txtemprendimiento=(EditText) vista.findViewById(R.id.txtResp1010);
        txtotro=(EditText) vista.findViewById(R.id.txtOtro109);


        rdactividadSi=(RadioButton) vista.findViewById(R.id.SiTengoUnaActividEcono);
        rdactividadNo=(RadioButton) vista.findViewById(R.id.NoTengoUnaActividEcono);


        rdplanNegocioSi=(RadioButton) vista.findViewById(R.id.SiRealiceUnPlanDeNegoc);
        rdplanNegocioNo=(RadioButton) vista.findViewById(R.id.NoRealiceUnPlanDeNegoc);

        rdproductivoSi=(RadioButton) vista.findViewById(R.id.SiProductivo);
        rdproductivoNo=(RadioButton) vista.findViewById(R.id.NoProductivo);

        rdServiciosSi=(RadioButton) vista.findViewById(R.id.SiServicios);
        rdServiciosNo=(RadioButton) vista.findViewById(R.id.NoServicios);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi109);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo109);

        display108=(LinearLayout) vista.findViewById(R.id.layout108);

        rdactividadNo.setOnClickListener(v -> {

            display108.setVisibility(View.INVISIBLE);
            display108.setVisibility(View.GONE);


        });

        rdactividadSi.setOnClickListener(v -> {

            display108.setVisibility(View.VISIBLE);

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
                actividadActual = jsonObject.optInt("actividad_economica");
                planNegocio = jsonObject.optInt("emprendimiento_plan_negocio");
                productivo = jsonObject.optInt("productivo");
                servicios = jsonObject.optInt("servicio");
                otro = jsonObject.optInt("otro_emprendimiento");
                otronom = jsonObject.optString("otro_emprendimiento_nombre");
                emprendimiento = jsonObject.optString("cual_emprendimiento");

                if(actividadActual==2){

                    display108.setVisibility(View.INVISIBLE);
                    display108.setVisibility(View.GONE);

                }

                if(actividadActual==1){
                    rdactividadSi.setChecked(true);
                }else if(actividadActual==2){
                    rdactividadNo.setChecked(true);
                }

                if(planNegocio==1){
                    rdplanNegocioSi.setChecked(true);
                }else if(planNegocio==2){
                    rdplanNegocioNo.setChecked(true);
                }

                if(productivo==1){
                    rdproductivoSi.setChecked(true);
                }else if(productivo==2){
                    rdproductivoNo.setChecked(true);
                }

                if(servicios==1){
                    rdServiciosSi.setChecked(true);
                }else if(servicios==2){
                    rdServiciosNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }

                txtotro.setText(otronom.toString());
                txtemprendimiento.setText(emprendimiento.toString());


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
        String url=ip+"actualiza107.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    if(rdactividadNo.isChecked()){
                        interfaceComunicaFragments.enviarEncuesta60(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta55(idFragment.getText().toString());
                    }

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta53(idFragment.getText().toString());

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
                String emprendimiento=txtemprendimiento.getText().toString();

                String actividadactual="0";
                if(rdactividadSi.isChecked()){
                    actividadactual="1";
                }else if(rdactividadNo.isChecked()){
                    actividadactual="2";
                }


                String planNegocio="0";
                if(rdplanNegocioSi.isChecked()){
                    planNegocio="1";
                }else if(rdplanNegocioNo.isChecked()){
                    planNegocio="2";
                }


                String productivo="0";
                if(rdproductivoSi.isChecked()){
                    productivo="1";
                }else if(rdproductivoNo.isChecked()){
                    productivo="2";
                }

                String servicios="0";
                if(rdServiciosSi.isChecked()){
                    servicios="1";
                }else if(rdServiciosNo.isChecked()){
                    servicios="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom", otronom);
                parametros.put("actividadactual", actividadactual);
                parametros.put("planNegocio", planNegocio);
                parametros.put("productivo", productivo);
                parametros.put("servicios", servicios);
                parametros.put("otro", otro);
                parametros.put("emprendimiento", emprendimiento);



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
