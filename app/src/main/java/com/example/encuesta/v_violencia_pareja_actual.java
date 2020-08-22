package com.example.encuesta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link v_violencia_pareja_actual.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link v_violencia_pareja_actual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class v_violencia_pareja_actual extends Fragment {
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
    private OnFragmentInteractionListener mListener;

    public v_violencia_pareja_actual() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment v_violencia_pareja_actual.
     */
    // TODO: Rename and change types and number of parameters
    public static v_violencia_pareja_actual newInstance(String param1, String param2) {
        v_violencia_pareja_actual fragment = new v_violencia_pareja_actual();
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
        vista=inflater.inflate(R.layout.fragment_v_violencia_pareja_actual, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente35);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras35);

        btnSiguiente.setOnClickListener(v -> {

            Fragment miFragment=null;
            miFragment=new v_buscar_ayuda();

            transaction=getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,miFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnAtras.setOnClickListener(v -> {

            Fragment miFragment=null;
            miFragment=new v_violencia_pareja();
            transaction=getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,miFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
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