package com.example.tatu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tatu.enumerativos.Instrucciones;
import com.example.tatu.enumerativos.Mensajes;
import com.example.tatu.juego.Tablero;
import com.example.tatu.juego.Tatu;

import java.util.LinkedList;
import java.util.List;

import static com.example.tatu.enumerativos.Instrucciones.Avanzar;
import static com.example.tatu.enumerativos.Instrucciones.Derecha;
import static com.example.tatu.enumerativos.Instrucciones.Izquierda;
import static com.example.tatu.enumerativos.Instrucciones.Seleccionar;

public class Actividad1 extends AppCompatActivity implements View.OnClickListener {

    private TableLayout table;
    private Tatu tatu;
    private Tablero juego;
    private Button btnEmpezar, btnSalir, btnLimpiar;
    private TextView tvInfo;
    private LinearLayout gInstrucciones, gBotones;
    private HorizontalScrollView horizontalScroll;
    private List<Instrucciones> instrucciones, lAux;
    private Mensajes msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad1);

        table = findViewById(R.id.tablero);
        ImageButton ibRight = findViewById(R.id.ibRight);
        ImageButton ibLeft = findViewById(R.id.ibLeft);
        ImageButton ibForward = findViewById(R.id.ibForward);
        ImageButton ibSel = findViewById(R.id.ibSel);
        tvInfo = findViewById(R.id.tvInfo);
        btnSalir = findViewById(R.id.btnSalir);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnEmpezar = findViewById(R.id.btnStart);
        gInstrucciones = findViewById(R.id.linearInstrucciones);
        horizontalScroll = findViewById(R.id.scrollHorizontal);
        gBotones = findViewById(R.id.linearBotones);

        instrucciones = new LinkedList<>();
        msg = Mensajes.NEUTRO;

        //Inicializar Tablero Codigo
        inicializarTablero();

        //Generacion Tablero Grafico
        interfazTablero();


        //Graficar Elementos
        graficar();

        //Configurar Clicks
        ibRight.setOnClickListener(this);
        ibLeft.setOnClickListener(this);
        ibForward.setOnClickListener(this);
        ibSel.setOnClickListener(this);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actividad1.this.finish();
            }
        });
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recorrer();
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gInstrucciones.removeAllViews();
                instrucciones.clear();

            }
        });

    }

    private void interfazTablero() {
        ImageView cell;
        TableLayout.LayoutParams sRows = new TableLayout.LayoutParams();
        sRows.weight = 1;
        for (int i = 0; i < Tablero.alto; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(sRows);
            for (int j = 0; j < Tablero.ancho; j++) {
                cell = new ImageView(this);
                cell.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                cell.setBackground(getResources().getDrawable(R.drawable.casilla));
                row.addView(cell);
            }
            table.addView(row);
        }
    }

    private void inicializarTablero() {
        tatu = Tatu.getInstance();
        juego = Tablero.getInstance();
        juego.cargarNivel(this.getIntent().getIntExtra("nivel", 1));
    }

    private void graficar() {

        //Mostrar Tablero
        TableRow row;
        ImageView cell;
        for (int i = 0; i < Tablero.alto; i++) {
            row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < Tablero.ancho; j++) {
                cell = (ImageView) row.getChildAt(j);
                switch (juego.getTipoCasilla(j, i)) {
                    case Meta:
                        cell.setImageResource(R.drawable.meta);
                        break;
                    case Parada:
                        cell.setImageResource(R.drawable.parada);
                        break;
                    case Bloqueado:
                        cell.setImageResource(R.drawable.blocked);
                        break;
                    case Nada:
                        cell.setImageResource(R.drawable.nada);
                        break;
                    case ParadaVisitada:
                        cell.setImageResource(R.drawable.parada_visitada);
                }


            }
        }

        //Mostrar Tatu
        row = (TableRow) table.getChildAt(tatu.getPosY());
        cell = (ImageView) row.getChildAt(tatu.getPosX());
        switch (tatu.getDir()) {
            case 0:
                cell.setImageResource(R.drawable.tatu_n);
                break;
            case 90:
                cell.setImageResource(R.drawable.tatu_e);
                break;
            case 180:
                cell.setImageResource(R.drawable.tatu_s);
                break;
            case 270:
                cell.setImageResource(R.drawable.tatu_o);
        }

        //Mostrar Mensaje
        switch (msg) {
            case META:
                tvInfo.setText(R.string.cartel_meta);
                break;
            case PARADA:
                tvInfo.setText(R.string.cartel_parada);
                break;
            case PARADA_VISITADA:
                tvInfo.setText(R.string.cartel_parada_visitada);
                break;
            case FALTA_PARADA:
                tvInfo.setText(R.string.cartel_paradas_faltantes);
                break;
            case ERROR:
                tvInfo.setText(R.string.cartel_error);
                break;
            case NEUTRO:
                tvInfo.setText("");
                break;
            case NO_LLEGASTE:
                tvInfo.setText(R.string.cartel_no_llego);
                break;
        }

    }

    private void recorrer() {
        gBotones.setVisibility(View.GONE);
        btnEmpezar.setVisibility(View.INVISIBLE);
        btnLimpiar.setVisibility(View.INVISIBLE);

        //Deshabilitar clic en la lista
        enableDisableView(gInstrucciones, false);
        enableDisableView(btnEmpezar, false);
        enableDisableView(btnLimpiar, false);

        horizontalScroll.postDelayed(new Runnable() {
            public void run() {
                horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        }, 100L);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Lista Auxiliar para guardar el recorrido hecho
                lAux = new LinkedList<>();
                lAux.addAll(instrucciones);

                //Recorrido de las intrucciones
                while (!instrucciones.isEmpty() && (msg != Mensajes.ERROR)) {
                    msg = Tatu.getInstance().accion(instrucciones.get(0));
                    instrucciones.remove(0);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            gInstrucciones.removeViewAt(0);
                            graficar();
                        }
                    });

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Termino el recorrido

                //Chequea mensaje
                if (msg == Mensajes.NEUTRO) {
                    msg = Mensajes.NO_LLEGASTE;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            graficar();
                        }
                    });
                }

                //Tiempo Muerto para mostrar el mensaje
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Reinicializar el juego
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        msg = Mensajes.NEUTRO;
                        gInstrucciones.removeAllViews();
                        instrucciones.clear();
                        gBotones.setVisibility(View.VISIBLE);
                        btnEmpezar.setVisibility(View.VISIBLE);
                        btnLimpiar.setVisibility(View.VISIBLE);
                        enableDisableView(btnLimpiar, true);
                        enableDisableView(btnEmpezar, true);

                        //enableDisableView(gInstrucciones,true);
                        inicializarTablero();
                        graficar();

                        //Vuelve a agregar el recorrido
                        for (int i = 0; i < lAux.size(); i++) {
                            addInstruction(lAux.get(i));
                        }
                        horizontalScroll.postDelayed(new Runnable() {
                            public void run() {
                                horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            }
                        }, 100L);


                    }
                });


            }
        });
        thread.start();


    }

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ibLeft:
                addInstruction(Izquierda);
                break;
            case R.id.ibForward:
                addInstruction(Avanzar);
                break;
            case R.id.ibRight:
                addInstruction(Derecha);
                break;
            case R.id.ibSel:
                addInstruction(Seleccionar);
                break;
        }

        //posiciona al final la lista, tiene que tener un delay para que cargue los elementos antes de moverse
        horizontalScroll.postDelayed(new Runnable() {
            public void run() {
                horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);

    }

    public void addInstruction(Instrucciones ins) {
        ImageButton aux = new ImageButton(this);
        aux.setLayoutParams(new LinearLayout.LayoutParams(104, 104));
        aux.setScaleType(ImageView.ScaleType.FIT_CENTER);
        aux.setBackgroundResource(R.drawable.fondo_comandos);
        aux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instrucciones.remove(gInstrucciones.indexOfChild(view));
                gInstrucciones.removeView(view);
            }
        });
        instrucciones.add(ins);
        switch (ins) {
            case Izquierda:

                aux.setImageResource(R.drawable.left);
                break;
            case Avanzar:

                aux.setImageResource(R.drawable.forward);
                break;
            case Derecha:

                aux.setImageResource(R.drawable.right);
                break;
            case Seleccionar:
                aux.setImageResource(R.drawable.sel);
                break;
        }
        gInstrucciones.addView(aux);
    }


}
