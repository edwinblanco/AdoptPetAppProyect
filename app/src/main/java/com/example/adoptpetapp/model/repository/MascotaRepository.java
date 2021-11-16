package com.example.adoptpetapp.model.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.adoptpetapp.model.entity.Mascota;
import com.example.adoptpetapp.model.network.CallBackAdoptPet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MascotaRepository {

    private FirebaseFirestore firestore;
    private static final String COLLECCION_MASCOTAS = "mascotas";

    public MascotaRepository(Context miContexto){
        firestore = FirebaseFirestore.getInstance();
    }

    public void obtenerMascotasFS(CallBackAdoptPet <ArrayList<Mascota>> callback){
        String TAG = "myApp";
        ArrayList<Mascota> lista_mascotas = new ArrayList<>();
        firestore.collection(COLLECCION_MASCOTAS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Mascota miMascota = document.toObject(Mascota.class);
                        miMascota.setIdMascota(document.getId());
                        lista_mascotas.add(miMascota);
                    }
                    callback.correcto(lista_mascotas);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    callback.error(task.getException());
                }
            }
        });
    }

    public void eliminarMascotaFS(Mascota miMascota, CallBackAdoptPet <Boolean> callback){
        firestore.collection(COLLECCION_MASCOTAS).document(miMascota.getIdMascota()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.correcto(true);
                }else{
                    callback.correcto(false);
                    callback.error(task.getException());
                }
            }
        });


    }

    public void agregarMascotaFs(Mascota miMascota, CallBackAdoptPet <Boolean> callback){
        firestore.collection(COLLECCION_MASCOTAS).add(miMascota).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    callback.correcto(true);
                }else{
                    callback.correcto(false);
                    callback.error(task.getException());
                }
            }
        });
    }

    public void editarMascotaFs(Mascota miMascota, CallBackAdoptPet <Boolean> callback){
        firestore.collection(COLLECCION_MASCOTAS).document(miMascota.getIdMascota()).set(miMascota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.correcto(true);
                }else{
                    callback.correcto(false);
                    callback.error(task.getException());
                }
            }
        });
    }

}
