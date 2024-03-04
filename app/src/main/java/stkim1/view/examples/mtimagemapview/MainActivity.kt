package stkim1.view.examples.mtimagemapview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import stkim1.view.examples.mtimagemapview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flipVisibility.setOnClickListener { _ ->
        }
    }
}