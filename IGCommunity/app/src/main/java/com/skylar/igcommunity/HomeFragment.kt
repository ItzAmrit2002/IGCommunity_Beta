package com.skylar.igcommunity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment: Fragment(), AdapterView.OnItemSelectedListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ArrayAdapter.createFromResource(requireContext(), R.array.spinner_array, android.R.layout.simple_spinner_item)
//            .also { adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                spinner.adapter
//                spinner.onItemSelectedListener
//            }
//        android.R.layout.simple_spinner_dropdown_item
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (spinner.editText as? AutoCompleteTextView)?.setAdapter(adapter)

    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }
    override fun onItemSelected(parent: AdapterView<*>, view: View,position: Int, id: Long) {

    }
}

/*    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.home_fragment, container, false)
        val values : Array<String> = arrayOf("USD", "UAH", "GBD", "EUR", "BIT", "RUB")
        var data = ArrayList<String>()

        data.add("USD")
        data.add("RUB")

        spinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data)


        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val values : Array<String> = arrayOf("USD", "UAH", "GBD", "EUR", "BIT", "RUB")
        var data = ArrayList<String>()
        data.add("USD")
        data.add("RUB")
        spinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data)
    }*/
