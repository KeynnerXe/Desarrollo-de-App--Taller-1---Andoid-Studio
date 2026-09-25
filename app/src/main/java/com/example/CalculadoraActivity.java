package com.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Pantalla de la Calculadora de Créditos Básica.
 * Realiza los cálculos financieros solicitados:
 * - Cuota mensual
 * - Valor total pagado
 * - Ganancia del prestamista (Intereses generados)
 */
public class CalculadoraActivity extends AppCompatActivity {

    // Declaración de variables para los campos de entrada
    private EditText etMontoCredito;
    private EditText etCuotas;
    private EditText etInteres;

    // Declaración del botón de cálculo
    private Button btnCalcularCredito;

    // Declaración de los TextViews para mostrar los resultados
    private TextView tvResultadoCuota;
    private TextView tvResultadoTotal;
    private TextView tvResultadoGanancia;
    private ImageButton btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        // 1. Capturar las vistas mediante sus IDs
        etMontoCredito = findViewById(R.id.etMontoCredito);
        etCuotas = findViewById(R.id.etCuotas);
        etInteres = findViewById(R.id.etInteres);
        btnCalcularCredito = findViewById(R.id.btnCalcularCredito);
        tvResultadoCuota = findViewById(R.id.tvResultadoCuota);
        tvResultadoTotal = findViewById(R.id.tvResultadoTotal);
        tvResultadoGanancia = findViewById(R.id.tvResultadoGanancia);
        btnVolver = findViewById(R.id.btnVolverCalculadora);

        // Botón opcional para regresar a la pantalla anterior
        if (btnVolver != null) {
            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // 2. Configurar el evento de clic en el botón de cálculo
        btnCalcularCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCredito();
            }
        });
    }

    /**
     * Valida los campos de entrada y ejecuta la fórmula matemática requerida.
     */
    private void calcularCredito() {
        String textoMonto = etMontoCredito.getText().toString().trim();
        String textoCuotas = etCuotas.getText().toString().trim();
        String textoInteres = etInteres.getText().toString().trim();

        // 1. Validación de campos vacíos
        if (textoMonto.isEmpty() || textoCuotas.isEmpty() || textoInteres.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos del crédito", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorCredito;
        int numeroCuotas;
        double porcentajeInteres;

        try {
            valorCredito = Double.parseDouble(textoMonto);
            numeroCuotas = Integer.parseInt(textoCuotas);
            porcentajeInteres = Double.parseDouble(textoInteres);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingresa números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Validación de lógica de negocio (evitar división por 0 o valores negativos)
        if (valorCredito <= 0) {
            Toast.makeText(this, "El valor del crédito debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            etMontoCredito.requestFocus();
            return;
        }

        if (numeroCuotas <= 0) {
            Toast.makeText(this, "El número de cuotas debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            etCuotas.requestFocus();
            return;
        }

        if (porcentajeInteres < 0) {
            Toast.makeText(this, "El porcentaje de interés no puede ser negativo", Toast.LENGTH_SHORT).show();
            etInteres.requestFocus();
            return;
        }

        // 3. Fórmulas matemáticas solicitadas:
        // * Valor de cuota = (Valor del crédito / Número de cuotas) + (Valor del crédito * (Interés / 100))
        double cuotaBase = valorCredito / numeroCuotas;
        double valorInteresMensual = valorCredito * (porcentajeInteres / 100.0);
        double valorCuota = cuotaBase + valorInteresMensual;

        // * Valor total del crédito = Valor de cuota * Número de cuotas
        double valorTotalCredito = valorCuota * numeroCuotas;

        // * Ganancia total = Valor total del crédito - Valor del crédito inicial
        double gananciaTotal = valorTotalCredito - valorCredito;

        // 4. Mostrar los tres resultados formateados a dos decimales
        tvResultadoCuota.setText(String.format(Locale.getDefault(), "Valor de cuota: $%,.2f", valorCuota));
        tvResultadoTotal.setText(String.format(Locale.getDefault(), "Valor total del crédito: $%,.2f", valorTotalCredito));
        tvResultadoGanancia.setText(String.format(Locale.getDefault(), "Ganancia total: $%,.2f", gananciaTotal));

        Toast.makeText(this, "Cálculo realizado con éxito", Toast.LENGTH_SHORT).show();
    }
}
