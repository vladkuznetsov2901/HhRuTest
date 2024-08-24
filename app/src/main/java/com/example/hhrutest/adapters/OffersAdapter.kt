package com.example.hhrutest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hhrutest.R
import com.example.hhrutest.data.entities.Offer
import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.databinding.OffersItemBinding
import com.example.hhrutest.databinding.VacancyCardItemBinding
import com.example.hhrutest.presentation.ui.home.ResponseBottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class OffersAdapter :
    ListAdapter<Offer, OffersAdapter.OfferViewHolder>(DiffUtilCallback()) {

    private var onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(
            OffersItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val icons = listOf(R.drawable.avatar, R.drawable.avatar2, R.drawable.avatar3)
        val item = getItem(position)
        with(holder.binding) {
            if (position >= icons.size) avatar.setImageResource(icons[0])
            else avatar.setImageResource(icons[position])
            upResumeText.text = item.title
            if (item.button == null) raiseText.visibility = View.GONE
            else raiseText.text = item.button.text
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item.link)
        }


    }

    fun formatPublishedDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date: Date? = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("dd MMMM", Locale("ru"))

        return date?.let { "Опубликовано ${outputFormat.format(it)}" } ?: "Неизвестная дата"
    }

    interface OnItemClickListener {
        fun onItemClick(link: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class OfferViewHolder(val binding: OffersItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    class DiffUtilCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem == newItem
    }

}