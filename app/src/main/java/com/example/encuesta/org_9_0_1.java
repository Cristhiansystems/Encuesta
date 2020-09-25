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
 * {@link org_9_0_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link org_9_0_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class org_9_0_1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public org_9_0_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment org_9_0_1.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtrespuesta, txtorganizacion;
    String idEncuesta, respuesta, organizacion;
    Integer cargo, estudiando;
    RadioButton rdSi, rdNo;
    LinearLayout display;

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
    public static org_9_0_1 newInstance(String param1, String param2) {
        org_9_0_1 fragment = new org_9_0_1();
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
        vista= inflater.inflate(R.layout.fragment_org_9_0_1, container, false);

        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente46);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras46);
        idFragment=(TextView) vista.findViewById(R.id.idemppers91);
        txtrespuesta=(EditText) vista.findViewById(R.id.txtResp91);
        txtorganizacion=(EditText) vista.findViewById(R.id.txt902);
        display=(LinearLayout) vista.findViewById(R.id.layout91);
        display.setVisibility(View.INVISIBLE);
        display.setVisibility(View.GONE);

        conn=new ConexionSQLiteHelper(vista.getContext(), "encuestas", null, 2);

        rdSi=(RadioButton) vista.findViewById(R.id.SiOcupasCarg);
        rdNo=(RadioButton) vista.findViewById(R.id.NoOcupasCarg);

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
            interfaceComunicaFragments.enviarEncuesta50(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
            if(estudiando==1){
                interfaceComunicaFragments.enviarEncuesta46(idFragment.getText().toString());
            }else{
                interfaceComunicaFragments.enviarEncuesta48(idFragment.getText().toString());
            }
        });
        return vista;
    }
    private void cargarWebServices() {




        SQLiteDatabase db=conn.getReadableDatabase();

        String[] parametros={idFragment.getText().toString()};
        String [] campos={"respuesta_agrupacion","nombre_agrupacion","ocupaste_cargo_agrupacion","estudiante_actualmente"};

        Cursor cursor=db.query("encuesta_emt",campos,"encuesta_emt=?",parametros,null,null,null);
        cursor.moveToFirst();

        respuesta =  cursor.getString(0);
        organizacion =  cursor.getString(1);
        cargo =Integer.parseInt( cursor.getString(2));

        estudiando =Integer.parseInt(  cursor.getString(3));
        txtrespuesta.setText(respuesta.toString());
        txtorganizacion.setText(organizacion.toString());

        if(cargo==1){
            rdSi.setChecked(true);
        }else if(cargo==2){
            rdNo.setChecked(true);
        }



        cursor.close();






    }


    private void actualizar(String pantalla) {

        try {

            String respuesta=txtrespuesta.getText().toString();
            String organizacion=txtorganizacion.getText().toString();
            String cargo="0";
            if(rdSi.isChecked()){
                cargo="1";
            }else if(rdNo.isChecked()){
                cargo="2";
            }


            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros = {idFragment.getText().toString()};
            ContentValues values = new ContentValues();


            values.put("respuesta_agrupacion",respuesta);//string
            values.put("nombre_agrupacion",organizacion);//string
            values.put("ocupaste_cargo_agrupacion",cargo);//bool

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
