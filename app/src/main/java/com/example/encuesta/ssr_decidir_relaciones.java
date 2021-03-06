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
 * {@link ssr_decidir_relaciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssr_decidir_relaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssr_decidir_relaciones extends Fragment{
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
    RadioButton rdUsarAnticonceptivoSi, rdUsarAnticonceptivoNo, rdSinProteccionSi, rdSinProteccionNo, rdNegociarParejaSi, rdNegociarParejaNo, rdForzarParejaSi, rdForzarParejaNo, rdOtroSi, rdOtroNo;
    EditText txtRespuesta, txtOtroDecidirRelacion;
    String idEncuesta, respuesta, otroDecidirRelacion;
    StringRequest stringRequest;
    Integer UsarAnticonceptivo, SinProteccion, NegociarPareja, ForzarPareja, Otro;
    LinearLayout display;
    //volley

    ProgressDialog progreso;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    private OnFragmentInteractionListener mListener;

    public ssr_decidir_relaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssr_decidir_relaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static ssr_decidir_relaciones newInstance(String param1, String param2) {
        ssr_decidir_relaciones fragment = new ssr_decidir_relaciones();
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
        vista=inflater.inflate(R.layout.fragment_ssr_decidir_relaciones, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente21);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras21);

        idFragment= (TextView) vista.findViewById(R.id.idDecidirrelaciones);

        //Radio Butons
        rdUsarAnticonceptivoSi=(RadioButton) vista.findViewById(R.id.usarAnticonceptivoSi);
        rdUsarAnticonceptivoNo=(RadioButton) vista.findViewById(R.id.usarAnticonceptivoNo);
        rdSinProteccionSi=(RadioButton) vista.findViewById(R.id.relacionesSinProteccionSi);
        rdSinProteccionNo=(RadioButton) vista.findViewById(R.id.relacionesSinproteccionNo);
        rdNegociarParejaSi=(RadioButton) vista.findViewById(R.id.negociarParejaSi);
        rdNegociarParejaNo=(RadioButton) vista.findViewById(R.id.negociasParejaNo);
        rdForzarParejaSi=(RadioButton) vista.findViewById(R.id.forzarParejaSi);
        rdForzarParejaNo=(RadioButton) vista.findViewById(R.id.forzarParejaNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroDecidirRelacionesSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroDecidirrelacionesNo);
        display=(LinearLayout) vista.findViewById(R.id.layoutdecidirrlaciones);
        txtRespuesta=(EditText) vista.findViewById(R.id.txtrespuestaDecidirRelaciones);
        txtOtroDecidirRelacion=(EditText) vista.findViewById(R.id.txtotroDecidirRelaciones);
        display.setVisibility(View.INVISIBLE);
        display.setVisibility(View.GONE);
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
        String url=ip+"actualizaDecidirRelaciones.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta24(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta22(idFragment.getText().toString());

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
                String usarAnticonceptivo = "0";
                if (rdUsarAnticonceptivoSi.isChecked()) {
                    usarAnticonceptivo = "1";
                } else if (rdUsarAnticonceptivoNo.isChecked()) {
                    usarAnticonceptivo = "2";
                }

                String sinProteccion = "0";
                if (rdSinProteccionSi.isChecked()) {
                    sinProteccion = "1";
                } else if (rdSinProteccionNo.isChecked()) {
                    sinProteccion = "2";
                }


                String negociarPareja = "0";
                if (rdNegociarParejaSi.isChecked()) {
                    negociarPareja = "1";
                } else if (rdNegociarParejaNo.isChecked()) {
                    negociarPareja = "2";
                }

                String forzarPareja = "0";
                if (rdForzarParejaSi.isChecked()) {
                    forzarPareja = "1";
                } else if (rdForzarParejaNo.isChecked()) {
                    forzarPareja = "2";
                }

                String otro = "0";
                if (rdOtroSi.isChecked()) {
                    otro = "1";
                } else if (rdOtroNo.isChecked()) {
                    otro = "2";
                }


                String otroDecidirrelacion = txtOtroDecidirRelacion.getText().toString();
                String respuesta = txtRespuesta.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("usarAnticonceptivo", usarAnticonceptivo);
                parametros.put("sinProteccion", sinProteccion);
                parametros.put("negociarPareja", negociarPareja);
                parametros.put("forzarPareja", forzarPareja);
                parametros.put("otro", otro);
                parametros.put("otroDecidirrelacion", otroDecidirrelacion);
                parametros.put("respuesta", respuesta);

                return parametros;
            }
        };
       // request.add(stringRequest);
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
                UsarAnticonceptivo = jsonObject.optInt("usar_anticonceptivo");
                SinProteccion = jsonObject.optInt("sin_proteccion");
                NegociarPareja= jsonObject.optInt("negociar_relacion");
                ForzarPareja= jsonObject.optInt("forzar_sin_proteccion");
                Otro= jsonObject.optInt("otro_decidir_relacion");

                respuesta = jsonObject.optString("respuesta_decidir_relaciones");
                otroDecidirRelacion = jsonObject.optString("otro_decidir_relacion_nombre");

                if (UsarAnticonceptivo == 1) {
                    rdUsarAnticonceptivoSi.setChecked(true);
                } else if (UsarAnticonceptivo == 2) {
                    rdUsarAnticonceptivoNo.setChecked(true);
                }

                if (SinProteccion == 1) {
                    rdSinProteccionSi.setChecked(true);
                } else if (SinProteccion == 2) {
                    rdSinProteccionNo.setChecked(true);
                }

                if (NegociarPareja == 1) {
                    rdNegociarParejaSi.setChecked(true);
                } else if (NegociarPareja == 2) {
                    rdNegociarParejaNo.setChecked(true);
                }

                if (ForzarPareja == 1) {
                    rdForzarParejaSi.setChecked(true);
                } else if (ForzarPareja == 2) {
                    rdForzarParejaNo.setChecked(true);
                }

                if (Otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (Otro == 2) {
                    rdOtroNo.setChecked(true);
                }

                txtRespuesta.setText(respuesta.toString());
                txtOtroDecidirRelacion.setText(otroDecidirRelacion.toString());

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
