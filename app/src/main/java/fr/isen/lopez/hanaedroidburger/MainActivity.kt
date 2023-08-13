package fr.isen.lopez.hanaedroidburger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.lopez.hanaedroidburger.accueil.AccueilFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val accueilFragment = AccueilFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, accueilFragment)
                .commit()
        }
    }

}