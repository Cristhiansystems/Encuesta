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
 * {@link v_buscar_ayuda.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_buscar_ayuda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_buscar_ayuda extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
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
    RadioButton rdAyudaSi, rdAyudaNo, rdMiedoSi, rdMiedoNo, rdCreenciasSi, rdCreenciasNo, rdRepresaliasSi, rdRepresaliasNo, rdotroSi, rdOtroNo, rdNoSabeSi, rdNoSabeNO;
    String idEncuesta, otroBuscarAyuda, dondeBuscasteAyuda, MencionarOrganizacion;
    EditText txtOtro, txtDondeBuscasteAyuda, txtMencionaOrganizacion;
    Integer miedo, creencias, represalias, noSabe, otro, ayuda;


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

    public v_buscar_ayuda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_buscar_ayuda.
     */
    // TODO: Rename and change types and number of parameters
    public static v_buscar_ayuda newInstance(String param1, String param2) {
        v_buscar_ayuda fragment = new v_buscar_ayuda();
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
        vista=inflater.inflate(R.layout.fragment_v_buscar_ayuda, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente36);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras36);
        idFragment=(TextView) vista.findViewById(R.id.idBuscarAyuda);
        rdMiedoSi=(RadioButton) vista.findViewById(R.id.miedoSi);
        rdMiedoNo=(RadioButton) vista.findViewById(R.id.miedoNo);
        rdCreenciasSi=(RadioButton) vista.findViewById(R.id.creenciasSi);
        rdCreenciasNo=(RadioButton) vista.findViewById(R.id.creenciasNo);
        rdRepresaliasSi=(RadioButton) vista.findViewById(R.id.represaliasSi);
        rdRepresaliasNo=(RadioButton) vista.findViewById(R.id.represaliasNo);
        rdotroSi=(RadioButton) vista.findViewById(R.id.otroNoBuscarAyudaSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroNoBuscarAyudaNo);
        rdNoSabeSi=(RadioButton) vista.findViewById(R.id.noBuscarAyudaNoSabeSi);
        rdNoSabeNO=(RadioButton) vista.findViewById(R.id.noBuscarAyudaNoSabeNo);
        rdAyudaSi=(RadioButton) vista.findViewById(R.id.buscasteAyudaSi);
        rdAyudaNo=(RadioButton) vista.findViewById(R.id.buscasteAyudaNo);

        txtDondeBuscasteAyuda=(EditText) vista.findViewById(R.id.txtDondeAyuda);
        txtOtro=(EditText) vista.findViewById(R.id.txtotroNoBuscarAyuda);
        txtMencionaOrganizacion=(EditText) vista.findViewById(R.id.txtrespuestaConocerOrganizacion);


        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta40(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta38(idFragment.getText().toString());
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
            ayuda= jsonObject.optInt("buscaste_ayuda");
            miedo= jsonObject.optInt("no_buscar_ayuda_miedo");
            creencias= jsonObject.optInt("no_buscar_ayuda_creencias");
            represalias= jsonObject.optInt("no_buscar_ayuda_represalias");
            otro= jsonObject.optInt("no_buscar_ayuda_otro");
            noSabe= jsonObject.optInt("no_buscar_ayuda_nosabe");

            otroBuscarAyuda= jsonObject.optString("no_buscar_ayuda_otro_nombre");
            MencionarOrganizacion= jsonObject.optString("conoces_organizacion");
            dondeBuscasteAyuda= jsonObject.optString("donde_buscaste_ayuda");


            if(ayuda==1){
                rdAyudaSi.setChecked(true);
            }else if(ayuda==2){
                rdAyudaNo.setChecked(true);
            }

            if(miedo==1){
                rdMiedoSi.setChecked(true);
            }else if(miedo==2){
                rdMiedoNo.setChecked(true);
            }

            if(creencias==1){
                rdCreenciasSi.setChecked(true);
            }else if(creencias==2){
                rdCreenciasNo.setChecked(true);
            }

            if(represalias==1){
                rdRepresaliasSi.setChecked(true);
            }else if(represalias==2){
                rdRepresaliasNo.setChecked(true);
            }

            if(noSabe==1){
                rdNoSabeSi.setChecked(true);
            }else if(noSabe==2){
                rdNoSabeNO.setChecked(true);
            }


            if(otro==1){
                rdotroSi.setChecked(true);
            }else if(otro==2){
                rdOtroNo.setChecked(true);
            }
            txtOtro.setText(otroBuscarAyuda.toString());
            txtDondeBuscasteAyuda.setText(dondeBuscasteAyuda.toString());
            txtMencionaOrganizacion.setText(MencionarOrganizacion.toString());

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
