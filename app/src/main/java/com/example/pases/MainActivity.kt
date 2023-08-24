package com.example.pases


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.pases.adapter.CountryAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var countryApi: CountryService
    lateinit var listaCountries: RecyclerView
    lateinit var searchView: SearchView
    private val adapter by lazy { CountryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupRetrofit()
        getAllCountries()
        listaCountries.adapter = adapter



    }


    fun setupView() {
        listaCountries = findViewById(R.id.rv_lista_repositories)
        searchView = findViewById(R.id.sv_pesquisar)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filter(it) }
                return true
            }
        })

    }

    fun setupRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor) //o loggin tem que ser o ultimo a ser adicionado
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        countryApi = retrofit.create(CountryService::class.java)
    }

    fun getAllCountries() {
        countryApi.getAllCountries().enqueue(object : Callback<List<Country>> {

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.updateList(it)
                    }
                } else {
                    Toast.makeText(this@MainActivity, R.string.response_error, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Toast.makeText(this@MainActivity, R.string.response_error2, Toast.LENGTH_LONG)
                    .show()

            }


        })

    }




}