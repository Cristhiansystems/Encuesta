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
 * {@link emp_lab_11_0_3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_3.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtSi, txtNo;
    String  idEncuesta, si, no;
    Integer desarrollar, trabajaste;
    RadioButton rdSi, rdNo;
    LinearLayout display1103, display1104, display1105;

    //Conexion Sqlite
    ConexionSQLiteHelper conn;


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
    // TODO: Rename and change types and number of parameters
    public static emp_lab_11_0_3 newInstance(String param1, String param2) {
        emp_lab_11_0_3 fragment = new emp_lab_11_0_3();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_3, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente58);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras58);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1103);

        txtSi=(EditText) vista.findViewById(R.id.txtResp1104);
        txtNo=(EditText) vista.findViewById(R.id.txtResp1105);

        rdSi=(RadioButton) vista.findViewById(R.id.SiMeGustariaUnaActividadLabarol);
        rdNo=(RadioButton) vista.findViewById(R.id.NoMeGustariaUnaActividadLabarol);
        display1103=(LinearLayout) vista.findViewById(R.id.layout1103);
        display1104=(LinearLayout) vista.findViewById(R.id.layout1104);
        display1105=(LinearLayout) vista.findViewById(R.id.layout1105);

        conn=new ConexionSQLiteHelper(vista.getContext(), "encuestas", null, 2);

        rdSi.setOnClickListener(v -> {

            display1105.setVisibility(View.INVISIBLE);
            display1105.setVisibility(View.GONE);
            display1104.setVisibility(View.VISIBLE);

        });

        rdNo.setOnClickListener(v -> {

            display1104.setVisibility(View.INVISIBLE);
            display1104.setVisibility(View.GONE);
            display1105.setVisibility(View.VISIBLE);

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
            interfaceComunicaFragments.enviarEncuesta62(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta60(idFragment.getText().toString());
        });
        return vista;
    }
    private void cargarWebServices() {


        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "gustaria_desarrollar_actividad"
                ,"si_gustaria_trabajar"
                ,"no_gustaria_trabajar"
                ,"alguna_vez_trabajaste"




        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        desarrollar = Integer.parseInt( cursor.getString(0));
        si = cursor.getString(1);
        no = cursor.getString(2);

        //otro fragment
        trabajaste =  Integer.parseInt( cursor.getString(3));

        txtSi.setText(si.toString());
        txtNo.setText(no.toString());


        if(desarrollar==1){
            display1105.setVisibility(View.INVISIBLE);
            display1105.setVisibility(View.GONE);
        }else if(desarrollar==2){
            display1104.setVisibility(View.INVISIBLE);
            display1104.setVisibility(View.GONE);
        }

        if(trabajaste==1){
            display1103.setVisibility(View.INVISIBLE);
            display1103.setVisibility(View.GONE);
        }


        if(desarrollar==1){
            rdSi.setChecked(true);
        }else if(desarrollar==2){
            rdNo.setChecked(true);
        }

        cursor.close();

    }

    private void actualizar(String pantalla) {

        try {

            String si=txtSi.getText().toString();
            String no=txtNo.getText().toString();

            String desarrollar="0";
            if(rdSi.isChecked()){
                desarrollar="1";
            }else if(rdNo.isChecked()){

                desarrollar="2";
            }

            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("si_gustaria_trabajar", si);
            values.put("no_gustaria_trabajar", no);
            values.put("gustaria_desarrollar_actividad", desarrollar);
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
