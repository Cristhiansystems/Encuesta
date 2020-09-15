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
 * {@link emp_econ_10_0_11.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_11#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_11 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_11() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_11.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtnovendes, txtrespuesta;
    String idEncuesta, novendes, otronom, respuesta;
    Integer vendes, ferias, puesto, mercadoFormales, consignacion, particulares, otro;
    RadioButton rdvendesSi, rdvendesNo, rdFeriaSi, rdFeriaNo, rdPuestoSi, rdPuestoNo, rdMercadoFormalSi, rdMercadoFormalNo, rdOtroSi, rdOtroNo, rdConsignacionSi, rdConsignacionNo, rdParticularSi, rdparticularNo;
    LinearLayout display1012, display1013ops;
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
    public static emp_econ_10_0_11 newInstance(String param1, String param2) {
        emp_econ_10_0_11 fragment = new emp_econ_10_0_11();
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
        vista=inflater.inflate(R.layout.fragment_emp_econ_10_0_11, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente52);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras52);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1011);
        txtnovendes=(EditText) vista.findViewById(R.id.txtResp1012);
        txtotro=(EditText) vista.findViewById(R.id.txtOtro1013);
        txtrespuesta=(EditText) vista.findViewById(R.id.txtResp1013);


        rdvendesSi=(RadioButton) vista.findViewById(R.id.SiVendoProductQueElab);
        rdvendesNo=(RadioButton) vista.findViewById(R.id.NoVendoProductQueElab);


        rdFeriaSi=(RadioButton) vista.findViewById(R.id.SiFeria);
        rdFeriaNo=(RadioButton) vista.findViewById(R.id.NoFeria);

        rdPuestoSi=(RadioButton) vista.findViewById(R.id.SiPuesto);
        rdPuestoNo=(RadioButton) vista.findViewById(R.id.NoPuesto);

        rdMercadoFormalSi=(RadioButton) vista.findViewById(R.id.SiMercadosformales);
        rdMercadoFormalNo=(RadioButton) vista.findViewById(R.id.NoMercadosformales);

        rdConsignacionSi=(RadioButton) vista.findViewById(R.id.SiConsignacion);
        rdConsignacionNo=(RadioButton) vista.findViewById(R.id.NoConsignacion);

        rdParticularSi=(RadioButton) vista.findViewById(R.id.SiParticulares);
        rdparticularNo=(RadioButton) vista.findViewById(R.id.NoParticulares);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi1013);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo1013);
        display1012=(LinearLayout) vista.findViewById(R.id.layout1012);
        display1013ops=(LinearLayout) vista.findViewById(R.id.layout1013ops);
        display1013ops.setVisibility(View.INVISIBLE);
        display1013ops.setVisibility(View.GONE);

        rdvendesSi.setOnClickListener(v -> {

            display1012.setVisibility(View.INVISIBLE);
            display1012.setVisibility(View.GONE);


        });

        rdvendesNo.setOnClickListener(v -> {

            display1012.setVisibility(View.VISIBLE);

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
                vendes = jsonObject.optInt("vendes_productos");
                novendes = jsonObject.optString("no_vendes_productos");
                ferias = jsonObject.optInt("ferias");
                puesto = jsonObject.optInt("puestos");
                mercadoFormales = jsonObject.optInt("mercado_formal");
                consignacion = jsonObject.optInt("consignacion");
                particulares = jsonObject.optInt("particulares");
                otro = jsonObject.optInt("otro_lugar_vender");
                otronom = jsonObject.optString("otro_lugar_vender_nombre");
                respuesta = jsonObject.optString("respuesta_vendes_producto");

                if(vendes==1){

                    display1012.setVisibility(View.INVISIBLE);
                    display1012.setVisibility(View.GONE);

                }

                if(vendes==1){
                    rdvendesSi.setChecked(true);
                }else if(vendes==2){
                    rdvendesNo.setChecked(true);
                }

                if(ferias==1){
                    rdFeriaSi.setChecked(true);
                }else if(ferias==2){
                    rdFeriaNo.setChecked(true);
                }

                if(puesto==1){
                    rdPuestoSi.setChecked(true);
                }else if(puesto==2){
                    rdPuestoNo.setChecked(true);
                }

                if(mercadoFormales==1){
                    rdMercadoFormalSi.setChecked(true);
                }else if(mercadoFormales==2){
                    rdMercadoFormalNo.setChecked(true);
                }

                if(consignacion==1){
                    rdConsignacionSi.setChecked(true);
                }else if(consignacion==2){
                    rdConsignacionNo.setChecked(true);
                }

                if(particulares==1){
                    rdParticularSi.setChecked(true);
                }else if(particulares==2){
                    rdparticularNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }

                txtotro.setText(otronom.toString());
                txtnovendes.setText(novendes.toString());
                txtrespuesta.setText(respuesta.toString());


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
        String url=ip+"actualiza1011.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta56(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta54(idFragment.getText().toString());

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
                String novendes=txtnovendes.getText().toString();
                String respuesta=txtrespuesta.getText().toString();

                String vendes="0";
                if(rdvendesSi.isChecked()){
                    vendes="1";
                }else if(rdvendesNo.isChecked()){
                    vendes="2";
                }


                String feria="0";
                if(rdFeriaSi.isChecked()){
                    feria="1";
                }else if(rdFeriaNo.isChecked()){
                    feria="2";
                }


                String puesto="0";
                if(rdPuestoSi.isChecked()){
                    puesto="1";
                }else if(rdPuestoNo.isChecked()){
                    puesto="2";
                }

                String mercadoFormal="0";
                if(rdMercadoFormalSi.isChecked()){
                    mercadoFormal="1";
                }else if(rdMercadoFormalNo.isChecked()){
                    mercadoFormal="2";
                }

                String consignacion="0";
                if(rdConsignacionSi.isChecked()){
                    consignacion="1";
                }else if(rdConsignacionNo.isChecked()){
                    consignacion="2";
                }

                String particulares="0";
                if(rdParticularSi.isChecked()){
                    particulares="1";
                }else if(rdparticularNo.isChecked()){
                    particulares="2";
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
                parametros.put("novendes", novendes);
                parametros.put("vendes", vendes);
                parametros.put("feria", feria);
                parametros.put("puesto", puesto);
                parametros.put("otro", otro);
                parametros.put("mercadoFormal", mercadoFormal);
                parametros.put("consignacion", consignacion);
                parametros.put("particulares", particulares);
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
