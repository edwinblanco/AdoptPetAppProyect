package com.example.adoptpetapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adoptpetapp.R;
import com.example.adoptpetapp.model.entity.Mascota;

import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter {

    private ArrayList<Mascota> listado;
    private OnItemClickListener onItemClickListener;

    public MascotaAdapter(ArrayList<Mascota> listado) {
        this.listado = listado;
    }

    //set Onclicklistener
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
      this.onItemClickListener = onItemClickListener;
    }

    public void setListado(ArrayList<Mascota> listado) {
        this.listado = listado;
        notifyDataSetChanged();
    }

    public class MascotaViewHolder extends RecyclerView.ViewHolder{

        ImageView ivMascota;
        TextView tvEstado;
        TextView tvGenero;
        TextView tvEdad;
        TextView tvNombre;
        TextView tvContacto;

        public MascotaViewHolder(@NonNull final View itemView) {
            super(itemView);
            ivMascota = itemView.findViewById(R.id.iv_mascota_item);
            tvEdad = itemView.findViewById(R.id.tv_edad_item);
            tvNombre = itemView.findViewById(R.id.tv_nombre_item);
            tvEstado = itemView.findViewById(R.id.tv_estado_item);
            tvGenero = itemView.findViewById(R.id.tv_genero_item);
            tvContacto = itemView.findViewById(R.id.tv_contacto_item);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View miVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.mascota_item, parent, false);
        return new MascotaViewHolder(miVista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Mascota miMascota = listado.get(position);

        final MascotaViewHolder miHolder = (MascotaViewHolder)holder;
        miHolder.tvNombre.setText(miMascota.getNombre());
        miHolder.tvEdad.setText(String.valueOf(miMascota.getEdad()));
        miHolder.tvEstado.setText(String.valueOf(miMascota.getEstado()));
        miHolder.tvGenero.setText(String.valueOf(miMascota.getGenero()));
        miHolder.tvContacto.setText(String.valueOf(miMascota.getContacto()));
        Glide.with(miHolder.itemView.getContext()).load(miMascota.getUrlImagen()).into(miHolder.ivMascota);

        miHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onItemClickListener != null){
                    onItemClickListener.OnItemClick(miMascota);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(Mascota miMascota);
    }

}
