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
 * {@link emp_lab_11_0_18.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_18#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_18 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_18() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_18.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    EditText txtotro, txtrazonNoCurso;
    String  idEncuesta, otronom, razonNoCurso;
    Integer familiares, amistades, presna, agencias, programa, otro, taller;
    RadioButton rdFamiliaresSi, rdFamiliaresNo, rdAmistadesSi, rdAmistadesNo, rdPrensaSi, rdPrensaNo,rdAgenciasSi, rdAgenciasNo, rdProgramaSi, rdProgramaNo, rdOtroSi, rdOtroNo, rdTallerSi, rdTallerNo;
    LinearLayout display1120;
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
    public static emp_lab_11_0_18 newInstance(String param1, String param2) {
        emp_lab_11_0_18 fragment = new emp_lab_11_0_18();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_18, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente62);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras62);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1118);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1118);
        txtrazonNoCurso=(EditText) vista.findViewById(R.id.txtResp1120);


        rdFamiliaresSi=(RadioButton) vista.findViewById(R.id.SiAtravesDeMisFamiliares);
        rdFamiliaresNo=(RadioButton) vista.findViewById(R.id.NoAtravesDeMisFamiliares);

        rdAmistadesSi=(RadioButton) vista.findViewById(R.id.SiAtravesDeAmistades);
        rdAmistadesNo=(RadioButton) vista.findViewById(R.id.NoAtravesDeAmistades);

        rdPrensaSi=(RadioButton) vista.findViewById(R.id.SiAtravesDeAvisosPrensa);
        rdPrensaNo=(RadioButton) vista.findViewById(R.id.NoAtravesDeAvisosPrensa);

        rdAgenciasSi=(RadioButton) vista.findViewById(R.id.SiAtravesDeAvisosAgenciasEmpleo);
        rdAgenciasNo=(RadioButton) vista.findViewById(R.id.NoAtravesDeAvisosAgenciasEmpleo);

        rdProgramaSi=(RadioButton) vista.findViewById(R.id.SiAtravesDeLaIntervencionPrograma);
        rdProgramaNo=(RadioButton) vista.findViewById(R.id.NoAtravesDeLaIntervencionPrograma);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi1118);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo1118);

        rdTallerSi=(RadioButton) vista.findViewById(R.id.SiRealiceUnCursoTaller);
        rdTallerNo=(RadioButton) vista.findViewById(R.id.NoRealiceUnCursoTaller);

        display1120=(LinearLayout) vista.findViewById(R.id.layout1120);

        rdTallerSi.setOnClickListener(v -> {

            display1120.setVisibility(View.INVISIBLE);
            display1120.setVisibility(View.GONE);


        });

        rdTallerNo.setOnClickListener(v -> {

            display1120.setVisibility(View.VISIBLE);


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
                otronom = jsonObject.optString("encontrar_trabajo_otro_nombre");
                razonNoCurso = jsonObject.optString("razon_no_realizar_curso");

                familiares = jsonObject.optInt("encontrar_trabajo_familia");
                amistades = jsonObject.optInt("encontrar_trabajo_amistad");
                presna = jsonObject.optInt("encontrar_trabajo_prensa");
                agencias = jsonObject.optInt("encontrar_trabajo_agencia");
                programa = jsonObject.optInt("encontrar_trabajo_programa");
                otro = jsonObject.optInt("encontrar_trabajo_otro");
                taller = jsonObject.optInt("realizo_capacitacion");


                txtrazonNoCurso.setText(razonNoCurso.toString());
                txtotro.setText(otronom.toString());

                if(taller==1){
                    display1120.setVisibility(View.INVISIBLE);
                    display1120.setVisibility(View.GONE);
                }

                if(familiares==1){
                    rdFamiliaresSi.setChecked(true);
                }else if(familiares==2){
                    rdFamiliaresNo.setChecked(true);
                }

                if(amistades==1){
                    rdAmistadesSi.setChecked(true);
                }else if(amistades==2){
                    rdAmistadesNo.setChecked(true);
                }

                if(presna==1){
                    rdPrensaSi.setChecked(true);
                }else if(presna==2){
                    rdPrensaNo.setChecked(true);
                }

                if(agencias==1){
                    rdAgenciasSi.setChecked(true);
                }else if(agencias==2){
                    rdAgenciasNo.setChecked(true);
                }



                if(programa==1){
                    rdProgramaSi.setChecked(true);
                }else if(programa==2){
                    rdProgramaNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }

                if(taller==1){
                    rdTallerSi.setChecked(true);
                }else if(taller==2){
                    rdTallerNo.setChecked(true);
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
        String url=ip+"actualiza1118.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    if(rdTallerNo.isChecked()){
                        interfaceComunicaFragments.enviarEncuesta67(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta66(idFragment.getText().toString());
                    }

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta64(idFragment.getText().toString());

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
                String razonNoCurso=txtrazonNoCurso.getText().toString();



                String familiar="0";
                if(rdFamiliaresSi.isChecked()){
                    familiar="1";
                }else if(rdFamiliaresNo.isChecked()){

                    familiar="2";
                }

                String amistad="0";
                if(rdAmistadesSi.isChecked()){
                    amistad="1";
                }else if(rdAmistadesNo.isChecked()){

                    amistad="2";
                }
                String prensa="0";
                if(rdPrensaSi.isChecked()){
                    prensa="1";
                }else if(rdPrensaNo.isChecked()){

                    prensa="2";
                }

                String agencia="0";
                if(rdAgenciasSi.isChecked()){
                    agencia="1";
                }else if(rdAgenciasNo.isChecked()){

                    agencia="2";
                }

                String programa="0";
                if(rdProgramaSi.isChecked()){
                    programa="1";
                }else if(rdProgramaNo.isChecked()){

                    programa="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){

                    otro="2";
                }

                String taller="0";
                if(rdTallerSi.isChecked()){
                    taller="1";
                }else if(rdTallerNo.isChecked()){

                    taller="2";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("familiar", familiar);
                parametros.put("amistad", amistad);
                parametros.put("prensa", prensa);
                parametros.put("agencia", agencia);
                parametros.put("programa", programa);
                parametros.put("otro", otro);
                parametros.put("taller", taller);
                parametros.put("otronom", otronom);
                parametros.put("razonNoCurso", razonNoCurso);



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
