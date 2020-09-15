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
 * {@link emp_lab_11_0_34.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_34#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_34 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_34() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_34.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtmencionarBolsa, txtObjetivo;
    String idEncuesta, mencionarBolsa, objetivo;
    Integer bolsaTrabajo, planLaboral, realizaralguno, cumplir;
    RadioButton rdBolsaTrabajoSi, rdBolsaTrabajoNo, rdPlanLaboralSi, rdPlanlaboralNo, rdRealizasteAlgunoSi, rdRealizasteAlgunoNo, rdCumplirSi, rdCumplirNo, rdProceso;
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
    // TODO: Rename and change types and number of parameters
    public static emp_lab_11_0_34 newInstance(String param1, String param2) {
        emp_lab_11_0_34 fragment = new emp_lab_11_0_34();
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
       vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_34, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente68);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras68);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1134);

        txtmencionarBolsa=(EditText) vista.findViewById(R.id.txtResp1135);
        txtObjetivo=(EditText) vista.findViewById(R.id.txtResp1138);



        rdBolsaTrabajoSi=(RadioButton) vista.findViewById(R.id.RdSiBolsaDeTrabajo);
        rdBolsaTrabajoNo=(RadioButton) vista.findViewById(R.id.RdNoBolsaDeTrabajo);

        rdPlanLaboralSi=(RadioButton) vista.findViewById(R.id.RdSiPlanLaboral);
        rdPlanlaboralNo=(RadioButton) vista.findViewById(R.id.RdNoPlanLaboral);

        rdRealizasteAlgunoSi=(RadioButton) vista.findViewById(R.id.RdSiRealiceAlguno);
        rdRealizasteAlgunoNo=(RadioButton) vista.findViewById(R.id.RdNoRealiceAlguno);

        rdCumplirSi=(RadioButton) vista.findViewById(R.id.RdSi1139);
        rdCumplirNo=(RadioButton) vista.findViewById(R.id.RdNo1139);
        rdProceso=(RadioButton) vista.findViewById(R.id.RdEstoyEnProceso1139);

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

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                bolsaTrabajo = jsonObject.optInt("conoces_bolsa_laboral");
                planLaboral = jsonObject.optInt("conoces_plan_laboral");
                realizaralguno = jsonObject.optInt("ralizo_plan_laboral");
                cumplir = jsonObject.optInt("cumplir_plan_laboral");
                mencionarBolsa = jsonObject.optString("mencionar_bolsa_laboral");
                objetivo = jsonObject.optString("objetivo_plan_laboral");


                txtObjetivo.setText(objetivo.toString());
                txtmencionarBolsa.setText(mencionarBolsa.toString());

                if(bolsaTrabajo==1){
                    rdBolsaTrabajoSi.setChecked(true);
                }else if(bolsaTrabajo==2){
                    rdBolsaTrabajoNo.setChecked(true);
                }

                if(planLaboral==1){
                    rdPlanLaboralSi.setChecked(true);
                }else if(planLaboral==2){
                    rdPlanlaboralNo.setChecked(true);
                }

                if(realizaralguno==1){
                    rdRealizasteAlgunoSi.setChecked(true);
                }else if(realizaralguno==2){
                    rdRealizasteAlgunoNo.setChecked(true);
                }

                if(cumplir==1){
                    rdCumplirSi.setChecked(true);
                }else if(cumplir==2){
                    rdCumplirNo.setChecked(true);
                }else if(cumplir==3){
                    rdProceso.setChecked(true);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        request.add(jsonObjectRequest);
    }

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualiza1134.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta72(idFragment.getText().toString());

                }else if(pantalla=="Atras"){

                    interfaceComunicaFragments.enviarEncuesta70(idFragment.getText().toString());

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

                String mencionarBolsa=txtmencionarBolsa.getText().toString();
                String objetivo=txtObjetivo.getText().toString();


                String bolsaTrabajo="0";
                if(rdBolsaTrabajoSi.isChecked()){
                    bolsaTrabajo="1";
                }else if(rdBolsaTrabajoNo.isChecked()){
                    bolsaTrabajo="2";
                }

                String planLaboral="0";
                if(rdPlanLaboralSi.isChecked()){
                    planLaboral="1";

                }else if(rdPlanlaboralNo.isChecked()){
                    planLaboral="2";
                }

                String realizasteAlguno="0";
                if(rdRealizasteAlgunoSi.isChecked()){
                    realizasteAlguno="1";

                }else if(rdRealizasteAlgunoNo.isChecked()){
                    realizasteAlguno="2";
                }

                String cumplir="0";
                if(rdCumplirSi.isChecked()){
                    cumplir="1";

                }else if(rdCumplirNo.isChecked()){
                    cumplir="2";
                }else if(rdProceso.isChecked()){
                    cumplir="3";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("mencionarBolsa", mencionarBolsa);
                parametros.put("objetivo", objetivo);
                parametros.put("bolsaTrabajo", bolsaTrabajo);
                parametros.put("planLaboral", planLaboral);
                parametros.put("realizasteAlguno", realizasteAlguno);
                parametros.put("cumplir", cumplir);
                return parametros;
            }
        };
        request.add(stringRequest);
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
