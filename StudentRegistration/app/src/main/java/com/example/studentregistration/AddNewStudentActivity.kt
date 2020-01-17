package com.example.studentregistration

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.model.Student
import com.example.model.StudentDao
import com.example.model.StudentDataBase
import com.example.studentregistration.databinding.ActivityAddNewStudentBinding
import com.example.util.texto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class AddNewStudentActivity: AppCompatActivity() {

    lateinit var bind: ActivityAddNewStudentBinding
    private lateinit var dataBase: StudentDataBase
    private lateinit var dao: StudentDao
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_add_new_student)

        dataBase = Room.databaseBuilder(
            this,
            StudentDataBase::class.java,
            "student_database")
            .build()

        dao = dataBase.getStudentDao()

        bind.btnSubmit.setOnClickListener {
            addStudent()
        }
    }

    private fun addStudent() {
        val student = Student(
            studentId = 0,
            name = bind.etName.texto(),
            email = bind.etEmail.texto(),
            country = bind.etCountry.texto(),
            registeredTime = SimpleDateFormat("dd/MM/yyy HH:mm:ss",
                Locale.getDefault()).format(Date())
        )

        disposable = dao.insertStudent(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setResult(Activity.RESULT_OK)
                finish()
            }, {
                it.printStackTrace()
                setResult(Activity.RESULT_CANCELED)
                finish()
            })
    }

    override fun onDestroy() {
        if (::disposable.isInitialized &&
            ! disposable.isDisposed) {
            disposable.dispose()
        }
        super.onDestroy()
    }
}