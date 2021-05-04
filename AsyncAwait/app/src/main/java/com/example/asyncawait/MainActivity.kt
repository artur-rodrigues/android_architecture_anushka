package com.example.asyncawait

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Main).launch {
            log("Calculation started....")
            val stock1 = async(IO) {
                getStock1()
            }
            val stock2 = async(IO) {
                getStock2()
            }

            val total = stock1.await() + stock2.await()
            toast("Total is $total")
            log("Total is $total")
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private suspend fun getStock1(): Int {
        wait(10, 1)
        return 55000
    }

    private suspend fun getStock2(): Int {
        wait(8, 2)
        return 35000
    }

    private suspend fun wait(time: Long, stock: Int) {
        delay(time * 1000)
        log("Stock $stock returned")
    }

    private fun log(msg: String) {
        Log.i("MyTag", msg)
    }
}