package com.example.adoptpetapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adoptpetapp.R;
import com.example.adoptpetapp.model.entity.Mascota;
import com.example.adoptpetapp.model.network.CallBackAdoptPet;
import com.example.adoptpetapp.model.repository.MascotaRepository;

public class PublicarMascotaActivity extends AppCompatActivity {

    private Button btnPublicar;
    private TextView tvUrlImagen;
    private TextView tvNombre;
    private TextView tvEdad;
    private TextView tvDescripcion;
    private TextView tvContacto;
    private RadioGroup rgGenero;
    private RadioGroup rgEstado;
    private MascotaRepository mascotaRepository = new MascotaRepository(PublicarMascotaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_mascota);

        btnPublicar = findViewById(R.id.btn_agregarMascota);
        tvUrlImagen = findViewById(R.id.et_urlImagen_agregar);
        tvDescripcion = findViewById(R.id.et_descripcion_agregar);
        tvEdad = findViewById(R.id.et_edad_agregar2);
        tvNombre = findViewById(R.id.et_nombre_agregar);
        tvContacto = findViewById(R.id.et_contaco_agregar);
        rgGenero = findViewById(R.id.rgGenero);
        rgEstado = findViewById(R.id.rgEstado);


        Mascota miViejaMascota = (Mascota) getIntent().getSerializableExtra("mascota");

        if (miViejaMascota != null){

            tvUrlImagen.setText(miViejaMascota.getUrlImagen());
            tvDescripcion.setText(miViejaMascota.getDescripcion());
            tvEdad.setText(String.valueOf(miViejaMascota.getEdad()));
            tvNombre.setText(miViejaMascota.getNombre());
            tvContacto.setText(miViejaMascota.getContacto());

            btnPublicar.setOnClickListener(view -> {
                String nombre = tvNombre.getText().toString();
                int edad = Integer.parseInt(tvEdad.getText().toString());
                String imagen = tvUrlImagen.getText().toString();
                String descripcion = tvDescripcion.getText().toString();
                String contaco = tvContacto.getText().toString();

                int radioButtonId = rgGenero.getCheckedRadioButtonId();
                View radioButton = rgGenero.findViewById(radioButtonId);
                int indice = rgGenero.indexOfChild(radioButton);
                RadioButton rb = (RadioButton) rgGenero.getChildAt(indice);
                String genero = rb.getText().toString();

                int radioButtonId2 = rgEstado.getCheckedRadioButtonId();
                View radioButton2 = rgEstado.findViewById(radioButtonId2);
                int indice2 = rgEstado.indexOfChild(radioButton2);
                RadioButton rb2 = (RadioButton) rgEstado.getChildAt(indice2);
                String estado = rb2.getText().toString();

                miViejaMascota.setNombre(nombre);
                miViejaMascota.setEdad(edad);
                miViejaMascota.setUrlImagen(imagen);
                miViejaMascota.setDescripcion(descripcion);
                miViejaMascota.setGenero(genero);
                miViejaMascota.setEstado(estado);
                miViejaMascota.setContacto(contaco);

                mascotaRepository.editarMascotaFs(miViejaMascota, new CallBackAdoptPet<Boolean>() {
                    @Override
                    public void correcto(Boolean respuesta) {
                        Toast.makeText(PublicarMascotaActivity.this, "La mascota se modificó y se publicó nuevamente", Toast.LENGTH_LONG).show();
                        Intent iDatos = new Intent();
                        iDatos.putExtra("mascota", miViejaMascota);
                        setResult(RESULT_OK, iDatos);
                        finish();
                    }

                    @Override
                    public void error(Exception error) {
                        Toast.makeText(PublicarMascotaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            });

        }else{
            btnPublicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre = tvNombre.getText().toString();
                    int edad = Integer.parseInt(tvEdad.getText().toString());
                    String imagen = tvUrlImagen.getText().toString();
                    String descripcion = tvDescripcion.getText().toString();
                    String contacto = tvContacto.getText().toString();

                    int radioButtonId = rgGenero.getCheckedRadioButtonId();
                    View radioButton = rgGenero.findViewById(radioButtonId);
                    int indice = rgGenero.indexOfChild(radioButton);
                    RadioButton rb = (RadioButton) rgGenero.getChildAt(indice);
                    String genero = rb.getText().toString();

                    int radioButtonId2 = rgEstado.getCheckedRadioButtonId();
                    View radioButton2 = rgEstado.findViewById(radioButtonId2);
                    int indice2 = rgEstado.indexOfChild(radioButton2);
                    RadioButton rb2 = (RadioButton) rgEstado.getChildAt(indice2);
                    String estado = rb2.getText().toString();


                    Mascota miNuevaMascota = new Mascota(imagen, nombre, genero, edad, descripcion, estado, contacto);

                    mascotaRepository.agregarMascotaFs(miNuevaMascota, new CallBackAdoptPet<Boolean>() {
                        @Override
                        public void correcto(Boolean respuesta) {
                            Toast.makeText(PublicarMascotaActivity.this, "La mascota se agregó", Toast.LENGTH_LONG).show();
                            Intent iDatos = new Intent();
                            iDatos.putExtra("mascota", miNuevaMascota);
                            setResult(RESULT_OK, iDatos);
                            finish();
                        }

                        @Override
                        public void error(Exception error) {
                            Toast.makeText(PublicarMascotaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }
}