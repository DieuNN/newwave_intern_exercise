package com.dieunn.NewwaveInternExercise.adapters

import android.app.ActionBar.LayoutParams
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.dieunn.NewwaveInternExercise.Constants
import com.dieunn.NewwaveInternExercise.R
import com.dieunn.NewwaveInternExercise.databinding.MovieItemBinding
import com.dieunn.NewwaveInternExercise.models.Movie
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

class MovieAdapter(var data: Set<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(val itemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Using view binding
        // https://developer.android.com/topic/libraries/view-binding
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding = itemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data.toList()
        // Load images using Coil library
        // https://coil-kt.github.io/coil/
        holder.itemBinding.image.load("${Constants.BASE_IMAGE_URL}${data[position].posterPath}") {
            crossfade(true)
            placeholder(R.drawable.no_image)
        }


        holder.itemBinding.name.text = data[position].originalTitle.uppercase()

        // Using Kotlin extension func to get release date, example: "2022-06-01".toLocalDate().year return year
        // https://github.com/Kotlin/kotlinx-datetime
        holder.itemBinding.releaseYear.text =
            data[position].releaseDate.toLocalDate().year.toString()

        holder.itemBinding.score.text = data[position].voteAverage.toString()
    }

    fun refreshData(newData : Set<Movie>) {
        data = emptySet()
        data = newData
        notifyDataSetChanged()
        notifyItemRangeInserted(0, newData.size)
    }

}
