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
 * {@link sb_lugar_vives.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sb_lugar_vives#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sb_lugar_vives extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
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
    RadioButton rdPropia, rdAlquilada, rdAnticretico, rdCedida, rdOtro;
    EditText txtnomOtro;
    String nomOtro, idEncuesta;
    Integer lugar_vives;
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

    public sb_lugar_vives() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sb_lugar_vives.
     */
    // TODO: Rename and change types and number of parameters
    public static sb_lugar_vives newInstance(String param1, String param2) {
        sb_lugar_vives fragment = new sb_lugar_vives();
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
        vista=inflater.inflate(R.layout.fragment_sb_lugar_vives, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente10);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras10);
        idFragment= (TextView) vista.findViewById(R.id.idLugarVives);
        rdPropia=(RadioButton) vista.findViewById(R.id.lugarVivesPropia);
        rdAlquilada=(RadioButton) vista.findViewById(R.id.lugarVivesAlquilada);
        rdAnticretico=(RadioButton) vista.findViewById(R.id.lugarVivesAnticretico);
        rdCedida=(RadioButton) vista.findViewById(R.id.lugarVivesCedida);
        rdOtro=(RadioButton) vista.findViewById(R.id.lugarVivesOtro);
        txtnomOtro=(EditText) vista.findViewById(R.id.txtLugarVivesOtroNombre);
        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();

        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta11(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta9(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
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

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR: ", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try{
            jsonObject=json.getJSONObject(0);
            idEncuesta=jsonObject.optString("encuesta_emt");
            lugar_vives=jsonObject.optInt("lugar_vivienda");
            nomOtro=jsonObject.optString("lugar_vivienda_otro");

            if(lugar_vives==1){
                rdPropia.setChecked(true);
            }else if(lugar_vives==2){
                rdAlquilada.setChecked(true);
            }else if(lugar_vives==3){
                rdAnticretico.setChecked(true);
            }else if(lugar_vives==4){
                rdCedida.setChecked(true);
            }else if(lugar_vives==88){
                rdOtro.setChecked(true);
            }

            txtnomOtro.setText(nomOtro.toString());




        }catch (JSONException e){
            e.printStackTrace();
        }
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
