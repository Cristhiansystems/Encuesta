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
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link org_9_0_6.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link org_9_0_6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class org_9_0_6 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public org_9_0_6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment org_9_0_6.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtorganizacion, txtobjetivo;
    String idEncuesta, organizacion, objetivo;
    Integer ley, concejos, actividadConcejo, participar, organizacion95;
    RadioButton rdLeySi, rdLeyNo, rdConsejosSi, rdConsejosNo, rdActividadSi, rdActividadNo, rdParticiparSi, rdParticparNo;
    LinearLayout display96, display97, display98, display99, display910, display911;
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
    public static org_9_0_6 newInstance(String param1, String param2) {
        org_9_0_6 fragment = new org_9_0_6();
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
        vista= inflater.inflate(R.layout.fragment_org_9_0_6, container, false);

        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente48);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras48);
        idFragment=(TextView) vista.findViewById(R.id.idemppers96);
        txtorganizacion=(EditText) vista.findViewById(R.id.Resp906);
        txtobjetivo=(EditText) vista.findViewById(R.id.Resp907);


        rdLeySi=(RadioButton) vista.findViewById(R.id.SiEscuchadoSobreLaLey342);
        rdLeyNo=(RadioButton) vista.findViewById(R.id.NoEscuchadoSobreLaLey342);

        rdConsejosSi=(RadioButton) vista.findViewById(R.id.SiSeLosConsejosJuveniles);
        rdConsejosNo=(RadioButton) vista.findViewById(R.id.NoSeLosConsejosJuveniles);

        rdActividadSi=(RadioButton) vista.findViewById(R.id.SiConozAlgActiviConsejJuv);
        rdActividadNo=(RadioButton) vista.findViewById(R.id.NoConozAlgActiviConsejJuv);

        rdParticiparSi=(RadioButton) vista.findViewById(R.id.SiMeGustarParticConsejJuve);
        rdParticparNo=(RadioButton) vista.findViewById(R.id.NoMeGustarParticConsejJuv);

        display96=(LinearLayout) vista.findViewById(R.id.layout96);
        display97=(LinearLayout) vista.findViewById(R.id.layout97);
        display98=(LinearLayout) vista.findViewById(R.id.layout98);
        display99=(LinearLayout) vista.findViewById(R.id.layout99);
        display910=(LinearLayout) vista.findViewById(R.id.layout910);
        display911=(LinearLayout) vista.findViewById(R.id.layout911);

        rdConsejosNo.setOnClickListener(v -> {

            display910.setVisibility(View.INVISIBLE);
            display910.setVisibility(View.GONE);

            display911.setVisibility(View.INVISIBLE);
            display911.setVisibility(View.GONE);

        });

        rdConsejosSi.setOnClickListener(v -> {

            display910.setVisibility(View.VISIBLE);


            display911.setVisibility(View.VISIBLE);


        });

        rdActividadNo.setOnClickListener(v -> {



            display911.setVisibility(View.INVISIBLE);
            display911.setVisibility(View.GONE);

        });

        rdActividadSi.setOnClickListener(v -> {



            display911.setVisibility(View.VISIBLE);


        });

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
                organizacion = jsonObject.optString("conoces_organizaciones_ayuda_jovenes_nombre");
                objetivo = jsonObject.optString("conoces_organizaciones_ayuda_jovenes_objetivo");

                ley = jsonObject.optInt("conoces_ley_342");
                concejos = jsonObject.optInt("conoces_consejo_juvenil");
                actividadConcejo = jsonObject.optInt("conoces_actividad_consejo_juvenil");
                participar = jsonObject.optInt("participar_consejo_juvenil");


                //otro fragment
                organizacion95 = jsonObject.optInt("conoces_organizaciones_ayuda_jovenes");

                if(organizacion95==2){
                    display96.setVisibility(View.INVISIBLE);
                    display96.setVisibility(View.GONE);

                    display97.setVisibility(View.INVISIBLE);
                    display97.setVisibility(View.GONE);
                }
                if(ley==1){
                    rdLeySi.setChecked(true);
                }else if(ley==2){
                    rdLeyNo.setChecked(true);
                }


                if(concejos==1){
                    rdConsejosSi.setChecked(true);
                }else if(concejos==2){
                    rdConsejosNo.setChecked(true);
                }

                if(actividadConcejo==1){
                    rdActividadSi.setChecked(true);
                }else if(actividadConcejo==2){
                    rdActividadNo.setChecked(true);
                }

                if(participar==1){
                    rdParticiparSi.setChecked(true);
                }else if(participar==2){
                    rdParticparNo.setChecked(true);
                }

                txtorganizacion.setText(organizacion.toString());
                txtobjetivo.setText(objetivo.toString());




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
        String url=ip+"actualiza96.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta52(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta50(idFragment.getText().toString());

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

                String organizacion=txtorganizacion.getText().toString();
                String objetivo=txtobjetivo.getText().toString();

                String ley="0";

                if(rdLeySi.isChecked()){
                    ley="1";
                }else if(rdLeyNo.isChecked()){
                    ley="2";
                }

                String concejos="0";
                if(rdConsejosSi.isChecked()){
                    concejos="1";
                }else if(rdConsejosNo.isChecked()){
                    concejos="2";
                }

                String actividadConcejo="0";
                if(rdActividadSi.isChecked()){
                    actividadConcejo="1";
                }else if(rdActividadNo.isChecked()){
                    actividadConcejo="2";
                }

                String participar="0";
                if(rdParticiparSi.isChecked()){
                    participar="1";
                }else if(rdParticparNo.isChecked()){
                    participar="2";
                }

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("organizacion", organizacion);
                parametros.put("objetivo", objetivo);
                parametros.put("ley", ley);
                parametros.put("concejos", concejos);
                parametros.put("actividadConcejo", actividadConcejo);
                parametros.put("participar", participar);


                return parametros;
            }
        };
       // request.add(stringRequest);
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
