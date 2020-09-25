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
 * {@link emp_lab_11_0_27.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_27#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_27 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_27() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_27.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    EditText txtotro, txtsueldo1, txtsueldo2;
    String  idEncuesta, otronom, sueldo1, sueldo2;
    Integer horario;
    RadioButton rdTiempoCompleto, rdMedioTiempo, rdhoras, rdProducto, rdOtro;


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
    public static emp_lab_11_0_27 newInstance(String param1, String param2) {
        emp_lab_11_0_27 fragment = new emp_lab_11_0_27();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_27, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente65);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras65);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1127);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1127);
        txtsueldo1=(EditText) vista.findViewById(R.id.txtSueldo1);
        txtsueldo2=(EditText) vista.findViewById(R.id.txtSueldo2);

        rdTiempoCompleto=(RadioButton) vista.findViewById(R.id.RdTiempoCompleto);
        rdMedioTiempo=(RadioButton) vista.findViewById(R.id.RdMedioTiempo);
        rdhoras=(RadioButton) vista.findViewById(R.id.RdPorHoras);
        rdProducto=(RadioButton) vista.findViewById(R.id.RdPorProducto);
        rdOtro=(RadioButton) vista.findViewById(R.id.RdOtro1127);

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
            interfaceComunicaFragments.enviarEncuesta69(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta67(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {

        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "horario_trabajo"

                ,"horario_trabajo_otro_nombre"
                ,"monto1"
                ,"monto2"


        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        horario = Integer.parseInt( cursor.getString(0));

        otronom = cursor.getString(1);
        sueldo1 = cursor.getString(2);
        sueldo2 = cursor.getString(3);


        txtotro.setText(otronom.toString());
        txtsueldo1.setText(sueldo1.toString());
        txtsueldo2.setText(sueldo2.toString());



        if(horario==1){
            rdTiempoCompleto.setChecked(true);
        }else if(horario==2){
            rdMedioTiempo.setChecked(true);
        }else if(horario==3){
            rdhoras.setChecked(true);
        }else if(horario==4){
            rdProducto.setChecked(true);
        }else if(horario==88){
            rdOtro.setChecked(true);
        }

        cursor.close();


    }

    private void actualizar(String pantalla) {



        try {

            String otronom=txtotro.getText().toString();
            String sueldo1=txtsueldo1.getText().toString();
            String sueldo2=txtsueldo2.getText().toString();

            String horario="0";
            if(rdTiempoCompleto.isChecked()){
                horario="1";
            }else if(rdMedioTiempo.isChecked()){

                horario="2";
            }else if(rdhoras.isChecked()){

                horario="3";
            }else if(rdProducto.isChecked()){

                horario="4";
            }else if(rdOtro.isChecked()){

                horario="88";
            }




            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("monto1", sueldo1);
            values.put("monto2", sueldo2);
            values.put("horario_trabajo", horario);
            values.put("horario_trabajo_otro_nombre", otronom);

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
