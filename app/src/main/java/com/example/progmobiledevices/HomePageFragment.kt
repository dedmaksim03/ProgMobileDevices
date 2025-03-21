package com.example.progmobiledevices

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        val search: EditText = view.findViewById(R.id.search)

        // Дополнительная логика, если нужна
        search.setOnKeyListener { message, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

                val json = loadJSONFromAsset("cars.json")
                print(json)
                val cars = parseJsonToCars(json)

                val adapter = CarAdapter(cars)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val json = loadJSONFromAsset("cars.json")
        print(json)
        val cars = parseJsonToCars(json)

        val adapter = CarAdapter(cars)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        return view
    }

    private fun loadJSONFromAsset(filename: String): String {
        val inputStream = requireContext().assets.open(filename)
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun parseJsonToCars(json: String): List<Car> {
        val gson = Gson()
        val type = object : TypeToken<List<Car>>() {}.type
        return gson.fromJson(json, type)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}