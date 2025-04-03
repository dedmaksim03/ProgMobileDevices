package com.example.progmobiledevices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var ARG_PARAM1: List<Car>

/**
 * A simple [Fragment] subclass.
 * Use the [HomePageLoadingScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageLoadingScreenFragment : Fragment() {
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
        // Inflate the layout for this fragment
//        print("Loading: $json")
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            loadFragment(HomePageSearchFragment.newInstance(carList))
        }
        return inflater.inflate(R.layout.fragment_home_page_loading_screen, container, false)
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
            HomePageLoadingScreenFragment().apply {
                arguments = Bundle().apply {
                    ARG_PARAM1 = carList
                }
            }
    }
}