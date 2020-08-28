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
 * {@link db_combustible.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link db_combustible#newInstance} factory method to
 * create an instance of this fragment.
 */
public class db_combustible extends Fragment{
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
    RadioButton rdEnergiaSi, rdEnergiaNo, rdGasDomSi, rdGasDomNo, rdGasLicSi, rdGasLicNo, rdCarbonSi, rdCarbonNo, rdLenaSi, rdLenaNo, rdOtroSi, rdOtroNo;
    EditText txtOtroCombustible;
    String otroCombustible, idEncuesta;
    Integer Energia, GasDom, GasLic, Carbon, Lena, Otro;
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

    public db_combustible() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment db_combustible.
     */
    // TODO: Rename and change types and number of parameters
    public static db_combustible newInstance(String param1, String param2) {
        db_combustible fragment = new db_combustible();
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
        vista=inflater.inflate(R.layout.fragment_db_combustible, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente15);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras15);
        idFragment= (TextView) vista.findViewById(R.id.idCombustible);
        rdEnergiaSi= (RadioButton) vista.findViewById(R.id.combusEnergiaSi);
        rdEnergiaNo= (RadioButton) vista.findViewById(R.id.combusEnergiaNo);
        rdGasDomSi= (RadioButton) vista.findViewById(R.id.gasDomiciliarioSi);
        rdGasDomNo= (RadioButton) vista.findViewById(R.id.gasDomiciliarioNo);
        rdGasLicSi= (RadioButton) vista.findViewById(R.id.gasLicuadoSi);
        rdGasLicNo= (RadioButton) vista.findViewById(R.id.gasLicuadoNo);
        rdCarbonSi= (RadioButton) vista.findViewById(R.id.carbonSi);
        rdCarbonNo= (RadioButton) vista.findViewById(R.id.carbonNo);
        rdLenaSi= (RadioButton) vista.findViewById(R.id.lenaSi);
        rdLenaNo= (RadioButton) vista.findViewById(R.id.lenaNo);
        rdOtroSi= (RadioButton) vista.findViewById(R.id.otroCombustibleSi);
        rdOtroNo= (RadioButton) vista.findViewById(R.id.otroCombustibleNo);
        txtOtroCombustible= (EditText) vista.findViewById(R.id.txtotronomCombustible);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta16(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta14(idFragment.getText().toString());

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
                Energia = jsonObject.optInt("energia");
                GasDom = jsonObject.optInt("gas_domiciliario");
                GasLic = jsonObject.optInt("gas_licuado");
                Carbon = jsonObject.optInt("carbon");
                Lena = jsonObject.optInt("lena");
                Otro = jsonObject.optInt("otro_combustible");
                otroCombustible = jsonObject.optString("otro_combustible_nombre");

                if (Energia == 1) {
                    rdEnergiaSi.setChecked(true);
                } else if (Energia == 2) {
                    rdEnergiaNo.setChecked(true);
                }

                if (GasDom == 1) {
                    rdGasDomSi.setChecked(true);
                } else if (GasDom == 2) {
                    rdGasDomNo.setChecked(true);
                }

                if (GasLic == 1) {
                    rdGasLicSi.setChecked(true);
                } else if (GasLic == 2) {
                    rdGasLicNo.setChecked(true);
                }

                if (Carbon == 1) {
                    rdCarbonSi.setChecked(true);
                } else if (Carbon == 2) {
                    rdCarbonNo.setChecked(true);
                }

                if (Lena == 1) {
                    rdLenaSi.setChecked(true);
                } else if (Lena == 2) {
                    rdLenaNo.setChecked(true);
                }


                if (Otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (Otro == 2) {
                    rdOtroNo.setChecked(true);
                }

                txtOtroCombustible.setText(otroCombustible.toString());

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
