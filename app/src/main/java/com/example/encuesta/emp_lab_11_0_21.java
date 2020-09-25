package com.example.encuesta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link emp_lab_11_0_21.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_21#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_21 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_21() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_21.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    EditText txtotro;
    String  idEncuesta, otronom;
    Integer colegio, universidad, instituto, hogar, cea, icap, fuente, otro, tiempoCapacitaste;
    RadioButton rd13meses, rd36meses, rd6meses, rd1ano, rd3ano, rdColegioSi,rdColegioNo, rdUniversidadSi, rdUniversidadNo, rdInstitutoSi, rdInstitutoNo, rdHogarSi, rdHogarNo, rdCeaSi, rdCeaNo, rdIcapSi, rdIcapNo, rdFuenteSi, rdFuenteNo, rdOtroSi, rdOtroNo;

    //Conexion Sqlite
    ConexionSQLiteHelper conn;

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
    public static emp_lab_11_0_21 newInstance(String param1, String param2) {
        emp_lab_11_0_21 fragment = new emp_lab_11_0_21();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_21, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente63);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras63);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1121);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1121);



        rdColegioSi=(RadioButton) vista.findViewById(R.id.SiColegio);
        rdColegioNo=(RadioButton) vista.findViewById(R.id.NoColegio);

        rdUniversidadSi=(RadioButton) vista.findViewById(R.id.SiUniversidad);
        rdUniversidadNo=(RadioButton) vista.findViewById(R.id.NoUniversidad);

        rdInstitutoSi=(RadioButton) vista.findViewById(R.id.SiInstitutoTécnicos);
        rdInstitutoNo=(RadioButton) vista.findViewById(R.id.NoInstitutoTécnicos);

        rdHogarSi=(RadioButton) vista.findViewById(R.id.SiHogarAcogida);
        rdHogarNo=(RadioButton) vista.findViewById(R.id.NoHogarAcogida);

        rdCeaSi=(RadioButton) vista.findViewById(R.id.SiCEA);
        rdCeaNo=(RadioButton) vista.findViewById(R.id.NoCEA);

        rdIcapSi=(RadioButton) vista.findViewById(R.id.SiICAP);
        rdIcapNo=(RadioButton) vista.findViewById(R.id.NoICAP);

        rdFuenteSi=(RadioButton) vista.findViewById(R.id.SiFuenteLaboral);
        rdFuenteNo=(RadioButton) vista.findViewById(R.id.NoFuenteLaboral);

        rdOtroSi=(RadioButton) vista.findViewById(R.id.OtroSi1121);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.OtroNo1121);

        rd13meses=(RadioButton) vista.findViewById(R.id.RdMeses131122);
        rd36meses=(RadioButton) vista.findViewById(R.id.RdMeses361122);
        rd6meses=(RadioButton) vista.findViewById(R.id.RdMeses6AUnAno1122);
        rd1ano=(RadioButton) vista.findViewById(R.id.RdAnos131122);
        rd3ano=(RadioButton) vista.findViewById(R.id.RdAnosMasDe31122);

        conn=new ConexionSQLiteHelper(vista.getContext(), "encuestas", null, 2);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));


        }

        //Aqui empieza el volley
       // request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            String pantalla="Siguiente";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta67(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta65(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {

        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "capacitacion_otro_nombre"

                ,"capacitacion_colegio"
                ,"capacitacion_universidad"
                ,"capacitacion_instituto"
                ,"capacitacion_acogida"
                ,"capacitacion_cea"
                ,"capacitacion_icap"
                ,"capacitacion_fuente_laboral"
                ,"capacitacion_otro"
                ,"tiempo_capacitacion"
        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        otronom = cursor.getString(0);

        colegio = Integer.parseInt( cursor.getString(1));
        universidad = Integer.parseInt( cursor.getString(2));
        instituto = Integer.parseInt( cursor.getString(3));
        hogar = Integer.parseInt( cursor.getString(4));
        cea = Integer.parseInt( cursor.getString(5));
        icap = Integer.parseInt( cursor.getString(6));
        fuente =Integer.parseInt( cursor.getString(7));
        otro = Integer.parseInt( cursor.getString(8));
        tiempoCapacitaste = Integer.parseInt( cursor.getString(9));


        txtotro.setText(otronom.toString());


        if(colegio==1){
            rdColegioSi.setChecked(true);
        }else if(colegio==2){
            rdColegioNo.setChecked(true);
        }

        if(universidad==1){
            rdUniversidadSi.setChecked(true);
        }else if(universidad==2){
            rdUniversidadNo.setChecked(true);
        }

        if(hogar==1){
            rdHogarSi.setChecked(true);
        }else if(hogar==2){
            rdHogarNo.setChecked(true);
        }

        if(instituto==1){
            rdInstitutoSi.setChecked(true);
        }else if(instituto==2){
            rdInstitutoNo.setChecked(true);
        }

        if(cea==1){
            rdCeaSi.setChecked(true);
        }else if(cea==2){
            rdCeaNo.setChecked(true);
        }

        if(icap==1){
            rdIcapSi.setChecked(true);
        }else if(icap==2){
            rdIcapNo.setChecked(true);
        }

        if(fuente==1){
            rdFuenteSi.setChecked(true);
        }else if(fuente==2){
            rdFuenteNo.setChecked(true);
        }

        if(otro==1){
            rdOtroSi.setChecked(true);
        }else if(otro==2){
            rdOtroNo.setChecked(true);
        }

        if(tiempoCapacitaste==1){
            rd13meses.setChecked(true);
        }else if(tiempoCapacitaste==2){
            rd36meses.setChecked(true);
        }else if(tiempoCapacitaste==3){
            rd6meses.setChecked(true);
        }else if(tiempoCapacitaste==4){
            rd1ano.setChecked(true);
        }else if(tiempoCapacitaste==5){
            rd3ano.setChecked(true);
        }



        cursor.close();

    }

    private void actualizar(String pantalla) {


        try {

            String otronom=txtotro.getText().toString();


            String colegio="0";
            if(rdColegioSi.isChecked()){
                colegio="1";
            }else if(rdColegioNo.isChecked()){

                colegio="2";
            }

            String universidad="0";
            if(rdUniversidadSi.isChecked()){
                universidad="1";
            }else if(rdUniversidadNo.isChecked()){

                universidad="2";
            }

            String instituto="0";
            if(rdInstitutoSi.isChecked()){
                instituto="1";
            }else if(rdInstitutoNo.isChecked()){

                instituto="2";
            }

            String hogar="0";
            if(rdHogarSi.isChecked()){
                hogar="1";
            }else if(rdHogarNo.isChecked()){

                hogar="2";
            }

            String cea="0";
            if(rdCeaSi.isChecked()){
                cea="1";
            }else if(rdCeaNo.isChecked()){

                cea="2";
            }

            String icap="0";
            if(rdIcapSi.isChecked()){
                icap="1";
            }else if(rdIcapNo.isChecked()){

                icap="2";
            }

            String fuente="0";
            if(rdFuenteSi.isChecked()){
                fuente="1";
            }else if(rdFuenteNo.isChecked()){

                fuente="2";
            }

            String otro="0";
            if(rdOtroSi.isChecked()){
                otro="1";
            }else if(rdOtroNo.isChecked()){

                otro="2";
            }

            String tiempoCapacitaste="0";
            if(rd13meses.isChecked()){
                tiempoCapacitaste="1";
            }else if(rd36meses.isChecked()){

                tiempoCapacitaste="2";
            }else if(rd6meses.isChecked()){

                tiempoCapacitaste="3";
            }else if(rd1ano.isChecked()){

                tiempoCapacitaste="4";
            }else if(rd3ano.isChecked()){

                tiempoCapacitaste="5";
            }


            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("capacitacion_colegio", colegio);
            values.put("capacitacion_universidad", universidad);
            values.put("capacitacion_instituto", instituto);
            values.put("capacitacion_acogida", hogar);
            values.put("capacitacion_cea", cea);
            values.put("capacitacion_icap", icap);
            values.put("capacitacion_fuente_laboral", fuente);
            values.put("capacitacion_otro", otro);
            values.put("capacitacion_otro_nombre", otronom);
            values.put("tiempo_capacitacion", tiempoCapacitaste);

            db.update("encuesta_emt",values,"encuesta_emt=?",parametros);
            db.close();

        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }














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
