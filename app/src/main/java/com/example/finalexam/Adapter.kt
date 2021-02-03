package com.example.finalexam


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class Adapter(private val info: List<UserInfo>,private val context: Context )
    :RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView2: ImageView = itemView.findViewById(R.id.imageView2)
        val textView: TextView = itemView.findViewById(R.id.nameTextView)
        val ageTextView : TextView = itemView.findViewById(R.id.ageTextView)
        val statusTextView : TextView = itemView.findViewById(R.id.statusTextView)
        val hobbiesTextView : TextView = itemView.findViewById(R.id.hobbiesTextView)
        val workTextView : TextView = itemView.findViewById(R.id.workTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = info.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = info[position]
        holder.textView.text = p.name
        holder.ageTextView.text = p.age
        holder.statusTextView.text = p.status
        holder.hobbiesTextView.text = p.hobbies
        holder.workTextView.text = p.work
        Glide.with(context)
            .load(p.url)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(holder.imageView2)
    }
}