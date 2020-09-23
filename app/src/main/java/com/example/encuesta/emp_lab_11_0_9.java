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
 * {@link emp_lab_11_0_9.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_9#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_9 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_9() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_9.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;

    TextView idFragment;

    EditText txtrubro, txtnoCapacitarte, txtedad, txttrabajarEdad;
    String  idEncuesta, rubro, capacitarte, edad, trabajarEdad;
    Integer gustariaCapacitarte;
    RadioButton rdCapacitarteSi, rdCapacitarteNo;
    LinearLayout display1109, display1110, display1111, display1112, display1113;
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
    public static emp_lab_11_0_9 newInstance(String param1, String param2) {
        emp_lab_11_0_9 fragment = new emp_lab_11_0_9();
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
       vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_9, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente60);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras60);
        idFragment=(TextView) vista.findViewById(R.id.idemppers1109);

        txtrubro=(EditText) vista.findViewById(R.id.txtResp1110);
        txtnoCapacitarte=(EditText) vista.findViewById(R.id.txtResp1111);
        txtedad=(EditText) vista.findViewById(R.id.txtResp1112);
        txttrabajarEdad=(EditText) vista.findViewById(R.id.txtResp1113);

        rdCapacitarteSi=(RadioButton) vista.findViewById(R.id.SiMeGustariaCapacitarte);
        rdCapacitarteNo=(RadioButton) vista.findViewById(R.id.NoMeGustariaCapacitarte);

        display1109=(LinearLayout) vista.findViewById(R.id.layout1109);
        display1110=(LinearLayout) vista.findViewById(R.id.layout1110);
        display1111=(LinearLayout) vista.findViewById(R.id.layout1111);
        display1112=(LinearLayout) vista.findViewById(R.id.layout1112);
        display1113=(LinearLayout) vista.findViewById(R.id.layout1113);


        rdCapacitarteNo.setOnClickListener(v -> {

            display1110.setVisibility(View.INVISIBLE);
            display1110.setVisibility(View.GONE);
            display1111.setVisibility(View.VISIBLE);

        });

        rdCapacitarteSi.setOnClickListener(v -> {

            display1111.setVisibility(View.INVISIBLE);
            display1111.setVisibility(View.GONE);
            display1110.setVisibility(View.VISIBLE);

        });

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
                rubro = jsonObject.optString("rubro_desea_capacitar");
                capacitarte = jsonObject.optString("no_desea_capacitarte");
                edad = jsonObject.optString("edad_trabajar");
                trabajarEdad = jsonObject.optString("razon_edad_trabajar");

                gustariaCapacitarte = jsonObject.optInt("te_gustaria_capacitarte");


                txtrubro.setText(rubro.toString());
                txtnoCapacitarte.setText(capacitarte.toString());
                txtedad.setText(edad.toString());
                txttrabajarEdad.setText(trabajarEdad.toString());

                if(gustariaCapacitarte==2){
                    display1110.setVisibility(View.INVISIBLE);
                    display1110.setVisibility(View.GONE);
                }else if(gustariaCapacitarte==1){
                    display1111.setVisibility(View.INVISIBLE);
                    display1111.setVisibility(View.GONE);
                }

                if(gustariaCapacitarte==1){
                    rdCapacitarteSi.setChecked(true);
                }else if(gustariaCapacitarte==2){
                    rdCapacitarteNo.setChecked(true);
                }


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
        String url=ip+"actualiza1109.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta64(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta62(idFragment.getText().toString());

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

                String rubro=txtrubro.getText().toString();
                String noCapacitarte=txtnoCapacitarte.getText().toString();
                String edad=txtedad.getText().toString();
                String trabajarEdad=txttrabajarEdad.getText().toString();

                String gustariaCapacitarte="0";
                if(rdCapacitarteSi.isChecked()){
                    gustariaCapacitarte="1";
                }else if(rdCapacitarteNo.isChecked()){

                    gustariaCapacitarte="2";
                }


                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);

                parametros.put("rubro", rubro);
                parametros.put("noCapacitarte", noCapacitarte);
                parametros.put("edad", edad);
                parametros.put("trabajarEdad", trabajarEdad);
                parametros.put("gustariaCapacitarte", gustariaCapacitarte);



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
