package com.example.encuesta.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.encuesta.R;
import com.example.encuesta.entidades.Usuario;

import java.util.List;

public class EncuestadosAdapter extends RecyclerView.Adapter<EncuestadosAdapter.EncuestadosHolder>
implements View.OnClickListener{

    List<Usuario> listaEncuestados;
    private View.OnClickListener listener;
    public EncuestadosAdapter(List<Usuario> listaEncuestados) {
        this.listaEncuestados = listaEncuestados;
    }


    @Override
    public EncuestadosHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.encuestados,parent,false);
            RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            vista.setLayoutParams(layoutParams);
            vista.setOnClickListener(this);

            return new EncuestadosHolder(vista);
    }

    public void updateData(List<Usuario>  viewModels) {
        listaEncuestados.clear();
        listaEncuestados.addAll(viewModels);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder( EncuestadosHolder holder, int position) {

        holder.txtNroEncuesta.setText( listaEncuestados.get(position).getNroEncuesta().toString());
        holder.txtNombre.setText(listaEncuestados.get(position).getNombre().toString());
        holder.txtFecha.setText(listaEncuestados.get(position).getFecha().toString());

    }
    public void refreshList(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaEncuestados.size();
    }
 public  void setOnClickListener(View.OnClickListener Listener){
        this.listener=Listener;
 }
    @Override
    public void onClick(View v) {
        if (listener!=null) {

        listener.onClick(v);
        }
    }

    public class EncuestadosHolder extends RecyclerView.ViewHolder{

        TextView txtNroEncuesta,txtNombre,txtFecha;

        public EncuestadosHolder(View itemView) {
            super(itemView);
            txtNroEncuesta= (TextView) itemView.findViewById(R.id.txtNroEncuesta);
            txtNombre= (TextView) itemView.findViewById(R.id.txtNombreEncuestado);
            txtFecha= (TextView) itemView.findViewById(R.id.txtFecha);

        }
    }



    // List<EncuestadosAdapter> listaUsuarios;

}
