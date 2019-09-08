package com.example.tatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Niveles extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles);
        TableLayout tabla= findViewById(R.id.tabla);
        for(int i=0; i<tabla.getChildCount();i++){
            TableRow fila=(TableRow) tabla.getChildAt(i);
            for (int j=0; j<fila.getChildCount();j++){
                fila.getChildAt(j).setOnClickListener(this);
            }
        }

        Button btnVolver=findViewById(R.id.btnSalir);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Niveles.this.finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        String aux = ((TextView) view).getText().toString();
        int nroNivel = Integer.parseInt(aux);
        Intent intent = new Intent(Niveles.this, com.example.tatu.Juego.class);
        intent.putExtra("nivel", nroNivel);
        startActivity(intent);
    }
}
