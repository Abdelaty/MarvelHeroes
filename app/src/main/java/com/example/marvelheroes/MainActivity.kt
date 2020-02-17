package com.example.marvelheroes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelheroes.ui.main.MainFragment
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
//public key
//ebd094990a30b40e97ab5a73fbf86af8

    //private key
//5cfb621f5407c5143a916c86bb0b674596246a62
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        val input = "15cfb621f5407c5143a916c86bb0b674596246a62ebd094990a30b40e97ab5a73fbf86af8"
        val md5 = input.md5()
        println("computed md5 value is $md5")
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}
