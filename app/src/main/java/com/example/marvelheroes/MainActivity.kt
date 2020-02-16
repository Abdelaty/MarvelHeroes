package com.example.marvelheroes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelheroes.ui.main.MainFragment

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
    }

}
