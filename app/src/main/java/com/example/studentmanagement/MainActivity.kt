package com.example.studentmanagement

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import android.app.AlertDialog

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

        fullNameEditText = findViewById(R.id.full_name)
        studentCodeEditText = findViewById(R.id.student_code)
        addButton = findViewById(R.id.add_btn)
        updateButton = findViewById(R.id.update_btn)
        deleteButton = findViewById(R.id.delete_btn)
        studentListView = findViewById(R.id.data_list)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        studentListView.adapter = adapter

        studentListView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val selectedStudent = studentList[position]
            val parts = selectedStudent.split(" - ")
            if (parts.size == 2) {
                fullNameEditText.setText(parts[0])
                studentCodeEditText.setText(parts[1])
            }
        }

        addButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val studentCode = studentCodeEditText.text.toString()
            if (fullName.isNotEmpty() && studentCode.isNotEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Xác nhận thêm")
                    .setMessage("Bạn có muốn thêm sinh viên này?")
                    .setPositiveButton("Thêm") { _, _ ->
                        studentList.add("$fullName - $studentCode")
                        adapter.notifyDataSetChanged()
                        fullNameEditText.text.clear()
                        studentCodeEditText.text.clear()
                        Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        updateButton.setOnClickListener {
            if (selectedPosition >= 0) {
                val fullName = fullNameEditText.text.toString()
                val studentCode = studentCodeEditText.text.toString()
                if (fullName.isNotEmpty() && studentCode.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setTitle("Xác nhận cập nhật")
                        .setMessage("Bạn có muốn cập nhật thông tin sinh viên?")
                        .setPositiveButton("Cập nhật") { _, _ ->
                            studentList[selectedPosition] = "$fullName - $studentCode"
                            adapter.notifyDataSetChanged()
                            fullNameEditText.text.clear()
                            studentCodeEditText.text.clear()
                            selectedPosition = -1
                            Toast.makeText(this, "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Hủy", null)
                        .show()
                } else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            if (selectedPosition >= 0) {
                AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa sinh viên này?")
                    .setPositiveButton("Xóa") { _, _ ->
                        studentList.removeAt(selectedPosition)
                        adapter.notifyDataSetChanged()
                        fullNameEditText.text.clear()
                        studentCodeEditText.text.clear()
                        selectedPosition = -1
                        Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để xóa", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
