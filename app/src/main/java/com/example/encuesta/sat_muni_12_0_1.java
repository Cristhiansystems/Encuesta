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
 * {@link sat_muni_12_0_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sat_muni_12_0_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sat_muni_12_0_1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public sat_muni_12_0_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sat_muni_12_0_1.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    String idEncuesta;
    Integer recreativo, recreativoSatisfaccion, aidaj, aidajSatisfaccion, albergue, albergueSatisfaccion;
    RadioButton rdRecreativoSi, rdRecreativoNo, rdRecreativoMS, rdRecreativoS, rdRecreativoI, rdRecreativoIn, rdRecreativoMIn;
    RadioButton rdaidajSi, rdaidajNo, rdaidajMS, rdaidajS, rdaidajI, rdaidajIn, rdaidajMIn;
    RadioButton rdalbergueSi, rdalbergueNo, rdalbergueMS, rdalbergueS, rdalbergueI, rdalbergueIn, rdalbergueMIn;
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
    public static sat_muni_12_0_1 newInstance(String param1, String param2) {
        sat_muni_12_0_1 fragment = new sat_muni_12_0_1();
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
        vista= inflater.inflate(R.layout.fragment_sat_muni_12_0_1, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente69);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras69);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1201);


        rdRecreativoSi=(RadioButton) vista.findViewById(R.id.RdSiPrograRecreatYForm);
        rdRecreativoNo=(RadioButton) vista.findViewById(R.id.RdNoPrograRecreatYForm);
        rdRecreativoMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1201);
        rdRecreativoS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1201);
        rdRecreativoI=(RadioButton) vista.findViewById(R.id.RdIndiferente1201);
        rdRecreativoIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1201);
        rdRecreativoMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1201);



        rdaidajSi=(RadioButton) vista.findViewById(R.id.RdSiAtencionSaludAIDAJ);
        rdaidajNo=(RadioButton) vista.findViewById(R.id.RdNoAtencionSaludAIDAJ);
        rdaidajMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1202);
        rdaidajS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1202);
        rdaidajI=(RadioButton) vista.findViewById(R.id.RdIndiferente1202);
        rdaidajIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1202);
        rdaidajMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1202);


        rdalbergueSi=(RadioButton) vista.findViewById(R.id.RdSiAtencionAlberguesSalud);
        rdalbergueNo=(RadioButton) vista.findViewById(R.id.RdNoAtencionAlberguesSalud);
        rdalbergueMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1203);
        rdalbergueS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1203);
        rdalbergueI=(RadioButton) vista.findViewById(R.id.RdIndiferente1203);
        rdalbergueIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1203);
        rdalbergueMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1203);

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
            interfaceComunicaFragments.enviarEncuesta73(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta71(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {


        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "programacion_recreativa"

                ,"programacion_recreativa_satisfaccion"
                ,"atencion_salud"
                ,"atencion_salud_satisfaccion"
                ,"atencion_albergues"
                ,"atencion_albergues_satisfaccion"

        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        recreativo = Integer.parseInt( cursor.getString(0));
        recreativoSatisfaccion = Integer.parseInt( cursor.getString(1));
        aidaj = Integer.parseInt( cursor.getString(2));
        aidajSatisfaccion = Integer.parseInt( cursor.getString(3));
        albergue = Integer.parseInt( cursor.getString(4));
        albergueSatisfaccion = Integer.parseInt( cursor.getString(5));



        if(recreativo==1){
            rdRecreativoSi.setChecked(true);
        }else if(recreativo==2){
            rdRecreativoNo.setChecked(true);
        }

        if(recreativoSatisfaccion==1){
            rdRecreativoMS.setChecked(true);
        }else if(recreativoSatisfaccion==2){
            rdRecreativoS.setChecked(true);
        }else if(recreativoSatisfaccion==3){
            rdRecreativoI.setChecked(true);
        }else if(recreativoSatisfaccion==4){
            rdRecreativoIn.setChecked(true);
        }else if(recreativoSatisfaccion==5){
            rdRecreativoMIn.setChecked(true);
        }

        if(aidaj==1){
            rdaidajSi.setChecked(true);
        }else if(aidaj==2){
            rdaidajNo.setChecked(true);
        }

        if(aidajSatisfaccion==1){
            rdaidajMS.setChecked(true);
        }else if(aidajSatisfaccion==2){
            rdaidajS.setChecked(true);
        }else if(aidajSatisfaccion==3){
            rdaidajI.setChecked(true);
        }else if(aidajSatisfaccion==4){
            rdaidajIn.setChecked(true);
        }else if(aidajSatisfaccion==5){
            rdaidajMIn.setChecked(true);
        }

        if(albergue==1){
            rdalbergueSi.setChecked(true);
        }else if(albergue==2){
            rdalbergueNo.setChecked(true);
        }

        if(albergueSatisfaccion==1){
            rdalbergueMS.setChecked(true);
        }else if(albergueSatisfaccion==2){
            rdalbergueS.setChecked(true);
        }else if(albergueSatisfaccion==3){
            rdalbergueI.setChecked(true);
        }else if(albergueSatisfaccion==4){
            rdalbergueIn.setChecked(true);
        }else if(albergueSatisfaccion==5){
            rdalbergueMIn.setChecked(true);
        }





        cursor.close();






    }

    private void actualizar(String pantalla) {


        try {

            String recreativo="0";
            if(rdRecreativoSi.isChecked()){
                recreativo="1";
            }else if(rdRecreativoNo.isChecked()){
                recreativo="2";
            }

            String recreativoSatisfaccion="0";
            if(rdRecreativoMS.isChecked()){
                recreativoSatisfaccion="1";

            }else if(rdRecreativoS.isChecked()){
                recreativoSatisfaccion="2";
            }else if(rdRecreativoI.isChecked()){
                recreativoSatisfaccion="3";
            }else if(rdRecreativoIn.isChecked()){
                recreativoSatisfaccion="4";
            }else if(rdRecreativoMIn.isChecked()){
                recreativoSatisfaccion="5";
            }


            String aidaj="0";
            if(rdaidajSi.isChecked()){
                aidaj="1";
            }else if(rdaidajNo.isChecked()){
                aidaj="2";
            }

            String aidajSatisfaccion="0";
            if(rdaidajMS.isChecked()){
                aidajSatisfaccion="1";

            }else if(rdaidajS.isChecked()){
                aidajSatisfaccion="2";
            }else if(rdaidajI.isChecked()){
                aidajSatisfaccion="3";
            }else if(rdaidajIn.isChecked()){
                aidajSatisfaccion="4";
            }else if(rdaidajMIn.isChecked()){
                aidajSatisfaccion="5";
            }

            String albergue="0";
            if(rdalbergueSi.isChecked()){
                albergue="1";
            }else if(rdalbergueNo.isChecked()){
                albergue="2";
            }

            String albergueSatisfaccion="0";
            if(rdalbergueMS.isChecked()){
                albergueSatisfaccion="1";

            }else if(rdalbergueS.isChecked()){
                albergueSatisfaccion="2";
            }else if(rdalbergueI.isChecked()){
                albergueSatisfaccion="3";
            }else if(rdalbergueIn.isChecked()){
                albergueSatisfaccion="4";
            }else if(rdalbergueMIn.isChecked()){
                albergueSatisfaccion="5";
            }

            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("programacion_recreativa", recreativo);
            values.put("programacion_recreativa_satisfaccion", recreativoSatisfaccion);
            values.put("atencion_salud", aidaj);
            values.put("atencion_salud_satisfaccion", aidajSatisfaccion);
            values.put("atencion_albergues", albergue);
            values.put("atencion_albergues_satisfaccion", albergueSatisfaccion);

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
