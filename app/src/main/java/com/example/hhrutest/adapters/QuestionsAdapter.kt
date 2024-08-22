package com.example.hhrutest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hhrutest.R
import com.example.hhrutest.data.entities.Question
import com.example.hhrutest.data.entities.Vacancy
import com.example.hhrutest.databinding.QuestionItemBinding
import com.example.hhrutest.databinding.VacancyCardItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class QuestionsAdapter(private var questions: List<String>) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            QuestionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        with(holder.binding) {

            questionText.text = question
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(question)
        }
    }

    override fun getItemCount(): Int = questions.size

    fun updateQuestions(newQuestions: List<String>) {
        questions = newQuestions
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(question: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class QuestionViewHolder(val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
