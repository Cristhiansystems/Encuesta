package com.example.encuesta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.encuesta.Adapter.FamiliaAdapter;
import com.example.encuesta.entidades.Familia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ddef_gastar_dinero.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ddef_gastar_dinero#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ddef_gastar_dinero extends Fragment{
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

    EditText porAlquiler, porAlimentacion, porEstudios, porVestimenta, porServicios, porViajes, porRecreacion, porOtro, porNosabe, nomOtro, Proyecto;
    TextView idFragment;
    Spinner orAlquiler, orAlimentacion, orEstudios, orVestimenta, orServicios, orViajes, orRecreacion, orOtro, orNosabe;
    String idEncuesta, strAlquiler, strAlimentacion, strEstudios, strVestimenta, strServicios, strViajes, strRecreacion, strOtro, strNosabe, strnomOtro, strPoyecto ;
    String strorAlquiler, strorAlimentacion, strorEstudios, strorVestimenta, strorServicios, strorViajes, strorRecreacion, strorOtro, strorNosabe;


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

    public ddef_gastar_dinero() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ddef_gastar_dinero.
     */
    // TODO: Rename and change types and number of parameters
    public static ddef_gastar_dinero newInstance(String param1, String param2) {
        ddef_gastar_dinero fragment = new ddef_gastar_dinero();
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
        vista=inflater.inflate(R.layout.fragment_ddef_gastar_dinero, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente9);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras9);
        idFragment= (TextView) vista.findViewById(R.id.idGastarDinero);

        orAlquiler=(Spinner) vista.findViewById(R.id.OrdenAlquiler);
        orAlimentacion=(Spinner) vista.findViewById(R.id.OrdenAlimentacion);
        orEstudios=(Spinner) vista.findViewById(R.id.OrdenEstudios);
        orVestimenta=(Spinner) vista.findViewById(R.id.OrdenVestimenta);
        orServicios=(Spinner) vista.findViewById(R.id.OrdenServicios);
        orViajes=(Spinner) vista.findViewById(R.id.OrdenViajes);
        orRecreacion=(Spinner) vista.findViewById(R.id.OrdenRecreacion);
        orOtro=(Spinner) vista.findViewById(R.id.OrdenOtroDineroGastar);
        orNosabe=(Spinner) vista.findViewById(R.id.OrdenNosabe);

        porAlquiler=(EditText) vista.findViewById(R.id.txtPorcentajeAlquiler);
        porAlimentacion=(EditText) vista.findViewById(R.id.txtPorcentajeAlimentacion);
        porEstudios=(EditText) vista.findViewById(R.id.txtPorcentajeEstudios);
        porVestimenta=(EditText) vista.findViewById(R.id.txtPorcentajeVestimenta);
        porServicios=(EditText) vista.findViewById(R.id.txtPorcentajeServicios);
        porViajes=(EditText) vista.findViewById(R.id.txtPorcentajeViajes);
        porRecreacion=(EditText) vista.findViewById(R.id.txtPorcentajeRecreacion);
        porOtro=(EditText) vista.findViewById(R.id.txtOtroDineroGastar);
        porNosabe=(EditText) vista.findViewById(R.id.txtPorcentajeNosabe);
        nomOtro=(EditText) vista.findViewById(R.id.txtOtroDineroGastarNombre);
        Proyecto=(EditText) vista.findViewById(R.id.txtenterarProyecto);




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
        String url="http://192.168.0.13/encuestasWS/actualizarGastarDinero.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta10(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta8(idFragment.getText().toString());
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
                String ordAlquiler=orAlquiler.getSelectedItem().toString();
                String ordAlimentacion=orAlimentacion.getSelectedItem().toString();
                String ordEstudios=orEstudios.getSelectedItem().toString();
                String ordVestimenta=orVestimenta.getSelectedItem().toString();
                String ordServiciosBasicos=orServicios.getSelectedItem().toString();
                String ordViajes=orViajes.getSelectedItem().toString();
                String ordActividades=orRecreacion.getSelectedItem().toString();
                String ordOtro=orOtro.getSelectedItem().toString();
                String ordNoSabe=orNosabe.getSelectedItem().toString();


                String pordAlquiler=porAlquiler.getText().toString();
                String pordAlimentacion=porAlimentacion.getText().toString();
                String pordEstudios=porEstudios.getText().toString();
                String pordVestimenta=porVestimenta.getText().toString();
                String pordServiciosBasicos=porServicios.getText().toString();
                String pordViajes=porViajes.getText().toString();
                String pordActividades=porRecreacion.getText().toString();
                String pordOtro=porOtro.getText().toString();
                String pordNoSabe=porNosabe.getText().toString();

                String proyecto=Proyecto.getText().toString();
                String otro=nomOtro.getText().toString();

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("ordAlquiler",ordAlquiler);
                parametros.put("ordAlimentacion",ordAlimentacion);
                parametros.put("ordEstudios",ordEstudios);
                parametros.put("ordVestimenta",ordVestimenta);
                parametros.put("ordServiciosBasicos",ordServiciosBasicos);
                parametros.put("ordViajes",ordViajes);
                parametros.put("ordActividades",ordActividades);
                parametros.put("ordOtro",ordOtro);
                parametros.put("ordNoSabe",ordNoSabe);


                parametros.put("pordAlquiler",pordAlquiler);
                parametros.put("pordAlimentacion",pordAlimentacion);
                parametros.put("pordEstudios",pordEstudios);
                parametros.put("pordVestimenta",pordVestimenta);
                parametros.put("pordServiciosBasicos",pordServiciosBasicos);
                parametros.put("pordViajes",pordViajes);
                parametros.put("pordActividades",pordActividades);
                parametros.put("pordOtro",pordOtro);
                parametros.put("pordNoSabe",pordNoSabe);


                parametros.put("otro",otro);
                parametros.put("proyecto",proyecto);


                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json=response.optJSONArray("usuario");
            JSONObject jsonObject=null;

            try{
                jsonObject=json.getJSONObject(0);
                idEncuesta=jsonObject.optString("encuesta_emt");
                strorAlquiler=jsonObject.optString("alquiler_orden");
                strorAlimentacion=jsonObject.optString("alimentacion_orden");
                strorEstudios=jsonObject.optString("estudios_orden");
                strorVestimenta=jsonObject.optString("vestimenta_orden");
                strorServicios=jsonObject.optString("servicios_basicos_orden");
                strorViajes=jsonObject.optString("viajes_orden");
                strorRecreacion=jsonObject.optString("recreacion_orden");
                strorOtro=jsonObject.optString("otro_orden");
                strorNosabe=jsonObject.optString("gasto_dinero_no_sabe_orden");


                strAlquiler=jsonObject.optString("alquiler_porc");
                strAlimentacion=jsonObject.optString("alimentacion_porc");
                strEstudios=jsonObject.optString("estudios_porc");
                strVestimenta=jsonObject.optString("vestimenta_porc");
                strServicios=jsonObject.optString("servicios_basicos_porc");
                strViajes=jsonObject.optString("viajes_porc");
                strRecreacion=jsonObject.optString("recreacion_porc");
                strOtro=jsonObject.optString("otro_porc");
                strNosabe=jsonObject.optString("gasto_dinero_no_sabe_porc");


                strPoyecto=jsonObject.optString("enteraste_proyecto");
                strnomOtro=jsonObject.optString("gasto_dinero_otro_especificar");


                ArrayList<String> ordenGastoAlquiler= new ArrayList<String>();
                ordenGastoAlquiler.add(strorAlquiler);
                ordenGastoAlquiler.add("1");
                ordenGastoAlquiler.add("2");
                ordenGastoAlquiler.add("3");
                ordenGastoAlquiler.add("4");
                ordenGastoAlquiler.add("5");
                ordenGastoAlquiler.add("6");
                ordenGastoAlquiler.add("7");
                ordenGastoAlquiler.add("8");
                ordenGastoAlquiler.add("9");

                ArrayAdapter<CharSequence> adaptadorAlquiler=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastoAlquiler);
                orAlquiler.setAdapter(adaptadorAlquiler);


                ArrayList<String> ordenGastoAlimentacion= new ArrayList<String>();
                ordenGastoAlimentacion.add(strorAlimentacion);
                ordenGastoAlimentacion.add("1");
                ordenGastoAlimentacion.add("2");
                ordenGastoAlimentacion.add("3");
                ordenGastoAlimentacion.add("4");
                ordenGastoAlimentacion.add("5");
                ordenGastoAlimentacion.add("6");
                ordenGastoAlimentacion.add("7");
                ordenGastoAlimentacion.add("8");
                ordenGastoAlimentacion.add("9");

                ArrayAdapter<CharSequence> adaptadorAlimentacion=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastoAlimentacion);
                orAlimentacion.setAdapter(adaptadorAlimentacion);


                ArrayList<String> ordenGastoEstudios= new ArrayList<String>();
                ordenGastoEstudios.add(strorEstudios);
                ordenGastoEstudios.add("1");
                ordenGastoEstudios.add("2");
                ordenGastoEstudios.add("3");
                ordenGastoEstudios.add("4");
                ordenGastoEstudios.add("5");
                ordenGastoEstudios.add("6");
                ordenGastoEstudios.add("7");
                ordenGastoEstudios.add("8");
                ordenGastoEstudios.add("9");

                ArrayAdapter<CharSequence> adaptadorEstudios=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastoEstudios);
                orEstudios.setAdapter(adaptadorEstudios);

                ArrayList<String> ordenGastoVestimenta= new ArrayList<String>();
                ordenGastoVestimenta.add(strorVestimenta);
                ordenGastoVestimenta.add("1");
                ordenGastoVestimenta.add("2");
                ordenGastoVestimenta.add("3");
                ordenGastoVestimenta.add("4");
                ordenGastoVestimenta.add("5");
                ordenGastoVestimenta.add("6");
                ordenGastoVestimenta.add("7");
                ordenGastoVestimenta.add("8");
                ordenGastoVestimenta.add("9");

                ArrayAdapter<CharSequence> adaptadorVestimenta=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastoVestimenta);
                orVestimenta.setAdapter(adaptadorVestimenta);

                ArrayList<String> ordenGastoServicios= new ArrayList<String>();
                ordenGastoServicios.add(strorServicios);
                ordenGastoServicios.add("1");
                ordenGastoServicios.add("2");
                ordenGastoServicios.add("3");
                ordenGastoServicios.add("4");
                ordenGastoServicios.add("5");
                ordenGastoServicios.add("6");
                ordenGastoServicios.add("7");
                ordenGastoServicios.add("8");
                ordenGastoServicios.add("9");

                ArrayAdapter<CharSequence> adaptadorServicios=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastoServicios);
                orServicios.setAdapter(adaptadorServicios);


                ArrayList<String> ordenGastoViajes= new ArrayList<String>();
                ordenGastoViajes.add(strorViajes);
                ordenGastoViajes.add("1");
                ordenGastoViajes.add("2");
                ordenGastoViajes.add("3");
                ordenGastoViajes.add("4");
                ordenGastoViajes.add("5");
                ordenGastoViajes.add("6");
                ordenGastoViajes.add("7");
                ordenGastoViajes.add("8");
                ordenGastoViajes.add("9");

                ArrayAdapter<CharSequence> adaptadorViaje=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastoViajes);
                orViajes.setAdapter(adaptadorViaje);

                ArrayList<String> ordenGastRecreacion= new ArrayList<String>();
                ordenGastRecreacion.add(strorRecreacion);
                ordenGastRecreacion.add("1");
                ordenGastRecreacion.add("2");
                ordenGastRecreacion.add("3");
                ordenGastRecreacion.add("4");
                ordenGastRecreacion.add("5");
                ordenGastRecreacion.add("6");
                ordenGastRecreacion.add("7");
                ordenGastRecreacion.add("8");
                ordenGastRecreacion.add("9");

                ArrayAdapter<CharSequence> adaptadorRecreacion=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastRecreacion);
                orRecreacion.setAdapter(adaptadorRecreacion);

                ArrayList<String> ordenGastOtro= new ArrayList<String>();
                ordenGastOtro.add(strorOtro);
                ordenGastOtro.add("1");
                ordenGastOtro.add("2");
                ordenGastOtro.add("3");
                ordenGastOtro.add("4");
                ordenGastOtro.add("5");
                ordenGastOtro.add("6");
                ordenGastOtro.add("7");
                ordenGastOtro.add("8");
                ordenGastOtro.add("9");

                ArrayAdapter<CharSequence> adaptadorOtro=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastOtro);
                orOtro.setAdapter(adaptadorOtro);

                ArrayList<String> ordenGastNosabe= new ArrayList<String>();
                ordenGastNosabe.add(strorNosabe);
                ordenGastNosabe.add("1");
                ordenGastNosabe.add("2");
                ordenGastNosabe.add("3");
                ordenGastNosabe.add("4");
                ordenGastNosabe.add("5");
                ordenGastNosabe.add("6");
                ordenGastNosabe.add("7");
                ordenGastNosabe.add("8");
                ordenGastNosabe.add("9");

                ArrayAdapter<CharSequence> adaptadorNosabe=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,ordenGastNosabe);
                orNosabe.setAdapter(adaptadorNosabe);


                porAlquiler.setText(strAlquiler.toString());
                porAlimentacion.setText(strAlimentacion.toString());
                porEstudios.setText(strEstudios.toString());
                porVestimenta.setText(strVestimenta.toString());
                porServicios.setText(strServicios.toString());
                porViajes.setText(strViajes.toString());
                porRecreacion.setText(strRecreacion.toString());
                porOtro.setText(strOtro.toString());
                porNosabe.setText(strNosabe.toString());

                nomOtro.setText(strnomOtro.toString());
                Proyecto.setText(strPoyecto.toString());



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
