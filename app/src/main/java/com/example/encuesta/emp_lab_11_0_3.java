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
 * {@link emp_lab_11_0_3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link emp_lab_11_0_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emp_lab_11_0_3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public emp_lab_11_0_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emp_lab_11_0_3.
     */
    FragmentTransaction transaction;
    Button btnSiguiente;
    Button btnAtras;
    View vista;
    // TODO: Rename and change types and number of parameters
    public static emp_lab_11_0_3 newInstance(String param1, String param2) {
        emp_lab_11_0_3 fragment = new emp_lab_11_0_3();
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
        vista= inflater.inflate(R.layout.fragment_emp_lab_11_0_3, container, false);
        btnSiguiente= (Button) vista.findViewById(R.id.btnSiguiente58);
        btnAtras= (Button) vista.findViewById(R.id.btnAtras58);

        btnSiguiente.setOnClickListener(v -> {

            Fragment miFragment=null;
            miFragment=new emp_lab_11_0_6();

            transaction=getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,miFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnAtras.setOnClickListener(v -> {

            Fragment miFragment=null;
            miFragment=new emp_lab_11_0_1();
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
