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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ssr_enamorado.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssr_enamorado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssr_enamorado extends Fragment{
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
    RadioButton rdTuvisteEnamoradoSi, rdTuvisteEnamoradoNo, rdRelacionesRecienSi, rdRelacionesRecienNo, rdEdadRelacionNoTuvo, rdEdadRelacionNosabe;
    EditText txtedadPrimeraRelacion;
    String idEncuesta, edadPrimeraRelacion;

    Integer tuvisteEnamorado, relacionesRecien, edadRelacion, oirAnticonceptivo;

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

    public ssr_enamorado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssr_enamorado.
     */
    // TODO: Rename and change types and number of parameters
    public static ssr_enamorado newInstance(String param1, String param2) {
        ssr_enamorado fragment = new ssr_enamorado();
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
        vista=inflater.inflate(R.layout.fragment_ssr_enamorado, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente20);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras20);
        idFragment= (TextView) vista.findViewById(R.id.idEnamorado);

        //Radio Butons
        rdTuvisteEnamoradoSi=(RadioButton) vista.findViewById(R.id.tuvisteEnamoradoSi);
        rdTuvisteEnamoradoNo=(RadioButton) vista.findViewById(R.id.tuvisteEnamoradoNo);
        rdRelacionesRecienSi=(RadioButton) vista.findViewById(R.id.relacionesrecienSi);
        rdRelacionesRecienNo=(RadioButton) vista.findViewById(R.id.relacionesrecienNo);
        rdEdadRelacionNosabe=(RadioButton) vista.findViewById(R.id.edadrelacionSexualNoSabe);
        rdEdadRelacionNoTuvo=(RadioButton) vista.findViewById(R.id.edadrelacionSexualNoTuvo);

        txtedadPrimeraRelacion=(EditText) vista.findViewById(R.id.txtedadPrimeraRelacion);

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
        String ip=getString(R.string.ip);
        String url=ip+"actualizaEnamorado.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    if(rdTuvisteEnamoradoNo.isChecked()){
                        interfaceComunicaFragments.enviarEncuesta28(idFragment.getText().toString());
                    }else if(rdTuvisteEnamoradoSi.isChecked()){
                        interfaceComunicaFragments.enviarEncuesta23(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta23(idFragment.getText().toString());
                    }

                }else if(pantalla=="Atras"){
                    if(oirAnticonceptivo==1  || oirAnticonceptivo==0){
                        interfaceComunicaFragments.enviarEncuesta21(idFragment.getText().toString());
                    }else if(oirAnticonceptivo==2 || oirAnticonceptivo==8){

                        interfaceComunicaFragments.enviarEncuesta20(idFragment.getText().toString());
                    }


                }

            } else {

                Toast.makeText(getContext(), "Error en la actualizacion" + response.toString() , Toast.LENGTH_SHORT).show();



            }

        },  error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id = idFragment.getText().toString();
                String enamorado = "0";
                if (rdTuvisteEnamoradoSi.isChecked()) {
                    enamorado = "1";
                } else if (rdTuvisteEnamoradoNo.isChecked()) {
                    enamorado = "2";
                }

                String relacionesSexuales = "0";
                if (rdRelacionesRecienSi.isChecked()) {
                    relacionesSexuales = "1";
                } else if (rdRelacionesRecienNo.isChecked()) {
                    relacionesSexuales = "2";
                }


                String relacionEdad = "0";
                if (rdEdadRelacionNoTuvo.isChecked()) {
                    relacionEdad = "1";
                } else if (rdEdadRelacionNosabe.isChecked()) {
                    relacionEdad = "2";
                }


                String strEdadRelacion = txtedadPrimeraRelacion.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("enamorado", enamorado);
                parametros.put("relacionesSexuales", relacionesSexuales);
                parametros.put("relacionEdad", relacionEdad);
                parametros.put("strEdadRelacion", strEdadRelacion);

                return parametros;
            }
            };
        request.add(stringRequest);

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
                tuvisteEnamorado = jsonObject.optInt("tuviste_enamorado");
                relacionesRecien = jsonObject.optInt("relaciones_sexuales_recien");
                edadRelacion = jsonObject.optInt("tuviste_relaciones_sexuales");

                edadPrimeraRelacion = jsonObject.optString("edad_relacion_sexual");
                //otro fragment
                oirAnticonceptivo = jsonObject.optInt("conoces_medio_anticonceptivo");

                if (tuvisteEnamorado == 1) {
                    rdTuvisteEnamoradoSi.setChecked(true);
                } else if (tuvisteEnamorado == 2) {
                    rdTuvisteEnamoradoNo.setChecked(true);
                }

                if (relacionesRecien == 1) {
                    rdRelacionesRecienSi.setChecked(true);
                } else if (relacionesRecien == 2) {
                    rdRelacionesRecienNo.setChecked(true);
                }

                if (edadRelacion == 1) {
                    rdEdadRelacionNoTuvo.setChecked(true);
                } else if (edadRelacion == 2) {
                    rdEdadRelacionNosabe.setChecked(true);
                }



                txtedadPrimeraRelacion.setText(edadPrimeraRelacion.toString());

            } catch (JSONException e) {
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
