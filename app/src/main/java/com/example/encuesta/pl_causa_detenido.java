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
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
 * {@link pl_causa_detenido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pl_causa_detenido#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pl_causa_detenido extends Fragment{
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
    View vista;

    TextView idFragment;
    RadioButton rdPelasSi, rdPeleasNo, rdRoboSi, rdRoboNo, rdHurtoSi, rdHurtoNo, rdHomicidioSi, rdHomicidioNo, rdDrogasSi, rdDrogasNo, rdOtroSi, rdOtroNo;
    EditText txtrespuesta, txtOtro;
    String idEncuesta, otroCausaDetenido, respuesta;

    Integer peleas, robo, hurto, homicidio, drogas, otro;
    LinearLayout display;

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
    private OnFragmentInteractionListener mListener;

    public pl_causa_detenido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pl_causa_detenido.
     */
    // TODO: Rename and change types and number of parameters
    public static pl_causa_detenido newInstance(String param1, String param2) {
        pl_causa_detenido fragment = new pl_causa_detenido();
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
        vista=inflater.inflate(R.layout.fragment_pl_causa_detenido, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente31);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras31);
        idFragment= (TextView) vista.findViewById(R.id.idCausaDetenido);
        rdPelasSi=(RadioButton) vista.findViewById(R.id.peleasSi);
        rdPeleasNo=(RadioButton) vista.findViewById(R.id.peleasNo);
        rdRoboSi=(RadioButton) vista.findViewById(R.id.roboSi);
        rdRoboNo=(RadioButton) vista.findViewById(R.id.roboNo);
        rdHurtoSi=(RadioButton) vista.findViewById(R.id.hurtoSi);
        rdHurtoNo=(RadioButton) vista.findViewById(R.id.hurtoNo);
        rdHomicidioSi=(RadioButton) vista.findViewById(R.id.homicidioSi);
        rdHomicidioNo=(RadioButton) vista.findViewById(R.id.homicidioNo);
        rdDrogasSi=(RadioButton) vista.findViewById(R.id.drogasSi);
        rdDrogasNo=(RadioButton) vista.findViewById(R.id.drogasNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otroCausaApresadoSi);
        rdOtroNo=(RadioButton) vista.findViewById(R.id.otroCausaApresadoNo);

        txtOtro=(EditText) vista.findViewById(R.id.txtotroCausaApresado);
        txtrespuesta=(EditText) vista.findViewById(R.id.txtrespuestaCausaDetenido);
        display=(LinearLayout) vista.findViewById(R.id.layoutcausadetenido);
        display.setVisibility(View.INVISIBLE);
        display.setVisibility(View.GONE);
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

    private void actualizar(String pantalla) {
        String ip=getString(R.string.ip);
        String url=ip+"actualizarCausaDetenido.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, response -> {
            if (response.trim().equalsIgnoreCase("actualiza")) {
                if(pantalla=="Siguiente"){
                    interfaceComunicaFragments.enviarEncuesta34(idFragment.getText().toString());

                }else if(pantalla=="Atras"){
                    interfaceComunicaFragments.enviarEncuesta32(idFragment.getText().toString());

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
                String peleas = "0";
                if (rdPelasSi.isChecked()) {
                    peleas = "1";
                } else if (rdPeleasNo.isChecked()) {
                    peleas = "2";
                }

                String robo = "0";
                if (rdRoboSi.isChecked()) {
                    robo = "1";
                } else if (rdRoboNo.isChecked()) {
                    robo = "2";
                }

                String hurto = "0";
                if (rdHurtoSi.isChecked()) {
                    hurto = "1";
                } else if (rdHurtoNo.isChecked()) {
                    hurto = "2";
                }


                String homicidio = "0";
                if (rdHomicidioSi.isChecked()) {
                    homicidio = "1";
                } else if (rdHomicidioNo.isChecked()) {
                    homicidio = "2";
                }

                String drogas = "0";
                if (rdDrogasSi.isChecked()) {
                    drogas = "1";
                } else if (rdDrogasNo.isChecked()) {
                    drogas = "2";
                }

                String otro = "0";
                if (rdOtroSi.isChecked()) {
                    otro = "1";
                } else if (rdOtroNo.isChecked()) {
                    otro = "2";
                }

                String respuesta= txtrespuesta.getText().toString();
                String otroCausaDetenido= txtOtro.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("peleas", peleas);
                parametros.put("robo", robo);
                parametros.put("hurto", hurto);
                parametros.put("homicidio", homicidio);
                parametros.put("drogas", drogas);
                parametros.put("otro", otro);
                parametros.put("respuesta", respuesta);
                parametros.put("otroCausaDetenido", otroCausaDetenido);


                return parametros;
            }
        };
        request.add(stringRequest);
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
                peleas= jsonObject.optInt("peleas");
                robo= jsonObject.optInt("robo");
                hurto= jsonObject.optInt("hurto");
                homicidio= jsonObject.optInt("intencion_homicidio");
                drogas= jsonObject.optInt("drogas");
                otro= jsonObject.optInt("otro_detenido");
                respuesta= jsonObject.optString("respuesta_causa_detenido");
                otroCausaDetenido= jsonObject.optString("otro_detenido_nombre");




                if(peleas==1){
                    rdPelasSi.setChecked(true);
                }else if(peleas==2){
                    rdPeleasNo.setChecked(true);
                }

                if(robo==1){
                    rdRoboSi.setChecked(true);
                }else if(robo==2){
                    rdRoboNo.setChecked(true);
                }

                if(hurto==1){
                    rdHurtoSi.setChecked(true);
                }else if(hurto==2){
                    rdHurtoNo.setChecked(true);
                }

                if(homicidio==1){
                    rdHomicidioSi.setChecked(true);
                }else if(homicidio==2){
                    rdHomicidioNo.setChecked(true);
                }

                if(drogas==1){
                    rdDrogasSi.setChecked(true);
                }else if(drogas==2){
                    rdDrogasNo.setChecked(true);
                }

                if(otro==1){
                    rdOtroSi.setChecked(true);
                }else if(otro==2){
                    rdOtroNo.setChecked(true);
                }
                txtrespuesta.setText(respuesta.toString());
                txtOtro.setText(otroCausaDetenido.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), "No se pudo registrar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR: ", error.toString());
        });
        request.add(jsonObjectRequest);
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
