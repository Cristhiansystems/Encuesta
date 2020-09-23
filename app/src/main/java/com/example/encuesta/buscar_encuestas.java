package com.example.encuesta;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.encuesta.Adapter.EncuestadosAdapter;
import com.example.encuesta.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.widget.DatePicker;
import java.util.Calendar;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link buscar_encuestas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link buscar_encuestas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_encuestas extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  FragmentManager fragmentManager;
  FragmentTransaction fragmentTransaction;
  ProgressDialog progreso;
  RequestQueue request;
  JsonObjectRequest jsonObjectRequest;
  EditText NroEncuesta,Fecha;
  LinearLayout display;

  Button btnBuscE;
  View vista;
  EncuestadosAdapter adapter;
  RecyclerView recyclerEncuestados;
  ArrayList<Usuario> ListaUsuarios;
  IComunicacionFragments interfaceComunicaFragments;
  Activity actividad;
  Calendar c=Calendar.getInstance();
  static final int DATE_ID = 0;
  Identificacion_geografica identificacion_geografica;
  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public buscar_encuestas() {
    // Required empty public constructor
  }


  public static buscar_encuestas newInstance(String param1, String param2) {
    buscar_encuestas fragment = new buscar_encuestas();
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
    vista= inflater.inflate(R.layout.fragment_buscar_encuestas, container, false);
    ListaUsuarios=new ArrayList<>();

    recyclerEncuestados=(RecyclerView)vista.findViewById(R.id.ListaEncuestados);


    recyclerEncuestados.setLayoutManager(new LinearLayoutManager(this.getContext()));
    recyclerEncuestados.setHasFixedSize(true);
    // recyclerEncuestados.setAdapter(adapter);
    NroEncuesta=(EditText)vista.findViewById(R.id.txtNroEncuesta);
    Fecha=(EditText)vista.findViewById(R.id.txtFecha);
    btnBuscE=(Button)vista.findViewById(R.id.btnBuscarEncuesta);
  display=(LinearLayout) vista.findViewById(R.id.layoutbusqueda);
    Bundle args = getArguments();
    String usu = args.getString("usu", "0");

    if(!usu.equals("0")){
      display.setVisibility(View.INVISIBLE);
      display.setVisibility(View.GONE);
    }
    Fecha.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        abrirCalendario();
      }
    });


    request= Volley.newRequestQueue(getContext());

    cargarWebServices(usu);

    btnBuscE.setOnClickListener(v -> {

      cargarWebServices(usu);
      // adapter.notifyDataSetChanged();
    });



    return vista;
  }



  public void abrirCalendario(){
    Calendar cal=Calendar.getInstance();
    int anio=cal.get(Calendar.YEAR);
    int mes=cal.get(Calendar.MONTH);
    int dia=cal.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog dpd=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fecha= dayOfMonth +"-"+(month+1)+"-"+year;
        Fecha.setText(fecha);
      }
    },anio,mes,dia);
    dpd.show();
  }



  private void cargarWebServices(String usu) {

    progreso=new ProgressDialog(getContext());
    progreso.setMessage("Consultando....");
    progreso.show();
    String ip=getString(R.string.ip);

    if(usu.equals("0")){
    String url=ip+"consultaListaEncuesta.php?id="+NroEncuesta.getText().toString()+"&fecha="+Fecha.getText().toString();

    jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
    request.add(jsonObjectRequest);
    }else{

      String url=ip+"consultaListaEncuestaUsuario.php?idusuario="+usu;

      jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
      request.add(jsonObjectRequest);
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

    if(context instanceof Activity){
      this.actividad= (Activity) context;
      interfaceComunicaFragments= (IComunicacionFragments) this.actividad;
    }
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

  @Override
  public void onErrorResponse(VolleyError error) {
    progreso.hide();
    Toast.makeText(getContext(),"No se pudo consultar:"+error.toString(),Toast.LENGTH_SHORT).show();
    Log.i("ERROR",error.toString());
  }



  @Override
  public void onResponse(JSONObject response) {
    // recyclerEncuestados.removeAllViewsInLayout();
    ListaUsuarios.clear();
    Usuario usuario=null;
    JSONArray json=response.optJSONArray("usuario");



    try {
      for(int  i=0;i<json.length();i++) {
        usuario = new Usuario();
        JSONObject jsonObject = null;
        jsonObject=json.getJSONObject(i);
        usuario.setNroEncuesta(jsonObject.optInt("encuesta_emt"));
        String Nombre=jsonObject.optString("nombre")+" "+jsonObject.optString("apellido_paterno")+" "+jsonObject.optString("apellido_materno");
        usuario.setNombre(Nombre);
        usuario.setFecha(jsonObject.optString("fecha"));
        // adapter.updateData(ListaUsuarios);

        ListaUsuarios.add(usuario);
      }
      progreso.hide();

      adapter=new EncuestadosAdapter(ListaUsuarios);

      recyclerEncuestados.setAdapter(adapter);
      DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
      recyclerEncuestados.addItemDecoration(dividerItemDecoration);
      adapter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // Toast.makeText(getContext(), "Seleccion:"+ListaUsuarios.get(recyclerEncuestados.getChildAdapterPosition(v)).getNroEncuesta(),Toast.LENGTH_SHORT).show();
          interfaceComunicaFragments.enviarEncuesta(ListaUsuarios.get(recyclerEncuestados.getChildAdapterPosition(v)).getNroEncuesta().toString());
        }
      });

      // adapter.refreshList();



    } catch (JSONException e) {
      e.printStackTrace();
      Toast.makeText(getContext(),"No se ha podido  establecer conexion con el servidor"+" "+response,Toast.LENGTH_SHORT).show();
      progreso.hide();
    }


  }




  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
 