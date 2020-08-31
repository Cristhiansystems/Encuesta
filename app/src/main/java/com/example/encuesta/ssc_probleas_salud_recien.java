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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ssc_probleas_salud_recien.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssc_probleas_salud_recien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssc_probleas_salud_recien extends Fragment{
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
    RadioButton rdDolorCabezaSi, rdDolorCabezaNo, rdDigestivasSi, rdDigestivasNo, rdRespiratoriasSi, rdRespiratoriasNo, rdMolestiasGenitalesSi, rdMolestiasGenitalesNo, rdCortesSi, rdCortesNo, rdProblemasPielSi, rdProblemasPielNo, rdFracturasSi, rdFracturasNo, rdDesmayosSi, rdDesmayosNo, rdItsSi, rdItsNo, rdOtroSi, rdOtroNo;
    EditText txtOtroProblemaSaludRecien, txtRespuesta;
    String otroProblemaSaludRecien, idEncuesta, respuesta;
    Integer dolorCabeza, digestivas, respiratorias, molestiasGenitales, cortes, problemasPiel, fracturas, desmayos, its, otro;
    Integer dolorCabezaFrec, digestivasFrec, respiratoriasFrec, molestiasGenitalesFrec, cortesFrec, problemasPielFrec, fracturasFrec, desmayosFrec, itsFrec, otroFrec;
    Spinner spindolorCabeza, spindigestivas, spinrespiratorias, spinmolestiasGenitales, spincortes, spinproblemasPiel, spinfracturas, spindesmayos, spinits, spinotro;
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
    private OnFragmentInteractionListener mListener;

    public ssc_probleas_salud_recien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssc_probleas_salud_recien.
     */
    // TODO: Rename and change types and number of parameters
    public static ssc_probleas_salud_recien newInstance(String param1, String param2) {
        ssc_probleas_salud_recien fragment = new ssc_probleas_salud_recien();
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
        vista=inflater.inflate(R.layout.fragment_ssc_probleas_salud_recien, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente17);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras17);
        idFragment= (TextView) vista.findViewById(R.id.idProblemaSaludRecien);

        //Radio Butons
        rdDolorCabezaSi=(RadioButton) vista.findViewById(R.id.dolorCabezaSi);
        rdDolorCabezaNo=(RadioButton) vista.findViewById(R.id.dolorCabezaNo);
        rdDigestivasSi=(RadioButton) vista.findViewById(R.id.digestivasSi);
        rdDigestivasNo=(RadioButton) vista.findViewById(R.id.digestivasNo);
        rdRespiratoriasSi=(RadioButton) vista.findViewById(R.id.respiratoriaSi);
        rdRespiratoriasNo=(RadioButton) vista.findViewById(R.id.respiratoriaNo);
        rdMolestiasGenitalesSi=(RadioButton) vista.findViewById(R.id.molestiasGenitalesSi);
        rdMolestiasGenitalesNo=(RadioButton) vista.findViewById(R.id.molestiasGenitalesNo);
        rdCortesSi=(RadioButton) vista.findViewById(R.id.cortesSi);
        rdCortesNo=(RadioButton) vista.findViewById(R.id.cortesNo);
        rdProblemasPielSi=(RadioButton) vista.findViewById(R.id.problemasPielSi);
        rdProblemasPielNo=(RadioButton) vista.findViewById(R.id.problemasPielNo);
        rdFracturasSi=(RadioButton) vista.findViewById(R.id.fracturaSi);
        rdFracturasNo=(RadioButton) vista.findViewById(R.id.fracturaNo);
        rdDesmayosSi=(RadioButton) vista.findViewById(R.id.desmayoSi);
        rdDesmayosNo=(RadioButton) vista.findViewById(R.id.desmayoNo);
        rdItsSi=(RadioButton) vista.findViewById(R.id.itsSi);
        rdItsNo=(RadioButton) vista.findViewById(R.id.itsNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroProblemaSaludRecienSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroProblemaSaludRecienNo);



        //spinners
        spindolorCabeza=(Spinner) vista.findViewById(R.id.frecuenciaDolorCabeza);
        spindigestivas=(Spinner) vista.findViewById(R.id.frecuenciaDigestivas);
        spinrespiratorias=(Spinner) vista.findViewById(R.id.frecuenciaRespiratoria);
        spinmolestiasGenitales=(Spinner) vista.findViewById(R.id.frecuenciaMolestiasGenitales);
        spincortes=(Spinner) vista.findViewById(R.id.frecuenciaCortes);
        spinproblemasPiel=(Spinner) vista.findViewById(R.id.frecuenciaproblemasPiel);
        spinfracturas=(Spinner) vista.findViewById(R.id.frecuenciaFractura);
        spindesmayos=(Spinner) vista.findViewById(R.id.frecuenciaDesmayo);
        spinits=(Spinner) vista.findViewById(R.id.frecuenciaIts);
        spinotro=(Spinner) vista.findViewById(R.id.frecuenciaotroProblemaSaludRecien);

        //TextViews
        txtOtroProblemaSaludRecien=(EditText) vista.findViewById(R.id.txtOtroProblemasSaludRecien);
        txtRespuesta=(EditText) vista.findViewById(R.id.txtrespuestaProblemasSaludRecien);

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

    private void actualizar(String pantalla) {
        String url="http://192.168.0.13/encuestasWS/actualizaProblemaSaludRecien.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta19(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta17(idFragment.getText().toString());

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
                String dolorCabeza="0";
                if(rdDolorCabezaSi.isChecked()){
                    dolorCabeza="1";
                }else if(rdDolorCabezaNo.isChecked()){
                    dolorCabeza="2";
                }

                String digestivas="0";
                if(rdDigestivasSi.isChecked()){
                    digestivas="1";
                }else if(rdDigestivasNo.isChecked()){
                    digestivas="2";
                }

                String respiratorias="0";
                if(rdRespiratoriasSi.isChecked()){
                    respiratorias="1";
                }else if(rdRespiratoriasNo.isChecked()){
                    respiratorias="2";
                }

                String molestiasGenitales="0";
                if(rdMolestiasGenitalesSi.isChecked()){
                    molestiasGenitales="1";
                }else if(rdMolestiasGenitalesNo.isChecked()){
                    molestiasGenitales="2";
                }


                String cortes="0";
                if(rdCortesSi.isChecked()){
                    cortes="1";
                }else if(rdCortesNo.isChecked()){
                    cortes="2";
                }

                String problemaPiel="0";
                if(rdProblemasPielSi.isChecked()){
                    problemaPiel="1";
                }else if(rdProblemasPielNo.isChecked()){
                    problemaPiel="2";
                }

                String fractura="0";
                if(rdFracturasSi.isChecked()){
                    fractura="1";
                }else if(rdFracturasNo.isChecked()){
                    fractura="2";
                }

                String desmayo="0";
                if(rdDesmayosSi.isChecked()){
                    desmayo="1";
                }else if(rdDesmayosNo.isChecked()){
                    desmayo="2";
                }

                String its="0";
                if(rdItsSi.isChecked()){
                    its="1";
                }else if(rdItsNo.isChecked()){
                    its="2";
                }

                String otro="0";
                if(rdOtroSi.isChecked()){
                    otro="1";
                }else if(rdOtroNo.isChecked()){
                    otro="2";
                }

                String frecDolorCabeza="0";
                if(spindolorCabeza.getSelectedItem().toString()=="A diario"){
                    frecDolorCabeza="1";
                }else if(spindolorCabeza.getSelectedItem().toString()=="Una vez a la semana"){
                    frecDolorCabeza="2";
                }else if(spindolorCabeza.getSelectedItem().toString()=="2 veces al mes"){
                    frecDolorCabeza="3";
                }else if(spindolorCabeza.getSelectedItem().toString()=="Rara vez"){
                    frecDolorCabeza="4";
                }


                String frecDigestiva="0";
                if(spindigestivas.getSelectedItem().toString()=="A diario"){
                    frecDigestiva="1";
                }else if(spindigestivas.getSelectedItem().toString()=="Una vez a la semana"){
                    frecDigestiva="2";
                }else if(spindigestivas.getSelectedItem().toString()=="2 veces al mes"){
                    frecDigestiva="3";
                }else if(spindigestivas.getSelectedItem().toString()=="Rara vez"){
                    frecDigestiva="4";
                }


                String frecRespiratorias="0";
                if(spinrespiratorias.getSelectedItem().toString()=="A diario"){
                    frecRespiratorias="1";
                }else if(spinrespiratorias.getSelectedItem().toString()=="Una vez a la semana"){
                    frecRespiratorias="2";
                }else if(spinrespiratorias.getSelectedItem().toString()=="2 veces al mes"){
                    frecRespiratorias="3";
                }else if(spinrespiratorias.getSelectedItem().toString()=="Rara vez"){
                    frecRespiratorias="4";
                }

                String frecMolestiasGenitales="0";
                if(spinmolestiasGenitales.getSelectedItem().toString()=="A diario"){
                    frecMolestiasGenitales="1";
                }else if(spinmolestiasGenitales.getSelectedItem().toString()=="Una vez a la semana"){
                    frecMolestiasGenitales="2";
                }else if(spinmolestiasGenitales.getSelectedItem().toString()=="2 veces al mes"){
                    frecMolestiasGenitales="3";
                }else if(spinmolestiasGenitales.getSelectedItem().toString()=="Rara vez"){
                    frecMolestiasGenitales="4";
                }


                String frecCortes="0";
                if(spincortes.getSelectedItem().toString()=="A diario"){
                    frecCortes="1";
                }else if(spincortes.getSelectedItem().toString()=="Una vez a la semana"){
                    frecCortes="2";
                }else if(spincortes.getSelectedItem().toString()=="2 veces al mes"){
                    frecCortes="3";
                }else if(spincortes.getSelectedItem().toString()=="Rara vez"){
                    frecCortes="4";
                }

                String frecProblemasPiel="0";
                if(spinproblemasPiel.getSelectedItem().toString()=="A diario"){
                    frecProblemasPiel="1";
                }else if(spinproblemasPiel.getSelectedItem().toString()=="Una vez a la semana"){
                    frecProblemasPiel="2";
                }else if(spinproblemasPiel.getSelectedItem().toString()=="2 veces al mes"){
                    frecProblemasPiel="3";
                }else if(spinproblemasPiel.getSelectedItem().toString()=="Rara vez"){
                    frecProblemasPiel="4";
                }

                String frecFracturas="0";
                if(spinfracturas.getSelectedItem().toString()=="A diario"){
                    frecFracturas="1";
                }else if(spinfracturas.getSelectedItem().toString()=="Una vez a la semana"){
                    frecFracturas="2";
                }else if(spinfracturas.getSelectedItem().toString()=="2 veces al mes"){
                    frecFracturas="3";
                }else if(spinfracturas.getSelectedItem().toString()=="Rara vez"){
                    frecFracturas="4";
                }


                String frecDesmayos="0";
                if(spindesmayos.getSelectedItem().toString()=="A diario"){
                    frecDesmayos="1";
                }else if(spindesmayos.getSelectedItem().toString()=="Una vez a la semana"){
                    frecDesmayos="2";
                }else if(spindesmayos.getSelectedItem().toString()=="2 veces al mes"){
                    frecDesmayos="3";
                }else if(spindesmayos.getSelectedItem().toString()=="Rara vez"){
                    frecDesmayos="4";
                }

                String frecIts="0";
                if(spinits.getSelectedItem().toString()=="A diario"){
                    frecIts="1";
                }else if(spinits.getSelectedItem().toString()=="Una vez a la semana"){
                    frecIts="2";
                }else if(spinits.getSelectedItem().toString()=="2 veces al mes"){
                    frecIts="3";
                }else if(spinits.getSelectedItem().toString()=="Rara vez"){
                    frecIts="4";
                }

                String frecOtro="0";
                if(spinotro.getSelectedItem().toString()=="A diario"){
                    frecOtro="1";
                }else if(spinotro.getSelectedItem().toString()=="Una vez a la semana"){
                    frecOtro="2";
                }else if(spinotro.getSelectedItem().toString()=="2 veces al mes"){
                    frecOtro="3";
                }else if(spinotro.getSelectedItem().toString()=="Rara vez"){
                    frecOtro="4";
                }

                String otroProblemaSalud=txtOtroProblemaSaludRecien.getText().toString();
                String respuesta=txtRespuesta.getText().toString();




                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("dolorCabeza",dolorCabeza);
                parametros.put("digestivas",digestivas);
                parametros.put("respiratorias",respiratorias);
                parametros.put("molestiasGenitales",molestiasGenitales);
                parametros.put("cortes",cortes);
                parametros.put("problemaPiel",problemaPiel);
                parametros.put("fractura",fractura);
                parametros.put("desmayo",desmayo);
                parametros.put("its",its);
                parametros.put("otro",otro);
                parametros.put("frecDolorCabeza",frecDolorCabeza);
                parametros.put("frecDigestiva",frecDigestiva);
                parametros.put("frecRespiratorias",frecRespiratorias);
                parametros.put("frecMolestiasGenitales",frecMolestiasGenitales);
                parametros.put("frecCortes",frecCortes);
                parametros.put("frecProblemasPiel",frecProblemasPiel);
                parametros.put("frecFracturas",frecFracturas);
                parametros.put("frecDesmayos",frecDesmayos);
                parametros.put("frecIts",frecIts);
                parametros.put("frecOtro",frecOtro);

                parametros.put("otroProblemaSalud",otroProblemaSalud);
                parametros.put("respuesta",respuesta);


                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                dolorCabeza = jsonObject.optInt("dolor_cabeza");
                dolorCabezaFrec = jsonObject.optInt("dolor_cabeza_frecuencia");
                digestivas = jsonObject.optInt("digestivas");
                digestivasFrec = jsonObject.optInt("digestivas_frecuencia");
                respiratorias = jsonObject.optInt("respiratorias");
                respiratoriasFrec = jsonObject.optInt("respiratorias_frecuencia");
                molestiasGenitales = jsonObject.optInt("molestias_genitales");
                molestiasGenitalesFrec = jsonObject.optInt("molestias_genitales_frecuencia");
                cortes = jsonObject.optInt("cortes");
                cortesFrec = jsonObject.optInt("cortes_frecuencia");
                problemasPiel = jsonObject.optInt("problemas_piel");
                problemasPielFrec = jsonObject.optInt("problemas_piel_frecuencia");
                fracturas = jsonObject.optInt("fracturas");
                fracturasFrec = jsonObject.optInt("fracturas_frecuencia");
                desmayos = jsonObject.optInt("desmayos");
                desmayosFrec = jsonObject.optInt("desmayos_frecuencia");
                its = jsonObject.optInt("its");
                itsFrec = jsonObject.optInt("its_frecuencia");
                otro = jsonObject.optInt("problema_salud_otro");
                otroFrec = jsonObject.optInt("problema_salud_otro_frecuencia");

                otroProblemaSaludRecien = jsonObject.optString("problema_salud_otro_nombre");
                respuesta = jsonObject.optString("respuesta_salud_recien");
                //LLenar spinner frecuencia dolor de cabeza
                String frecuenciaDC;
                if(dolorCabezaFrec==1){
                    frecuenciaDC="A diario";
                }else if(dolorCabezaFrec==2){
                    frecuenciaDC="Una vez a la semana";
                }else if(dolorCabezaFrec==3){
                    frecuenciaDC="2 veces al mes";
                }else if(dolorCabezaFrec==4){
                    frecuenciaDC="Rara vez";
                }else{
                    frecuenciaDC="";
                }

                ArrayList<String> frecDolorCabeza= new ArrayList<String>();
                frecDolorCabeza.add(frecuenciaDC);
                frecDolorCabeza.add("A diario");
                frecDolorCabeza.add("Una vez a la semana");
                frecDolorCabeza.add("2 veces al mes");
                frecDolorCabeza.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorDolorCabeza=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecDolorCabeza);
                spindolorCabeza.setAdapter(adaptadorDolorCabeza);


                //LLenar spinner frecuencia digestivas
                String frecuenciaD;
                if(digestivasFrec==1){
                    frecuenciaD="A diario";
                }else if(digestivasFrec==2){
                    frecuenciaD="Una vez a la semana";
                }else if(digestivasFrec==3){
                    frecuenciaD="2 veces al mes";
                }else if(digestivasFrec==4){
                    frecuenciaD="Rara vez";
                }else{
                    frecuenciaD="";
                }

                ArrayList<String> frecDigestivas= new ArrayList<String>();
                frecDigestivas.add(frecuenciaD);
                frecDigestivas.add("A diario");
                frecDigestivas.add("Una vez a la semana");
                frecDigestivas.add("2 veces al mes");
                frecDigestivas.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorDigestivas=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecDigestivas);
                spindigestivas.setAdapter(adaptadorDigestivas);


                //LLenar spinner frecuencia respiratorias
                String frecuenciaR;
                if(respiratoriasFrec==1){
                    frecuenciaR="A diario";
                }else if(respiratoriasFrec==2){
                    frecuenciaR="Una vez a la semana";
                }else if(respiratoriasFrec==3){
                    frecuenciaR="2 veces al mes";
                }else if(respiratoriasFrec==4){
                    frecuenciaR="Rara vez";
                }else{
                    frecuenciaR="";
                }

                ArrayList<String> frecRespiratorias= new ArrayList<String>();
                frecRespiratorias.add(frecuenciaR);
                frecRespiratorias.add("A diario");
                frecRespiratorias.add("Una vez a la semana");
                frecRespiratorias.add("2 veces al mes");
                frecRespiratorias.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorRespiratorias=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecRespiratorias);
                spinrespiratorias.setAdapter(adaptadorRespiratorias);


                //LLenar spinner frecuencia molestias genitales
                String frecuenciaMG;
                if(molestiasGenitalesFrec==1){
                    frecuenciaMG="A diario";
                }else if(molestiasGenitalesFrec==2){
                    frecuenciaMG="Una vez a la semana";
                }else if(molestiasGenitalesFrec==3){
                    frecuenciaMG="2 veces al mes";
                }else if(molestiasGenitalesFrec==4){
                    frecuenciaMG="Rara vez";
                }else{
                    frecuenciaMG="";
                }

                ArrayList<String> frecMolestiasGenitales= new ArrayList<String>();
                frecMolestiasGenitales.add(frecuenciaMG);
                frecMolestiasGenitales.add("A diario");
                frecMolestiasGenitales.add("Una vez a la semana");
                frecMolestiasGenitales.add("2 veces al mes");
                frecMolestiasGenitales.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorMolestiasGenitales=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecMolestiasGenitales);
                spinmolestiasGenitales.setAdapter(adaptadorMolestiasGenitales);


                //LLenar spinner frecuencia cortes
                String frecuenciaC;
                if(cortesFrec==1){
                    frecuenciaC="A diario";
                }else if(cortesFrec==2){
                    frecuenciaC="Una vez a la semana";
                }else if(cortesFrec==3){
                    frecuenciaC="2 veces al mes";
                }else if(cortesFrec==4){
                    frecuenciaC="Rara vez";
                }else{
                    frecuenciaC="";
                }

                ArrayList<String> frecCortes= new ArrayList<String>();
                frecCortes.add(frecuenciaC);
                frecCortes.add("A diario");
                frecCortes.add("Una vez a la semana");
                frecCortes.add("2 veces al mes");
                frecCortes.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorCortes=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecCortes);
                spincortes.setAdapter(adaptadorCortes);


                //LLenar spinner frecuencia problemas piel
                String frecuenciaPP;
                if(problemasPielFrec==1){
                    frecuenciaPP="A diario";
                }else if(problemasPielFrec==2){
                    frecuenciaPP="Una vez a la semana";
                }else if(problemasPielFrec==3){
                    frecuenciaPP="2 veces al mes";
                }else if(problemasPielFrec==4){
                    frecuenciaPP="Rara vez";
                }else{
                    frecuenciaPP="";
                }

                ArrayList<String> frecProblemasPiel= new ArrayList<String>();
                frecProblemasPiel.add(frecuenciaPP);
                frecProblemasPiel.add("A diario");
                frecProblemasPiel.add("Una vez a la semana");
                frecProblemasPiel.add("2 veces al mes");
                frecProblemasPiel.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorProblemasPiel=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecProblemasPiel);
                spinproblemasPiel.setAdapter(adaptadorProblemasPiel);

                //LLenar spinner frecuencia fracturas
                String frecuenciaF;
                if(fracturasFrec==1){
                    frecuenciaF="A diario";
                }else if(fracturasFrec==2){
                    frecuenciaF="Una vez a la semana";
                }else if(fracturasFrec==3){
                    frecuenciaF="2 veces al mes";
                }else if(fracturasFrec==4){
                    frecuenciaF="Rara vez";
                }else{
                    frecuenciaF="";
                }

                ArrayList<String> frecFracturas= new ArrayList<String>();
                frecFracturas.add(frecuenciaF);
                frecFracturas.add("A diario");
                frecFracturas.add("Una vez a la semana");
                frecFracturas.add("2 veces al mes");
                frecFracturas.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorFracturas=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecProblemasPiel);
                spinfracturas.setAdapter(adaptadorFracturas);


                //LLenar spinner frecuencia desmayos
                String frecuenciaDE;
                if(desmayosFrec==1){
                    frecuenciaDE="A diario";
                }else if(desmayosFrec==2){
                    frecuenciaDE="Una vez a la semana";
                }else if(desmayosFrec==3){
                    frecuenciaDE="2 veces al mes";
                }else if(desmayosFrec==4){
                    frecuenciaDE="Rara vez";
                }else{
                    frecuenciaDE="";
                }

                ArrayList<String> frecDesmayos= new ArrayList<String>();
                frecDesmayos.add(frecuenciaDE);
                frecDesmayos.add("A diario");
                frecDesmayos.add("Una vez a la semana");
                frecDesmayos.add("2 veces al mes");
                frecDesmayos.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorDesmayos=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecDesmayos);
                spindesmayos.setAdapter(adaptadorDesmayos);


                //LLenar spinner frecuencia its
                String frecuenciaI;
                if(itsFrec==1){
                    frecuenciaI="A diario";
                }else if(itsFrec==2){
                    frecuenciaI="Una vez a la semana";
                }else if(itsFrec==3){
                    frecuenciaI="2 veces al mes";
                }else if(itsFrec==4){
                    frecuenciaI="Rara vez";
                }else{
                    frecuenciaI="";
                }

                ArrayList<String> frecIts= new ArrayList<String>();
                frecIts.add(frecuenciaI);
                frecIts.add("A diario");
                frecIts.add("Una vez a la semana");
                frecIts.add("2 veces al mes");
                frecIts.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorIts=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecIts);
                spinits.setAdapter(adaptadorIts);


                //LLenar spinner frecuencia otro
                String frecuenciaO;
                if(otroFrec==1){
                    frecuenciaO="A diario";
                }else if(otroFrec==2){
                    frecuenciaO="Una vez a la semana";
                }else if(otroFrec==3){
                    frecuenciaO="2 veces al mes";
                }else if(otroFrec==4){
                    frecuenciaO="Rara vez";
                }else{
                    frecuenciaO="";
                }

                ArrayList<String> frecOtro= new ArrayList<String>();
                frecOtro.add(frecuenciaO);
                frecOtro.add("A diario");
                frecOtro.add("Una vez a la semana");
                frecOtro.add("2 veces al mes");
                frecOtro.add("Rara vez");

                ArrayAdapter<CharSequence> adaptadorOtro=new ArrayAdapter
                        (this.getActivity(),android.R.layout.simple_spinner_item,frecOtro);
                spinotro.setAdapter(adaptadorOtro);


                if (dolorCabeza == 1) {
                    rdDolorCabezaSi.setChecked(true);
                } else if (dolorCabeza == 2) {
                    rdDolorCabezaNo.setChecked(true);
                }

                if (digestivas == 1) {
                    rdDigestivasSi.setChecked(true);
                } else if (digestivas == 2) {
                    rdDigestivasNo.setChecked(true);
                }

                if (respiratorias == 1) {
                    rdRespiratoriasSi.setChecked(true);
                } else if (respiratorias == 2) {
                    rdRespiratoriasNo.setChecked(true);
                }

                if (molestiasGenitales == 1) {
                    rdMolestiasGenitalesSi.setChecked(true);
                } else if (molestiasGenitales == 2) {
                    rdMolestiasGenitalesNo.setChecked(true);
                }

                if (cortes == 1) {
                    rdCortesSi.setChecked(true);
                } else if (cortes == 2) {
                    rdCortesNo.setChecked(true);
                }

                if (problemasPiel == 1) {
                    rdProblemasPielSi.setChecked(true);
                } else if (problemasPiel == 2) {
                    rdProblemasPielNo.setChecked(true);
                }

                if (fracturas == 1) {
                    rdFracturasSi.setChecked(true);
                } else if (fracturas == 2) {
                    rdFracturasNo.setChecked(true);
                }


                if (desmayos == 1) {
                    rdDesmayosSi.setChecked(true);
                } else if (desmayos == 2) {
                    rdDesmayosNo.setChecked(true);
                }


                if (its == 1) {
                    rdItsSi.setChecked(true);
                } else if (its == 2) {
                    rdItsNo.setChecked(true);
                }


                if (otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (otro == 2) {
                    rdOtroNo.setChecked(true);
                }

                txtOtroProblemaSaludRecien.setText(otroProblemaSaludRecien.toString());
                txtRespuesta.setText(respuesta.toString());

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
