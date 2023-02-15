package cristian.app.unsplashapidesafio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import cristian.app.unsplashapidesafio.R
import cristian.app.unsplashapidesafio.adapter.FotosAdapter
import cristian.app.unsplashapidesafio.databinding.ActivityMainBinding
import cristian.app.unsplashapidesafio.model.FotoResponse
import cristian.app.unsplashapidesafio.model.SearchResponse
import cristian.app.unsplashapidesafio.service.RetrofitInstance
import kotlinx.coroutines.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val retrofitService by lazy { RetrofitInstance.service }

    private lateinit var adapterFoto: FotosAdapter
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configToolbar()

        adapterFoto = FotosAdapter()
        binding.rvFotos.adapter = adapterFoto
        binding.rvFotos.layoutManager = GridLayoutManager(
            this, 2, RecyclerView.VERTICAL, false
        )

        binding.btnPesquisar.setOnClickListener {
            getSearch()
            binding.editPesquisa.setText("")
        }
    }

    private fun getSearch() {

        CoroutineScope(Dispatchers.IO).launch {

            val campoPesquisa = binding.editPesquisa.text.toString()

            if (campoPesquisa.isNotEmpty()) {
                var responseSearch: Response<SearchResponse>? = null
                try {
                    responseSearch = retrofitService.getSearchPhoto(campoPesquisa)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("info_foto", "Erro ao pesquisar fotos - MESSAGE: ${e.message}")
                }

                if (responseSearch != null && responseSearch.isSuccessful) {

                    val listSearch = responseSearch.body()?.results
                    if (listSearch != null) {
                        withContext(Dispatchers.Main) {
                            adapterFoto.getResultApi(listSearch)
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "É necessário um valor para o campo pesquisa",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun getListPhotos() {

        job = CoroutineScope(Dispatchers.IO).launch {

            var response: Response<FotoResponse>? = null
            try {
                response = retrofitService.getListPhotos()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("info_foto", "Erro ao recuperar lista de fotos - MESSAGE: ${e.message}")
            }

            if (response != null && response.isSuccessful) {

                val fotoResponse = response.body()
                if (fotoResponse != null) {
                    withContext(Dispatchers.Main) {
                        adapterFoto.getResultApi(fotoResponse)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_sair) {
            auth.signOut()
            Toast.makeText(
                this,
                "Você foi deslogado",
                Toast.LENGTH_LONG
            ).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return true
    }

    private fun configToolbar() {
        supportActionBar?.title = "Unsplash"
    }

    override fun onStart() {
        super.onStart()
        getListPhotos()
        Log.i("info_ciclo_vida", "onStart: CHAMADO")
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
}