package com.example.adoptpetapp.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adoptpetapp.R;
import com.example.adoptpetapp.model.entity.Mascota;
import com.example.adoptpetapp.model.network.CallBackAdoptPet;
import com.example.adoptpetapp.model.repository.MascotaRepository;
import com.example.adoptpetapp.view.adapter.MascotaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class activity_listado extends AppCompatActivity {

    private static final int CODIGO_AGREGAR_MASCOTA = 100;
    private static final int CODIGO_DETALLE_MASCOTA = 110;
    private static final String COLLECCION_MASCOTA = "mascotas";
    private ArrayList<Mascota> lista_mascotas;
    private RecyclerView rvMascotas;
    private MascotaAdapter miAdaptador;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Button btn;
    private MascotaRepository mascotaRepository = new MascotaRepository(activity_listado.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        btn = findViewById(R.id.btn_ver);
        rvMascotas = findViewById(R.id.rv_listado_mascotas);

        lista_mascotas = new ArrayList<>();
        getMascotas();

        btn.setOnClickListener(view -> {
            Intent miIntencion = new Intent(activity_listado.this, PublicarMascotaActivity.class);
            startActivityForResult(miIntencion, CODIGO_AGREGAR_MASCOTA);
        });

        miAdaptador = new MascotaAdapter(lista_mascotas);
        rvMascotas.setAdapter(miAdaptador);

        //cargarMascotas();

        rvMascotas.setLayoutManager(new LinearLayoutManager(activity_listado.this));
        rvMascotas.setHasFixedSize(true);
        miAdaptador.setOnItemClickListener(new MascotaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Mascota miMascota) {
                Intent miIntencion = new Intent(activity_listado.this, DetalleActivity.class);
                miIntencion.putExtra("mascota", miMascota);
                startActivityForResult(miIntencion, CODIGO_DETALLE_MASCOTA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CODIGO_AGREGAR_MASCOTA && resultCode==RESULT_OK){

            if (data != null) {
                Mascota miMascota = (Mascota) data.getSerializableExtra("mascota");
                Mascota ultimo = lista_mascotas.get(lista_mascotas.size()-1);

                getMascotas();
                miAdaptador.setListado(lista_mascotas);
            }

        }

        if (requestCode==CODIGO_DETALLE_MASCOTA && resultCode==RESULT_OK){
            if (data!=null){
                Mascota miMascota = (Mascota) data.getSerializableExtra("mascota");
                Boolean editar = data.getBooleanExtra("editar", false);
                getMascotas();
                miAdaptador.setListado(lista_mascotas);
            }
        }
    }

    private void cargarMascotas(){

        Mascota mascota1 = new Mascota("https://www.nacionrex.com/__export/1515916465717/sites/debate/img/2018/01/14/foto_de_portada_perros.jpg_172596871.jpg", "Toby", "Macho", 10, "", "Adopción", "3142342343");
        Mascota mascota2 = new Mascota("https://cdni.rt.com/actualidad/public_images/2019.04/article/5ca865b308f3d9f63a8b4567.jpg", "Toby", "Macho", 10, "", "Adopción", "3142342343");
        Mascota mascota3 = new Mascota("https://www.nacionrex.com/__export/1515916465717/sites/debate/img/2018/01/14/foto_de_portada_perros.jpg_172596871.jpg", "Toby", "Macho", 10, "", "Adopción", "3142342343");
        Mascota mascota4 = new Mascota("https://cdn.pixabay.com/photo/2016/11/01/23/38/beach-1790049_960_720.jpg", "Toby", "Macho", 10, "", "Adopción", "3142342343");
        Mascota mascota5 = new Mascota("https://www.nacionrex.com/__export/1515916465717/sites/debate/img/2018/01/14/foto_de_portada_perros.jpg_172596871.jpg", "Toby", "Macho", 10, "", "Adopción", "3142342343");

        //lista_mascotas.add(mascota1);
        //lista_mascotas.add(mascota2);
        //lista_mascotas.add(mascota3);
        //lista_mascotas.add(mascota4);
        //lista_mascotas.add(mascota5);

        firestore.collection(COLLECCION_MASCOTA).add(mascota1);
        firestore.collection(COLLECCION_MASCOTA).add(mascota2);
        firestore.collection(COLLECCION_MASCOTA).add(mascota3);
        firestore.collection(COLLECCION_MASCOTA).add(mascota4);
        firestore.collection(COLLECCION_MASCOTA).add(mascota5);

    }

    private void getMascotas(){
        mascotaRepository.obtenerMascotasFS(new CallBackAdoptPet<ArrayList<Mascota>>() {
            @Override
            public void correcto(ArrayList<Mascota> respuesta) {
                lista_mascotas = respuesta;
                miAdaptador.setListado(respuesta);
            }

            @Override
            public void error(Exception error) {
                Toast.makeText(activity_listado.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /*String TAG = "myApp";
        firestore.collection("mascotas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Mascota miMascota = document.toObject(Mascota.class);
                        miMascota.setIdMascota(document.getId());
                        lista_mascotas.add(miMascota);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                Log.d(TAG, "----------------------------------------"+lista_mascotas.toString());
            }
        });*/
    }

}