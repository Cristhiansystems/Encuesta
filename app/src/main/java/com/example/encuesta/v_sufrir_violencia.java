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
 * {@link v_sufrir_violencia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_sufrir_violencia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_sufrir_violencia extends Fragment {
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
    RadioButton rdpadreMM, rdpadreAV, rdpadreUV, rdpadreN, rdmadreMM, rdmadreAV, rdmadreUV, rdmadreN, rdhermanosMM, rdhermanosAV, rdhermanosUV, rdhermanosN, rdparientesMM, rdparientesAV, rdparientesUV, rdparientesN, rdamigosMM, rdamigosAV, rdamigosUV, rdamigosN, rdparejaMM, rdparejaAV, rdparejaUV, rdparejaN, rdeducadoresMM, rdeducadoresAV, rdeducadoresUV, rdeducadoresN, rdprofesoresMM, rdprofesoresAV, rdprofesoresUV, rdprofesoresN, rdotroMM, rdotroAV, rdotroUV, rdotroN;
    String idEncuesta, otroSufrirViolencia;
    EditText txtOtro;
    Integer padre, madre, hermanos, parientes, amigos, pareja, educadores, profesores, otro;


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

    public v_sufrir_violencia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_sufrir_violencia.
     */
    // TODO: Rename and change types and number of parameters
    public static v_sufrir_violencia newInstance(String param1, String param2) {
        v_sufrir_violencia fragment = new v_sufrir_violencia();
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
        vista=inflater.inflate(R.layout.fragment_v_sufrir_violencia, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente33);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras33);
        idFragment= (TextView) vista.findViewById(R.id.idSufrirViolencia);
        rdpadreMM=(RadioButton) vista.findViewById(R.id.mmPadre);
        rdpadreAV=(RadioButton) vista.findViewById(R.id.avPadre);
        rdpadreUV=(RadioButton) vista.findViewById(R.id.uvPadre);
        rdpadreN=(RadioButton) vista.findViewById(R.id.ncPadre);
        rdmadreMM=(RadioButton) vista.findViewById(R.id.mmMadre);
        rdmadreAV=(RadioButton) vista.findViewById(R.id.avMadre);
        rdmadreUV=(RadioButton) vista.findViewById(R.id.uvMadre);
        rdmadreN=(RadioButton) vista.findViewById(R.id.ncMadre);
        rdhermanosMM=(RadioButton) vista.findViewById(R.id.mmHermano);
        rdhermanosAV=(RadioButton) vista.findViewById(R.id.avHermano);
        rdhermanosUV=(RadioButton) vista.findViewById(R.id.uvHermano);
        rdhermanosN=(RadioButton) vista.findViewById(R.id.ncHermano);
        rdparientesMM=(RadioButton) vista.findViewById(R.id.mmPariente);
        rdparientesAV=(RadioButton) vista.findViewById(R.id.avPariente);
        rdparientesUV=(RadioButton) vista.findViewById(R.id.uvPariente);
        rdparientesN=(RadioButton) vista.findViewById(R.id.ncPariente);
        rdamigosMM=(RadioButton) vista.findViewById(R.id.mmAmigo);
        rdamigosAV=(RadioButton) vista.findViewById(R.id.avAmigo);
        rdamigosUV=(RadioButton) vista.findViewById(R.id.uvAmigo);
        rdamigosN=(RadioButton) vista.findViewById(R.id.ncAmigo);
        rdparejaMM=(RadioButton) vista.findViewById(R.id.mmPareja);
        rdparejaAV=(RadioButton) vista.findViewById(R.id.avPareja);
        rdparejaUV=(RadioButton) vista.findViewById(R.id.uvPareja);
        rdparejaN=(RadioButton) vista.findViewById(R.id.ncPareja);
        rdeducadoresMM=(RadioButton) vista.findViewById(R.id.mmEducador);
        rdeducadoresAV=(RadioButton) vista.findViewById(R.id.avEducador);
        rdeducadoresUV=(RadioButton) vista.findViewById(R.id.uvEducador);
        rdeducadoresN=(RadioButton) vista.findViewById(R.id.ncEducador);
        rdprofesoresMM=(RadioButton) vista.findViewById(R.id.mmProfesor);
        rdprofesoresAV=(RadioButton) vista.findViewById(R.id.avProfesor);
        rdprofesoresUV=(RadioButton) vista.findViewById(R.id.uvProfesor);
        rdprofesoresN=(RadioButton) vista.findViewById(R.id.ncProfesor);
        rdotroMM=(RadioButton) vista.findViewById(R.id.mmOtroSufrirViolencia);
        rdotroAV=(RadioButton) vista.findViewById(R.id.avOtroSufrirViolencia);
        rdotroUV=(RadioButton) vista.findViewById(R.id.uvOtroSufrirViolencia);
        rdotroN=(RadioButton) vista.findViewById(R.id.ncOtroSufrirViolencia);


        txtOtro=(EditText) vista.findViewById(R.id.txtotroSufrirViolencia);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta37(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta35(idFragment.getText().toString());
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
                padre= jsonObject.optInt("padre_violencia");
                madre= jsonObject.optInt("madre_violencia");
                hermanos= jsonObject.optInt("hemanos_violencia");
                amigos= jsonObject.optInt("amigos_violencia");
                parientes= jsonObject.optInt("parientes_violencia");
                pareja= jsonObject.optInt("pareja_violencia");
                educadores= jsonObject.optInt("educadores_violencia");
                profesores= jsonObject.optInt("profesores_violencia");
                otro= jsonObject.optInt("otro_sufrir_violencia");

                otroSufrirViolencia= jsonObject.optString("otro_sufrir_violencia_nombre");


                if(padre==1){
                    rdpadreMM.setChecked(true);
                }else if(padre==2){
                    rdpadreAV.setChecked(true);
                }else if(padre==3){
                    rdpadreUV.setChecked(true);
                }else if(padre==4){
                    rdpadreN.setChecked(true);
                }

                if(madre==1){
                    rdmadreMM.setChecked(true);
                }else if(madre==2){
                    rdmadreAV.setChecked(true);
                }else if(madre==3){
                    rdmadreUV.setChecked(true);
                }else if(madre==4){
                    rdmadreN.setChecked(true);
                }


                if(hermanos==1){
                    rdhermanosMM.setChecked(true);
                }else if(hermanos==2){
                    rdhermanosAV.setChecked(true);
                }else if(hermanos==3){
                    rdhermanosUV.setChecked(true);
                }else if(hermanos==4){
                    rdhermanosN.setChecked(true);
                }

                if(amigos==1){
                    rdamigosMM.setChecked(true);
                }else if(amigos==2){
                    rdamigosAV.setChecked(true);
                }else if(amigos==3){
                    rdamigosUV.setChecked(true);
                }else if(amigos==4){
                    rdamigosN.setChecked(true);
                }


                if(parientes==1){
                    rdparientesMM.setChecked(true);
                }else if(parientes==2){
                    rdparientesAV.setChecked(true);
                }else if(parientes==3){
                    rdparientesUV.setChecked(true);
                }else if(parientes==4){
                    rdparientesN.setChecked(true);
                }



                if(pareja==1){
                    rdparejaMM.setChecked(true);
                }else if(pareja==2){
                    rdparejaAV.setChecked(true);
                }else if(pareja==3){
                    rdparejaUV.setChecked(true);
                }else if(pareja==4){
                    rdparejaN.setChecked(true);
                }


                if(educadores==1){
                    rdeducadoresMM.setChecked(true);
                }else if(educadores==2){
                    rdeducadoresAV.setChecked(true);
                }else if(educadores==3){
                    rdeducadoresUV.setChecked(true);
                }else if(educadores==4){
                    rdeducadoresN.setChecked(true);
                }

                if(profesores==1){
                    rdprofesoresMM.setChecked(true);
                }else if(profesores==2){
                    rdprofesoresAV.setChecked(true);
                }else if(profesores==3){
                    rdprofesoresUV.setChecked(true);
                }else if(profesores==4){
                    rdprofesoresN.setChecked(true);
                }

                if(otro==1){
                    rdotroMM.setChecked(true);
                }else if(otro==2){
                    rdotroAV.setChecked(true);
                }else if(otro==3){
                    rdotroUV.setChecked(true);
                }else if(otro==4){
                    rdotroN.setChecked(true);
                }
                txtOtro.setText(otroSufrirViolencia.toString());
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
