package com.example.progmobiledevices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private lateinit var ARG_PARAM1: List<Car>

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var carList: List<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carList = ARG_PARAM1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page_search, container, false)
        print("Search: $carList")
        val arrow_back: ImageView = view.findViewById(R.id.arrow_back)
//        val cars = parseJsonToCars(carList)

        arrow_back.setOnClickListener {
            loadFragment(HomePageFragment())
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val adapter = CarAdapter(carList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        return view
    }

    private fun parseJsonToCars(json: String): List<Car> {
        val gson = Gson()
        val type = object : TypeToken<List<Car>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun loadFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Добавляем в BackStack, чтобы можно было вернуться назад
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(carList: List<Car>) =
            HomePageSearchFragment().apply {
                arguments = Bundle().apply {
                    ARG_PARAM1 = carList
                }
            }
    }
}