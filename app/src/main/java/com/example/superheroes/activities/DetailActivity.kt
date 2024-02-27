package com.example.superheroes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.superheroes.R
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesServiceApi
import com.example.superheroes.databinding.ActivityDetailBinding
import com.example.superheroes.utils.Constants
import com.example.superheroes.utils.RetrofitProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "SUPERHERO_ID"
        const val EXTRA_NAME = "SUPERHERO_NAME"
        const val EXTRA_IMAGE = "SUPERHERO_IMAGE"
    }

    private var superheroId:String? = null
    private lateinit var superhero:Superhero


    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        superheroId = intent.getStringExtra(EXTRA_ID)
        val name = intent.getStringExtra(EXTRA_NAME)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        binding.toolbarLayout.title = name
        Picasso.get().load(image).into(binding.photoImageView)

        findSuperheroById(superheroId!!)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)

        // Show back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        //val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.toolbar_layout)
        //collapsingToolbar.title = "Your Title"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun findSuperheroById(id: String) {
        //binding.progress.visibility = View.VISIBLE

        val service: SuperheroesServiceApi = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.findById(id)

            runOnUiThread {
                // Modificar UI
                //binding.progress.visibility = View.GONE
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    superhero = response.body()!!
                    binding.toolbarLayout.title = superhero.name
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }
            }
        }
    }
}