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
 * {@link emp_lab_11_0_29.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_29#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_29 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_29() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_29.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtorEstudio, txtorRopa, txtorAlimentacion, txtorDiversion, txtorPagoServicios, txtorEquipos, txtorApoyoFamilia, txtorahorro, txtorotro;
    String otronom, idEncuesta, orEstudio, orRopa, orAlimentacion, orDiversion, orPagoServicios, orEquipos, orApoyoFamilia, orahorro, orotro;
    Integer satisfecho;
    RadioButton rdSi, rdNo;

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
    public static emp_lab_11_0_29 newInstance(String param1, String param2) {
        emp_lab_11_0_29 fragment = new emp_lab_11_0_29();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_29, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente66);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras66);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1129);

        txtotro=(EditText) vista.findViewById(R.id.txtRespOtro1129);


        txtorEstudio=(EditText) vista.findViewById(R.id.txtEstudios29);
        txtorRopa=(EditText) vista.findViewById(R.id.txtRopa29);
        txtorAlimentacion=(EditText) vista.findViewById(R.id.txtAlimentacion29);
        txtorDiversion=(EditText) vista.findViewById(R.id.txtDiversion29);
        txtorPagoServicios=(EditText) vista.findViewById(R.id.txtPagoDeServicios29);
        txtorEquipos=(EditText) vista.findViewById(R.id.txtEquiposCelulares29);
        txtorApoyoFamilia=(EditText) vista.findViewById(R.id.txtApoyoALaFamilia29);
        txtorahorro=(EditText) vista.findViewById(R.id.txtAhorro29);
        txtorotro=(EditText) vista.findViewById(R.id.txtOtro29);

        rdSi=(RadioButton) vista.findViewById(R.id.RdSiEstoySatisfechoConMiTrabajo);
        rdNo=(RadioButton) vista.findViewById(R.id.RdNoEstoySatisfechoConMiTrabajo);

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
            interfaceComunicaFragments.enviarEncuesta70(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta68(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {



        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "gastar_dinero_estudio_orden"

                ,"gastar_dinero_ropa_orden"
                ,"gastar_dinero_alimentacion_orden"
                ,"gastar_dinero_diversion_orden"
                ,"gastar_dinero_servicios_orden"
                ,"gastar_dinero_equipo_orden"
                ,"gastar_dinero_familia_orden"
                ,"gastar_dinero_ahorro_orden"
                ,"gastar_dinero_estudio_orden_otro"
                ,"gastar_dinero_estudio_orden_otro_nombre"
                ,"satisfecho_trabajo"


        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        orEstudio = cursor.getString(0);
        orRopa = cursor.getString(1);
        orAlimentacion = cursor.getString(2);
        orDiversion =cursor.getString(3);;
        orPagoServicios = cursor.getString(4);
        orEquipos =cursor.getString(5);
        orApoyoFamilia = cursor.getString(6);
        orahorro = cursor.getString(7);
        orotro = cursor.getString(8);
        otronom = cursor.getString(9);
        satisfecho = Integer.parseInt( cursor.getString(10));


        txtotro.setText(otronom.toString());
        txtorEstudio.setText(orEstudio.toString());
        txtorRopa.setText(orRopa.toString());
        txtorAlimentacion.setText(orAlimentacion.toString());
        txtorDiversion.setText(orDiversion.toString());
        txtorPagoServicios.setText(orPagoServicios.toString());
        txtorEquipos.setText(orEquipos.toString());
        txtorApoyoFamilia.setText(orApoyoFamilia.toString());
        txtorahorro.setText(orahorro.toString());
        txtorotro.setText(orotro.toString());

        if(satisfecho==1){
            rdSi.setChecked(true);
        }else if(satisfecho==2){
            rdNo.setChecked(true);
        }

        cursor.close();

    }

    private void actualizar(String pantalla) {


        try {

            String otronom=txtotro.getText().toString();
            String orEstudios=txtorEstudio.getText().toString();
            String orRopa=txtorRopa.getText().toString();
            String orAlimentacion=txtorAlimentacion.getText().toString();
            String orDiversion=txtorDiversion.getText().toString();
            String orPagoServicios=txtorPagoServicios.getText().toString();
            String orEquipos=txtorEquipos.getText().toString();
            String orApoyoFamilia=txtorApoyoFamilia.getText().toString();
            String orAhorro=txtorahorro.getText().toString();
            String orOtro=txtorotro.getText().toString();

            String satisfecho="0";

            if(rdSi.isChecked()){
                satisfecho="1";

            }else if(rdNo.isChecked()){
                satisfecho="2";
            }




            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("gastar_dinero_estudio_orden_otro_nombre", otronom);
            values.put("gastar_dinero_estudio_orden", orEstudios);
            values.put("gastar_dinero_ropa_orden", orRopa);
            values.put("gastar_dinero_alimentacion_orden", orAlimentacion);
            values.put("gastar_dinero_diversion_orden", orDiversion);
            values.put("gastar_dinero_servicios_orden", orPagoServicios);
            values.put("gastar_dinero_equipo_orden", orEquipos);
            values.put("gastar_dinero_familia_orden", orApoyoFamilia);
            values.put("gastar_dinero_ahorro_orden", orAhorro);
            values.put("gastar_dinero_estudio_orden_otro", orOtro);
            values.put("satisfecho_trabajo", satisfecho);

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
