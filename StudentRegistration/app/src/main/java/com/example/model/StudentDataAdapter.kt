package com.example.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregistration.R
import com.example.studentregistration.databinding.StudentListItemBinding


class StudentDataAdapter : RecyclerView.Adapter<StudentViewHolder>() {

    var students: MutableList<Student> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<StudentListItemBinding>(inflater, R.layout.student_list_item, parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    fun getStudent(index: Int): Student {
        return students[index]
    }

    fun removeStudent(student: Student) {
        students.remove(student)
        notifyDataSetChanged()
    }

    fun removeStudent(index: Int) {
        students.removeAt(index)
        notifyDataSetChanged()
    }
}

class StudentViewHolder(private val binding: StudentListItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(student: Student) {
        with(binding) {
            with(student) {
                labelName.text = name
                labelEmail.text = email
                labelCountry.text = country
                labelTime.text = registeredTime
            }
        }
    }
}