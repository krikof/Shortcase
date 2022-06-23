package com.example.shortcaserc.ui.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shortcaserc.database.ComicObject
import com.example.shortcaserc.databinding.ComicFavoriteItemBinding
import com.example.shortcaserc.utils.Utils

class FavoritesAdapter(private var dataSet: List<ComicObject>?) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    inner class FavoritesViewHolder(val binding: ComicFavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = FavoritesViewHolder(
            ComicFavoriteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        parent.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return view
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val utils: Utils = Utils()

        if (dataSet != null) {

            val date = try {
                utils.dateFormatter(
                    dataSet!![position].day.toInt(),
                    dataSet!![position].month.toInt(),
                    dataSet!![position].year.toInt()
                )
            } catch (e: Error) {
                Log.e("Date Forammting", "Something went wrong while formatting")
            }

            holder.binding.favoriteComicImage.load(dataSet!![position].localImage)

            holder.binding.favoriteComicTitle.text = dataSet!![position].title
            holder.binding.favoriteComicPublished.text = "Published: $date"
            holder.binding.favoriteComicIssue.text = "Comic issue: ${dataSet!![position].num}"

            // TODO - Set onCrossClick listener for all the views, open fullscreen fragment or activity
            // TODO - create CustomViewClass for entire shit, it is whack rn
        }
    }

    override fun getItemCount(): Int {
        return if (dataSet != null) {
            dataSet!!.size
        } else {
            0
        }
    }

    fun updateData(newData: List<ComicObject>) {
        dataSet = newData
        notifyDataSetChanged()
    }

}