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
 * {@link emp_econ_10_0_21.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_21#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_21 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_21() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_21.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtotro, txtorEstudio, txtorRopa, txtorAlimentacion, txtorDiversion, txtorPagoServicios, txtorEquipos, txtorApoyoFamilia, txtorahorro, txtorotro;
    String otronom, idEncuesta, orEstudio, orRopa, orAlimentacion, orDiversion, orPagoServicios, orEquipos, orApoyoFamilia, orahorro, orotro;

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
    public static emp_econ_10_0_21 newInstance(String param1, String param2) {
        emp_econ_10_0_21 fragment = new emp_econ_10_0_21();
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
        vista= inflater.inflate(R.layout.fragment_emp_econ_10_0_21, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente56);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras56);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1021);

        txtotro=(EditText) vista.findViewById(R.id.txtOtro1021);


        txtorEstudio=(EditText) vista.findViewById(R.id.txtEstudios);
        txtorRopa=(EditText) vista.findViewById(R.id.txtRopa);
        txtorAlimentacion=(EditText) vista.findViewById(R.id.txtAlimentacion);
        txtorDiversion=(EditText) vista.findViewById(R.id.txtDiversion);
        txtorPagoServicios=(EditText) vista.findViewById(R.id.txtPagoDeServicios);
        txtorEquipos=(EditText) vista.findViewById(R.id.txtEquiposCelulares);
        txtorApoyoFamilia=(EditText) vista.findViewById(R.id.txtApoyoALaFamilia);
        txtorahorro=(EditText) vista.findViewById(R.id.txtAhorro);
        txtorotro=(EditText) vista.findViewById(R.id.txtOtroOrden1021);

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
        });

        btnAtras.setOnClickListener(v -> {

            String pantalla="Atras";
            actualizar(pantalla);
        });
        return vista;
    }

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                orEstudio = jsonObject.optString("gastar_estudios_orden");
                orRopa = jsonObject.optString("gastar_ropa_orden");
                orAlimentacion = jsonObject.optString("gastar_alimentacion_orden");
                orDiversion = jsonObject.optString("gastar_diversion_orden");
                orPagoServicios = jsonObject.optString("gastar_servicios_orden");
                orEquipos = jsonObject.optString("gastar_equipos_orden");
                orApoyoFamilia = jsonObject.optString("gastar_familia_orden");
                orahorro = jsonObject.optString("gastar_ahorro_orden");
                orotro = jsonObject.optString("gastar_otro_orden");
                otronom = jsonObject.optString("gastar_otro_orden_nombre");



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

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        //request.add(jsonObjectRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualiza1021.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta60(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta58(idFragment.getText().toString());

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
                String id = idFragment.getText().toString();

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




                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("otronom", otronom);
                parametros.put("orEstudios", orEstudios);
                parametros.put("orRopa", orRopa);
                parametros.put("orAlimentacion", orAlimentacion);
                parametros.put("orDiversion", orDiversion);
                parametros.put("orPagoServicios", orPagoServicios);
                parametros.put("orEquipos", orEquipos);
                parametros.put("orApoyoFamilia", orApoyoFamilia);
                parametros.put("orAhorro", orAhorro);
                parametros.put("orOtro", orOtro);

                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
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
