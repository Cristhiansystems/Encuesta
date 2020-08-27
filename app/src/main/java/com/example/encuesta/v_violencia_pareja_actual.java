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
 * {@link v_violencia_pareja_actual.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_violencia_pareja_actual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_violencia_pareja_actual extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
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
    RadioButton rdempujadoMM, rdempujadoAV, rdempujadoNC, rdempujadoN, rdgolpeadoMM, rdgolpeadoAV, rdgolpeadoNC, rdgolpeadoN, rdgolpeadoObjetoMM, rdgolpeadoObjetoAV, rdgolpeadoObjetoNC, rdgolpeadoObjetoN, rdGolpeadoMarcasMM, rdgolpeadoMarcasAV, rdgolpeadoMarcasNC, rdgolpeadoMarcasN, rdForzarrelacionesMM, rdforzarRelacionesAV, rdforzarRelacionesNC, rdforzarRelacionesN, rdotroMM, rdotroAV, rdotroNC, rdotroN;
    String idEncuesta, otroViolenciaParejaActual;
    EditText txtOtro;
    Integer empujado, golpeado, golpeadoObjeto, golpeadoMarcas,forzarRelaciones, otro;


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

    public v_violencia_pareja_actual() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_violencia_pareja_actual.
     */
    // TODO: Rename and change types and number of parameters
    public static v_violencia_pareja_actual newInstance(String param1, String param2) {
        v_violencia_pareja_actual fragment = new v_violencia_pareja_actual();
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
        vista=inflater.inflate(R.layout.fragment_v_violencia_pareja_actual, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente35);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras35);
        idFragment= (TextView) vista.findViewById(R.id.idViolenciaParejaActual);
        rdgolpeadoMM=(RadioButton) vista.findViewById(R.id.mmGolpeado);
        rdgolpeadoAV=(RadioButton) vista.findViewById(R.id.avGolpeado);
        rdgolpeadoN=(RadioButton) vista.findViewById(R.id.nGolpeado);
        rdgolpeadoNC=(RadioButton) vista.findViewById(R.id.ncGolpeado);
        rdempujadoMM=(RadioButton) vista.findViewById(R.id.mmEmpujado);
        rdempujadoAV=(RadioButton) vista.findViewById(R.id.avEmpujado);
        rdempujadoN=(RadioButton) vista.findViewById(R.id.nEmpujado);
        rdempujadoNC=(RadioButton) vista.findViewById(R.id.ncEmpujado);
        rdgolpeadoObjetoMM=(RadioButton) vista.findViewById(R.id.mmGolpeadoObjeto);
        rdgolpeadoObjetoAV=(RadioButton) vista.findViewById(R.id.avGolpeadoObjeto);
        rdgolpeadoObjetoN=(RadioButton) vista.findViewById(R.id.nGolpeadoObjeto);
        rdgolpeadoObjetoNC=(RadioButton) vista.findViewById(R.id.ncGolpeadoObjeto);
        rdGolpeadoMarcasMM=(RadioButton) vista.findViewById(R.id.mmGolpeadoMarcas);
        rdgolpeadoMarcasAV=(RadioButton) vista.findViewById(R.id.avGolpeadoMarcas);
        rdgolpeadoMarcasN=(RadioButton) vista.findViewById(R.id.nGolpeadoMarcas);
        rdgolpeadoMarcasNC=(RadioButton) vista.findViewById(R.id.ncGolpeadoMarcas);
        rdForzarrelacionesMM=(RadioButton) vista.findViewById(R.id.mmForzarRelaciones);
        rdforzarRelacionesAV=(RadioButton) vista.findViewById(R.id.avForzarRelaciones);
        rdforzarRelacionesN=(RadioButton) vista.findViewById(R.id.nForzarRelaciones);
        rdforzarRelacionesNC=(RadioButton) vista.findViewById(R.id.ncForzarRelaciones);
        rdotroMM=(RadioButton) vista.findViewById(R.id.mmViolenciaParejaActua);
        rdotroAV=(RadioButton) vista.findViewById(R.id.avViolenciaParejaActua);
        rdotroN=(RadioButton) vista.findViewById(R.id.nViolenciaParejaActua);
        rdotroNC=(RadioButton) vista.findViewById(R.id.ncViolenciaParejaActua);


        txtOtro=(EditText) vista.findViewById(R.id.txtotroViolenciaParejaActual);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta39(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta37(idFragment.getText().toString());
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
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            idEncuesta = jsonObject.optString("encuesta_emt");
            empujado= jsonObject.optInt("empujado");
            golpeado= jsonObject.optInt("golpeado");
            golpeadoObjeto= jsonObject.optInt("golpeado_objeto");
            golpeadoMarcas= jsonObject.optInt("golpeado_marcas");
            forzarRelaciones= jsonObject.optInt("forzar_relaciones");
            otro= jsonObject.optInt("violencia_pareja_recientemente_otro");

            otroViolenciaParejaActual= jsonObject.optString("otro_violencia_pareja_nombre");


            if(empujado==1){
                rdempujadoMM.setChecked(true);
            }else if(empujado==2){
                rdempujadoAV.setChecked(true);
            }else if(empujado==3){
                rdempujadoN.setChecked(true);
            }else if(empujado==4){
                rdempujadoNC.setChecked(true);
            }

            if(golpeado==1){
                rdgolpeadoMM.setChecked(true);
            }else if(golpeado==2){
                rdgolpeadoAV.setChecked(true);
            }else if(golpeado==3){
                rdgolpeadoN.setChecked(true);
            }else if(golpeado==4){
                rdgolpeadoNC.setChecked(true);
            }


            if(golpeadoObjeto==1){
                rdgolpeadoObjetoMM.setChecked(true);
            }else if(golpeadoObjeto==2){
                rdgolpeadoObjetoAV.setChecked(true);
            }else if(golpeadoObjeto==3){
                rdgolpeadoObjetoN.setChecked(true);
            }else if(golpeadoObjeto==4){
                rdgolpeadoObjetoNC.setChecked(true);
            }

            if(golpeadoMarcas==1){
                rdGolpeadoMarcasMM.setChecked(true);
            }else if(golpeadoMarcas==2){
                rdgolpeadoMarcasAV.setChecked(true);
            }else if(golpeadoMarcas==3){
                rdgolpeadoMarcasN.setChecked(true);
            }else if(golpeadoMarcas==4){
                rdgolpeadoMarcasNC.setChecked(true);
            }


            if(forzarRelaciones==1){
                rdForzarrelacionesMM.setChecked(true);
            }else if(forzarRelaciones==2){
                rdforzarRelacionesAV.setChecked(true);
            }else if(forzarRelaciones==3){
                rdforzarRelacionesN.setChecked(true);
            }else if(forzarRelaciones==4){
                rdforzarRelacionesNC.setChecked(true);
            }







            if(otro==1){
                rdotroMM.setChecked(true);
            }else if(otro==2){
                rdotroAV.setChecked(true);
            }else if(otro==3){
                rdotroN.setChecked(true);
            }else if(otro==4){
                rdotroNC.setChecked(true);
            }
            txtOtro.setText(otroViolenciaParejaActual.toString());

        } catch (JSONException e) {
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
