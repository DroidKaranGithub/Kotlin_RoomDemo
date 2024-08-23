package com.kiran.roomdemo.db

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kiran.roomdemo.R
import com.kiran.roomdemo.databinding.ListItemBinding

class MyRecyclerViewAdapter(
     private val clickListener: (Subscriber) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = subscribersList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }

 fun setList(subscribers: List<Subscriber>){
     subscribersList.clear()
     subscribersList.addAll(subscribers)
 }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.txtName.text = subscriber.name
        binding.txtEmail.text = subscriber.email
        binding.listItem.setOnClickListener {
            clickListener(subscriber)
        }
    }
}