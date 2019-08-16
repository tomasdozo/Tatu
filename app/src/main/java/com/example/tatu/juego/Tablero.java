package com.example.tatu.juego;

import com.example.tatu.enumerativos.Elementos;

import java.util.List;

import static com.example.tatu.enumerativos.Elementos.Bloqueado;
import static com.example.tatu.enumerativos.Elementos.Meta;
import static com.example.tatu.enumerativos.Elementos.Parada;


public class Tablero {
	private static final Tablero tablero = new Tablero();
	private Casillero[][] mapa;
	private int ancho;
	private int alto;
	private int paradas;

	private Tablero() {
		this.ancho = 7;
		this.alto = 7;
	}

	public static Tablero getInstance() {
		return tablero;
	}

	public void inicializar() {
		paradas=0;
		mapa = new Casillero[ancho][alto];
		for (int x = 0; x < ancho; x++) {
			for (int y = 0; y < alto; y++) {
				mapa[x][y] = new Casillero(Elementos.Nada,x,y);
			}
		}
	}

	public void cargarNivel(int niv){
		inicializar();
		switch (niv){
			case 1:
				setTipoCasilla(Meta,0,0);
				setTipoCasilla(Bloqueado,0,3);
				setTipoCasilla(Bloqueado,1,0);
				setTipoCasilla (Bloqueado,1,1);
				setTipoCasilla(Bloqueado, 1,3);
				setTipoCasilla(Bloqueado,2,3);
				setTipoCasilla(Bloqueado,3,1);
				setTipoCasilla(Bloqueado,3,2);
				setTipoCasilla(Bloqueado,3,3);
				Tatu.getInstance().inicializar(0,4,90);
				break;
			case 2:
				setTipoCasilla(Bloqueado, 0, 2);
				setTipoCasilla(Bloqueado, 1, 2);
				setTipoCasilla(Bloqueado, 2, 2);
				setTipoCasilla(Parada, 2, 3);
				setTipoCasilla(Meta, 0, 4);
				Tatu.getInstance().inicializar(0,0,90);
				break;
			case 3:
				setTipoCasilla(Bloqueado,1,1);
				setTipoCasilla(Parada,1,2);
				setTipoCasilla(Meta,2,0);
				setTipoCasilla(Bloqueado,2,1);
				setTipoCasilla(Bloqueado,3,1);
				setTipoCasilla(Parada,3,3);
				Tatu.getInstance().inicializar(2,4,0);
				break;
			case 4:
				setTipoCasilla(Bloqueado,0,3);
				setTipoCasilla(Parada,0,4);
				setTipoCasilla(Bloqueado,1,1);
				setTipoCasilla(Bloqueado,1,3);
				setTipoCasilla(Bloqueado,2,1);
				setTipoCasilla(Bloqueado,2,3);
				setTipoCasilla(Bloqueado,3,1);
				setTipoCasilla(Meta,3,2);
				setTipoCasilla(Bloqueado,3,3);
				setTipoCasilla(Parada,4,0);
				setTipoCasilla(Bloqueado,4,1);
				Tatu.getInstance().inicializar(2,2,0);
				break;

		}

	}

	public void setTipoCasilla(Elementos tipo, int x, int y) {
		Casillero aux = mapa[x][y];
		aux.setDato(tipo);
		if(tipo==Elementos.Parada){
			paradas++;
		}
	}

	public Elementos getTipoCasilla(int x, int y) {
		Casillero aux = mapa[x][y];
		return aux.getDato();
	}

	public int getAncho() {
		return ancho;
	}


	public int getAlto() {
		return alto;
	}


	public int getParadas (){
		return paradas;
	}

	public void enParada(int x, int y){
		mapa[x][y].setDato(Elementos.ParadaVisitada);
		paradas--;
	}

}
