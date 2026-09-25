package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Pantalla principal del taller de Android.
 * Contiene el menú con navegación por medio de Intents
 * hacia el Convertidor de Moneda y la Calculadora de Créditos.
 */
public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz
    private Button btnConvertidor;
    private Button btnCalculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Enlace de vistas con los IDs del archivo XML (activity_main.xml)
        btnConvertidor = findViewById(R.id.btnConvertidor);
        btnCalculadora = findViewById(R.id.btnCalculadora);

        // 2. Configurar evento de clic para el botón del Convertidor de Moneda
        btnConvertidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent explícito para navegar hacia ConvertidorActivity
                Intent intentConvertidor = new Intent(MainActivity.this, ConvertidorActivity.class);
                startActivity(intentConvertidor);
            }
        });

        // 3. Configurar evento de clic para el botón de la Calculadora de Créditos
        btnCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent explícito para navegar hacia CalculadoraActivity
                Intent intentCalculadora = new Intent(MainActivity.this, CalculadoraActivity.class);
                startActivity(intentCalculadora);
            }
        });
    }
}
