package com.example.tatu.enumerativos;

import com.example.tatu.R;

public enum Elementos {
    Bloqueado(R.drawable.blocked),
    Meta(R.drawable.meta),
    Nada(R.drawable.nada),
    Parada(R.drawable.parada),
    ParadaVisitada(R.drawable.parada_visitada);

    private int imageID;

    Elementos(int aux) {
        imageID = aux;
    }

    public int getImageID() {
        return imageID;
    }
}
