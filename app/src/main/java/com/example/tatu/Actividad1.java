package com.example.tatu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tatu.enumerativos.Instrucciones;
import com.example.tatu.enumerativos.Mensajes;
import com.example.tatu.juego.Tablero;
import com.example.tatu.juego.Tatu;

import java.util.LinkedList;

import static com.example.tatu.enumerativos.Instrucciones.Avanzar;
import static com.example.tatu.enumerativos.Instrucciones.Derecha;
import static com.example.tatu.enumerativos.Instrucciones.Izquierda;
import static com.example.tatu.enumerativos.Instrucciones.Seleccionar;

@SuppressWarnings("FieldCanBeLocal")
public class Actividad1 extends AppCompatActivity implements View.OnClickListener {

    private TableLayout table;
    private Tatu tatu;
    private Tablero juego;
    private Button btnEmpezar, btnSalir, btnLimpiar;
    private TextView tvInfo;
    private LinearLayout gInstrucciones, gBotones;
    private HorizontalScrollView horizontalScroll;
    private LinkedList<Instrucciones> instrucciones, lAux;
    private Mensajes msg;
    private int delay, nivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad1);

        table = findViewById(R.id.tablero);
        ImageButton ibRight = findViewById(R.id.ibRight);
        ImageButton ibLeft = findViewById(R.id.ibLeft);
        ImageButton ibForward = findViewById(R.id.ibForward);
        ImageButton ibSel = findViewById(R.id.ibSel);
        ToggleButton fastForward = findViewById(R.id.fastForward);
        tvInfo = findViewById(R.id.tvInfo);
        btnSalir = findViewById(R.id.btnSalir);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnEmpezar = findViewById(R.id.btnStart);
        gInstrucciones = findViewById(R.id.linearInstrucciones);
        horizontalScroll = findViewById(R.id.scrollHorizontal);
        gBotones = findViewById(R.id.linearBotones);
        instrucciones = new LinkedList<>();
        msg = Mensajes.NULO;
        delay = 500;
        nivel = this.getIntent().getIntExtra("nivel", 1);

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
        fastForward.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    delay = 100;
                    buttonView.setTextColor(getResources().getColor(R.color.colorAccent));
                    buttonView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    delay = 500;
                    buttonView.setTextColor(getResources().getColor(R.color.white));
                    buttonView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
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
        juego.cargarNivel(nivel);
    }

    private void graficar() {

        //Mostrar Tablero
        TableRow row;
        ImageView cell;
        for (int i = 0; i < Tablero.alto; i++) {
            row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < Tablero.ancho; j++) {
                cell = (ImageView) row.getChildAt(j);
                cell.setImageResource(juego.getTipoCasilla(j, i).getImageID());
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
        tvInfo.setText(msg.getDescriptionID());
        if (msg != Mensajes.NULO) {
            tvInfo.setVisibility(View.VISIBLE);
        } else {
            tvInfo.setVisibility(View.GONE);
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
                while (!instrucciones.isEmpty() && (msg != Mensajes.ERROR) && (msg != Mensajes.META)) {
                    msg = Tatu.getInstance().accion(instrucciones.removeFirst());

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            gInstrucciones.removeViewAt(0);
                            graficar();
                        }
                    });

                    try {

                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Termino el recorrido

                //Chequea mensaje
                if (msg == Mensajes.NULO) {
                    msg = Mensajes.NO_LLEGASTE;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            graficar();
                        }
                    });
                } else if (msg == Mensajes.META) {
                    nivel++;
                    lAux.clear();
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
                        msg = Mensajes.NULO;
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
                        while (lAux.size() > 0) {
                            addInstruction(lAux.removeFirst());
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

    private void addInstruction(Instrucciones ins) {
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
        instrucciones.addLast(ins);
        aux.setImageResource(ins.getImageID());
        gInstrucciones.addView(aux);
    }


}
