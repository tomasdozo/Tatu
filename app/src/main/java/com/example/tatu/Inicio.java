package com.example.tatu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity {
    private Boolean insRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        final Toast toast = Toast.makeText(Inicio.this, "Primero lea las intrucciones", Toast.LENGTH_LONG);
        insRead = getPreferences(MODE_PRIVATE).getBoolean("instructionsRead", false);

        Button btnJugar = findViewById(R.id.btnJugar);
        Button btnInstrucciones = findViewById(R.id.btnInstrucciones);


        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (insRead) {
                    Intent intent = new Intent(Inicio.this, Niveles1.class);
                    startActivity(intent);
                } else {
                    toast.show();
                }

            }
        });

        btnInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!insRead) {
                    insRead = true;
                    SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                    editor.putBoolean("instructionsRead", insRead);
                    editor.apply();
                    toast.cancel();
                }
                Intent intent = new Intent(Inicio.this, com.example.tatu.Instrucciones.class);
                startActivity(intent);

            }
        });

    }

}
