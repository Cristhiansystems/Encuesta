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
 * {@link preg_eval_13_0_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link preg_eval_13_0_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class preg_eval_13_0_1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public preg_eval_13_0_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment preg_eval_13_0_1.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    String idEncuesta, ingresoMensual, salario, ingresoMensualActual;
    EditText txtIngresoMensual, txtSalario, txtingresoMensualActual;
    Integer ingresoMejoro, trabajado, programaAyuda;
    RadioButton rdIngresoMejoroSi, rdIngresoMejoroNo, rdTrabajadiSi, rdTrabajadoNo, rdprogramaAyudaSi, rdProgramaAyudaNo;

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
    public static preg_eval_13_0_1 newInstance(String param1, String param2) {
        preg_eval_13_0_1 fragment = new preg_eval_13_0_1();
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
        vista= inflater.inflate(R.layout.fragment_preg_eval_13_0_1, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente72);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras72);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1301);


        txtIngresoMensual=(EditText) vista.findViewById(R.id.txtResp1301);
        txtSalario=(EditText) vista.findViewById(R.id.txtResp1322);
        txtingresoMensualActual=(EditText) vista.findViewById(R.id.txtResp1323);

        rdIngresoMejoroSi=(RadioButton) vista.findViewById(R.id.RdSiMejoroMisIngresos);
        rdIngresoMejoroNo=(RadioButton) vista.findViewById(R.id.RdNoMejoroMisIngresos);
        rdTrabajadiSi=(RadioButton) vista.findViewById(R.id.RdSiTrabaje);
        rdTrabajadoNo=(RadioButton) vista.findViewById(R.id.RdNoTrabaje);
        rdprogramaAyudaSi=(RadioButton) vista.findViewById(R.id.RdSiMeAyudoTenerEmpleo);
        rdProgramaAyudaNo=(RadioButton) vista.findViewById(R.id.RdNoMeAyudoTenerEmpleo);

        conn=new ConexionSQLiteHelper(vista.getContext(), "encuestas", null, 2);


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
            interfaceComunicaFragments.enviarEncuesta76(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta74(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {


        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "ingreso_mensual_antes"

                ,"salario_antes"
                ,"ingreso_mensual_ahora"
                ,"ingreso_mejoro"
                ,"trabajo_3_meses"
                ,"programa_ayudo_empleo"

        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        ingresoMensual = cursor.getString(0);
        salario = cursor.getString(1);
        ingresoMensualActual = cursor.getString(2);

        ingresoMejoro = Integer.parseInt( cursor.getString(3));
        trabajado = Integer.parseInt( cursor.getString(4));
        programaAyuda = Integer.parseInt( cursor.getString(5));


        if(ingresoMejoro==1){
            rdIngresoMejoroSi.setChecked(true);
        }else if(ingresoMejoro==2){
            rdIngresoMejoroNo.setChecked(true);
        }


        if(trabajado==1){
            rdTrabajadiSi.setChecked(true);
        }else if(trabajado==2){
            rdTrabajadoNo.setChecked(true);
        }


        if(programaAyuda==1){
            rdprogramaAyudaSi.setChecked(true);
        }else if(programaAyuda==2){
            rdProgramaAyudaNo.setChecked(true);
        }

        txtIngresoMensual.setText(ingresoMensual.toString());
        txtingresoMensualActual.setText(ingresoMensualActual.toString());
        txtSalario.setText(salario.toString());






        cursor.close();









    }

    private void actualizar(String pantalla) {

        try {

            String ingresoMensual= txtIngresoMensual.getText().toString();
            String ingresoMensualActual= txtingresoMensualActual.getText().toString();
            String salario= txtSalario.getText().toString();


            String ingresoMejoro="0";
            if(rdIngresoMejoroSi.isChecked()){
                ingresoMejoro="1";
            }else if(rdIngresoMejoroNo.isChecked()){
                ingresoMejoro="2";
            }


            String trabajado="0";
            if(rdTrabajadiSi.isChecked()){
                trabajado="1";
            }else if(rdTrabajadoNo.isChecked()){
                trabajado="2";
            }



            String programaAyuda="0";
            if(rdprogramaAyudaSi.isChecked()){
                programaAyuda="1";
            }else if(rdProgramaAyudaNo.isChecked()){
                programaAyuda="2";
            }



            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

           values.put("ingreso_mensual_antes", ingresoMensual);
            values.put("ingreso_mensual_ahora", ingresoMensualActual);
            values.put("salario_antes", salario);
            values.put("ingreso_mejoro", ingresoMejoro);
            values.put("trabajo_3_meses", trabajado);
            values.put("programa_ayudo_empleo", programaAyuda);

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
