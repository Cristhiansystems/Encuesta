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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link emp_econ_10_0_17.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_econ_10_0_17#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_econ_10_0_17 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_econ_10_0_17() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_econ_10_0_17.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtmonto1, txtmonto2, txtmonto3, txtingreso1, txtingreso2, txtingreso3, txtdias1, txtdias2, txtdias3;
    String idEncuesta, monto1, monto2, monto3, ingreso1, ingreso2, ingreso3, dias1, dias2, dias3;
    Integer analizarGasto;
    RadioButton rdSiempre, rdAlgunaVez, rdOcasionalmente, rdNunca;

    //volley

    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    //
    //

    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;
    // TODO: Rename and change types and number of parameters
    public static emp_econ_10_0_17 newInstance(String param1, String param2) {
        emp_econ_10_0_17 fragment = new emp_econ_10_0_17();
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
        vista= inflater.inflate(R.layout.fragment_emp_econ_10_0_17, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente54);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras54);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1017);

        txtmonto1=(EditText) vista.findViewById(R.id.Mes1Monto);
        txtmonto2=(EditText) vista.findViewById(R.id.Mes2Monto);
        txtmonto3=(EditText) vista.findViewById(R.id.Mes3Monto);

        txtingreso1=(EditText) vista.findViewById(R.id.Mes1IngresoDia);
        txtingreso2=(EditText) vista.findViewById(R.id.Mes2IngresoDia);
        txtingreso3=(EditText) vista.findViewById(R.id.Mes3IngresoDia);

        txtdias1=(EditText) vista.findViewById(R.id.Mes1DiasTrabajo);
        txtdias2=(EditText) vista.findViewById(R.id.Mes2DiasTrabajo);
        txtdias3=(EditText) vista.findViewById(R.id.Mes3DiasTrabajo);

        rdSiempre=(RadioButton) vista.findViewById(R.id.RdSiempre);
        rdAlgunaVez=(RadioButton) vista.findViewById(R.id.RdAlgunaVez);
        rdOcasionalmente=(RadioButton) vista.findViewById(R.id.RdOcasionalmente);
        rdNunca=(RadioButton) vista.findViewById(R.id.RdNunca);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }

        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
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
                monto1 = jsonObject.optString("mes_1_monto");
                monto2 = jsonObject.optString("mes_2_monto");
                monto3= jsonObject.optString("mes_3_monto");


                ingreso1 = jsonObject.optString("mes_1_ingreso_dia");
                ingreso2 = jsonObject.optString("mes_2_ingreso_dia");
                ingreso3= jsonObject.optString("mes_3_ingreso_dia");
                dias1 = jsonObject.optString("mes_1_dias_trabajados");
                dias2 = jsonObject.optString("mes_2_dias_trabajados");
                dias3 = jsonObject.optString("mes_3_dias_trabajados");


                analizarGasto = jsonObject.optInt("analizas_gastar");



                if(analizarGasto==1){
                    rdSiempre.setChecked(true);
                }else if(analizarGasto==2){
                    rdAlgunaVez.setChecked(true);
                }else if(analizarGasto==3){
                    rdOcasionalmente.setChecked(true);
                }else if(analizarGasto==4){
                    rdNunca.setChecked(true);
                }




                txtmonto1.setText(monto1.toString());
                txtmonto2.setText(monto2.toString());
                txtmonto3.setText(monto3.toString());

              txtingreso1.setText(ingreso1.toString());
              txtingreso2.setText(ingreso2.toString());
              txtingreso3.setText(ingreso3.toString());

              txtdias1.setText(dias1.toString());
              txtdias2.setText(dias2.toString());
              txtdias3.setText(dias3.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        request.add(jsonObjectRequest);
    }

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualiza1017.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta58(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta56(idFragment.getText().toString());

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

                String monto1=txtmonto1.getText().toString();
                String monto2=txtmonto2.getText().toString();
                String monto3=txtmonto3.getText().toString();

                String ingreso1=txtingreso1.getText().toString();
                String ingreso2=txtingreso2.getText().toString();
                String ingreso3=txtingreso3.getText().toString();

                String dias1=txtdias1.getText().toString();
                String dias2=txtdias2.getText().toString();
                String dias3=txtdias3.getText().toString();

                String analizarIngreso="0";
                if(rdSiempre.isChecked()){
                    analizarIngreso="1";
                }else if(rdAlgunaVez.isChecked()){
                    analizarIngreso="2";
                }else if(rdOcasionalmente.isChecked()){
                    analizarIngreso="3";
                }else if(rdNunca.isChecked()){
                    analizarIngreso="4";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("monto1", monto1);
                parametros.put("monto2", monto2);
                parametros.put("monto3", monto3);
                parametros.put("ingreso1", ingreso1);
                parametros.put("ingreso2", ingreso2);
                parametros.put("ingreso3", ingreso3);
                parametros.put("dias1", dias1);
                parametros.put("dias2", dias2);
                parametros.put("dias3", dias3);
                parametros.put("analizarIngreso", analizarIngreso);

                return parametros;
            }
        };
        request.add(stringRequest);
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
