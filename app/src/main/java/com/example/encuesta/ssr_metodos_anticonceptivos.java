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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ssr_metodos_anticonceptivos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ssr_metodos_anticonceptivos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ssr_metodos_anticonceptivos extends Fragment {
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
    RadioButton rdAbstinenciaSi, rdAbstinenciaNo, rdLigadurasSi, rdLigadurasNo, rdVasectomiaSi, rdVasectomiaNo, rdPildorasSi, rdPildorasNo, rdDiuSi, rdDiuNo, rdInyeccionSi, rdInyecionNo, rdImpantesSi, rdImplantesNo, rdConconMasculinoSi, rdConconMasculinoNo, rdConconFemeninoSi, rdConconFemeninoNo, rdTabletasSi, rdTabletasNo, rdLactanciaSi, rdLactanciaNo, rdRitmoSi, rdRitmoNo, rdCollarSi, rdCollarNo, rdEmergenciaSi, rdEmergenciaNo, rdOtroSi, rdOtrono;
    EditText txtrespuesta, txtOtroNombre;
    String idEncuesta, otroNombre, repuesta;

    Integer abstinencia, ligaduras, vasectomia, pildoras, diu, inyecciones, implantes, condonMasculino, condonFemenino, tabletas, lactancia, ritmo, collar, emergencia, otro;

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



    public ssr_metodos_anticonceptivos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ssr_metodos_anticonceptivos.
     */
    // TODO: Rename and change types and number of parameters
    public static ssr_metodos_anticonceptivos newInstance(String param1, String param2) {
        ssr_metodos_anticonceptivos fragment = new ssr_metodos_anticonceptivos();
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
        vista=inflater.inflate(R.layout.fragment_ssr_metodos_anticonceptivos, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente190);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras190);
        idFragment= (TextView) vista.findViewById(R.id.idMetodosAnticonceptivo);

        //Radio Butons
        rdAbstinenciaSi=(RadioButton) vista.findViewById(R.id.abstinenciaSi);
        rdAbstinenciaNo=(RadioButton) vista.findViewById(R.id.abstinenciaNo);
        rdLigadurasSi=(RadioButton) vista.findViewById(R.id.ligadurasSi);
        rdLigadurasNo=(RadioButton) vista.findViewById(R.id.ligadurasNo);
        rdVasectomiaSi=(RadioButton) vista.findViewById(R.id.vasectomiaSi);
        rdVasectomiaNo=(RadioButton) vista.findViewById(R.id.vasectomiaNo);
        rdPildorasSi=(RadioButton) vista.findViewById(R.id.pildorasSi);
        rdPildorasNo=(RadioButton) vista.findViewById(R.id.pildorasNo);
        rdDiuSi=(RadioButton) vista.findViewById(R.id.diuSi);
        rdDiuNo=(RadioButton) vista.findViewById(R.id.diuNo);
        rdInyeccionSi=(RadioButton) vista.findViewById(R.id.inyeccionesSi);
        rdInyecionNo=(RadioButton) vista.findViewById(R.id.inyeccionesNo);
        rdImpantesSi=(RadioButton) vista.findViewById(R.id.implantesSi);
        rdImplantesNo=(RadioButton) vista.findViewById(R.id.implantesNo);
        rdConconMasculinoSi=(RadioButton) vista.findViewById(R.id.condonMasculinoSi);
        rdConconMasculinoNo=(RadioButton) vista.findViewById(R.id.condonMasculinoNo);
        rdConconFemeninoSi=(RadioButton) vista.findViewById(R.id.condonFemeninoSi);
        rdConconFemeninoNo=(RadioButton) vista.findViewById(R.id.condonFemeninoNo);
        rdTabletasSi=(RadioButton) vista.findViewById(R.id.tabletasSi);
        rdTabletasNo=(RadioButton) vista.findViewById(R.id.tabletasNo);
        rdLactanciaSi=(RadioButton) vista.findViewById(R.id.lactanciaSi);
        rdLactanciaNo=(RadioButton) vista.findViewById(R.id.lactanciaNo);
        rdRitmoSi=(RadioButton) vista.findViewById(R.id.ritmoSi);
        rdRitmoNo=(RadioButton) vista.findViewById(R.id.ritmoNo);
        rdCollarSi=(RadioButton) vista.findViewById(R.id.collarSi);
        rdCollarNo=(RadioButton) vista.findViewById(R.id.collarNo);
        rdEmergenciaSi=(RadioButton) vista.findViewById(R.id.emergenciaSi);
        rdEmergenciaNo=(RadioButton) vista.findViewById(R.id.emergenciaNo);
        rdOtroSi=(RadioButton) vista.findViewById(R.id.otrometodoAnticonceptivoSi);
        rdOtrono=(RadioButton) vista.findViewById(R.id.otrometodoAnticonceptivoNo);

        txtrespuesta=(EditText) vista.findViewById(R.id.txtrespuestaMetodoAnticonceptivo);
        txtOtroNombre=(EditText) vista.findViewById(R.id.txtotroMetodoAnticpnceptivo);

        Bundle data=getArguments();

        if(data!=null){

            idFragment.setText(data.getString("idEncuesta"));



        }
        //Aqui empieza el volley
        request= Volley.newRequestQueue(getContext());
        //aqui se llama al web services
        cargarWebServices();
        btnSiguiente.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta22(idFragment.getText().toString());
        });

        btnAtras.setOnClickListener(v -> {

            interfaceComunicaFragments.enviarEncuesta20(idFragment.getText().toString());
        });
        return vista;
    }
    private void cargarWebServices() {

        String url="http://192.168.0.13/encuestasWS/consultaEncuesta.php?id="+idFragment.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {


            JSONArray json = response.optJSONArray("usuario");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                idEncuesta = jsonObject.optString("encuesta_emt");
                abstinencia = jsonObject.optInt("abstinencia");
                ligaduras = jsonObject.optInt("ligadura_trompas");
                vasectomia = jsonObject.optInt("vasectomia");
                pildoras = jsonObject.optInt("pildoras");
                diu = jsonObject.optInt("diu");
                inyecciones = jsonObject.optInt("inyecciones");
                implantes = jsonObject.optInt("implantes");
                condonMasculino = jsonObject.optInt("condon_masculino");
                condonFemenino = jsonObject.optInt("condon_femenino");
                tabletas = jsonObject.optInt("tabletas");
                lactancia = jsonObject.optInt("metodo_lactancia");
                ritmo = jsonObject.optInt("metodo_ritmo");
                collar = jsonObject.optInt("metodo_collar");
                emergencia = jsonObject.optInt("pildora_emergencia");
                otro = jsonObject.optInt("otro_anticonceptivo");
                otroNombre = jsonObject.optString("otro_anticonceptivo_nombre");
                repuesta = jsonObject.optString("respuesta_metodo_anticonceptivo");

                if (abstinencia == 1) {
                    rdAbstinenciaSi.setChecked(true);
                } else if (abstinencia == 2) {
                    rdAbstinenciaNo.setChecked(true);
                }

                if (ligaduras == 1) {
                    rdLigadurasSi.setChecked(true);
                } else if (ligaduras == 2) {
                    rdLigadurasNo.setChecked(true);
                }

                if (vasectomia == 1) {
                    rdVasectomiaSi.setChecked(true);
                } else if (vasectomia == 2) {
                    rdVasectomiaNo.setChecked(true);
                }

                if (pildoras == 1) {
                    rdPildorasSi.setChecked(true);
                } else if (pildoras == 2) {
                    rdPildorasNo.setChecked(true);
                }

                if (diu == 1) {
                    rdDiuSi.setChecked(true);
                } else if (diu == 2) {
                    rdDiuNo.setChecked(true);
                }

                if (inyecciones == 1) {
                    rdInyeccionSi.setChecked(true);
                } else if (inyecciones == 2) {
                    rdInyecionNo.setChecked(true);
                }

                if (implantes == 1) {
                    rdImpantesSi.setChecked(true);
                } else if (implantes == 2) {
                    rdImplantesNo.setChecked(true);
                }

                if (condonMasculino == 1) {
                    rdConconMasculinoSi.setChecked(true);
                } else if (condonMasculino == 2) {
                    rdConconMasculinoNo.setChecked(true);
                }

                if (condonFemenino == 1) {
                    rdConconFemeninoSi.setChecked(true);
                } else if (condonFemenino == 2) {
                    rdConconFemeninoNo.setChecked(true);
                }

                if (tabletas == 1) {
                    rdTabletasSi.setChecked(true);
                } else if (tabletas == 2) {
                    rdTabletasNo.setChecked(true);
                }


                if (lactancia == 1) {
                    rdLactanciaSi.setChecked(true);
                } else if (lactancia == 2) {
                    rdLactanciaNo.setChecked(true);
                }


                if (collar == 1) {
                    rdCollarSi.setChecked(true);
                } else if (collar == 2) {
                    rdCollarNo.setChecked(true);
                }

                if (emergencia == 1) {
                    rdEmergenciaSi.setChecked(true);
                } else if (emergencia == 2) {
                    rdEmergenciaNo.setChecked(true);
                }


                if (ritmo == 1) {
                    rdRitmoSi.setChecked(true);
                } else if (ritmo == 2) {
                    rdRitmoNo.setChecked(true);
                }


                if (otro == 1) {
                    rdOtroSi.setChecked(true);
                } else if (otro == 2) {
                    rdOtrono.setChecked(true);
                }

                txtOtroNombre.setText(otroNombre.toString());
                txtrespuesta.setText(repuesta.toString());
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
