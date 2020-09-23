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
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ssc_alimento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssc_alimento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssc_alimento extends Fragment{
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
    RadioButton rdPagoCuentaSi, rdPagoCuentaNo, rdComedoresSi, rdComedoresNo, rdPaganOtroSi, rdPaganOtroNo, rdPagaFamiliaSi, rdPagaFamiliaNo, rdRegalanSi, rdRegalanNo, rdInsumosSi, rdInsumosNo, rdOtroSi, rdOtroNo;
    EditText txtOtroAlimento, txtNumeroVeces;
    String otroAlimento,numeroVeces, idEncuesta;
    Integer pagoCuenta, comedores, paganOtro, pagaFamilia, regalan, insumos, otro;
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
    private OnFragmentInteractionListener mListener;

    public ssc_alimento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssc_alimento.
     */
    // TODO: Rename and change types and number of parameters
    public static ssc_alimento newInstance(String param1, String param2) {
        ssc_alimento fragment = new ssc_alimento();
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
        vista=inflater.inflate(R.layout.fragment_ssc_alimento, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente16);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras16);
        idFragment= (TextView) vista.findViewById(R.id.idAlimento);
        rdPagoCuentaSi=(RadioButton) vista.findViewById(R.id.pagoCuentaSi);
        rdPagoCuentaNo=(RadioButton) vista.findViewById(R.id.pagoCuentaNo);
        rdComedoresSi=(RadioButton) vista.findViewById(R.id.comedoresSi);
        rdComedoresNo=(RadioButton) vista.findViewById(R.id.comedoresNo);
        rdPaganOtroSi=(RadioButton) vista.findViewById(R.id.paganOtrosSi);
        rdPaganOtroNo=(RadioButton) vista.findViewById(R.id.paganOtrosNo);
        rdPagaFamiliaSi=(RadioButton) vista.findViewById(R.id.pagaFamiliaSi);
        rdPagaFamiliaNo=(RadioButton) vista.findViewById(R.id.pagaFamiliaNo);
        rdRegalanSi=(RadioButton) vista.findViewById(R.id.regalanSi);
        rdRegalanNo=(RadioButton) vista.findViewById(R.id.regalanNo);
        rdInsumosSi=(RadioButton) vista.findViewById(R.id.insumosSi);
        rdInsumosNo=(RadioButton) vista.findViewById(R.id.insumosNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroAlimentoSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroAlimentoNo);

        txtOtroAlimento= (EditText) vista.findViewById(R.id.txtotronomAlimento);
        txtNumeroVeces= (EditText) vista.findViewById(R.id.txtNrolimentos);

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

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualizaAlimento.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta17(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta15(idFragment.getText().toString());

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
                String pagoCuenta="0";
                if(rdPagoCuentaSi.isChecked()){
                    pagoCuenta="1";
                }else if(rdPagoCuentaNo.isChecked()){
                    pagoCuenta="2";
                }

                String comedores="0";
                if(rdComedoresSi.isChecked()){
                    comedores="1";
                }else if(rdComedoresNo.isChecked()){
                    comedores="2";
                }

                String pagaOtros="0";
                if(rdPaganOtroSi.isChecked()){
                    pagaOtros="1";
                }else if(rdPaganOtroNo.isChecked()){
                    pagaOtros="2";
                }

                String pagaFamilia="0";
                if(rdPagaFamiliaSi.isChecked()){
                    pagaFamilia="1";
                }else if(rdPagaFamiliaNo.isChecked()){
                    pagaFamilia="2";
                }


                String regalan="0";
                if(rdRegalanSi.isChecked()){
                    regalan="1";
                }else if(rdRegalanNo.isChecked()){
                    regalan="2";
                }

                String insumos="0";
                if(rdInsumosSi.isChecked()){
                    insumos="1";
                }else if(rdInsumosNo.isChecked()){
                    insumos="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }

                String otroAlimento=txtOtroAlimento.getText().toString();
                String nVeces=txtNumeroVeces.getText().toString();


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("pagoCuenta",pagoCuenta);
                parametros.put("comedores",comedores);
                parametros.put("pagaOtros",pagaOtros);
                parametros.put("pagaFamilia",pagaFamilia);
                parametros.put("regalan",regalan);
                parametros.put("insumos",insumos);
                parametros.put("otro",otro);
                parametros.put("otroAlimento",otroAlimento);
                parametros.put("nVeces",nVeces);


                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
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
                pagoCuenta = jsonObject.optInt("pago_por_mi_cuenta");
                comedores = jsonObject.optInt("comedores");
                paganOtro = jsonObject.optInt("pagan_otros");
                pagaFamilia = jsonObject.optInt("paga_familia");
                regalan = jsonObject.optInt("regalan");
                insumos = jsonObject.optInt("insumos");
                otro = jsonObject.optInt("otro_alimento");
                otroAlimento = jsonObject.optString("otro_alimento_nombre");
                numeroVeces = jsonObject.optString("comida_al_dia");

                if (pagoCuenta == 1) {
                    rdPagoCuentaSi.setChecked(true);
                } else if (pagoCuenta == 2) {
                    rdPagoCuentaNo.setChecked(true);
                }

                if (comedores == 1) {
                    rdComedoresSi.setChecked(true);
                } else if (comedores == 2) {
                    rdComedoresNo.setChecked(true);
                }

                if (paganOtro == 1) {
                    rdPaganOtroSi.setChecked(true);
                } else if (paganOtro == 2) {
                    rdPaganOtroNo.setChecked(true);
                }

                if (pagaFamilia == 1) {
                    rdPagaFamiliaSi.setChecked(true);
                } else if (pagaFamilia == 2) {
                    rdPagaFamiliaNo.setChecked(true);
                }

                if (regalan == 1) {
                    rdRegalanSi.setChecked(true);
                } else if (regalan == 2) {
                    rdRegalanNo.setChecked(true);
                }

                if (insumos == 1) {
                    rdInsumosSi.setChecked(true);
                } else if (insumos == 2) {
                    rdInsumosNo.setChecked(true);
                }

                if (otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (otro == 2) {
                    rdOtroNo.setChecked(true);
                }

                txtOtroAlimento.setText(otroAlimento.toString());
                txtNumeroVeces.setText(numeroVeces.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
       // request.add(jsonObjectRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
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
