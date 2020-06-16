package com.adarsh.weatherapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//While using Retrofit, no need to use OkHttp and GSON

class MainActivity : AppCompatActivity() {
    val originalList = arrayListOf<User>()
    val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.onItemClick = {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            intent.putExtra("ID", it)
            startActivity(intent)
        }

        RV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        //Retrofit work
        GlobalScope.launch(Dispatchers.Main) {

            val response = withContext(Dispatchers.IO) {
                Client.api.getUsers()
            }
            if(response.isSuccessful){
                response.body()?.let {
                    originalList.addAll(it)
                    adapter.swapData(it)
                }
            }
        }

        //Searching Users
        search_view.isSubmitButtonEnabled = true
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchUser(newText)
                }
                return true
            }

        })

        search_view.setOnCloseListener {
            adapter.swapData(originalList)
            true
        }


        //OKHTTP and JSON and GSON and Picasso Work
       /* val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.github.com/users/2001adarsh")
            //.url("http://api.weatherstack.com/current?access_key=35451e4a1d425e4905283829f50283a5&query=New%20York")
            .build()

       val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()


        GlobalScope.launch(Dispatchers.Main) {

          val response =  withContext(Dispatchers.IO){
                okHttpClient.newCall(request).execute().body?.string()
          }
           // Log.i("Networking", "${response.body?.string() }")
    */
        /*        val obj = JSONObject(response)

            val image = obj.getString("avatar_url")
            val login = obj.getString("login")
            val name = obj.getString("name")
*//*
            val user = gson.fromJson<User>(response, User::class.java)

            textView.text = user.name
            textView2.text = user.login

            //Image setting using Picasso
            Picasso.get().load(user.avatarUrl).into(imageView)
        }
*/
    }

    fun searchUser(query: String){
        GlobalScope.launch(Dispatchers.Main) {

            val response = withContext(Dispatchers.IO) {
                Client.api.searchUser(query)
            }
            if(response.isSuccessful){
                response.body().let {
                    it?.items?.let { it1 -> adapter.swapData(it1) }
                }
            }
        }
    }
}