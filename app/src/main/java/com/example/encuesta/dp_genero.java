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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.encuesta.entidades.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dp_genero.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dp_genero#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dp_genero extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    TextView idFragment;
    View vista;
    EditText rdgenero, txtedad, txtFecNac;
    RadioButton radHombre, radMujer;
    String idEncuesta,  Edad, FechaNac;
    Integer genero;
    Date date;
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
    private OnFragmentInteractionListener mListener;

    public dp_genero() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dp_genero.
     */
    // TODO: Rename and change types and number of parameters
    public static dp_genero newInstance(String param1, String param2) {
        dp_genero fragment = new dp_genero();
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
        vista=inflater.inflate(R.layout.fragment_dp_genero, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente3);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras3);
        idFragment= (TextView) vista.findViewById(R.id.idGenero);
        txtedad=(EditText) vista.findViewById(R.id.txtedad);
        txtFecNac=(EditText) vista.findViewById(R.id.txtFecNac);
        radHombre=(RadioButton) vista.findViewById(R.id.radHombre);
        radMujer=(RadioButton) vista.findViewById(R.id.radMujer);

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
            //
        });

        btnAtras.setOnClickListener(v -> {
            String pantalla="Atras";
            actualizar(pantalla);
            //
        });
        return vista;
    }

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualizarNombreGenero.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){

                    interfaceComunicaFragments.enviarEncuesta4(idFragment.getText().toString());
                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta2(idFragment.getText().toString());
                }

            } else {

                Toast.makeText(getContext(), "Error en la actualizacion" + response.toString() , Toast.LENGTH_SHORT).show();



            }


        }, error -> {
            Toast.makeText(getContext(), "No se pudo actualizar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id=idFragment.getText().toString();
                String genero="0";
                if(radHombre.isChecked()){
                    genero="1";
                }else if(radMujer.isChecked()){
                    genero="2";
                }

                String edad=txtedad.getText().toString();
                String fecha_nacimiento=txtFecNac.getText().toString();
                String dateString = fecha_nacimiento;

                // convert string to date
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = f.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String result = df.format(date);

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("genero",genero);
                parametros.put("edad",edad);
                parametros.put("fecha_nacimiento",result);

                return parametros;
            }
        };
        //request.add(stringRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }

    private void cargarWebServices() {
        String ip=getString(R.string.ip);
        String url=ip+"consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json=response.optJSONArray("usuario");
            JSONObject jsonObject=null;

            try{
                jsonObject=json.getJSONObject(0);
                idEncuesta=jsonObject.optString("encuesta_emt");
                genero=jsonObject.optInt("genero");
                Edad=jsonObject.optString("edad");
                FechaNac=jsonObject.optString("fecha_nacimiento");
                String dateString = FechaNac;

                // convert string to date
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                Date date = f.parse(dateString);

                // change the date format
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String result = df.format(date);

                txtFecNac.setText(result);
                txtedad.setText(Edad.toString());


                if(genero==1){
                    radHombre.setChecked(true);
                }else if(genero==2){
                    radMujer.setChecked(true);
                }

            }catch (JSONException e){
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }, error -> {
            Toast.makeText(getContext(), "No se pudo consultar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
       // request.add(jsonObjectRequest);
        volleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
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
