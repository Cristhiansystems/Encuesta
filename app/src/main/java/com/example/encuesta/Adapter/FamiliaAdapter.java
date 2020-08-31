package com.example.encuesta.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.encuesta.entidades.Familia;
import com.example.encuesta.R;
import java.util.List;

public class FamiliaAdapter extends RecyclerView.Adapter<FamiliaAdapter.FamiliaHolder> {

    List<Familia> listaFamilia;

    public FamiliaAdapter(List<Familia> listaFamilia){
        this.listaFamilia = listaFamilia;
    }
    @NonNull
    @Override
    public FamiliaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_familia,viewGroup,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new FamiliaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull FamiliaHolder familiaHolder, int i) {

        Integer parentesco, sexo;
        String nombreParentesco="", nombreSexo="";

        parentesco=listaFamilia.get(i).getParentesco();
        sexo=listaFamilia.get(i).getGenero();


        if(parentesco==1){
            nombreParentesco="Madre";
        }else if(parentesco==2){
            nombreParentesco="Padre";
        }else if(parentesco==3){
            nombreParentesco="Hermanos";
        }else if(parentesco==4){
            nombreParentesco="Abuelos";
        }else if(parentesco==5){
            nombreParentesco="Pareja";
        }else if(parentesco==6){
            nombreParentesco="Hijos";
        }else if(parentesco==7){
            nombreParentesco="Otros Parientes";
        }else if(parentesco==8){
            nombreParentesco="Otros no parientes";
        }



        if(sexo==1){
            nombreSexo="Hombre";
        }else if(sexo==2){
            nombreSexo="Mujer";
        }
        familiaHolder.txtParentesco.setText(nombreParentesco);
        familiaHolder.txtNombre.setText("Nombre: " + listaFamilia.get(i).getNombre().toString());
        familiaHolder.txtApellido.setText("Apellido: " + listaFamilia.get(i).getApellidos().toString());
        familiaHolder.txtSexo.setText("Sexo: " + nombreSexo);
        familiaHolder.txtTelefono.setText("Telefono: " + listaFamilia.get(i).getTelefono().toString());
        familiaHolder.txtActividad.setText("Actividad Economica: "+ listaFamilia.get(i).getActividad_laboral().toString());
        familiaHolder.txtIngreso.setText("Ingreso Mensual bs. "+listaFamilia.get(i).getIngreso_mensual().toString());
        familiaHolder.txtReferencia.setText("Referencia: "+listaFamilia.get(i).getReferencia().toString());
    }

    @Override
    public int getItemCount() {
        return listaFamilia.size();
    }

    public class FamiliaHolder extends RecyclerView.ViewHolder {

        TextView txtParentesco, txtNombre, txtApellido, txtSexo, txtTelefono, txtActividad, txtIngreso, txtReferencia;
        public FamiliaHolder(@NonNull View itemView) {
            super(itemView);
            txtParentesco=(TextView) itemView.findViewById(R.id.familiaListParentesco);
            txtNombre=(TextView) itemView.findViewById(R.id.familiaListNombre);
            txtApellido=(TextView) itemView.findViewById(R.id.familiaListApellido);
            txtSexo=(TextView) itemView.findViewById(R.id.familiaListSexo);
            txtTelefono=(TextView) itemView.findViewById(R.id.familiaListTelefono);
            txtActividad=(TextView) itemView.findViewById(R.id.familiaListActividad);
            txtReferencia=(TextView) itemView.findViewById(R.id.familiaListReferencia);
            txtIngreso=(TextView) itemView.findViewById(R.id.familiaListIngreso);
        }
    }
}