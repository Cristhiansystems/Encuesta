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

        btnSiguiente.setOnClickListener(v -> {

            Fragment miFragment=null;
            miFragment=new ssr_enamorado();
            transaction=getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,miFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnAtras.setOnClickListener(v -> {

            Fragment miFragment=null;
            miFragment=new ssr_oir_anticonceptivo();
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
