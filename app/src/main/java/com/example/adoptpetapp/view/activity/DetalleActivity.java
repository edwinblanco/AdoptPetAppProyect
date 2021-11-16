package com.example.adoptpetapp.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adoptpetapp.R;
import com.example.adoptpetapp.model.entity.Mascota;
import com.example.adoptpetapp.model.network.CallBackAdoptPet;
import com.example.adoptpetapp.model.repository.MascotaRepository;

public class DetalleActivity extends AppCompatActivity {

    private static final int CODIGO_EDITAR_MASCOTA = 200;
    private TextView tvNombre;
    private ImageView ivImagen;
    private TextView tvEstado;
    private TextView tvGenero;
    private TextView tvEdad;
    private TextView tvDescripcion;
    private TextView tvContacto;
    private Mascota miMascota;
    private Button btnEliminar;
    private Button btnEditar;
    private MascotaRepository mascotaRepository = new MascotaRepository(DetalleActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        tvNombre = findViewById(R.id.tv_nombre_detalle);
        ivImagen = findViewById(R.id.iv_imagen_detalle);
        tvEdad = findViewById(R.id.tv_edad_detalle);
        tvEstado = findViewById(R.id.tv_estado_detalle);
        tvGenero = findViewById(R.id.tv_genero_detalle);
        tvDescripcion = findViewById(R.id.tv_descripcion_detalle);
        btnEliminar = findViewById(R.id.btn_eliminar_detalle);
        btnEditar = findViewById(R.id.btn_editar_detalle);
        tvContacto = findViewById(R.id.tv_contacto_detalle);

        miMascota = (Mascota) getIntent().getSerializableExtra("mascota");
        cargarDatosMascota();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetalleActivity.this, PublicarMascotaActivity.class);
                i.putExtra("mascota", miMascota);
                startActivityForResult(i,CODIGO_EDITAR_MASCOTA);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iData = new Intent();
                iData.putExtra("mascota", miMascota);
                mascotaRepository.eliminarMascotaFS(miMascota, new CallBackAdoptPet<Boolean>() {
                    @Override
                    public void correcto(Boolean respuesta) {
                        if(respuesta){
                            Toast.makeText(DetalleActivity.this, "La mascota se eliminó", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK, iData);
                            finish();
                        }
                    }

                    @Override
                    public void error(Exception error) {
                        Toast.makeText(DetalleActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void cargarDatosMascota(){

        tvNombre.setText(getString(R.string.titulo_detalle_mascota, miMascota.getNombre()));
        tvEdad.setText(getString(R.string.edad_detalle_mascota, String.valueOf(miMascota.getEdad())));
        tvEstado.setText(getString(R.string.adopcion_detalle_mascota, miMascota.getEstado()));
        tvGenero.setText(getString(R.string.estado_detalle_mascota, miMascota.getGenero()));
        tvDescripcion.setText(getString(R.string.descripcion_detalle_mascota, miMascota.getDescripcion()));
        tvContacto.setText(getString(R.string.contacto_detalle_mascota, miMascota.getContacto()));

        Glide.with(DetalleActivity.this).load(miMascota.getUrlImagen()).into(ivImagen);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CODIGO_EDITAR_MASCOTA && resultCode==RESULT_OK){
            if(data!=null){

                miMascota = (Mascota) data.getSerializableExtra("mascota");
                cargarDatosMascota();
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent iData = new Intent();
        setResult(RESULT_OK, iData);

        super.onBackPressed();
    }
}