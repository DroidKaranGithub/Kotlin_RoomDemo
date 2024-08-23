package com.kiran.roomdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiran.roomdemo.databinding.ActivityMainBinding
import com.kiran.roomdemo.db.MyRecyclerViewAdapter
import com.kiran.roomdemo.db.Subscriber
import com.kiran.roomdemo.db.SubscriberDatabase
import com.kiran.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        viewModel.message.observe(this) { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            MyRecyclerViewAdapter({ selectedItem: Subscriber -> listItemClicked(selectedItem) })
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscriberList()
    }

    private fun displaySubscriberList() {
        viewModel.subscribers.observe(this) { it ->
            Log.i("MyTag", it.toString())
            adapter.setList(it)
//            adapter.notifyDataSetChanged()
        }
//        viewModel.getAllSubscribers().observe(this, Observer {
//            Log.i("MyTag", it.toString())
//            adapter.setList(it)
//            adapter.notifyDataSetChanged()
//        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
//        Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        viewModel.initUpdateAndDelete(subscriber)
    }
}