package com.example.tatu.juego;

import com.example.tatu.enumerativos.Elementos;

class Casillero {
	private Elementos dato;
	private int x;
	private int y;
	public Casillero (Elementos var,int x, int y) {
		dato=var;
		this.x=x;
		this.y=y;
	}
	public Elementos getDato() {
		return dato;
	}
	public void setDato(Elementos dato) {
		this.dato = dato;
	}
}
