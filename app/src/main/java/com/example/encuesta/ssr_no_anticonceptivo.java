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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ssr_no_anticonceptivo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssr_no_anticonceptivo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssr_no_anticonceptivo extends Fragment {
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
    RadioButton rdEconomicoSi, rdEconomicoNo, rdDeseoHijoSi, rdDeseoHijoNo, rdCausaDanoMujerSi, rdCausaDanoMujerNo, rdCondonNoMismoSi, rdCondonNoMismoNo, rdNoceUsanSi, rdNoceUsanNo, rdOtroSi, rdOtroNo;
    EditText txtRespuesta, txtOtro;
    String idEncuesta, respuesta, otroNoAnticonceptivoNombre;
    Integer economico, deseoHijos, causanDanoMujer, condonNoMismo, noceUsan, otro;

    //volley

    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    private OnFragmentInteractionListener mListener;

    public ssr_no_anticonceptivo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssr_no_anticonceptivo.
     */
    // TODO: Rename and change types and number of parameters
    public static ssr_no_anticonceptivo newInstance(String param1, String param2) {
        ssr_no_anticonceptivo fragment = new ssr_no_anticonceptivo();
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
        vista=inflater.inflate(R.layout.fragment_ssr_no_anticonceptivo, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente23);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras23);
        idFragment= (TextView) vista.findViewById(R.id.idNoAnticonceptivo);

        //Radio Butons
        rdEconomicoSi=(RadioButton) vista.findViewById(R.id.costoEconomicoSi);
        rdEconomicoNo=(RadioButton) vista.findViewById(R.id.costoEconomicoNo);
        rdDeseoHijoSi=(RadioButton) vista.findViewById(R.id.desearHijoSi);
        rdDeseoHijoNo=(RadioButton) vista.findViewById(R.id.deserHijoNo);
        rdCausaDanoMujerSi=(RadioButton) vista.findViewById(R.id.causaDañoMujerSi);
        rdCausaDanoMujerNo=(RadioButton) vista.findViewById(R.id.causanDañoMujerNo);
        rdCondonNoMismoSi=(RadioButton) vista.findViewById(R.id.condonNoMismoSi);
        rdCondonNoMismoNo=(RadioButton) vista.findViewById(R.id.condonNoMismoNo);
        rdNoceUsanSi=(RadioButton) vista.findViewById(R.id.noceUsanSi);
        rdNoceUsanNo=(RadioButton) vista.findViewById(R.id.noceUsanNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroNoAnticonceptivoSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroNoAnticonceptivoNo);

        txtOtro=(EditText) vista.findViewById(R.id.txtotroNoAnticonceptivo);
        txtRespuesta=(EditText) vista.findViewById(R.id.txtrespuestaNoAnticonceptivo);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta26(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta24(idFragment.getText().toString());
        });
        return vista;
    }
    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                economico = jsonObject.optInt("costo_economico");
                deseoHijos = jsonObject.optInt("tener_hijo");
                causanDanoMujer = jsonObject.optInt("causan_dano_mujer");
                condonNoMismo = jsonObject.optInt("condon_no_mismo");
                noceUsan = jsonObject.optInt("noce_usar");
                otro = jsonObject.optInt("otro_no_anticonceptivo");
                otroNoAnticonceptivoNombre = jsonObject.optString("otro_no_anticonceptivo_nombre");
                respuesta = jsonObject.optString("respuesta_no_anticonceptivo");


                if (economico == 1) {
                    rdEconomicoSi.setChecked(true);
                } else if (economico == 2) {
                    rdEconomicoNo.setChecked(true);
                }

                if (deseoHijos == 1) {
                    rdDeseoHijoSi.setChecked(true);
                } else if (deseoHijos == 2) {
                    rdDeseoHijoNo.setChecked(true);
                }

                if (causanDanoMujer == 1) {
                    rdCausaDanoMujerSi.setChecked(true);
                } else if (causanDanoMujer == 2) {
                    rdCausaDanoMujerNo.setChecked(true);
                }

                if (condonNoMismo == 1) {
                    rdCondonNoMismoSi.setChecked(true);
                } else if (condonNoMismo == 2) {
                    rdCondonNoMismoNo.setChecked(true);
                }

                if (noceUsan == 1) {
                    rdNoceUsanSi.setChecked(true);
                } else if (noceUsan == 2) {
                    rdNoceUsanNo.setChecked(true);
                }

                if (otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (otro == 2) {
                    rdOtroSi.setChecked(true);
                }

                txtOtro.setText(otroNoAnticonceptivoNombre.toString());
                txtRespuesta.setText(respuesta.toString());

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
