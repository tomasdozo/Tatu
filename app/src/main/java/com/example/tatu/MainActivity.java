package com.example.tatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnJugar = findViewById(R.id.btnJugar);
        Button btnInstrucciones = findViewById(R.id.btnInstrucciones);
        final EditText etNivel=findViewById(R.id.etNivel);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etNivel.getText().toString().isEmpty()) {
                    int nivel = Integer.parseInt(etNivel.getText().toString());
                    if (nivel > 0 && nivel < 5) {
                        Intent intent = new Intent(MainActivity.this, com.example.tatu.Juego.class);
                        intent.putExtra("nivel", nivel);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "El nivel debe ser entre 1 y 4", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        btnInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,com.example.tatu.Instrucciones.class);
                startActivity(intent);

            }
        });
    }
}
