package com.example.borys.boryschajdas

import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private fun MutableList<Photo>.replaceAllItems(newItems: List<Photo>){

        for(i in newItems.indices){
            if(i < this.size){
                this[i] = newItems[i]
            }
            else{
                this.add(newItems[i])
            }
        }
    }

    private val photoItems = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item_layout, parent, false)
        )
    }

    override fun getItemCount() = photoItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        photoItems[position].run {
            holder.photoTitle.text = title
            holder.photoDescription.text = desc
        }

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.start()

        Glide.with(holder.itemView.context)
                .load(photoItems[position].url)
                .apply(RequestOptions()
                        .placeholder(circularProgressDrawable))
                .into(holder.photo)
    }

    fun updateItems(newItems: List<Photo>){

        photoItems.replaceAllItems(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoTitle: TextView = view.findViewById(R.id.itemTitle_textView)
        val photoDescription: TextView = view.findViewById(R.id.itemDesc_textView)
        val photo: ImageView = view.findViewById(R.id.item_photo)
    }
}