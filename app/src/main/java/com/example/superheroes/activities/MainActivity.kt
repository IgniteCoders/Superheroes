package com.example.superheroes.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superheroes.R
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesResponse
import com.example.superheroes.data.SuperheroesServiceApi
import com.example.superheroes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SuperheroAdapter
    private var superheroList:List<Superhero> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            searchSuperheroes(searchText)
        }*/

        adapter = SuperheroAdapter() {
            onItemClickListener(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)

        initSearchView(menu?.findItem(R.id.menu_search))

        return true
    }

    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            var searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchSuperheroes(query!!)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun onItemClickListener(position:Int) {
        val superhero: Superhero = superheroList[position]

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, superhero.id)
        intent.putExtra(DetailActivity.EXTRA_NAME, superhero.name)
        intent.putExtra(DetailActivity.EXTRA_IMAGE, superhero.image.url)
        startActivity(intent)
        //Toast.makeText(this, getString(horoscope.name), Toast.LENGTH_LONG).show()
    }

    private fun searchSuperheroes(query: String) {
        binding.progress.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SuperheroesServiceApi = retrofit.create(SuperheroesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.searchByName(query)

            runOnUiThread {
                // Modificar UI
                binding.progress.visibility = View.GONE

                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    superheroList = response.body()?.results.orEmpty()
                    adapter.updateItems(superheroList)

                    if (superheroList.isNotEmpty()) {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyPlaceholder.visibility = View.GONE
                    } else {
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyPlaceholder.visibility = View.VISIBLE
                    }
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }
            }
        }
    }
}