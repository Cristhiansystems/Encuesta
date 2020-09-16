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
 * {@link emp_lab_11_0_31.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_31#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_31 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_31() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_31.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtNoSatisfecho;
    String idEncuesta, otronom, noSatisfecho;
    Integer ley, contrato, salario, horario, derechos, otro, trabajas, satisfecho;
    RadioButton rdLeySi, rdLeyNo, rdContratoSi, rdContratoNo, rdSalarioSi, rdSalarioNo, rdHorarioSi, rdHorarioNo, rdDerechoSi, rdDerechoNo, rdOtroSi, rdOtroNo;
    LinearLayout display1131, display1132, display1133;

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
    public static emp_lab_11_0_31 newInstance(String param1, String param2) {
        emp_lab_11_0_31 fragment = new emp_lab_11_0_31();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_31, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente67);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras67);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1131);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1133);
        txtNoSatisfecho=(EditText) vista.findViewById(R.id.txtResp1131);


        rdLeySi=(RadioButton) vista.findViewById(R.id.RdSiLey);
        rdLeyNo=(RadioButton) vista.findViewById(R.id.RdNoLey);

        rdContratoSi=(RadioButton) vista.findViewById(R.id.RdSiSeUnContrato);
        rdContratoNo=(RadioButton) vista.findViewById(R.id.RdNoSeUnContrato);


        rdSalarioSi=(RadioButton) vista.findViewById(R.id.RdSiSeUnSalarioMinimo);
        rdSalarioNo=(RadioButton) vista.findViewById(R.id.RdNoSeUnSalarioMinimo);

        rdHorarioSi=(RadioButton) vista.findViewById(R.id.RdSiLosHorarios);
        rdHorarioNo=(RadioButton) vista.findViewById(R.id.RdNoLosHorarios);

        rdDerechoSi=(RadioButton) vista.findViewById(R.id.RdSiSeDereechosDeberes);
        rdDerechoNo=(RadioButton) vista.findViewById(R.id.RdNoSeDereechosDeberes);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi1133);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo1133);

        display1131=(LinearLayout) vista.findViewById(R.id.layout1131);
        display1132=(LinearLayout) vista.findViewById(R.id.layout1132);
        display1133=(LinearLayout) vista.findViewById(R.id.layout1133);



        rdLeyNo.setOnClickListener(v -> {

            display1133.setVisibility(View.INVISIBLE);
            display1133.setVisibility(View.GONE);

        });

        rdLeySi.setOnClickListener(v -> {

            display1133.setVisibility(View.VISIBLE);

        });


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
                noSatisfecho = jsonObject.optString("no_satisfecho");
                ley = jsonObject.optInt("conoces_ley_laboral");
                contrato = jsonObject.optInt("que_es_contrato");
                salario= jsonObject.optInt("cual_salario_minimo");
                horario = jsonObject.optInt("los_horarios");
                derechos = jsonObject.optInt("derechos_deberes");
                otro = jsonObject.optInt("derecho_laboral_otro");
                otronom = jsonObject.optString("derecho_laboral_otro_nombre");
                trabajas = jsonObject.optInt("trabajas_actualmente");

                satisfecho = jsonObject.optInt("satisfecho_trabajo");

                if(satisfecho==1){
                    display1131.setVisibility(View.INVISIBLE);
                    display1131.setVisibility(View.GONE);
                }

                if(ley==2){
                    display1133.setVisibility(View.INVISIBLE);
                    display1133.setVisibility(View.GONE);
                }

                txtotro.setText(otronom.toString());
                txtNoSatisfecho.setText(noSatisfecho.toString());

                if(trabajas==2){

                    display1131.setVisibility(View.INVISIBLE);
                    display1131.setVisibility(View.GONE);

                    display1132.setVisibility(View.INVISIBLE);
                    display1132.setVisibility(View.GONE);

                }

                if(ley==1){
                    rdLeySi.setChecked(true);
                }else if(ley==2){
                    rdLeyNo.setChecked(true);
                }

                if(contrato==1){
                    rdContratoSi.setChecked(true);
                }else if(contrato==2){
                    rdContratoNo.setChecked(true);
                }

                if(salario==1){
                    rdSalarioSi.setChecked(true);
                }else if(salario==2){
                    rdSalarioNo.setChecked(true);
                }

                if(horario==1){
                    rdHorarioSi.setChecked(true);
                }else if(horario==2){
                    rdHorarioNo.setChecked(true);
                }

                if(derechos==1){
                    rdDerechoSi.setChecked(true);
                }else if(derechos==2){
                    rdDerechoNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
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
        String url=ip+"actualiza1131.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta71(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    if(trabajas==2){
                        interfaceComunicaFragments.enviarEncuesta64(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta69(idFragment.getText().toString());
                    }

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

                String otronom=txtotro.getText().toString();
                String noSatisfecho=txtNoSatisfecho.getText().toString();


                String ley="0";
                if(rdLeySi.isChecked()){
                    ley="1";

                }else if(rdLeyNo.isChecked()){
                    ley="2";
                }

                String contrato="0";
                if(rdContratoSi.isChecked()){
                    contrato="1";

                }else if(rdContratoNo.isChecked()){
                    contrato="2";
                }

                String salario="0";
                if(rdSalarioSi.isChecked()){
                    salario="1";

                }else if(rdSalarioNo.isChecked()){
                    salario="2";
                }

                String horario="0";
                if(rdHorarioSi.isChecked()){
                    horario="1";

                }else if(rdHorarioNo.isChecked()){
                    horario="2";
                }

                String derecho="0";
                if(rdDerechoSi.isChecked()){
                    derecho="1";

                }else if(rdDerechoNo.isChecked()){
                    derecho="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";

                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom", otronom);
                parametros.put("noSatisfecho", noSatisfecho);
                parametros.put("ley", ley);
                parametros.put("contrato", contrato);
                parametros.put("salario", salario);
                parametros.put("horario", horario);
                parametros.put("derecho", derecho);
                parametros.put("otro", otro);
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
