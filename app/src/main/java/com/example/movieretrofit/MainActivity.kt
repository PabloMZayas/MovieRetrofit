package com.example.movieretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movieretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogIn.setOnClickListener { getRetrofit() }
    }

    private fun getRetrofit() : List<UsersItem> {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(UsersApi::class.java)

        val retrofitData = retrofitBuilder.getUsers()
        var myResponse = mutableListOf<UsersItem>()
        retrofitData.enqueue(object : Callback<List<UsersItem>?> {
            override fun onResponse(
                call: Call<List<UsersItem>?>,
                response: Response<List<UsersItem>?>
            ) {
                myResponse = response.body() as MutableList<UsersItem>
                logIn(myResponse)
            }

            override fun onFailure(call: Call<List<UsersItem>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failure", Toast.LENGTH_SHORT).show()
                //myResponse = emptyList()
            }
        })
        return myResponse
    }

    private fun logIn(myResponse: List<UsersItem>) {
        val user = binding.userName.text.toString()
        val password = binding.userPassword.text.toString()


        val listPasswords = mutableListOf<String>()
        val listUsers = mutableListOf<String>()

        for ((i, value) in myResponse.withIndex()){
            listPasswords.add(value.username)
            listUsers.add(value.id.toString())
        }

        var aux = 0
        for(i in 0 until listUsers.size){
            if (user == listUsers[i] && password == listPasswords[i]){
                aux = 1
                Toast.makeText(this, "logged", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoggedActivity::class.java)
                    startActivity(intent)
            }
        }
        if(aux == 0){
            Toast.makeText(this, "wrong password", Toast.LENGTH_SHORT).show()
        }
    }
}