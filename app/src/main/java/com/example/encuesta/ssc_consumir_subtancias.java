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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ssc_consumir_subtancias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssc_consumir_subtancias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssc_consumir_subtancias extends Fragment {
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
    RadioButton rdAlcoholSi, rdAlcoholNo, rdTabacoSi, rdTabacoNo, rdTranquilizantesSi, rdTranqulizantesNo, rdInhalantesSi, rdInhalantesNo, rdMarihuanaSi, rdMarihuanaNo, rdPastaBaseSi, rdPastaBaseNo, rdCocainaSi, rdCocainaNo, rdOtroSi, rdOtroNo;
    EditText txtOtroConsumirSubstancia;
    String otroConsumirSubstancia, idEncuesta;
    Integer alcohol, tabaco, tranquilizante,  inhalantes, marihuana, pastaBase, cocoaina, otro;
    Integer alcoholFrec, tabacoFrec, tranquilizanteFrec, inhalantesFrec, marihuanaFrec, pastaBaseFrec, cocoainaFrec, otroFrec;
    Spinner spinalcohol, spintabaco, spintranquilizante, spininhalantes, spinmarihuana, spinpastaBase, spincocoaina, spinotro;
    //volley

    ProgressDialog progreso;
   // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    private OnFragmentInteractionListener mListener;

    public ssc_consumir_subtancias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssc_consumir_subtancias.
     */
    // TODO: Rename and change types and number of parameters
    public static ssc_consumir_subtancias newInstance(String param1, String param2) {
        ssc_consumir_subtancias fragment = new ssc_consumir_subtancias();
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
        vista=inflater.inflate(R.layout.fragment_ssc_consumir_subtancias, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente18);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras18);
        idFragment= (TextView) vista.findViewById(R.id.idConsumirSubstancias);

        //Radio Butons
        rdAlcoholSi=(RadioButton) vista.findViewById(R.id.alcoholSi);
        rdAlcoholNo=(RadioButton) vista.findViewById(R.id.alcoholNo);
        rdTabacoSi=(RadioButton) vista.findViewById(R.id.tabacoSi);
        rdTabacoNo=(RadioButton) vista.findViewById(R.id.tabacoNo);
        rdTranquilizantesSi=(RadioButton) vista.findViewById(R.id.tranquilizantesSi);
        rdTranqulizantesNo=(RadioButton) vista.findViewById(R.id.tranquilizantesNo);
        rdInhalantesSi=(RadioButton) vista.findViewById(R.id.inhalantesSi);
        rdInhalantesNo=(RadioButton) vista.findViewById(R.id.inhalantesNo);
        rdMarihuanaSi=(RadioButton) vista.findViewById(R.id.marihuanaSi);
        rdMarihuanaNo=(RadioButton) vista.findViewById(R.id.marihuanaNo);
        rdPastaBaseSi=(RadioButton) vista.findViewById(R.id.pastaBaseSi);
        rdPastaBaseNo=(RadioButton) vista.findViewById(R.id.pastaBaseNo);
        rdCocainaSi=(RadioButton) vista.findViewById(R.id.cocainaSi);
        rdCocainaNo=(RadioButton) vista.findViewById(R.id.cocainaNo);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroSubstanciaSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroSubstanciaNo);



        //spinners
        spinalcohol=(Spinner) vista.findViewById(R.id.frecuenciaAlcohol);
        spintabaco=(Spinner) vista.findViewById(R.id.frecuenciaTabaco);
        spintranquilizante=(Spinner) vista.findViewById(R.id.frecuenciaTranquilizantes);
        spininhalantes=(Spinner) vista.findViewById(R.id.frecuenciainhalantes);
        spinmarihuana=(Spinner) vista.findViewById(R.id.frecuenciaMarihuana);
        spinpastaBase=(Spinner) vista.findViewById(R.id.frecuenciaPastaBase);
        spincocoaina=(Spinner) vista.findViewById(R.id.frecuenciaCocaina);
        spinotro=(Spinner) vista.findViewById(R.id.frecuenciaSubstancia);

        //TextViews
        txtOtroConsumirSubstancia=(EditText) vista.findViewById(R.id.txtOtroSubstancia);


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

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualizaConsumirSubtancia.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta20(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta18(idFragment.getText().toString());

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
                String id=idFragment.getText().toString();
                String alcohol="0";
                if(rdAlcoholSi.isChecked()){
                    alcohol="1";
                }else if(rdAlcoholNo.isChecked()){
                    alcohol="2";
                }

                String tabaco="0";
                if(rdTabacoSi.isChecked()){
                    tabaco="1";
                }else if(rdTabacoNo.isChecked()){
                    tabaco="2";
                }

                String tranquilizante="0";
                if(rdTranquilizantesSi.isChecked()){
                    tranquilizante="1";
                }else if(rdTranqulizantesNo.isChecked()){
                    tranquilizante="2";
                }

                String inhalante="0";
                if(rdInhalantesSi.isChecked()){
                    inhalante="1";
                }else if(rdInhalantesNo.isChecked()){
                    inhalante="2";
                }


                String marihuana="0";
                if(rdMarihuanaSi.isChecked()){
                    marihuana="1";
                }else if(rdMarihuanaNo.isChecked()){
                    marihuana="2";
                }

                String pastaBase="0";
                if(rdPastaBaseSi.isChecked()){
                    pastaBase="1";
                }else if(rdPastaBaseNo.isChecked()){
                    pastaBase="2";
                }

                String cocaina="0";
                if(rdCocainaSi.isChecked()){
                    cocaina="1";
                }else if(rdCocainaNo.isChecked()){
                    cocaina="2";
                }


                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }

                String frecAlcohol="0";
                if(spinalcohol.getSelectedItem().toString()=="Una sola vez"){
                    frecAlcohol="1";
                }else if(spinalcohol.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecAlcohol="2";
                }else if(spinalcohol.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecAlcohol="3";
                }else if(spinalcohol.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecAlcohol="4";
                }else if(spinalcohol.getSelectedItem().toString()=="Diariamente"){
                    frecAlcohol="5";
                }else if(spinalcohol.getSelectedItem().toString()=="No contesta"){
                    frecAlcohol="6";
                }

                String frecTabaco="0";
                if(spintabaco.getSelectedItem().toString()=="Una sola vez"){
                    frecTabaco="1";
                }else if(spintabaco.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecTabaco="2";
                }else if(spintabaco.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecTabaco="3";
                }else if(spintabaco.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecTabaco="4";
                }else if(spintabaco.getSelectedItem().toString()=="Diariamente"){
                    frecTabaco="5";
                }else if(spintabaco.getSelectedItem().toString()=="No contesta"){
                    frecTabaco="6";
                }

                String frecTranquilizante="0";
                if(spintranquilizante.getSelectedItem().toString()=="Una sola vez"){
                    frecTranquilizante="1";
                }else if(spintranquilizante.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecTranquilizante="2";
                }else if(spintranquilizante.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecTranquilizante="3";
                }else if(spintranquilizante.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecTranquilizante="4";
                }else if(spintranquilizante.getSelectedItem().toString()=="Diariamente"){
                    frecTranquilizante="5";
                }else if(spintranquilizante.getSelectedItem().toString()=="No contesta"){
                    frecTranquilizante="6";
                }


                String frecInhalante="0";
                if(spininhalantes.getSelectedItem().toString()=="Una sola vez"){
                    frecInhalante="1";
                }else if(spininhalantes.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecInhalante="2";
                }else if(spininhalantes.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecInhalante="3";
                }else if(spininhalantes.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecInhalante="4";
                }else if(spininhalantes.getSelectedItem().toString()=="Diariamente"){
                    frecInhalante="5";
                }else if(spininhalantes.getSelectedItem().toString()=="No contesta"){
                    frecInhalante="6";
                }

                String frecMarihuana="0";
                if(spinmarihuana.getSelectedItem().toString()=="Una sola vez"){
                    frecMarihuana="1";
                }else if(spinmarihuana.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecMarihuana="2";
                }else if(spinmarihuana.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecMarihuana="3";
                }else if(spinmarihuana.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecMarihuana="4";
                }else if(spinmarihuana.getSelectedItem().toString()=="Diariamente"){
                    frecMarihuana="5";
                }else if(spinmarihuana.getSelectedItem().toString()=="No contesta"){
                    frecMarihuana="6";
                }

                String frecPastaBase="0";
                if(spinpastaBase.getSelectedItem().toString()=="Una sola vez"){
                    frecPastaBase="1";
                }else if(spinpastaBase.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecPastaBase="2";
                }else if(spinpastaBase.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecPastaBase="3";
                }else if(spinpastaBase.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecPastaBase="4";
                }else if(spinpastaBase.getSelectedItem().toString()=="Diariamente"){
                    frecPastaBase="5";
                }else if(spinpastaBase.getSelectedItem().toString()=="No contesta"){
                    frecPastaBase="6";
                }


                String frecCocaina="0";
                if(spincocoaina.getSelectedItem().toString()=="Una sola vez"){
                    frecCocaina="1";
                }else if(spincocoaina.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecCocaina="2";
                }else if(spincocoaina.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecCocaina="3";
                }else if(spincocoaina.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecCocaina="4";
                }else if(spincocoaina.getSelectedItem().toString()=="Diariamente"){
                    frecCocaina="5";
                }else if(spincocoaina.getSelectedItem().toString()=="No contesta"){
                    frecCocaina="6";
                }


                String frecOtro="0";
                if(spinotro.getSelectedItem().toString()=="Una sola vez"){
                    frecOtro="1";
                }else if(spinotro.getSelectedItem().toString()=="Algunas veces durante los últimos 12 meses"){
                    frecOtro="2";
                }else if(spinotro.getSelectedItem().toString()=="Algunas veces mensualmente"){
                    frecOtro="3";
                }else if(spinotro.getSelectedItem().toString()=="Algunas veces semanalmente"){
                    frecOtro="4";
                }else if(spinotro.getSelectedItem().toString()=="Diariamente"){
                    frecOtro="5";
                }else if(spinotro.getSelectedItem().toString()=="No contesta"){
                    frecOtro="6";
                }



                String otroConsumirSubstancia=txtOtroConsumirSubstancia.getText().toString();





                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("alcohol",alcohol);
                parametros.put("tabaco",tabaco);
                parametros.put("tranquilizante",tranquilizante);
                parametros.put("inhalante",inhalante);
                parametros.put("marihuana",marihuana);
                parametros.put("pastaBase",pastaBase);
                parametros.put("cocaina",cocaina);
                parametros.put("otro",otro);


                parametros.put("frecAlcohol",frecAlcohol);
                parametros.put("frecTabaco",frecTabaco);
                parametros.put("frecTranquilizante",frecTranquilizante);
                parametros.put("frecInhalante",frecInhalante);
                parametros.put("frecMarihuana",frecMarihuana);
                parametros.put("frecPastaBase",frecPastaBase);
                parametros.put("frecCocaina",frecCocaina);
                parametros.put("frecOtro",frecOtro);

                parametros.put("otroConsumirSubstancia",otroConsumirSubstancia);



                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
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
                alcohol = jsonObject.optInt("alcohol");
                alcoholFrec = jsonObject.optInt("alcohol_frecuencia");
                tabaco = jsonObject.optInt("tabaco");
                tabacoFrec = jsonObject.optInt("tabaco_frecuencia");
                tranquilizante = jsonObject.optInt("tranquilizantes");
                tranquilizanteFrec = jsonObject.optInt("tranquilizantes_frecuencia");
                inhalantes = jsonObject.optInt("inhalantes");
                inhalantesFrec = jsonObject.optInt("inhalantes_frecuencia");
                marihuana = jsonObject.optInt("marihuana");
                marihuanaFrec = jsonObject.optInt("marihuana_frecuencia");
                pastaBase = jsonObject.optInt("pasta_base");
                pastaBaseFrec = jsonObject.optInt("pasta_base_frecuencia");
                cocoaina = jsonObject.optInt("cocaina");
                cocoainaFrec = jsonObject.optInt("cocaina_frecuencia");
                otro = jsonObject.optInt("otro_substancia");
                otroFrec = jsonObject.optInt("otro_substancia_frecuencia");

                otroConsumirSubstancia = jsonObject.optString("otro_substancia_nombre");

                //LLenar spinner frecuencia alcohol
                String frecuenciaA;
                if(alcoholFrec==1){
                    frecuenciaA="Una sola vez";
                }else if(alcoholFrec==2){
                    frecuenciaA="Algunas veces durante los últimos 12 meses";
                }else if(alcoholFrec==3){
                    frecuenciaA="Algunas veces mensualmente";
                }else if(alcoholFrec==4){
                    frecuenciaA="Algunas veces semanalmente";
                }else if(alcoholFrec==5){
                    frecuenciaA="Diariamente";
                }else if(alcoholFrec==6){
                    frecuenciaA="No contesta";
                }else{
                    frecuenciaA="";
                }

                ArrayList<String> frecAlcohol= new ArrayList<String>();
                frecAlcohol.add(frecuenciaA);
                frecAlcohol.add("Una sola vez");
                frecAlcohol.add("Algunas veces durante los últimos 12 meses");
                frecAlcohol.add("Algunas veces mensualmente");
                frecAlcohol.add("Algunas veces semanalmente");
                frecAlcohol.add("Diariamente");
                frecAlcohol.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorAlcohol=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecAlcohol);
                spinalcohol.setAdapter(adaptadorAlcohol);


                //LLenar spinner frecuencia tabaco
                String frecuenciaT;
                if(tabacoFrec==1){
                    frecuenciaT="Una sola vez";
                }else if(tabacoFrec==2){
                    frecuenciaT="Algunas veces durante los últimos 12 meses";
                }else if(tabacoFrec==3){
                    frecuenciaT="Algunas veces mensualmente";
                }else if(tabacoFrec==4){
                    frecuenciaT="Algunas veces semanalmente";
                }else if(tabacoFrec==5){
                    frecuenciaT="Diariamente";
                }else if(tabacoFrec==6){
                    frecuenciaT="No contesta";
                }else{
                    frecuenciaT="";
                }

                ArrayList<String> frecTabaco= new ArrayList<String>();
                frecTabaco.add(frecuenciaT);
                frecTabaco.add("Una sola vez");
                frecTabaco.add("Algunas veces durante los últimos 12 meses");
                frecTabaco.add("Algunas veces mensualmente");
                frecTabaco.add("Algunas veces semanalmente");
                frecTabaco.add("Diariamente");
                frecTabaco.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorTabaco=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecTabaco);
                spintabaco.setAdapter(adaptadorTabaco);

                //LLenar spinner frecuencia tranquilizante
                String frecuenciaTr;
                if(tranquilizanteFrec==1){
                    frecuenciaTr="Una sola vez";
                }else if(tranquilizanteFrec==2){
                    frecuenciaTr="Algunas veces durante los últimos 12 meses";
                }else if(tranquilizanteFrec==3){
                    frecuenciaTr="Algunas veces mensualmente";
                }else if(tranquilizanteFrec==4){
                    frecuenciaTr="Algunas veces semanalmente";
                }else if(tranquilizanteFrec==5){
                    frecuenciaTr="Diariamente";
                }else if(tranquilizanteFrec==6){
                    frecuenciaTr="No contesta";
                }else{
                    frecuenciaTr="";
                }

                ArrayList<String> frecTranquilizante= new ArrayList<String>();
                frecTranquilizante.add(frecuenciaTr);
                frecTranquilizante.add("Una sola vez");
                frecTranquilizante.add("Algunas veces durante los últimos 12 meses");
                frecTranquilizante.add("Algunas veces mensualmente");
                frecTranquilizante.add("Algunas veces semanalmente");
                frecTranquilizante.add("Diariamente");
                frecTranquilizante.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorTranquilizante=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecTranquilizante);
                spintranquilizante.setAdapter(adaptadorTranquilizante);


                //LLenar spinner frecuencia inhalantes
                String frecuenciaI;
                if(inhalantesFrec==1){
                    frecuenciaI="Una sola vez";
                }else if(inhalantesFrec==2){
                    frecuenciaI="Algunas veces durante los últimos 12 meses";
                }else if(inhalantesFrec==3){
                    frecuenciaI="Algunas veces mensualmente";
                }else if(inhalantesFrec==4){
                    frecuenciaI="Algunas veces semanalmente";
                }else if(inhalantesFrec==5){
                    frecuenciaI="Diariamente";
                }else if(inhalantesFrec==6){
                    frecuenciaI="No contesta";
                }else{
                    frecuenciaI="";
                }

                ArrayList<String> frecInhalantes= new ArrayList<String>();
                frecInhalantes.add(frecuenciaI);
                frecInhalantes.add("Una sola vez");
                frecInhalantes.add("Algunas veces durante los últimos 12 meses");
                frecInhalantes.add("Algunas veces mensualmente");
                frecInhalantes.add("Algunas veces semanalmente");
                frecInhalantes.add("Diariamente");
                frecInhalantes.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorInhalantes=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecInhalantes);
                spininhalantes.setAdapter(adaptadorInhalantes);

                //LLenar spinner frecuencia marihuana
                String frecuenciaM;
                if(marihuanaFrec==1){
                    frecuenciaM="Una sola vez";
                }else if(marihuanaFrec==2){
                    frecuenciaM="Algunas veces durante los últimos 12 meses";
                }else if(marihuanaFrec==3){
                    frecuenciaM="Algunas veces mensualmente";
                }else if(marihuanaFrec==4){
                    frecuenciaM="Algunas veces semanalmente";
                }else if(marihuanaFrec==5){
                    frecuenciaM="Diariamente";
                }else if(marihuanaFrec==6){
                    frecuenciaM="No contesta";
                }else{
                    frecuenciaM="";
                }

                ArrayList<String> frecMarihuana= new ArrayList<String>();
                frecMarihuana.add(frecuenciaM);
                frecMarihuana.add("Una sola vez");
                frecMarihuana.add("Algunas veces durante los últimos 12 meses");
                frecMarihuana.add("Algunas veces mensualmente");
                frecMarihuana.add("Algunas veces semanalmente");
                frecMarihuana.add("Diariamente");
                frecMarihuana.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorMarihuana=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecMarihuana);
                spinmarihuana.setAdapter(adaptadorMarihuana);

                //LLenar spinner frecuencia pasta base
                String frecuenciaPB;
                if(pastaBaseFrec==1){
                    frecuenciaPB="Una sola vez";
                }else if(pastaBaseFrec==2){
                    frecuenciaPB="Algunas veces durante los últimos 12 meses";
                }else if(pastaBaseFrec==3){
                    frecuenciaPB="Algunas veces mensualmente";
                }else if(pastaBaseFrec==4){
                    frecuenciaPB="Algunas veces semanalmente";
                }else if(pastaBaseFrec==5){
                    frecuenciaPB="Diariamente";
                }else if(pastaBaseFrec==6){
                    frecuenciaPB="No contesta";
                }else{
                    frecuenciaPB="";
                }

                ArrayList<String> frecPastaBase= new ArrayList<String>();
                frecPastaBase.add(frecuenciaPB);
                frecPastaBase.add("Una sola vez");
                frecPastaBase.add("Algunas veces durante los últimos 12 meses");
                frecPastaBase.add("Algunas veces mensualmente");
                frecPastaBase.add("Algunas veces semanalmente");
                frecPastaBase.add("Diariamente");
                frecPastaBase.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorPastaBase=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecPastaBase);
                spinpastaBase.setAdapter(adaptadorPastaBase);


                //LLenar spinner frecuencia cocaina
                String frecuenciaC;
                if(cocoainaFrec==1){
                    frecuenciaC="Una sola vez";
                }else if(cocoainaFrec==2){
                    frecuenciaC="Algunas veces durante los últimos 12 meses";
                }else if(cocoainaFrec==3){
                    frecuenciaC="Algunas veces mensualmente";
                }else if(cocoainaFrec==4){
                    frecuenciaC="Algunas veces semanalmente";
                }else if(cocoainaFrec==5){
                    frecuenciaC="Diariamente";
                }else if(cocoainaFrec==6){
                    frecuenciaC="No contesta";
                }else{
                    frecuenciaC="";
                }

                ArrayList<String> frecCocaina= new ArrayList<String>();
                frecCocaina.add(frecuenciaC);
                frecCocaina.add("Una sola vez");
                frecCocaina.add("Algunas veces durante los últimos 12 meses");
                frecCocaina.add("Algunas veces mensualmente");
                frecCocaina.add("Algunas veces semanalmente");
                frecCocaina.add("Diariamente");
                frecCocaina.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorCocaina=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecCocaina);
                spincocoaina.setAdapter(adaptadorCocaina);


                //LLenar spinner frecuencia otro
                String frecuenciaO;
                if(otroFrec==1){
                    frecuenciaO="Una sola vez";
                }else if(otroFrec==2){
                    frecuenciaO="Algunas veces durante los últimos 12 meses";
                }else if(otroFrec==3){
                    frecuenciaO="Algunas veces mensualmente";
                }else if(otroFrec==4){
                    frecuenciaO="Algunas veces semanalmente";
                }else if(otroFrec==5){
                    frecuenciaO="Diariamente";
                }else if(otroFrec==6){
                    frecuenciaO="No contesta";
                }else{
                    frecuenciaO="";
                }

                ArrayList<String> frecOtro= new ArrayList<String>();
                frecOtro.add(frecuenciaO);
                frecOtro.add("Una sola vez");
                frecOtro.add("Algunas veces durante los últimos 12 meses");
                frecOtro.add("Algunas veces mensualmente");
                frecOtro.add("Algunas veces semanalmente");
                frecOtro.add("Diariamente");
                frecOtro.add("No contesta");

                ArrayAdapter<CharSequence> adaptadorOtro=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecOtro);
                spinotro.setAdapter(adaptadorOtro);

                if (alcohol == 1) {
                    rdAlcoholSi.setChecked(true);
                } else if (alcohol == 2) {
                    rdAlcoholNo.setChecked(true);
                }

                if (tabaco == 1) {
                    rdTabacoSi.setChecked(true);
                } else if (tabaco == 2) {
                    rdTabacoNo.setChecked(true);
                }

                if (tranquilizante == 1) {
                    rdTranquilizantesSi.setChecked(true);
                } else if (tranquilizante == 2) {
                    rdTranqulizantesNo.setChecked(true);
                }

                if (inhalantes == 1) {
                    rdInhalantesSi.setChecked(true);
                } else if (inhalantes == 2) {
                    rdInhalantesNo.setChecked(true);
                }

                if (marihuana == 1) {
                    rdMarihuanaSi.setChecked(true);
                } else if (marihuana == 2) {
                    rdMarihuanaNo.setChecked(true);
                }

                if (pastaBase == 1) {
                    rdPastaBaseSi.setChecked(true);
                } else if (pastaBase == 2) {
                    rdPastaBaseNo.setChecked(true);
                }

                if (cocoaina == 1) {
                    rdCocainaSi.setChecked(true);
                } else if (cocoaina == 2) {
                    rdCocainaNo.setChecked(true);
                }




                if (otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (otro == 2) {
                    rdOtroNo.setChecked(true);
                }

                txtOtroConsumirSubstancia.setText(otroConsumirSubstancia.toString());

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
