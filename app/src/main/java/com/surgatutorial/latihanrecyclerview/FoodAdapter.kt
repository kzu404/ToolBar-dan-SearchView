package com.surgatutorial.latihanrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.surgatutorial.latihanrecyclerview.databinding.FoodListBinding
import java.util.*
import kotlin.collections.ArrayList


class FoodAdapter(private val listFood: List<Food>) : RecyclerView.Adapter<FoodAdapter.FoodHolder>(), Filterable {
    private var foodFilterList: List<Food>
    init {
        foodFilterList = listFood
    }


    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FoodHolder {
        val binding = FoodListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FoodHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.bind(foodFilterList[position])
    }

    override fun getItemCount(): Int = foodFilterList.size


    inner class FoodHolder(private val binding: FoodListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            with(binding){
                Glide.with(itemView.context)
                    .load(food.image)
                    .apply(RequestOptions().override(70, 70))
                    .into(imgFood)

                tvName.text = food.name
                tvTime.text = food.time

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(food) }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                foodFilterList = if (charSearch.isEmpty()) {
                    listFood
                } else {
                    val resultList = ArrayList<Food>()
                    for (row in listFood) {
                        if (row.name.lowercase(Locale.getDefault()).contains(constraint.toString()
                                .lowercase(Locale.getDefault()))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = foodFilterList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                foodFilterList = results?.values as List<Food>
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(food: Food)
    }

}