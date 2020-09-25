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
 * {@link emp_lab_11_0_14.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_14#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_14 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_14() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_14.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;
    TextView idFragment;
    EditText txtnoTrabajo, txtcargo, txtpuesto;
    String  idEncuesta, noTrabajo, cargo, puesto;
    Integer trabajas, tiempoTrabajo;
    RadioButton rd13meses, rd36meses, rd6meses, rd1ano, rd3ano, rdSi,rdNo;
    LinearLayout display1114, display1115, display1116, display1117;

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
    public static emp_lab_11_0_14 newInstance(String param1, String param2) {
        emp_lab_11_0_14 fragment = new emp_lab_11_0_14();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_14, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente61);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras61);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1114);

        txtnoTrabajo=(EditText) vista.findViewById(R.id.txt1115);
        txtcargo=(EditText) vista.findViewById(R.id.txtCargo);
        txtpuesto=(EditText) vista.findViewById(R.id.txtPuesto);


        rdSi=(RadioButton) vista.findViewById(R.id.SiTrabajoActualmente);
        rdNo=(RadioButton) vista.findViewById(R.id.NoTrabajoActualmente);

        rd13meses=(RadioButton) vista.findViewById(R.id.RdMeses13);
        rd36meses=(RadioButton) vista.findViewById(R.id.RdMeses36);
        rd6meses=(RadioButton) vista.findViewById(R.id.RdMeses6AUnAno);
        rd1ano=(RadioButton) vista.findViewById(R.id.RdAnos13);
        rd3ano=(RadioButton) vista.findViewById(R.id.RdAnosMasDe3);

        display1114=(LinearLayout) vista.findViewById(R.id.layout1114);
        display1115=(LinearLayout) vista.findViewById(R.id.layout1115);
        display1116=(LinearLayout) vista.findViewById(R.id.layout1116);
        display1117=(LinearLayout) vista.findViewById(R.id.layout1117);

        conn=new ConexionSQLiteHelper(vista.getContext(), "encuestas", null, 2);

        rdSi.setOnClickListener(v -> {

            display1115.setVisibility(View.INVISIBLE);
            display1115.setVisibility(View.GONE);

            display1116.setVisibility(View.VISIBLE);
            display1117.setVisibility(View.VISIBLE);

        });

        rdNo.setOnClickListener(v -> {

            display1116.setVisibility(View.INVISIBLE);
            display1116.setVisibility(View.GONE);
            display1117.setVisibility(View.INVISIBLE);
            display1117.setVisibility(View.GONE);
            display1115.setVisibility(View.VISIBLE);

        });

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
            if(rdNo.isChecked()){
                interfaceComunicaFragments.enviarEncuesta70(idFragment.getText().toString());
            }else{
                interfaceComunicaFragments.enviarEncuesta65(idFragment.getText().toString());
            }
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta63(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {


        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "razon_no_trabajar"
                ,"cargo_trabajo"
                ,"lugar_trabajo"
                ,"trabajas_actualmente"
                ,"tiempo_trabajo"
        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        noTrabajo = cursor.getString(0);
        cargo = cursor.getString(1);
        puesto = cursor.getString(2);


        trabajas = Integer.parseInt( cursor.getString(3));
        tiempoTrabajo = Integer.parseInt( cursor.getString(4));


        txtnoTrabajo.setText(noTrabajo.toString());
        txtcargo.setText(cargo.toString());
        txtpuesto.setText(puesto.toString());

        if(trabajas==1){
            display1115.setVisibility(View.INVISIBLE);
            display1115.setVisibility(View.GONE);
        }else if(trabajas==2){
            display1116.setVisibility(View.INVISIBLE);
            display1116.setVisibility(View.GONE);
            display1117.setVisibility(View.INVISIBLE);
            display1117.setVisibility(View.GONE);
        }
        if(trabajas==1){
            rdSi.setChecked(true);
        }else if(trabajas==2){
            rdNo.setChecked(true);
        }

        if(tiempoTrabajo==1){
            rd13meses.setChecked(true);
        }else if(tiempoTrabajo==2){
            rd36meses.setChecked(true);
        }else if(tiempoTrabajo==3){
            rd6meses.setChecked(true);
        }else if(tiempoTrabajo==4){
            rd1ano.setChecked(true);
        }else if(tiempoTrabajo==5){
            rd3ano.setChecked(true);
        }

        cursor.close();

    }

    private void actualizar(String pantalla) {

        try {

            String noTrabajas=txtnoTrabajo.getText().toString();
            String cargo=txtcargo.getText().toString();
            String puesto=txtpuesto.getText().toString();


            String trabajas="0";
            if(rdSi.isChecked()){
                trabajas="1";
            }else if(rdNo.isChecked()){

                trabajas="2";
            }

            String tiempoTrabajo="0";
            if(rd13meses.isChecked()){
                tiempoTrabajo="1";
            }else if(rd36meses.isChecked()){

                tiempoTrabajo="2";
            }else if(rd6meses.isChecked()){

                tiempoTrabajo="3";
            }else if(rd1ano.isChecked()){

                tiempoTrabajo="4";
            }else if(rd3ano.isChecked()){

                tiempoTrabajo="5";
            }


            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("razon_no_trabajar", noTrabajas);
            values.put("cargo_trabajo", cargo);
            values.put("lugar_trabajo", puesto);
            values.put("trabajas_actualmente", trabajas);
            values.put("tiempo_trabajo", tiempoTrabajo);
            //   values.put("no_desarrollo_plan_vida", noproyecto);

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
