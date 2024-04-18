package com.example.digital
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var editTextEntryTime: EditText
    private lateinit var editTextLunchOutTime: EditText
    private lateinit var editTextLunchInTime: EditText
    private lateinit var editTextExitTime: EditText
    private lateinit var textViewCurrentTime: TextView
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var clickedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEntryTime = findViewById(R.id.editTextEntryTime)
        editTextLunchOutTime = findViewById(R.id.editTextLunchOutTime)
        editTextLunchInTime = findViewById(R.id.editTextLunchInTime)
        editTextExitTime = findViewById(R.id.editTextExitTime)
        textViewCurrentTime = findViewById(R.id.textViewCurrentTime)

        setupBiometricPrompt()

        val buttonEntry = findViewById<Button>(R.id.buttonEntry)
        val buttonLunchOut = findViewById<Button>(R.id.buttonLunchOut)
        val buttonLunchIn = findViewById<Button>(R.id.buttonLunchIn)
        val buttonExit = findViewById<Button>(R.id.buttonExit)
        val buttonRegisterTime = findViewById<Button>(R.id.buttonRegisterTime)

        buttonEntry.setOnClickListener {
            clickedButton = buttonEntry
            showBiometricPrompt()
        }
        buttonLunchOut.setOnClickListener {
            clickedButton = buttonLunchOut
            showBiometricPrompt()
        }
        buttonLunchIn.setOnClickListener {
            clickedButton = buttonLunchIn
            showBiometricPrompt()
        }
        buttonExit.setOnClickListener {
            clickedButton = buttonExit
            showBiometricPrompt()
        }

        buttonRegisterTime.setOnClickListener {
            registerTime()
        }

        val buttonTimeList = findViewById<Button>(R.id.buttonTimeList)
        buttonTimeList.setOnClickListener {
            startActivity(Intent(this, TimeListActivity::class.java))
        }

    }

    private fun setupBiometricPrompt() {
        val executor = Executor { runnable -> runOnUiThread(runnable) }
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                registerHours()
            }
        }
        biometricPrompt = BiometricPrompt(this, executor, callback)
    }

    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticar para Registrar Hora")
            .setNegativeButtonText("Cancelar")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    private fun registerHours() {
        // Obtenha a hora e data atual
        val currentTime = Calendar.getInstance().time
        val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentTime)

        // Exibe a hora e data atual no EditText correspondente
        when (clickedButton.id) {
            R.id.buttonEntry -> editTextEntryTime.setText(formattedTime)
            R.id.buttonLunchOut -> editTextLunchOutTime.setText(formattedTime)
            R.id.buttonLunchIn -> editTextLunchInTime.setText(formattedTime)
            R.id.buttonExit -> editTextExitTime.setText(formattedTime)
        }

        // Exibe a hora e data atual na TextView
        textViewCurrentTime.text = formattedTime
    }

    private fun registerTime() {
        try {
            // Converte as strings dos EditTexts para objetos Date
            val entryTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(editTextEntryTime.text.toString())
            val lunchOutTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(editTextLunchOutTime.text.toString())
            val lunchInTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(editTextLunchInTime.text.toString())
            val exitTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse(editTextExitTime.text.toString())

            // Armazenar os horários no banco de dados
            val dbHelper = DatabaseHelper(this)
            dbHelper.insertTimeEntry(entryTime, lunchOutTime, lunchInTime, exitTime)

            // Notificar o usuário que os horários foram cadastrados
            Toast.makeText(this, "Horários cadastrados com sucesso!", Toast.LENGTH_SHORT).show()
        } catch (e: ParseException) {
            // Se ocorrer um erro ao converter as strings para Date, exibir uma mensagem de erro
            Toast.makeText(this, "Erro ao converter a hora. Certifique-se de inserir no formato correto.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
