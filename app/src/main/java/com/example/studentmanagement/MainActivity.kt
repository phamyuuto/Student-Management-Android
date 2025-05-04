package com.example.studentmanagement

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var studentCodeEditText: EditText
    private lateinit var addButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var studentListView: ListView

    private val studentList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các view từ layout
        fullNameEditText = findViewById(R.id.full_name)
        studentCodeEditText = findViewById(R.id.student_code)
        addButton = findViewById(R.id.add_btn)
        updateButton = findViewById(R.id.update_btn)
        deleteButton = findViewById(R.id.delete_btn)
        studentListView = findViewById(R.id.data_list)

        // Khởi tạo adapter cho ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        studentListView.adapter = adapter

        // Xử lý sự kiện khi chọn item trong ListView
        studentListView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val selectedStudent = studentList[position]
            // Giả định định dạng "Họ tên - MSSV"
            val parts = selectedStudent.split(" - ")
            if (parts.size == 2) {
                fullNameEditText.setText(parts[0])
                studentCodeEditText.setText(parts[1])
            }
        }

        // Xử lý sự kiện nút Add
        addButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val studentCode = studentCodeEditText.text.toString()
            if (fullName.isNotEmpty() && studentCode.isNotEmpty()) {
                studentList.add("$fullName - $studentCode")
                adapter.notifyDataSetChanged()
                fullNameEditText.text.clear()
                studentCodeEditText.text.clear()
            }
        }

        // Xử lý sự kiện nút Update
        updateButton.setOnClickListener {
            if (selectedPosition >= 0) {
                val fullName = fullNameEditText.text.toString()
                val studentCode = studentCodeEditText.text.toString()
                if (fullName.isNotEmpty() && studentCode.isNotEmpty()) {
                    studentList[selectedPosition] = "$fullName - $studentCode"
                    adapter.notifyDataSetChanged()
                    fullNameEditText.text.clear()
                    studentCodeEditText.text.clear()
                    selectedPosition = -1
                }
            }
        }

        // Xử lý sự kiện nút Delete
        deleteButton.setOnClickListener {
            if (selectedPosition >= 0) {
                studentList.removeAt(selectedPosition)
                adapter.notifyDataSetChanged()
                fullNameEditText.text.clear()
                studentCodeEditText.text.clear()
                selectedPosition = -1
            }
        }
    }
}

