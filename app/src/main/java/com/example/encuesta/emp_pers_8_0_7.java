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
import android.widget.CheckBox;
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
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link emp_pers_8_0_7.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_pers_8_0_7#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_pers_8_0_7 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_pers_8_0_7() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_pers_8_0_7.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtcolegio, txtotro, txtparaleloprimaria, txtparalelosecundaria;
    String idEncuesta, colegio, otro, paraleloPrimaria, paraleloSecundaria;
    Integer nivelPrimaria, nivelSecundaria, nivelPolicia, nivelUniversidad, nivelTecnicoUniversitario, nivelTecnicoMedio, nivelOtro, nivelNosabe;
    Integer gradoPrimaria, gradoSecundaria, gradoPolicia, gradoUniversidad, gradoTecnicoUniversitario, gradoTecnicoMedio;
    Integer nivel1, nivel2, nivel3, nivel4, nivel5, nivel6, nivel7, nivel8;
    RadioButton rdPrimario1, rdPrimario2, rdPrimario3, rdPrimario4,rdPrimario5, rdPrimario6;
    RadioButton rdSecundario1, rdSecundario2, rdSecundario3, rdSecundario4,rdSecundario5, rdSecundario6;
    RadioButton rdPolicia1, rdPolicia2, rdPolicia3, rdPolicia4;
    RadioButton rdUniversidad1, rdUniversidad2, rdUniversidad3, rdUniversidad4, rdUniversidad5;
    RadioButton rdTecnicoUniversidad1, rdTecnicoUniversidad2, rdTecnicoUniversidad3;
    RadioButton rdTecnicoMedio1, rdTecnicoMedio2, rdTecnicoMedio3;
    CheckBox chNivel1, chNivel2, chNivel3, chNivel4, chNivel5, chNivel6, chNivel7, chNivel8;
    //volley

    ProgressDialog progreso;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //

    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    // TODO: Rename and change types and number of parameters
    public static emp_pers_8_0_7 newInstance(String param1, String param2) {
        emp_pers_8_0_7 fragment = new emp_pers_8_0_7();
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
        vista= inflater.inflate(R.layout.fragment_emp_pers_8_0_7, container, false);
        idFragment=(TextView) vista.findViewById(R.id.idemppers807);

        //Grado Primario
        rdPrimario1=(RadioButton) vista.findViewById(R.id.Rd1Pri88);
        rdPrimario2=(RadioButton) vista.findViewById(R.id.Rd2Pri88);
        rdPrimario3=(RadioButton) vista.findViewById(R.id.Rd3Pri88);
        rdPrimario4=(RadioButton) vista.findViewById(R.id.Rd4Pri88);
        rdPrimario5=(RadioButton) vista.findViewById(R.id.Rd5Pri88);
        rdPrimario6=(RadioButton) vista.findViewById(R.id.Rd6Pri88);

        //Grado Secundaria

        rdSecundario1=(RadioButton) vista.findViewById(R.id.Rd1Sec88);
        rdSecundario2=(RadioButton) vista.findViewById(R.id.Rd2Sec88);
        rdSecundario3=(RadioButton) vista.findViewById(R.id.Rd3Sec88);
        rdSecundario4=(RadioButton) vista.findViewById(R.id.Rd4Sec88);
        rdSecundario5=(RadioButton) vista.findViewById(R.id.Rd5Sec88);
        rdSecundario6=(RadioButton) vista.findViewById(R.id.Rd6Sec88);


        //Grado Policia
        rdPolicia1=(RadioButton) vista.findViewById(R.id.Rd1NorPolMil88);
        rdPolicia2=(RadioButton) vista.findViewById(R.id.Rd2NorPolMil88);
        rdPolicia3=(RadioButton) vista.findViewById(R.id.Rd3NorPolMil88);
        rdPolicia4=(RadioButton) vista.findViewById(R.id.Rd4NorPolMil88);

        //Grado Universidad
        rdUniversidad1=(RadioButton) vista.findViewById(R.id.Rd1Unil88);
        rdUniversidad2=(RadioButton) vista.findViewById(R.id.Rd2Unil88);
        rdUniversidad3=(RadioButton) vista.findViewById(R.id.Rd3Unil88);
        rdUniversidad4=(RadioButton) vista.findViewById(R.id.Rd4Unil88);
        rdUniversidad5=(RadioButton) vista.findViewById(R.id.Rd5Unil88);

        //grado tecnico Universitario
        rdTecnicoUniversidad1=(RadioButton) vista.findViewById(R.id.Rd1TecUnil88);
        rdTecnicoUniversidad2=(RadioButton) vista.findViewById(R.id.Rd2TecUnil88);
        rdTecnicoUniversidad3=(RadioButton) vista.findViewById(R.id.Rd3TecUnil88);

        // grado tecnico medio
        rdTecnicoMedio1=(RadioButton) vista.findViewById(R.id.Rd1TecMedl88);
        rdTecnicoMedio2=(RadioButton) vista.findViewById(R.id.Rd2TecMedl88);
        rdTecnicoMedio3=(RadioButton) vista.findViewById(R.id.Rd3TecMedl88);


        // checjbox nivel
        chNivel1=(CheckBox) vista.findViewById(R.id.chkPrimaria88);
        chNivel2=(CheckBox) vista.findViewById(R.id.chkSecundaria88);
        chNivel3=(CheckBox) vista.findViewById(R.id.chkNormal88);
        chNivel4=(CheckBox) vista.findViewById(R.id.chkUniversidad88);
        chNivel5=(CheckBox) vista.findViewById(R.id.chkTecnicoUniversitario88);
        chNivel6=(CheckBox) vista.findViewById(R.id.chkTecnicoMedio88);
        chNivel7=(CheckBox) vista.findViewById(R.id.chkOtroEspecificar88);
        chNivel8=(CheckBox) vista.findViewById(R.id.chkNoSabe88);

        txtcolegio=(EditText) vista.findViewById(R.id.txtNombCentEdu);
        txtotro=(EditText) vista.findViewById(R.id.txtOtro808);
        txtparaleloprimaria=(EditText) vista.findViewById(R.id.txtParPri);
        txtparalelosecundaria=(EditText) vista.findViewById(R.id.txtSec88);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente41);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras41);
        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }

        //Aqui empieza el volley
        //request= Volley.newRequestQueue(getContext());
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
                colegio = jsonObject.optString("colegio");

                //primaria
                gradoPrimaria = jsonObject.optInt("grado_colegio");
                nivel1 = jsonObject.optInt("nivel_colegio");
                paraleloPrimaria = jsonObject.optString("paralelo_colegio");

                //secundaria
                gradoSecundaria = jsonObject.optInt("grado_secundaria");
                nivel2 = jsonObject.optInt("nivel_secundaria");
                paraleloSecundaria = jsonObject.optString("paralelo_secundaria");

                //policia
                gradoPolicia = jsonObject.optInt("grado_policia");
                nivel3 = jsonObject.optInt("nivel_policia");

                //universidad
                gradoUniversidad = jsonObject.optInt("grado_universidad");
                nivel4 = jsonObject.optInt("nivel_universidad");


                //tecnico universitario
                gradoTecnicoUniversitario = jsonObject.optInt("grado_tecnico_universitario");
                nivel5 = jsonObject.optInt("nivel_tecnico_universitario");

                //tecnico medio
                gradoTecnicoMedio = jsonObject.optInt("grado_tecnico_medio");
                nivel6 = jsonObject.optInt("nivel_tecnico_medio");

                //otro

                nivel7 = jsonObject.optInt("nivel_otro_actual");
                otro = jsonObject.optString("nivel_otro_actual_nombre");
                //nosabe
                nivel8 = jsonObject.optInt("nivel_no_sabe");

                colegio = jsonObject.optString("colegio");

                if(gradoPrimaria==1){
                    rdPrimario1.setChecked(true);
                }else if(gradoPrimaria==2){
                    rdPrimario2.setChecked(true);
                }else if(gradoPrimaria==3){
                    rdPrimario3.setChecked(true);
                }else if(gradoPrimaria==4){
                    rdPrimario4.setChecked(true);
                }else if(gradoPrimaria==5){
                    rdPrimario5.setChecked(true);
                }else if(gradoPrimaria==6){
                    rdPrimario6.setChecked(true);
                }


                if(gradoSecundaria==1){
                    rdSecundario1.setChecked(true);
                }else if(gradoSecundaria==2){
                    rdSecundario2.setChecked(true);
                }else if(gradoSecundaria==3){
                    rdSecundario3.setChecked(true);
                }else if(gradoSecundaria==4){
                    rdSecundario4.setChecked(true);
                }else if(gradoSecundaria==5){
                    rdSecundario5.setChecked(true);
                }else if(gradoSecundaria==6){
                    rdSecundario6.setChecked(true);
                }


                if(gradoPolicia==1){
                    rdPolicia1.setChecked(true);
                }else if(gradoPolicia==2){
                    rdPolicia2.setChecked(true);
                }else if(gradoPolicia==3){
                    rdPolicia3.setChecked(true);
                }else if(gradoPolicia==4){
                    rdPolicia4.setChecked(true);
                }


                if(gradoUniversidad==1){
                    rdUniversidad1.setChecked(true);
                }else if(gradoUniversidad==2){
                    rdUniversidad2.setChecked(true);
                }else if(gradoUniversidad==3){
                    rdUniversidad3.setChecked(true);
                }else if(gradoUniversidad==4){
                    rdUniversidad4.setChecked(true);
                }else if(gradoUniversidad==5){
                    rdUniversidad5.setChecked(true);
                }

                if(gradoTecnicoUniversitario==1){
                    rdTecnicoUniversidad1.setChecked(true);
                }else if(gradoTecnicoUniversitario==2){
                    rdTecnicoUniversidad2.setChecked(true);
                }else if(gradoTecnicoUniversitario==3){
                    rdTecnicoUniversidad3.setChecked(true);
                }

                if(gradoTecnicoMedio==1){
                    rdTecnicoMedio1.setChecked(true);
                }else if(gradoTecnicoMedio==2){
                    rdTecnicoMedio2.setChecked(true);
                }else if(gradoTecnicoMedio==3){
                    rdTecnicoMedio3.setChecked(true);
                }

                if(nivel1==1){
                    chNivel1.setChecked(true);
                }

                if(nivel2==1){
                    chNivel2.setChecked(true);
                }

                if(nivel3==1){
                    chNivel3.setChecked(true);
                }

                if(nivel4==1){
                    chNivel4.setChecked(true);
                }

                if(nivel5==1){
                    chNivel5.setChecked(true);
                }

                if(nivel6==1){
                    chNivel6.setChecked(true);
                }

                if(nivel7==1){
                    chNivel7.setChecked(true);
                }

                if(nivel8==1){
                    chNivel8.setChecked(true);
                }





                txtcolegio.setText(colegio.toString());
                txtotro.setText(otro.toString());
                txtparaleloprimaria.setText(paraleloPrimaria.toString());
                txtparalelosecundaria.setText(paraleloSecundaria.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        //request.add(jsonObjectRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualiza87.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta45(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta43(idFragment.getText().toString());

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

                String colegio=txtcolegio.getText().toString();
                String otro=txtotro.getText().toString();
                String paraleloPrimaria=txtparaleloprimaria.getText().toString();
                String paraleloSecundaria=txtparalelosecundaria.getText().toString();

                String gradoPrimaria="0";
                if(rdPrimario1.isChecked()){
                    gradoPrimaria="1";
                }else if(rdPrimario2.isChecked()){
                    gradoPrimaria="2";
                }else if(rdPrimario3.isChecked()){
                    gradoPrimaria="3";
                }else if(rdPrimario4.isChecked()){
                    gradoPrimaria="4";
                }else if(rdPrimario5.isChecked()){
                    gradoPrimaria="5";
                }else if(rdPrimario6.isChecked()){
                    gradoPrimaria="6";
                }


                String gradoSecundaria="0";
                if(rdSecundario1.isChecked()){
                    gradoSecundaria="1";
                }else if(rdSecundario2.isChecked()){
                    gradoSecundaria="2";
                }else if(rdSecundario3.isChecked()){
                    gradoSecundaria="3";
                }else if(rdSecundario4.isChecked()){
                    gradoSecundaria="4";
                }else if(rdSecundario5.isChecked()){
                    gradoSecundaria="5";
                }else if(rdSecundario6.isChecked()){
                    gradoSecundaria="6";
                }

                String gradoPolicia="0";
                if(rdPolicia1.isChecked()){
                    gradoPolicia="1";
                }else if(rdPolicia2.isChecked()){
                    gradoPolicia="2";
                }else if(rdPolicia3.isChecked()){
                    gradoPolicia="3";
                }else if(rdPolicia4.isChecked()){
                    gradoPolicia="4";
                }


                String gradouniversidad="0";
                if(rdUniversidad1.isChecked()){
                    gradouniversidad="1";
                }else if(rdUniversidad2.isChecked()){
                    gradouniversidad="2";
                }else if(rdUniversidad3.isChecked()){
                    gradouniversidad="3";
                }else if(rdUniversidad4.isChecked()){
                    gradouniversidad="4";
                }else if(rdUniversidad5.isChecked()){
                    gradouniversidad="5";
                }

                String gradoTecnicouniversidad="0";
                if(rdTecnicoUniversidad1.isChecked()){
                    gradoTecnicouniversidad="1";
                }else if(rdTecnicoUniversidad2.isChecked()){
                    gradoTecnicouniversidad="2";
                }else if(rdTecnicoUniversidad3.isChecked()){
                    gradoTecnicouniversidad="3";
                }

                String gradoTecnicomedio="0";
                if(rdTecnicoMedio1.isChecked()){
                    gradoTecnicomedio="1";
                }else if(rdTecnicoMedio2.isChecked()){
                    gradoTecnicomedio="2";
                }else if(rdTecnicoMedio3.isChecked()){
                    gradoTecnicomedio="3";
                }

                String nivel1="0";
                if(chNivel1.isChecked()) {
                    nivel1 = "1";
                }

                String nivel2="0";
                if(chNivel2.isChecked()) {
                    nivel2 = "1";
                }

                String nivel3="0";
                if(chNivel3.isChecked()) {
                    nivel3 = "1";
                }

                String nivel4="0";
                if(chNivel4.isChecked()) {
                    nivel4 = "1";
                }

                String nivel5="0";
                if(chNivel5.isChecked()) {
                    nivel5 = "1";
                }

                String nivel6="0";
                if(chNivel6.isChecked()) {
                    nivel6 = "1";
                }

                String nivel7="0";
                if(chNivel7.isChecked()) {
                    nivel7 = "1";
                }

                String nivel8="0";
                if(chNivel8.isChecked()) {
                    nivel8 = "1";
                }

                    Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("colegio", colegio);
                parametros.put("otro", otro);
                parametros.put("paraleloPrimaria", paraleloPrimaria);
                parametros.put("paraleloSecundaria", paraleloSecundaria);
                parametros.put("gradoPrimaria", gradoPrimaria);
                parametros.put("gradoSecundaria", gradoSecundaria);
                parametros.put("gradoPolicia", gradoPolicia);
                parametros.put("gradoUniversidad", gradouniversidad);
                parametros.put("gradoTecnicoUniversidad", gradoTecnicouniversidad);
                parametros.put("gradoTecnicoMedio", gradoTecnicomedio);
                parametros.put("nivel1", nivel1);
                parametros.put("nivel2", nivel2);
                parametros.put("nivel3", nivel3);
                parametros.put("nivel4", nivel4);
                parametros.put("nivel5", nivel5);
                parametros.put("nivel6", nivel6);
                parametros.put("nivel7", nivel7);
                parametros.put("nivel8", nivel8);


                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
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
