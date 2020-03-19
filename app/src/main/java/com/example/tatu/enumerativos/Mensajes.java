package com.example.tatu.enumerativos;

import com.example.tatu.R;

public enum Mensajes {
    META(R.string.cartel_meta),
    FALTA_PARADA(R.string.cartel_paradas_faltantes),
    PARADA(R.string.cartel_parada),
    PARADA_VISITADA(R.string.cartel_parada_visitada),
    ERROR(R.string.cartel_error),
    NULO(R.string.cartel_neutro),
    NO_LLEGASTE(R.string.cartel_no_llego);

    private int descriptionID;

    Mensajes(int aux) {
        descriptionID = aux;
    }

    public int getDescriptionID() {
        return descriptionID;
    }
}
