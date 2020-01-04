package com.example.tatu.enumerativos;

import com.example.tatu.R;

public enum Instrucciones {
    Derecha(R.drawable.right),
    Izquierda(R.drawable.left),
    Avanzar(R.drawable.forward),
    Seleccionar(R.drawable.sel);

    private int imageID;

    Instrucciones(int aux) {
        imageID = aux;
    }

    public int getImageID() {
        return imageID;
    }
}
