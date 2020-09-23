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
 * {@link sat_muni_12_0_4.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sat_muni_12_0_4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sat_muni_12_0_4 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public sat_muni_12_0_4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sat_muni_12_0_4.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    String idEncuesta;
    Integer rehabilitacion, rehabilitacionSatisfaccion, juvenil, juvenilSatisfaccion, educacion, educacionSatisfaccion;
    RadioButton rdrehabilitacionSi, rdrehabilitacionNo, rdrehabilitacionMS, rdrehabilitacionS, rdrehabilitacionI, rdrehabilitacionIn, rdrehabilitacionMIn;
    RadioButton rdjuvenilSi, rdjuvenilNo, rdjuvenilMS, rdjuvenilS, rdjuvenilI, rdjuvenilIn, rdjuvenilMIn;
    RadioButton rdeducacionSi, rdeducacionNo, rdeducacionMS, rdeducacionS, rdeducacionI, rdeducacionIn, rdeducacionMIn;
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
    public static sat_muni_12_0_4 newInstance(String param1, String param2) {
        sat_muni_12_0_4 fragment = new sat_muni_12_0_4();
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
       vista= inflater.inflate(R.layout.fragment_sat_muni_12_0_4, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente70);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras70);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1204);


        rdrehabilitacionSi=(RadioButton) vista.findViewById(R.id.RdSiAtencionParaLaRehabilitacion);
        rdrehabilitacionNo=(RadioButton) vista.findViewById(R.id.RdNoAtencionParaLaRehabilitacion);
        rdrehabilitacionMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1204);
        rdrehabilitacionS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1204);
        rdrehabilitacionI=(RadioButton) vista.findViewById(R.id.RdIndiferente1204);
        rdrehabilitacionIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1204);
        rdrehabilitacionMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1204);



        rdjuvenilSi=(RadioButton) vista.findViewById(R.id.RdSiParticipacionJuvenil);
        rdjuvenilNo=(RadioButton) vista.findViewById(R.id.RdNoParticipacionJuvenil);
        rdjuvenilMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1205);
        rdjuvenilS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1205);
        rdjuvenilI=(RadioButton) vista.findViewById(R.id.RdIndiferente1205);
        rdjuvenilIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1205);
        rdjuvenilMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1205);


        rdeducacionSi=(RadioButton) vista.findViewById(R.id.RdSiEducacion);
        rdeducacionNo=(RadioButton) vista.findViewById(R.id.RdNoEducacion);
        rdeducacionMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1206);
        rdeducacionS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1206);
        rdeducacionI=(RadioButton) vista.findViewById(R.id.RdIndiferente1206);
        rdeducacionIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1206);
        rdeducacionMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1206);
        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));


        }

        //Aqui empieza el volley
        //request= Volley.newRequestQueue(getContext());
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
                rehabilitacion = jsonObject.optInt("atencion_rehabilitacion");
                rehabilitacionSatisfaccion = jsonObject.optInt("atencion_rehabilitacion_satisfaccion");
                juvenil = jsonObject.optInt("participacion_juvenil");
                juvenilSatisfaccion = jsonObject.optInt("participacion_juvenil_satisfaccion");
                educacion = jsonObject.optInt("educacion");
                educacionSatisfaccion = jsonObject.optInt("educacion_satisfaccion");



                if(rehabilitacion==1){
                    rdrehabilitacionSi.setChecked(true);
                }else if(rehabilitacion==2){
                    rdrehabilitacionNo.setChecked(true);
                }

                if(rehabilitacionSatisfaccion==1){
                    rdrehabilitacionMS.setChecked(true);
                }else if(rehabilitacionSatisfaccion==2){
                    rdrehabilitacionS.setChecked(true);
                }else if(rehabilitacionSatisfaccion==3){
                    rdrehabilitacionI.setChecked(true);
                }else if(rehabilitacionSatisfaccion==4){
                    rdrehabilitacionIn.setChecked(true);
                }else if(rehabilitacionSatisfaccion==5){
                    rdrehabilitacionMIn.setChecked(true);
                }

                if(juvenil==1){
                    rdjuvenilSi.setChecked(true);
                }else if(juvenil==2){
                    rdjuvenilNo.setChecked(true);
                }

                if(juvenilSatisfaccion==1){
                    rdjuvenilMS.setChecked(true);
                }else if(juvenilSatisfaccion==2){
                    rdjuvenilS.setChecked(true);
                }else if(juvenilSatisfaccion==3){
                    rdjuvenilI.setChecked(true);
                }else if(juvenilSatisfaccion==4){
                    rdjuvenilIn.setChecked(true);
                }else if(juvenilSatisfaccion==5){
                    rdjuvenilMIn.setChecked(true);
                }

                if(educacion==1){
                    rdeducacionSi.setChecked(true);
                }else if(educacion==2){
                    rdeducacionNo.setChecked(true);
                }

                if(educacionSatisfaccion==1){
                    rdeducacionMS.setChecked(true);
                }else if(educacionSatisfaccion==2){
                    rdeducacionS.setChecked(true);
                }else if(educacionSatisfaccion==3){
                    rdeducacionI.setChecked(true);
                }else if(educacionSatisfaccion==4){
                    rdeducacionIn.setChecked(true);
                }else if(educacionSatisfaccion==5){
                    rdeducacionMIn.setChecked(true);
                }




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
        String url=ip+"actualiza1204.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta74(idFragment.getText().toString());

                }else if(pantalla=="Atras"){

                    interfaceComunicaFragments.enviarEncuesta72(idFragment.getText().toString());

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



                String rehabilitacion="0";
                if(rdrehabilitacionSi.isChecked()){
                    rehabilitacion="1";
                }else if(rdrehabilitacionNo.isChecked()){
                    rehabilitacion="2";
                }

                String rehabilitacionSatisfaccion="0";
                if(rdrehabilitacionMS.isChecked()){
                    rehabilitacionSatisfaccion="1";

                }else if(rdrehabilitacionS.isChecked()){
                    rehabilitacionSatisfaccion="2";
                }else if(rdrehabilitacionI.isChecked()){
                    rehabilitacionSatisfaccion="3";
                }else if(rdrehabilitacionIn.isChecked()){
                    rehabilitacionSatisfaccion="4";
                }else if(rdrehabilitacionMIn.isChecked()){
                    rehabilitacionSatisfaccion="5";
                }


                String juvenil="0";
                if(rdjuvenilSi.isChecked()){
                    juvenil="1";
                }else if(rdjuvenilNo.isChecked()){
                    juvenil="2";
                }

                String juvenilSatisfaccion="0";
                if(rdjuvenilMS.isChecked()){
                    juvenilSatisfaccion="1";

                }else if(rdjuvenilS.isChecked()){
                    juvenilSatisfaccion="2";
                }else if(rdjuvenilI.isChecked()){
                    juvenilSatisfaccion="3";
                }else if(rdjuvenilIn.isChecked()){
                    juvenilSatisfaccion="4";
                }else if(rdjuvenilMIn.isChecked()){
                    juvenilSatisfaccion="5";
                }

                String educacion="0";
                if(rdeducacionSi.isChecked()){
                    educacion="1";
                }else if(rdeducacionNo.isChecked()){
                    educacion="2";
                }

                String educacionSatisfaccion="0";
                if(rdeducacionMS.isChecked()){
                    educacionSatisfaccion="1";

                }else if(rdeducacionS.isChecked()){
                    educacionSatisfaccion="2";
                }else if(rdeducacionI.isChecked()){
                    educacionSatisfaccion="3";
                }else if(rdeducacionIn.isChecked()){
                    educacionSatisfaccion="4";
                }else if(rdeducacionMIn.isChecked()){
                    educacionSatisfaccion="5";
                }

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("rehabilitacion", rehabilitacion);
                parametros.put("rehabilitacionSatisfaccion", rehabilitacionSatisfaccion);
                parametros.put("juvenil", juvenil);
                parametros.put("juvenilSatisfaccion", juvenilSatisfaccion);
                parametros.put("educacion", educacion);
                parametros.put("educacionSatisfaccion", educacionSatisfaccion);
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
