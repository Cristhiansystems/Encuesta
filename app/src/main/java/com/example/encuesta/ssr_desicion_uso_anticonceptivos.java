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
 * {@link ssr_desicion_uso_anticonceptivos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssr_desicion_uso_anticonceptivos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssr_desicion_uso_anticonceptivos extends Fragment{
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
    RadioButton rdTuya, rdTuPareja, rdDesicionConjunta, rdEmbarazadaSi, rdEmbarazadaNo, rdEmbazadaNoSabe;
    String idEncuesta;
    Integer desicionAnticonceptivo, embarazada,UsarAnticonceptivoActual;
    LinearLayout display;
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
    private OnFragmentInteractionListener mListener;

    public ssr_desicion_uso_anticonceptivos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssr_desicion_uso_anticonceptivos.
     */
    // TODO: Rename and change types and number of parameters
    public static ssr_desicion_uso_anticonceptivos newInstance(String param1, String param2) {
        ssr_desicion_uso_anticonceptivos fragment = new ssr_desicion_uso_anticonceptivos();
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
        vista=inflater.inflate(R.layout.fragment_ssr_desicion_uso_anticonceptivos, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente24);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras24);
        idFragment= (TextView) vista.findViewById(R.id.idDesicionUsoAnticonceptivo);

        //Radio Butons
        rdTuya=(RadioButton) vista.findViewById(R.id.usoAnticonceptivoTuya);
        rdTuPareja=(RadioButton) vista.findViewById(R.id.usoAnticonceptivoPareja);
        rdDesicionConjunta=(RadioButton) vista.findViewById(R.id.usoAnticonceptivoConjunta);
        rdEmbarazadaSi=(RadioButton) vista.findViewById(R.id.embarazoSi);
        rdEmbarazadaNo=(RadioButton) vista.findViewById(R.id.embarazoNo);
        rdEmbazadaNoSabe=(RadioButton) vista.findViewById(R.id.embarazoNosabe);
        display=(LinearLayout) vista.findViewById(R.id.layout59);
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

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualizaDesicionUsoAnticonceptivo.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    if (rdEmbarazadaNo.isChecked() || rdEmbazadaNoSabe.isChecked()){
                        interfaceComunicaFragments.enviarEncuesta28(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta27(idFragment.getText().toString());
                    }

                }else if(pantalla=="Atras"){
                    if(UsarAnticonceptivoActual==1){
                        interfaceComunicaFragments.enviarEncuesta24(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta25(idFragment.getText().toString());
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
                String usoAnticonceptivo = "0";
                if (rdTuya.isChecked()) {
                    usoAnticonceptivo = "1";
                } else if (rdTuPareja.isChecked()) {
                    usoAnticonceptivo = "2";
                }else if (rdDesicionConjunta.isChecked()) {
                    usoAnticonceptivo = "3";
                }

                String embarazada = "0";
                if (rdEmbarazadaSi.isChecked()) {
                    embarazada = "1";
                } else if (rdEmbarazadaNo.isChecked()) {
                    embarazada = "2";
                } else if (rdEmbazadaNoSabe.isChecked()) {
                    embarazada = "3";
                }



                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("usoAnticonceptivo", usoAnticonceptivo);
                parametros.put("embarazada", embarazada);

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
                desicionAnticonceptivo = jsonObject.optInt("desicion_uso_anticonceptivo");
                embarazada = jsonObject.optInt("embarazo");

                UsarAnticonceptivoActual = jsonObject.optInt("usa_anticonceptivo_actualmente");

                if(UsarAnticonceptivoActual==2 || UsarAnticonceptivoActual==3){
                    display.setVisibility(View.INVISIBLE);
                    display.setVisibility(View.GONE);
                }else{
                    display.setVisibility(View.VISIBLE);
                }

                if (desicionAnticonceptivo == 1) {
                    rdTuya.setChecked(true);
                } else if (desicionAnticonceptivo == 2) {
                    rdTuPareja.setChecked(true);
                }else if (desicionAnticonceptivo == 3) {
                    rdDesicionConjunta.setChecked(true);
                }

                if (embarazada == 1) {
                    rdEmbarazadaSi.setChecked(true);
                } else if (embarazada == 2) {
                    rdEmbarazadaNo.setChecked(true);
                }else if (embarazada == 3) {
                    rdEmbazadaNoSabe.setChecked(true);
                }




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
