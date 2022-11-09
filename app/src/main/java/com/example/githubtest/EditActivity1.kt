package com.example.githubtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.githubtest.databinding.ActivityEdit1Binding

class EditActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityEdit1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdit1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val myView :MyView = findViewById(R.id.myView)

        /*
        binding.choosePenColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if (it.isNotEmpty()){
                        MyView.changeColor(it)
                    }
                }
            }
        }*/

        binding.colortest.setOnClickListener {
            myView.changeColor("黄")
        }
    }
}