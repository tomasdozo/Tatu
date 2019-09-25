package com.example.tatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        Button btnJugar = findViewById(R.id.btnJugar);
        Button btnInstrucciones = findViewById(R.id.btnInstrucciones);


        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Niveles1.class);
                startActivity(intent);

            }
        });

        btnInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, com.example.tatu.Instrucciones.class);
                startActivity(intent);

            }
        });
    }
}
