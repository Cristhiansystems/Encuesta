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
 * {@link emp_econ_10_0_19.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_19#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_19 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_19() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_19.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtrespuesta;
    String idEncuesta, otronom, respuesta;
    Integer analisisCostos, costoDirecto, costoIndirecto, otro;
    RadioButton rdCostoDirectoSi, rdCostoDirectoNo, rdCostoIndirectoSi, rdCostoIndirectoNo, rdotroSi, rdOtroNo, rdAnalisisSi, rdAnalisisNo;
    LinearLayout display1020, display1020ops;

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
    public static emp_econ_10_0_19 newInstance(String param1, String param2) {
        emp_econ_10_0_19 fragment = new emp_econ_10_0_19();
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
        vista= inflater.inflate(R.layout.fragment_emp_econ_10_0_19, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente55);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras55);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1019);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1020);
        txtrespuesta=(EditText) vista.findViewById(R.id.respuesta1020);


        rdAnalisisSi=(RadioButton) vista.findViewById(R.id.SiRealizoAnalisisCostosDePrecios);
        rdAnalisisNo=(RadioButton) vista.findViewById(R.id.NoRealizoAnalisisCostosDePrecios);

        rdCostoDirectoSi=(RadioButton) vista.findViewById(R.id.SiCostoDirecto);
        rdCostoDirectoNo=(RadioButton) vista.findViewById(R.id.NoCostoDirecto);

        rdCostoIndirectoSi=(RadioButton) vista.findViewById(R.id.SiCostoIndirecto);
        rdCostoIndirectoNo=(RadioButton) vista.findViewById(R.id.NoCostoIndirecto);

        rdotroSi=(RadioButton) vista.findViewById(R.id.OtroSi1020);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo1020);
        display1020=(LinearLayout) vista.findViewById(R.id.layout1020);
        display1020ops=(LinearLayout) vista.findViewById(R.id.layout1020ops);

        display1020ops.setVisibility(View.INVISIBLE);
        display1020ops.setVisibility(View.GONE);

        rdAnalisisNo.setOnClickListener(v -> {

            display1020.setVisibility(View.INVISIBLE);
            display1020.setVisibility(View.GONE);


        });

        rdAnalisisSi.setOnClickListener(v -> {

            display1020.setVisibility(View.VISIBLE);

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
                analisisCostos = jsonObject.optInt("realizas_analisis_costos");
                costoDirecto = jsonObject.optInt("costo_directo");
                costoIndirecto= jsonObject.optInt("costo_indirecto");
                otro= jsonObject.optInt("analisis_costos_otro");
                otronom = jsonObject.optString("analisis_costos_otro_nombre");
                respuesta = jsonObject.optString("respuesta_analisis");

                if(analisisCostos==2){

                    display1020.setVisibility(View.INVISIBLE);
                    display1020.setVisibility(View.GONE);

                }

                if(costoDirecto==1){
                    rdCostoDirectoSi.setChecked(true);
                }else if(costoDirecto==2){
                    rdCostoDirectoNo.setChecked(true);
                }

                if(costoIndirecto==1){
                    rdCostoIndirectoSi.setChecked(true);
                }else if(costoIndirecto==2){
                    rdCostoIndirectoNo.setChecked(true);
                }

                if(otro==1){
                    rdotroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }

                if(analisisCostos==1){
                    rdAnalisisSi.setChecked(true);
                }else if(analisisCostos==2){
                    rdAnalisisNo.setChecked(true);
                }


                txtotro.setText(otronom.toString());
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
        String url=ip+"actualiza1019.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta59(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta57(idFragment.getText().toString());

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


                String analizarCostos="0";
                if(rdAnalisisSi.isChecked()){
                    analizarCostos="1";
                }else if(rdAnalisisNo.isChecked()){
                    analizarCostos="2";
                }

                String costoDirecto="0";
                if(rdCostoDirectoSi.isChecked()){
                    costoDirecto="1";
                }else if(rdCostoDirectoNo.isChecked()){
                    costoDirecto="2";
                }

                String costoIndirecto="0";
                if(rdCostoIndirectoSi.isChecked()){
                    costoIndirecto="1";
                }else if(rdCostoIndirectoNo.isChecked()){
                    costoIndirecto="2";
                }

                String otro="0";
                if(rdotroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom", otronom);
                parametros.put("analizarCostos", analizarCostos);
                parametros.put("costoDirecto", costoDirecto);
                parametros.put("costoIndirecto", costoIndirecto);
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
