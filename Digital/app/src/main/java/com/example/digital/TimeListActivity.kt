package com.example.digital
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digital.DatabaseHelper
import com.example.digital.R
import com.example.digital.TimeListAdapter
import com.example.digital.TimeRecord

class TimeListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var timeListAdapter: TimeListAdapter
    private lateinit var timeRecords: List<TimeRecord>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_list)

        recyclerView = findViewById(R.id.recyclerViewTimeList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obter os registros de horários do banco de dados
        timeRecords = DatabaseHelper(this).getAllTimeRecords()

        // Inicializar o adapter com os registros de horários
        timeListAdapter = TimeListAdapter(timeRecords)

        // Configurar o RecyclerView com o adapter
        recyclerView.adapter = timeListAdapter
    }
}
