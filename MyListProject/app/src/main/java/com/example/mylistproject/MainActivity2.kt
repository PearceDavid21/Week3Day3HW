package com.example.mylistproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mylistproject.adapter.GuestAdapter
import com.example.mylistproject.model.Guests
import com.example.mylistproject.view.MainActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity2 : AppCompatActivity(), GuestAdapter.GuestAdapterDelegate {

    private var guestList = mutableListOf<Guests>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        readFromExternal()

        delete_guest_button.setOnClickListener {
        guestList.size-1 
        }
    }
    private fun readFromExternal() {
        val fileInputStream = openFileInput(MainActivity.fileName2)
        val fileInputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(fileInputStreamReader)

        var readString: String? = null
        val delimiter = ":"

        guestList = mutableListOf()
        while ({ readString = bufferedReader.readLine(); readString }() != null) {
            val myInput = readString?.split(delimiter)
            if (myInput?.size ?: 0 > 1) {
                val readGuest = Guests(
                    myInput?.get(0),
                    Integer.parseInt(myInput?.get(1) ?:"0"),
                    Integer.parseInt(myInput?.get(2) ?:"0")
                )
                guestList.add(readGuest)
            }
        }

        val myBaseAdapter = GuestAdapter(guestList, this)
        hotel_list_view.adapter = myBaseAdapter
    }
    override fun deleteBooking(guestPosition: Int) {
        deleteItem(guestPosition)
    }

    private fun deleteItem(position: Int) {
        guestList.removeAt(position)
        var fileOutputStream = openFileOutput(MainActivity.fileName2, Context.MODE_PRIVATE)
        for (i in 0 until guestList.size) {
            val guestAsString =
                "${guestList.get(i).name}:${guestList.get(i).roomNumber}:${guestList.get(i).price}:"
            if (i == 0)
                fileOutputStream.write(guestAsString.toByteArray())
            else {
                fileOutputStream = openFileOutput(MainActivity.fileName2, Context.MODE_APPEND)
                fileOutputStream.write(guestAsString.toByteArray())
            }
        }
        Toast.makeText(this, "Guest checked out!", Toast.LENGTH_SHORT).show()
        readFromExternal()
    }
}
