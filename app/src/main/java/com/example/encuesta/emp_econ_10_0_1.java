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
 * {@link emp_econ_10_0_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_1.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtemprendimiento;
    String idEncuesta, otronom, tipoemprendimiento;
    Integer empleo, emprendimiento, social, realizando, otro, oportunidadEmprendimiento;
    RadioButton rdOportunidadEmprendimientoSi, rdOportunidadEmprendimientoNo, rdEmpleoSi, rdEmpleoNo, rdEmprendimientoSi, rdEmprendimientoNo, rdSocialSi, rdSocialNo, rdRealizandoSi, rdrealizandoNo, rdOtroSi, rdOtroNo;
    LinearLayout display102, display103;
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
    public static emp_econ_10_0_1 newInstance(String param1, String param2) {
        emp_econ_10_0_1 fragment = new emp_econ_10_0_1();
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
       vista= inflater.inflate(R.layout.fragment_emp_econ_10_0_1, container, false);

        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente49);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras49);

        idFragment=(TextView) vista.findViewById(R.id.idemppers101);
        txtotro=(EditText) vista.findViewById(R.id.txtOtro101);
        txtemprendimiento=(EditText) vista.findViewById(R.id.Resp103);


        rdEmpleoSi=(RadioButton) vista.findViewById(R.id.SiEmpleo);
        rdEmpleoNo=(RadioButton) vista.findViewById(R.id.NoEmpleo);

        rdEmprendimientoSi=(RadioButton) vista.findViewById(R.id.SiEmprendimiento);
        rdEmprendimientoNo=(RadioButton) vista.findViewById(R.id.NoEmprendimiento);

        rdSocialSi=(RadioButton) vista.findViewById(R.id.SiProyectoSocial);
        rdSocialNo=(RadioButton) vista.findViewById(R.id.NoProyectoSocial);

        rdRealizandoSi=(RadioButton) vista.findViewById(R.id.SiLoEstoyRealizando);
        rdrealizandoNo=(RadioButton) vista.findViewById(R.id.NoLoEstoyRealizando);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi101);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo101);

        rdOportunidadEmprendimientoSi=(RadioButton) vista.findViewById(R.id.SiTuveLaOportuDeRealizEmpren);
        rdOportunidadEmprendimientoNo=(RadioButton) vista.findViewById(R.id.NoTuveLaOportuDeRealizEmpren);

        display102=(LinearLayout) vista.findViewById(R.id.layout102);
        display103=(LinearLayout) vista.findViewById(R.id.layout103);

        rdOportunidadEmprendimientoNo.setOnClickListener(v -> {

            display103.setVisibility(View.INVISIBLE);
            display103.setVisibility(View.GONE);


        });

        rdOportunidadEmprendimientoSi.setOnClickListener(v -> {

            display103.setVisibility(View.VISIBLE);

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
                 empleo = jsonObject.optInt("empleo");
                emprendimiento = jsonObject.optInt("emprendimiento");
                 social = jsonObject.optInt("proyecto_social");
                 realizando = jsonObject.optInt("estoy_realizando");
                 otro = jsonObject.optInt("otro_actividad_ingreso");

                otronom  = jsonObject.optString("otro_actividad_ingreso_nombre");

                oportunidadEmprendimiento = jsonObject.optInt("opotunidad_realizar_emprendimiento");
                tipoemprendimiento = jsonObject.optString("menciona_tipo_emprendimiento");

                if(oportunidadEmprendimiento==2){

                    display103.setVisibility(View.INVISIBLE);
                    display103.setVisibility(View.GONE);

                }

                if(empleo==1){
                    rdEmpleoSi.setChecked(true);
                }else if(empleo==2){
                    rdEmpleoNo.setChecked(true);
                }


                if(emprendimiento==1){
                    rdEmprendimientoSi.setChecked(true);
                }else if(emprendimiento==2){
                    rdEmprendimientoNo.setChecked(true);
                }

                if(social==1){
                    rdSocialSi.setChecked(true);
                }else if(social==2){
                    rdSocialNo.setChecked(true);
                }

                if(realizando==1){
                    rdRealizandoSi.setChecked(true);
                }else if(realizando==2){
                    rdrealizandoNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }

                if(oportunidadEmprendimiento==1){
                    rdOportunidadEmprendimientoSi.setChecked(true);
                }else if(oportunidadEmprendimiento==2){
                    rdOportunidadEmprendimientoNo.setChecked(true);
                }


                txtotro.setText(otronom.toString());
                txtemprendimiento.setText(tipoemprendimiento.toString());




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
        String url=ip+"actualiza101.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    if(rdOportunidadEmprendimientoNo.isChecked()){
                        interfaceComunicaFragments.enviarEncuesta60(idFragment.getText().toString());
                    }else{
                        interfaceComunicaFragments.enviarEncuesta53(idFragment.getText().toString());
                    }


                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta51(idFragment.getText().toString());

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
                String tipoEmprendimiento=txtemprendimiento.getText().toString();

                String empleo="0";

                if(rdEmpleoSi.isChecked()){
                    empleo="1";
                }else if(rdEmpleoNo.isChecked()){
                    empleo="2";
                }

                String emprendimiento="0";
                if(rdEmprendimientoSi.isChecked()){
                    emprendimiento="1";
                }else if(rdEmprendimientoNo.isChecked()){
                    emprendimiento="2";
                }

                String social="0";
                if(rdSocialSi.isChecked()){
                    social="1";
                }else if(rdSocialNo.isChecked()){
                    social="2";
                }

                String realizando="0";
                if(rdRealizandoSi.isChecked()){
                    realizando="1";
                }else if(rdrealizandoNo.isChecked()){
                    realizando="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }

                String oportunidad="0";
                if(rdOportunidadEmprendimientoSi.isChecked()){
                    oportunidad="1";
                }else if(rdOportunidadEmprendimientoNo.isChecked()){
                    oportunidad="2";
                }

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom", otronom);
                parametros.put("tipoEmprendimiento", tipoEmprendimiento);
                parametros.put("empleo", empleo);
                parametros.put("emprendimiento", emprendimiento);
                parametros.put("social", social);
                parametros.put("realizando", realizando);
                parametros.put("otro", otro);
                parametros.put("oportunidad", oportunidad);


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
