package com.example.tatu.juego;

import com.example.tatu.enumerativos.Elementos;

class Casillero {
    private Elementos dato;

    Casillero(Elementos var) {
        dato = var;
    }

    Elementos getDato() {
        return dato;
    }

    void setDato(Elementos dato) {
        this.dato = dato;
    }
}
