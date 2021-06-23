package com.surgatutorial.latihanrecyclerview

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.surgatutorial.latihanrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    lateinit var foodsAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setSupportActionBar(bind.toolbar)
        val listFoods = listOf(
            Food(name = "Ayam Cabe Bumbu Kemangi","40 menit", image = R.drawable.ayam_cabe),
            Food(name = "Getuk Gulung Keju","45 menit", image = R.drawable.getuk),
            Food(name = "Ikan Krispi dengan Saus Santan Pedas","30 menit", image = R.drawable.ikan_krispy),
            Food(name = "Kare Ayam Krispi","50 menit", image = R.drawable.kare_ayam),
            Food(name = "Es Krim Kelapa dengan Cherry & Almon","25 menit", image = R.drawable.es_krim),
            Food(name = "Plecing Ayam","45 menit", image = R.drawable.plecing),
            Food(name = "Sayur Labu Udang","40 menit", image = R.drawable.sayur_labu),
            Food(name = "Udang Paprika Sambal","30 menit", image = R.drawable.udang),
            Food(name = "Udang Panggang Saus Peri-Peri","30 menit", image = R.drawable.udang_panggang),
            Food(name = "Daging Pedas Ala Thailand","30 menit", image = R.drawable.daging_pedas)
        )

        foodsAdapter = FoodAdapter(listFoods)

        bind.rvMain.setHasFixedSize(true)
        bind.rvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = foodsAdapter
        }
        foodsAdapter.setOnItemClickCallback(object : FoodAdapter.OnItemClickCallback{
            override fun onItemClicked(food: Food) {
                Toast.makeText(this@MainActivity, "${food.name} di pilih", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Keyword ..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                foodsAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                foodsAdapter.filter.filter(query)
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.share_action) {
            Toast.makeText(this@MainActivity, "Icon Toolbar di click", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}