package com.example.mylistproject.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mylistproject.MainActivity2
import com.example.mylistproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    companion object{
         val fileName2 = "MyGuestsList.txt"

    }
   // private var guestList = mutableListOf<Guests>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        check_in_button.setOnClickListener { _ ->
            writeToFile()
            val userIntent = Intent(this, MainActivity2::class.java)
            startActivity(userIntent)
        }
    }

    private fun writeToFile() {
        val fileOutputStream = openFileOutput(fileName2, Context.MODE_APPEND)
        val inputString =
            "\n${name_edittext.text}:${room_number_edit_text.text}:${price_edit_text.text}"
        fileOutputStream.write(inputString.toByteArray())
        Toast.makeText(
            this,
            "Room #${room_number_edit_text.text} checked into!",
            Toast.LENGTH_SHORT
        ).show()

        clearFields()
    }


    private fun clearFields() {
        name_edittext.text.clear()
        room_number_edit_text.text.clear()
        price_edit_text.text.clear()
    }

}
