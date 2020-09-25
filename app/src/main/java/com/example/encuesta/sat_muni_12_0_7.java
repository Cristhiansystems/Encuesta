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
 * {@link sat_muni_12_0_7.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sat_muni_12_0_7#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sat_muni_12_0_7 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public sat_muni_12_0_7() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sat_muni_12_0_7.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;
    String idEncuesta;
    Integer empleoJuvenil, empleoJuvenilSatisfaccion, empoderacionPersonal, empoderacionPersonalSatisfaccion, negocioJuvenil, negocioJuvenilSatisfaccion, intervencionDefensoria, intervencionDefensoriaSatisfaccion;
    RadioButton rdempleoJuvenilSi, rdempleoJuvenilNo, rdempleoJuvenilMS, rdempleoJuvenilS, rdempleoJuvenilI, rdempleoJuvenilIn, rdempleoJuvenilMIn;
    RadioButton rdempoderacionPersonalSi, rdempoderacionPersonalNo, rdempoderacionPersonalMS, rdempoderacionPersonalS, rdempoderacionPersonalI, rdempoderacionPersonalIn, rdempoderacionPersonalMIn;
    RadioButton rdnegocioJuvenilSi, rdnegocioJuvenilNo, rdnegocioJuvenilMS, rdnegocioJuvenilS, rdnegocioJuvenilI, rdnegocioJuvenilIn, rdnegocioJuvenilMIn;
    RadioButton rdintervencionDefensoriaSi, rdintervencionDefensoriaNo, rdintervencionDefensoriaMS, rdintervencionDefensoriaS, rdintervencionDefensoriaI, rdintervencionDefensoriaIn, rdintervencionDefensoriaMIn;

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
    public static sat_muni_12_0_7 newInstance(String param1, String param2) {
        sat_muni_12_0_7 fragment = new sat_muni_12_0_7();
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
        vista=inflater.inflate(R.layout.fragment_sat_muni_12_0_7, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente71);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras71);

        idFragment=(TextView) vista.findViewById(R.id.idemppers1207);


        rdempleoJuvenilSi=(RadioButton) vista.findViewById(R.id.RdSiProgramasApoyoEmpleoJuvenil);
        rdempleoJuvenilNo=(RadioButton) vista.findViewById(R.id.RdNoProgramasApoyoEmpleoJuvenil);
        rdempleoJuvenilMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1207);
        rdempleoJuvenilS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1207);
        rdempleoJuvenilI=(RadioButton) vista.findViewById(R.id.RdIndiferente1207);
        rdempleoJuvenilIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1207);
        rdempleoJuvenilMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1207);



        rdempoderacionPersonalSi=(RadioButton) vista.findViewById(R.id.RdSiEmpoderamientoPersonal);
        rdempoderacionPersonalNo=(RadioButton) vista.findViewById(R.id.RdNoEmpoderamientoPersonal);
        rdempoderacionPersonalMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1208);
        rdempoderacionPersonalS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1208);
        rdempoderacionPersonalI=(RadioButton) vista.findViewById(R.id.RdIndiferente1208);
        rdempoderacionPersonalIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1208);
        rdempoderacionPersonalMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1208);


        rdnegocioJuvenilSi=(RadioButton) vista.findViewById(R.id.RdSiProgramasApoyoCreacionNegocios);
        rdnegocioJuvenilNo=(RadioButton) vista.findViewById(R.id.RdNoProgramasApoyoCreacionNegocios);
        rdnegocioJuvenilMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1209);
        rdnegocioJuvenilS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1209);
        rdnegocioJuvenilI=(RadioButton) vista.findViewById(R.id.RdIndiferente1209);
        rdnegocioJuvenilIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1209);
        rdnegocioJuvenilMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1209);


        rdintervencionDefensoriaSi=(RadioButton) vista.findViewById(R.id.RdSiIntervencionesDefensoria);
        rdintervencionDefensoriaNo=(RadioButton) vista.findViewById(R.id.RdNoIntervencionesDefensoria);
        rdintervencionDefensoriaMS=(RadioButton) vista.findViewById(R.id.RdMuySatisfecho1210);
        rdintervencionDefensoriaS=(RadioButton) vista.findViewById(R.id.RdSatisfecho1210);
        rdintervencionDefensoriaI=(RadioButton) vista.findViewById(R.id.RdIndiferente1210);
        rdintervencionDefensoriaIn=(RadioButton) vista.findViewById(R.id.RdInsatisfecho1210);
        rdintervencionDefensoriaMIn=(RadioButton) vista.findViewById(R.id.RdMuyInsatisfecho1210);


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
            interfaceComunicaFragments.enviarEncuesta75(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            interfaceComunicaFragments.enviarEncuesta73(idFragment.getText().toString());
        });
        return vista;
    }

    private void cargarWebServices() {



        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={
                "empleo_juvenil"

                ,"empleo_juvenil_satisfaccion"
                ,"empoderamiento_personal"
                ,"empoderamiento_persona_satisfaccion"
                ,"negocio_juvenil"
                ,"negocio_juvenil_satisfaccion"
                ,"intervencion_defensoria"
                ,"intervencion_defensoria_satisfaccion"

        };

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        empleoJuvenil = Integer.parseInt( cursor.getString(0));
        empleoJuvenilSatisfaccion =Integer.parseInt( cursor.getString(1));
        empoderacionPersonal = Integer.parseInt( cursor.getString(2));
        empoderacionPersonalSatisfaccion = Integer.parseInt( cursor.getString(3));
        negocioJuvenil = Integer.parseInt( cursor.getString(4));
        negocioJuvenilSatisfaccion = Integer.parseInt( cursor.getString(5));

        intervencionDefensoria = Integer.parseInt( cursor.getString(6));
        intervencionDefensoriaSatisfaccion = Integer.parseInt( cursor.getString(7));


        if(empleoJuvenil==1){
            rdempleoJuvenilSi.setChecked(true);
        }else if(empleoJuvenil==2){
            rdempleoJuvenilNo.setChecked(true);
        }

        if(empleoJuvenilSatisfaccion==1){
            rdempleoJuvenilMS.setChecked(true);
        }else if(empleoJuvenilSatisfaccion==2){
            rdempleoJuvenilS.setChecked(true);
        }else if(empleoJuvenilSatisfaccion==3){
            rdempleoJuvenilI.setChecked(true);
        }else if(empleoJuvenilSatisfaccion==4){
            rdempleoJuvenilIn.setChecked(true);
        }else if(empleoJuvenilSatisfaccion==5){
            rdempleoJuvenilMIn.setChecked(true);
        }

        if(empoderacionPersonal==1){
            rdempoderacionPersonalSi.setChecked(true);
        }else if(empoderacionPersonal==2){
            rdempoderacionPersonalNo.setChecked(true);
        }

        if(empoderacionPersonalSatisfaccion==1){
            rdempoderacionPersonalMS.setChecked(true);
        }else if(empoderacionPersonalSatisfaccion==2){
            rdempoderacionPersonalS.setChecked(true);
        }else if(empoderacionPersonalSatisfaccion==3){
            rdempoderacionPersonalI.setChecked(true);
        }else if(empoderacionPersonalSatisfaccion==4){
            rdempoderacionPersonalIn.setChecked(true);
        }else if(empoderacionPersonalSatisfaccion==5){
            rdempoderacionPersonalMIn.setChecked(true);
        }

        if(negocioJuvenil==1){
            rdnegocioJuvenilSi.setChecked(true);
        }else if(negocioJuvenil==2){
            rdnegocioJuvenilNo.setChecked(true);
        }

        if(negocioJuvenilSatisfaccion==1){
            rdnegocioJuvenilMS.setChecked(true);
        }else if(negocioJuvenilSatisfaccion==2){
            rdnegocioJuvenilS.setChecked(true);
        }else if(negocioJuvenilSatisfaccion==3){
            rdnegocioJuvenilI.setChecked(true);
        }else if(negocioJuvenilSatisfaccion==4){
            rdnegocioJuvenilIn.setChecked(true);
        }else if(negocioJuvenilSatisfaccion==5){
            rdnegocioJuvenilMIn.setChecked(true);
        }

        if(intervencionDefensoria==1){
            rdintervencionDefensoriaSi.setChecked(true);
        }else if(intervencionDefensoria==2){
            rdintervencionDefensoriaNo.setChecked(true);
        }

        if(intervencionDefensoriaSatisfaccion==1){
            rdintervencionDefensoriaMS.setChecked(true);
        }else if(intervencionDefensoriaSatisfaccion==2){
            rdintervencionDefensoriaS.setChecked(true);
        }else if(intervencionDefensoriaSatisfaccion==3){
            rdintervencionDefensoriaI.setChecked(true);
        }else if(intervencionDefensoriaSatisfaccion==4){
            rdintervencionDefensoriaIn.setChecked(true);
        }else if(intervencionDefensoriaSatisfaccion==5){
            rdintervencionDefensoriaMIn.setChecked(true);
        }






        cursor.close();



    }

    private void actualizar(String pantalla) {

        try {

            String empleoJuvenil="0";
            if(rdempleoJuvenilSi.isChecked()){
                empleoJuvenil="1";
            }else if(rdempleoJuvenilNo.isChecked()){
                empleoJuvenil="2";
            }


            String empleoJuvenilSatisfaccion="0";
            if(rdempleoJuvenilMS.isChecked()){
                empleoJuvenilSatisfaccion="1";

            }else if(rdempleoJuvenilS.isChecked()){
                empleoJuvenilSatisfaccion="2";
            }else if(rdempleoJuvenilI.isChecked()){
                empleoJuvenilSatisfaccion="3";
            }else if(rdempleoJuvenilIn.isChecked()){
                empleoJuvenilSatisfaccion="4";
            }else if(rdempleoJuvenilMIn.isChecked()){
                empleoJuvenilSatisfaccion="5";
            }


            String empoderamientoPersonal="0";
            if(rdempoderacionPersonalSi.isChecked()){
                empoderamientoPersonal="1";
            }else if(rdempoderacionPersonalNo.isChecked()){
                empoderamientoPersonal="2";
            }

            String empoderamientoPersonalSatisfaccion="0";
            if(rdempoderacionPersonalMS.isChecked()){
                empoderamientoPersonalSatisfaccion="1";

            }else if(rdempoderacionPersonalS.isChecked()){
                empoderamientoPersonalSatisfaccion="2";
            }else if(rdempoderacionPersonalI.isChecked()){
                empoderamientoPersonalSatisfaccion="3";
            }else if(rdempoderacionPersonalIn.isChecked()){
                empoderamientoPersonalSatisfaccion="4";
            }else if(rdempoderacionPersonalMIn.isChecked()){
                empoderamientoPersonalSatisfaccion="5";
            }

            String negocioJuvenil="0";
            if(rdnegocioJuvenilSi.isChecked()){
                negocioJuvenil="1";
            }else if(rdnegocioJuvenilNo.isChecked()){
                negocioJuvenil="2";
            }

            String negocioJuvenilSatisfaccion="0";
            if(rdnegocioJuvenilMS.isChecked()){
                negocioJuvenilSatisfaccion="1";

            }else if(rdnegocioJuvenilS.isChecked()){
                negocioJuvenilSatisfaccion="2";
            }else if(rdnegocioJuvenilI.isChecked()){
                negocioJuvenilSatisfaccion="3";
            }else if(rdnegocioJuvenilIn.isChecked()){
                negocioJuvenilSatisfaccion="4";
            }else if(rdnegocioJuvenilMIn.isChecked()){
                negocioJuvenilSatisfaccion="5";
            }

            String intervencionDefensoria="0";
            if(rdintervencionDefensoriaSi.isChecked()){
                intervencionDefensoria="1";
            }else if(rdintervencionDefensoriaNo.isChecked()){
                intervencionDefensoria="2";
            }

            String intervecionDefensoriaSatisfaccion="0";
            if(rdintervencionDefensoriaMS.isChecked()){
                intervecionDefensoriaSatisfaccion="1";

            }else if(rdintervencionDefensoriaS.isChecked()){
                intervecionDefensoriaSatisfaccion="2";
            }else if(rdintervencionDefensoriaI.isChecked()){
                intervecionDefensoriaSatisfaccion="3";
            }else if(rdintervencionDefensoriaIn.isChecked()){
                intervecionDefensoriaSatisfaccion="4";
            }else if(rdintervencionDefensoriaMIn.isChecked()){
                intervecionDefensoriaSatisfaccion="5";
            }



            //  Toast.makeText(getContext(),respuesta,Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();

            values.put("empleo_juvenil", empleoJuvenil);
            values.put("empleo_juvenil_satisfaccion", empleoJuvenilSatisfaccion);
            values.put("empoderamiento_personal", empoderamientoPersonal);
            values.put("empoderamiento_persona_satisfaccion", empoderamientoPersonalSatisfaccion);
            values.put("negocio_juvenil", negocioJuvenil);
            values.put("negocio_juvenil_satisfaccion", negocioJuvenilSatisfaccion);
            values.put("intervencion_defensoria", intervencionDefensoria);
            values.put("intervencion_defensoria_satisfaccion", intervecionDefensoriaSatisfaccion);

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
