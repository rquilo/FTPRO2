package com.rodrigo_quilodran_eval1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.rodrigo_quilodran_eval1.model.CuentaMesa
import com.rodrigo_quilodran_eval1.model.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var cuentaMesa: CuentaMesa
    private val itemPastel = ItemMenu("Pastel de Choclo", "$12.000")
    private val itemCazuela = ItemMenu("Cazuela", "$10.000")
    private lateinit var etPastelCant: EditText
    private lateinit var etCazuelaCant: EditText
    private lateinit var swPropina: SwitchCompat
    private lateinit var tvPastelSubtotal: TextView
    private lateinit var tvCazuelaSubtotal: TextView
    private lateinit var tvComidaTotal: TextView
    private lateinit var tvPropinaTotal: TextView
    private lateinit var tvTotalFinal: TextView

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cuentaMesa = CuentaMesa(1)

        // Inicializar vistas
        etPastelCant = findViewById(R.id.etPastelCant)
        etCazuelaCant = findViewById(R.id.etCazuelaCant)
        swPropina = findViewById(R.id.swPropina)
        tvPastelSubtotal = findViewById(R.id.tvPastelSubtotal)
        tvCazuelaSubtotal = findViewById(R.id.tvCazuelaSubtotal)
        tvComidaTotal = findViewById(R.id.tvComidaTotal)
        tvPropinaTotal = findViewById(R.id.tvPropinaTotal)
        tvTotalFinal = findViewById(R.id.tvTotalFinal)

        setupListeners()
    }

    private fun setupListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                actualizarCuenta()
            }
        }

        etPastelCant.addTextChangedListener(textWatcher)
        etCazuelaCant.addTextChangedListener(textWatcher)

        swPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarCuenta()
        }
    }

    private fun actualizarCuenta() {
        val cantPastel = etPastelCant.text.toString().toIntOrNull() ?: 0
        val cantCazuela = etCazuelaCant.text.toString().toIntOrNull() ?: 0

        cuentaMesa.agregarItem(itemPastel, cantPastel)
        cuentaMesa.agregarItem(itemCazuela, cantCazuela)

        // Calcular subtotales individuales para la UI
        val subtotalPastel = cantPastel * 12000
        val subtotalCazuela = cantCazuela * 10000

        tvPastelSubtotal.text = format.format(subtotalPastel)
        tvCazuelaSubtotal.text = format.format(subtotalCazuela)

        // Totales
        tvComidaTotal.text = format.format(cuentaMesa.calcularTotalSinPropina())
        tvPropinaTotal.text = format.format(cuentaMesa.calcularPropina())
        tvTotalFinal.text = format.format(cuentaMesa.calcularTotalConPropina())
    }
}
