package com.example.tatu.juego;

import com.example.tatu.enumerativos.Elementos;
import com.example.tatu.enumerativos.Instrucciones;
import com.example.tatu.enumerativos.Mensajes;

public class Tatu {
    private static final Tatu instance = new Tatu();
    private int posX;
    private int posY;
    private int dir; //angulo en grados


    private Tatu() {
    }

    public static Tatu getInstance() {
        return instance;
    }

    void inicializar(int x, int y, int dir) {
        if (x < Tablero.ancho && x >= 0 && y < Tablero.alto && y >= 0) {
            posX = x;
            posY = y;
        } else {
            posX = 0;
            posY = 0;
        }
        if (dir == 0 || dir == 90 || dir == 180 || dir == 270) {
            this.dir = dir;
        } else {
            this.dir = 0;
        }

    }

    public Mensajes accion(Instrucciones aux) {
        Mensajes info = Mensajes.NULO;
        switch (aux) {
            case Izquierda:
                dir -= 90;
                if (dir == -90) {
                    dir = 270;
                }
                break;
            case Derecha:
                dir += 90;
                if (dir == 360) {
                    dir = 0;
                }
                break;
            case Avanzar:
                int x, y;
                x = posX + (int) Math.sin(Math.toRadians(dir));
                y = posY - (int) Math.cos(Math.toRadians(dir));
                if (this.enLimite(x, y) && (Tablero.getInstance().getTipoCasilla(x, y) != Elementos.Bloqueado)) {
                    posX = x;
                    posY = y;
                } else {
                    // Quiere avanzar a un espacio ilegal
                    info = Mensajes.ERROR;
                }
                break;
            case Seleccionar:
                info = this.sel();
                break;

        }
        return info;
    }

    private Mensajes sel() { //Se debe contemplar todas las casillas seleccionables, podria depender del tablero implementado
        Mensajes respuesta = Mensajes.NULO;
        Elementos aux = Tablero.getInstance().getTipoCasilla(posX, posY);
        switch (aux) {
            case Meta:
                if (Tablero.getInstance().getParadas() == 0) {
                    respuesta = Mensajes.META;
                } else {
                    respuesta = Mensajes.FALTA_PARADA;
                }
                break;
            case Parada:
                Tablero.getInstance().enParada(posX, posY);
                respuesta = Mensajes.PARADA;
                break;
            case ParadaVisitada:
                respuesta = Mensajes.PARADA_VISITADA;
                break;
        }
        return respuesta;
    }

    private boolean enLimite(int x, int y) {
        boolean aux = true;
        if ((x >= Tablero.ancho) || (x < 0)) {
            aux = false;
        }
        if ((y >= Tablero.alto) || (y < 0)) {
            aux = false;
        }

        return aux;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getDir() {
        return dir;
    }

}
