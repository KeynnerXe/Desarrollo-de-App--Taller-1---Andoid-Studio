package com.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Pantalla del Convertidor de Moneda.
 * Permite transformar valores entre Pesos y Dólares utilizando una tasa fija.
 */
public class ConvertidorActivity extends AppCompatActivity {

    // Constante con la tasa de cambio ficticia requerida (1 Dólar = 4000 Pesos)
    private static final double TASA_CAMBIO = 4000.0;

    // Declaración de variables para los componentes de la vista
    private EditText etMonto;
    private RadioGroup rgOrigen;
    private RadioGroup rgDestino;
    private RadioButton rbOrigenPesos;
    private RadioButton rbOrigenDolares;
    private RadioButton rbDestinoPesos;
    private RadioButton rbDestinoDolares;
    private Button btnConvertir;
    private TextView tvResultado;
    private ImageButton btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertidor);

        // 1. Capturar las vistas del archivo XML mediante findViewById
        etMonto = findViewById(R.id.etMonto);
        rgOrigen = findViewById(R.id.rgOrigen);
        rgDestino = findViewById(R.id.rgDestino);
        rbOrigenPesos = findViewById(R.id.rbOrigenPesos);
        rbOrigenDolares = findViewById(R.id.rbOrigenDolares);
        rbDestinoPesos = findViewById(R.id.rbDestinoPesos);
        rbDestinoDolares = findViewById(R.id.rbDestinoDolares);
        btnConvertir = findViewById(R.id.btnConvertir);
        tvResultado = findViewById(R.id.tvResultado);
        btnVolver = findViewById(R.id.btnVolverConvertidor);

        // Botón opcional para regresar a la pantalla anterior
        if (btnVolver != null) {
            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // 2. Configurar el evento de clic en el botón "Convertir"
        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarConversion();
            }
        });
    }

    /**
     * Valida el campo de texto y realiza el cálculo de conversión de moneda.
     */
    private void realizarConversion() {
        String textoMonto = etMonto.getText().toString().trim();

        // Validación: Verificar que el campo no esté vacío
        if (textoMonto.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el monto de dinero a convertir", Toast.LENGTH_SHORT).show();
            etMonto.requestFocus();
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(textoMonto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingresa un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación: Evitar montos negativos
        if (monto < 0) {
            Toast.makeText(this, "El monto no puede ser negativo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determinar qué opción está seleccionada en cada RadioGroup
        boolean esOrigenPesos = rbOrigenPesos.isChecked();
        boolean esDestinoPesos = rbDestinoPesos.isChecked();

        double resultado;
        String unidadDestino;

        if (esOrigenPesos && !esDestinoPesos) {
            // Conversión: Pesos a Dólares (División entre 4000)
            resultado = monto / TASA_CAMBIO;
            unidadDestino = "USD";
        } else if (!esOrigenPesos && esDestinoPesos) {
            // Conversión: Dólares a Pesos (Multiplicación por 4000)
            resultado = monto * TASA_CAMBIO;
            unidadDestino = "COP";
        } else {
            // Misma moneda (Pesos a Pesos o Dólares a Dólares)
            resultado = monto;
            unidadDestino = esOrigenPesos ? "COP" : "USD";
        }

        // Mostrar el resultado formateado a dos decimales con separador de miles
        String textoFormateado = String.format(Locale.getDefault(), "Resultado: $%,.2f %s", resultado, unidadDestino);
        tvResultado.setText(textoFormateado);
    }
}
