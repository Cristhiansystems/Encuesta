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

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link sb_servicio_sanitario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sb_servicio_sanitario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sb_servicio_sanitario extends Fragment{
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
    RadioButton rdServSanSiPriv, rdServSanSiCom, rdSerSanSiFuera, rdServSanNo, rdEnerSi, rdEnerNo, rdEnerOtro;
    EditText txtOtroEner;
    String otroEner, idEncuesta;
    Integer ServSan, Ener;
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

    public sb_servicio_sanitario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sb_servicio_sanitario.
     */
    // TODO: Rename and change types and number of parameters
    public static sb_servicio_sanitario newInstance(String param1, String param2) {
        sb_servicio_sanitario fragment = new sb_servicio_sanitario();
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
        vista=inflater.inflate(R.layout.fragment_sb_servicio_sanitario, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente13);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras13);
        idFragment= (TextView) vista.findViewById(R.id.idCuartos);
        rdServSanSiPriv=(RadioButton) vista.findViewById(R.id.servSanSiPriv);
        rdServSanSiCom=(RadioButton) vista.findViewById(R.id.servSanSiComp);
        rdSerSanSiFuera=(RadioButton) vista.findViewById(R.id.servSanSiFuera);
        rdServSanNo=(RadioButton) vista.findViewById(R.id.servSanNo);
        rdEnerSi=(RadioButton) vista.findViewById(R.id.energiaElecSi);
        rdEnerNo=(RadioButton) vista.findViewById(R.id.energiaElecNo);
        rdEnerOtro=(RadioButton) vista.findViewById(R.id.energiaElecOtro);
        txtOtroEner=(EditText) vista.findViewById(R.id.txtenrgiaElecOtro);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta14(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta12(idFragment.getText().toString());
        });
        return vista;
    }
    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json=response.optJSONArray("usuario");
            JSONObject jsonObject=null;

            try{
                jsonObject=json.getJSONObject(0);
                idEncuesta=jsonObject.optString("encuesta_emt");
                ServSan =jsonObject.optInt("servicio_sanitario");
                Ener=jsonObject.optInt("posee_energia_electrica");
                otroEner=jsonObject.optString("posee_energia_electrica_otro");

                if(ServSan==1){
                    rdServSanSiPriv.setChecked(true);
                }else if(ServSan==2){
                    rdServSanSiCom.setChecked(true);
                }else if(ServSan==3){
                    rdSerSanSiFuera.setChecked(true);
                }else if(ServSan==4){
                    rdServSanNo.setChecked(true);
                }

                if(Ener==1){
                    rdEnerSi.setChecked(true);
                }else if(Ener==2){
                    rdEnerNo.setChecked(true);
                }else if(Ener==3){
                    rdEnerOtro.setChecked(true);
                }

                txtOtroEner.setText(otroEner.toString());



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
