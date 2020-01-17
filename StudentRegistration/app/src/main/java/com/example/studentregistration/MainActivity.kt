package com.example.studentregistration

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.model.Student
import com.example.model.StudentDao
import com.example.model.StudentDataAdapter
import com.example.model.StudentDataBase
import com.example.studentregistration.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 12

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: StudentDataBase
    private lateinit var dao: StudentDao
    private lateinit var adapter: StudentDataAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBase = Room.databaseBuilder(
            this,
            StudentDataBase::class.java,
            "student_database")
            .build()

        dao = dataBase.getStudentDao()

        adapter = StudentDataAdapter()
        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@MainActivity.adapter
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            ItemTouchHelper(TouchHelperCallback(this@MainActivity.adapter){
                removeStudent(it)
            }).attachToRecyclerView(this)
        }

        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddNewStudentActivity::class.java), REQUEST_CODE)
        }

        loadStudents()
    }

    private fun loadStudents() {
        val disposable = dao.findAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.students = it
            }, {
                it.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }

    private fun removeStudent(student: Student) {
        val disposable = dao.deleteStudent(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.removeStudent(student)
            }, {
                it.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadStudents()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    class TouchHelperCallback(private val adapter: StudentDataAdapter,
                              val delete: (Student) -> Unit):
        ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            delete(adapter.getStudent(viewHolder.adapterPosition))
        }
    }
}
