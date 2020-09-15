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
 * {@link emp_pers_8_0_11.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_pers_8_0_11#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_pers_8_0_11 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_pers_8_0_11() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_pers_8_0_11.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtparaleloprimaria, txtparalelosecundaria;
    String idEncuesta, otro, paralelomaximo;
    Integer Maximonivel,MaximoGrado, estudiando;
    RadioButton rdPrimario1, rdPrimario2, rdPrimario3, rdPrimario4,rdPrimario5, rdPrimario6;
    RadioButton rdSecundario1, rdSecundario2, rdSecundario3, rdSecundario4,rdSecundario5, rdSecundario6;
    RadioButton rdPolicia1, rdPolicia2, rdPolicia3, rdPolicia4;
    RadioButton rdUniversidad1, rdUniversidad2, rdUniversidad3, rdUniversidad4, rdUniversidad5;
    RadioButton rdTecnicoUniversidad1, rdTecnicoUniversidad2, rdTecnicoUniversidad3;
    RadioButton rdTecnicoMedio1, rdTecnicoMedio2, rdTecnicoMedio3;
    CheckBox chNivel1, chNivel2, chNivel3, chNivel4, chNivel5, chNivel6, chNivel7, chNivel8;
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
    public static emp_pers_8_0_11 newInstance(String param1, String param2) {
        emp_pers_8_0_11 fragment = new emp_pers_8_0_11();
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
        vista= inflater.inflate(R.layout.fragment_emp_pers_8_0_11, container, false);




        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente44);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras44);
        idFragment=(TextView) vista.findViewById(R.id.idemppers811);

        //Grado Primario
        rdPrimario1=(RadioButton) vista.findViewById(R.id.Rd1Pri811);
        rdPrimario2=(RadioButton) vista.findViewById(R.id.Rd2Pri811);
        rdPrimario3=(RadioButton) vista.findViewById(R.id.Rd3Pri811);
        rdPrimario4=(RadioButton) vista.findViewById(R.id.Rd4Pri811);
        rdPrimario5=(RadioButton) vista.findViewById(R.id.Rd5Pri811);
        rdPrimario6=(RadioButton) vista.findViewById(R.id.Rd6Pri811);

        //Grado Secundaria

        rdSecundario1=(RadioButton) vista.findViewById(R.id.Rd1Sec811);
        rdSecundario2=(RadioButton) vista.findViewById(R.id.Rd2Sec811);
        rdSecundario3=(RadioButton) vista.findViewById(R.id.Rd3Sec811);
        rdSecundario4=(RadioButton) vista.findViewById(R.id.Rd4Sec811);
        rdSecundario5=(RadioButton) vista.findViewById(R.id.Rd5Sec811);
        rdSecundario6=(RadioButton) vista.findViewById(R.id.Rd6Sec811);


        //Grado Policia
        rdPolicia1=(RadioButton) vista.findViewById(R.id.Rd1NorPolMil811);
        rdPolicia2=(RadioButton) vista.findViewById(R.id.Rd2NorPolMi811);
        rdPolicia3=(RadioButton) vista.findViewById(R.id.Rd3NorPolMil811);
        rdPolicia4=(RadioButton) vista.findViewById(R.id.Rd4NorPolMil811);

        //Grado Universidad
        rdUniversidad1=(RadioButton) vista.findViewById(R.id.Rd1Unil811);
        rdUniversidad2=(RadioButton) vista.findViewById(R.id.Rd2Unil811);
        rdUniversidad3=(RadioButton) vista.findViewById(R.id.Rd3Unil811);
        rdUniversidad4=(RadioButton) vista.findViewById(R.id.Rd4Unil811);
        rdUniversidad5=(RadioButton) vista.findViewById(R.id.Rd5Unil811);

        //grado tecnico Universitario
        rdTecnicoUniversidad1=(RadioButton) vista.findViewById(R.id.Rd1TecUnil811);
        rdTecnicoUniversidad2=(RadioButton) vista.findViewById(R.id.Rd2TecUnil811);
        rdTecnicoUniversidad3=(RadioButton) vista.findViewById(R.id.Rd3TecUnil811);

        // grado tecnico medio
        rdTecnicoMedio1=(RadioButton) vista.findViewById(R.id.Rd1TecMedl811);
        rdTecnicoMedio2=(RadioButton) vista.findViewById(R.id.Rd2TecMedl811);
        rdTecnicoMedio3=(RadioButton) vista.findViewById(R.id.Rd3TecMedl811);


        // checjbox nivel
        chNivel1=(CheckBox) vista.findViewById(R.id.chkPrimaria811);
        chNivel2=(CheckBox) vista.findViewById(R.id.chkSecundaria811);
        chNivel3=(CheckBox) vista.findViewById(R.id.chkNormal811);
        chNivel4=(CheckBox) vista.findViewById(R.id.chkUniversidad811);
        chNivel5=(CheckBox) vista.findViewById(R.id.chkTecnicoUniversitario811);
        chNivel6=(CheckBox) vista.findViewById(R.id.chkTecnicoMedio811);
        chNivel7=(CheckBox) vista.findViewById(R.id.chkOtroEspecificar811);
        chNivel8=(CheckBox) vista.findViewById(R.id.chkNoSabe811);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro811);
        txtparaleloprimaria=(EditText) vista.findViewById(R.id.txtParPri811);
        txtparalelosecundaria=(EditText) vista.findViewById(R.id.txtSec811);
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

                MaximoGrado = jsonObject.optInt("grado_maximo");
                Maximonivel = jsonObject.optInt("nivel_maximo");
                paralelomaximo = jsonObject.optString("paralelo_maximo");

                //otro grafment
                estudiando = jsonObject.optInt("estudiante_actualmente");

                otro="";
                otro = jsonObject.optString("otro_nivel_maximo_nombre");


                if(Maximonivel==1){
                    chNivel1.setChecked(true);
                    if(MaximoGrado==1){
                        rdPrimario1.setChecked(true);
                    }else if(MaximoGrado==2){
                        rdPrimario2.setChecked(true);
                    }else if(MaximoGrado==3){
                        rdPrimario3.setChecked(true);
                    }else if(MaximoGrado==4){
                        rdPrimario4.setChecked(true);
                    }else if(MaximoGrado==5){
                        rdPrimario5.setChecked(true);
                    }else if(MaximoGrado==6){
                        rdPrimario6.setChecked(true);
                    }

                    txtparaleloprimaria.setText(paralelomaximo.toString());
                } else if(Maximonivel==2){
                    chNivel2.setChecked(true);

                    if(MaximoGrado==1){
                        rdSecundario1.setChecked(true);
                    }else if(MaximoGrado==2){
                        rdSecundario2.setChecked(true);
                    }else if(MaximoGrado==3){
                        rdSecundario3.setChecked(true);
                    }else if(MaximoGrado==4){
                        rdSecundario4.setChecked(true);
                    }else if(MaximoGrado==5){
                        rdSecundario5.setChecked(true);
                    }else if(MaximoGrado==6){
                        rdSecundario6.setChecked(true);
                    }

                    txtparalelosecundaria.setText(paralelomaximo.toString());
                } else if(Maximonivel==3){
                    chNivel3.setChecked(true);
                    if(MaximoGrado==1){
                        rdPolicia1.setChecked(true);
                    }else if(MaximoGrado==2){
                        rdPolicia2.setChecked(true);
                    }else if(MaximoGrado==3){
                        rdPolicia3.setChecked(true);
                    }else if(MaximoGrado==4){
                        rdPolicia4.setChecked(true);
                    }
                } else if(Maximonivel==4){
                    chNivel4.setChecked(true);
                    if(MaximoGrado==1){
                        rdUniversidad1.setChecked(true);
                    }else if(MaximoGrado==2){
                        rdUniversidad2.setChecked(true);
                    }else if(MaximoGrado==3){
                        rdUniversidad3.setChecked(true);
                    }else if(MaximoGrado==4){
                        rdUniversidad4.setChecked(true);
                    }else if(MaximoGrado==5){
                        rdUniversidad5.setChecked(true);
                    }
                } else if(Maximonivel==5){
                    chNivel5.setChecked(true);
                    if(MaximoGrado==1){
                        rdTecnicoUniversidad1.setChecked(true);
                    }else if(MaximoGrado==2){
                        rdTecnicoUniversidad2.setChecked(true);
                    }else if(MaximoGrado==3){
                        rdTecnicoUniversidad3.setChecked(true);
                    }
                } else if(Maximonivel==6){
                    chNivel6.setChecked(true);
                    if(MaximoGrado==1){
                        rdTecnicoMedio1.setChecked(true);
                    }else if(MaximoGrado==2){
                        rdTecnicoMedio2.setChecked(true);
                    }else if(MaximoGrado==3){
                        rdTecnicoMedio3.setChecked(true);
                    }

                } else if(Maximonivel==7){
                    chNivel7.setChecked(true);
                    txtotro.setText(otro.toString());
                } else if(Maximonivel==8){
                    chNivel8.setChecked(true);
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
        String url=ip+"actualiza811.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta48(idFragment.getText().toString());

                }else if(pantalla=="Atras"){

                    if(estudiando==2){
                        interfaceComunicaFragments.enviarEncuesta43 (idFragment.getText().toString());

                    }else{
                        interfaceComunicaFragments.enviarEncuesta46 (idFragment.getText().toString());
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
                String otro="";
                String paraleloMaximo="";



                String gradoMaximo="0";
                String nivelMaximo="0";
                if(chNivel1.isChecked()) {
                    nivelMaximo = "1";
                    paraleloMaximo=txtparaleloprimaria.getText().toString();
                    if(rdPrimario1.isChecked()){
                        gradoMaximo="1";
                    }else if(rdPrimario2.isChecked()){
                        gradoMaximo="2";
                    }else if(rdPrimario3.isChecked()){
                        gradoMaximo="3";
                    }else if(rdPrimario4.isChecked()){
                        gradoMaximo="4";
                    }else if(rdPrimario5.isChecked()){
                        gradoMaximo="5";
                    }else if(rdPrimario6.isChecked()){
                        gradoMaximo="6";
                    }
                }


               else if(chNivel2.isChecked()) {
                    nivelMaximo = "2";
                    paraleloMaximo=txtparalelosecundaria.getText().toString();
                    if(rdSecundario1.isChecked()){
                        gradoMaximo="1";
                    }else if(rdSecundario2.isChecked()){
                        gradoMaximo="2";
                    }else if(rdSecundario3.isChecked()){
                        gradoMaximo="3";
                    }else if(rdSecundario4.isChecked()){
                        gradoMaximo="4";
                    }else if(rdSecundario5.isChecked()){
                        gradoMaximo="5";
                    }else if(rdSecundario6.isChecked()){
                        gradoMaximo="6";
                    }
                }


               else if(chNivel3.isChecked()) {
                    nivelMaximo = "3";
                    if(rdPolicia1.isChecked()){
                        gradoMaximo="1";
                    }else if(rdPolicia2.isChecked()){
                        gradoMaximo="2";
                    }else if(rdPolicia3.isChecked()){
                        gradoMaximo="3";
                    }else if(rdPolicia4.isChecked()){
                        gradoMaximo="4";
                    }
                }

               else if(chNivel4.isChecked()) {
                    nivelMaximo = "4";
                    if(rdUniversidad1.isChecked()){
                        gradoMaximo="1";
                    }else if(rdUniversidad2.isChecked()){
                        gradoMaximo="2";
                    }else if(rdUniversidad3.isChecked()){
                        gradoMaximo="3";
                    }else if(rdUniversidad4.isChecked()){
                        gradoMaximo="4";
                    }else if(rdUniversidad5.isChecked()){
                        gradoMaximo="5";
                    }
                }

                else if(chNivel5.isChecked()) {
                    nivelMaximo = "5";
                    if(rdTecnicoUniversidad1.isChecked()){
                        gradoMaximo="1";
                    }else if(rdTecnicoUniversidad2.isChecked()){
                        gradoMaximo="2";
                    }else if(rdTecnicoUniversidad3.isChecked()){
                        gradoMaximo="3";
                    }
                }

               else if(chNivel6.isChecked()) {
                    nivelMaximo = "6";
                    if(rdTecnicoMedio1.isChecked()){
                        gradoMaximo="1";
                    }else if(rdTecnicoMedio2.isChecked()){
                        gradoMaximo="2";
                    }else if(rdTecnicoMedio3.isChecked()){
                        gradoMaximo="3";
                    }
                }


               else if(chNivel7.isChecked()) {
                    nivelMaximo = "7";
                    otro=txtotro.getText().toString();
                }

                else if(chNivel8.isChecked()) {
                    nivelMaximo = "8";
                }

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otro", otro);
                parametros.put("nivelMaximo", nivelMaximo);
                parametros.put("gradoMaximo", gradoMaximo);
                parametros.put("paralelomaximo", paraleloMaximo);



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
