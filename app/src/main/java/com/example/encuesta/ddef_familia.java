package com.example.encuesta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.encuesta.Adapter.FamiliaAdapter;
import com.example.encuesta.entidades.Familia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ddef_familia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ddef_familia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ddef_familia extends Fragment{
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
    RecyclerView recyclerFamilia;
    ArrayList<Familia> listaFamilia;

    //volley

    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //
    //
    //navegar pantallas
    Activity actividad;
    IComunicacionFragments interfaceComunicaFragments;

    private OnFragmentInteractionListener mListener;

    public ddef_familia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ddef_familia.
     */
    // TODO: Rename and change types and number of parameters
    public static ddef_familia newInstance(String param1, String param2) {
        ddef_familia fragment = new ddef_familia();
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
        vista=inflater.inflate(R.layout.fragment_ddef_familia, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente8);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras8);
        idFragment= (TextView) vista.findViewById(R.id.idFamilia);
        listaFamilia=new ArrayList<>();

        recyclerFamilia=(RecyclerView) vista.findViewById(R.id.idRecyclerFamilia);
        recyclerFamilia.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerFamilia.setHasFixedSize(true);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();

        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta9(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta7(idFragment.getText().toString());
        });
        return vista;
    }
    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuestaLista.php?id_encuesta="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            Familia familia=null;

            JSONArray json=response.optJSONArray("usuario");
            try {

                for (int i=0;i<json.length();i++){
                    familia=new Familia();
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);
                    familia.setId(jsonObject.optInt("id_familia_encuestado"));
                    familia.setNombre(jsonObject.optString("nombre"));
                    familia.setApellidos(jsonObject.optString("apellidos"));
                    familia.setGenero(jsonObject.optInt("genero"));
                    familia.setParentesco(jsonObject.optInt("parentesco"));
                    familia.setReferencia(jsonObject.optString("referencia"));
                    familia.setTelefono(jsonObject.optString("telefono"));
                    familia.setActividad_laboral(jsonObject.optString("actividad_laboral"));
                    familia.setIngreso_mensual(jsonObject.optString("ingreso_mensual"));

                    listaFamilia.add(familia);
                }

                FamiliaAdapter adapter=new FamiliaAdapter(listaFamilia);

                recyclerFamilia.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                        " "+response, Toast.LENGTH_LONG).show();

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
