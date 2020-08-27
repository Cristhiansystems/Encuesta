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
 * {@link ssc_problemas_salud_actual.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssc_problemas_salud_actual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssc_problemas_salud_actual extends Fragment{
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
    RadioButton rdCentroPublicaSi, rdCentroPublicaNo, rdCentroPrivadoSi, rdCentroPrivadoNo, rdFarmaciaSi, rdFarmaciaNo, rdInstitucionSi, rdInstitucionNo, rdNaturistaSi, rdNaturistaNo, rdCuroSoloSi, rdCuroSoloNo, rdOtroSi, rdOtroNo;
    EditText txtOtroProblemaSaludActual;
    String otroProblemaSaludActual, idEncuesta;
    Integer centroPublica, centroPrivado, farmacia, institucion, naturista, curoSolo, otro;
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

    public ssc_problemas_salud_actual() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssc_problemas_salud_actual.
     */
    // TODO: Rename and change types and number of parameters
    public static ssc_problemas_salud_actual newInstance(String param1, String param2) {
        ssc_problemas_salud_actual fragment = new ssc_problemas_salud_actual();
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
        vista=inflater.inflate(R.layout.fragment_ssc_problemas_salud_actual, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente160);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras160);
        idFragment= (TextView) vista.findViewById(R.id.idProblemaSaludActual);
        rdCentroPublicaSi=(RadioButton) vista.findViewById(R.id.centroPublicaSi);
        rdCentroPublicaNo=(RadioButton) vista.findViewById(R.id.centroPublicaNo);
        rdCentroPrivadoSi=(RadioButton) vista.findViewById(R.id.centroPirvadoSi);
        rdCentroPrivadoNo=(RadioButton) vista.findViewById(R.id.centroPrivadoNo);
        rdFarmaciaSi=(RadioButton) vista.findViewById(R.id.farmaciaSi);
        rdFarmaciaNo=(RadioButton) vista.findViewById(R.id.farmaciaNo);
        rdInstitucionSi=(RadioButton) vista.findViewById(R.id.institucionSi);
        rdInstitucionNo=(RadioButton) vista.findViewById(R.id.institucionNo);
        rdNaturistaSi=(RadioButton) vista.findViewById(R.id.naturistaSi);
        rdNaturistaNo=(RadioButton) vista.findViewById(R.id.naturistaNo);
        rdCuroSoloSi=(RadioButton) vista.findViewById(R.id.curoSoloSi);
        rdCuroSoloNo=(RadioButton) vista.findViewById(R.id.curoSoloNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroProblemaSaludActualSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroProblemaSaludActualNo);

        txtOtroProblemaSaludActual= (EditText) vista.findViewById(R.id.txtotroProblemasSaludActual);


        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta18(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta16(idFragment.getText().toString());
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
                centroPublica = jsonObject.optInt("centro_salud_publica");
                centroPrivado = jsonObject.optInt("centro_salud_privado");
                farmacia = jsonObject.optInt("farmacia");
                institucion = jsonObject.optInt("institucion");
                naturista = jsonObject.optInt("naturista");
                curoSolo = jsonObject.optInt("curo_solo");
                otro = jsonObject.optInt("otro_salud");
                otroProblemaSaludActual = jsonObject.optString("otro_salud_nombre");


                if (centroPublica == 1) {
                    rdCentroPublicaSi.setChecked(true);
                } else if (centroPublica == 2) {
                    rdCentroPublicaSi.setChecked(true);
                }

                if (centroPrivado == 1) {
                    rdCentroPrivadoSi.setChecked(true);
                } else if (centroPrivado == 2) {
                    rdCentroPrivadoNo.setChecked(true);
                }

                if (farmacia == 1) {
                    rdFarmaciaSi.setChecked(true);
                } else if (farmacia == 2) {
                    rdFarmaciaNo.setChecked(true);
                }

                if (institucion == 1) {
                    rdInstitucionSi.setChecked(true);
                } else if (institucion == 2) {
                    rdInstitucionNo.setChecked(true);
                }

                if (naturista == 1) {
                    rdNaturistaSi.setChecked(true);
                } else if (naturista == 2) {
                    rdNaturistaSi.setChecked(true);
                }

                if (curoSolo == 1) {
                    rdCuroSoloSi.setChecked(true);
                } else if (curoSolo == 2) {
                    rdCuroSoloNo.setChecked(true);
                }

                if (otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (otro == 2) {
                    rdOtroNo.setChecked(true);
                }

                txtOtroProblemaSaludActual.setText(otroProblemaSaludActual.toString());

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
