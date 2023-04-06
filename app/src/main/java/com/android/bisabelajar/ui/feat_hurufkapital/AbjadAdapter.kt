package com.android.bisabelajar.ui.feat_hurufkapital

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.bisabelajar.databinding.ItemAbjadBinding
import com.android.bisabelajar.ui.listener.adapter.AdapterListener
import com.android.bisabelajar.data.model.Abjad


class AbjadAdapter(val listener: AdapterListener): RecyclerView.Adapter<AbjadAdapter.RecentAdapterViewHolder>() {
    inner class RecentAdapterViewHolder(val view: View) :
        RecyclerView.ViewHolder(view)

    private val diffCallback = object : DiffUtil.ItemCallback<Abjad>() {
        override fun areItemsTheSame(oldItem: Abjad, newItem: Abjad): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Abjad, newItem: Abjad): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(list: ArrayList<Abjad?>?) {

        differ.submitList(list) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapterViewHolder {
        //return binding
        val binding = ItemAbjadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentAdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecentAdapterViewHolder, position: Int) {
        holder.view.apply {
            val data = differ.currentList[position]
            Log.d("TAG", "onBindViewHolder: $data")
            val binding = ItemAbjadBinding.bind(this)
            binding.txtAbjad.text = data?.abjad
            rootView.setOnClickListener {
                listener.onClick(data?.abjad, position, binding.root)
            }
        }
    }



    override fun getItemCount(): Int = differ.currentList.size

}

