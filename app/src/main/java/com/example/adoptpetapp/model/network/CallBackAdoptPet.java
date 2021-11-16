package com.example.adoptpetapp.model.network;

import com.example.adoptpetapp.model.entity.Mascota;

import java.util.ArrayList;

public interface CallBackAdoptPet <T>{

    void correcto(T respuesta);
    void error(Exception error);

}
