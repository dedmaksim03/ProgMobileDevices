package com.example.progmobiledevices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFooter.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFooter : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    interface OnFooterClickListener {
        fun onFooterClicked(page: String)
    }

    private var listener: OnFooterClickListener? = null

    fun setOnFooterClickListener(listener: OnFooterClickListener) {
        this.listener = listener
    }

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
        val view = inflater.inflate(R.layout.fragment_main_footer, container, false)

        val settings: ImageView = view.findViewById(R.id.settings)
        val home: ImageView = view.findViewById(R.id.home)
        val bookmark: ImageView = view.findViewById(R.id.bookmark)

        settings.setOnClickListener {
            listener?.onFooterClicked("settings")
            settings.setImageResource(R.drawable.settings_active)
            bookmark.setImageResource(R.drawable.bookmark)
            home.setImageResource(R.drawable.home)
        }

        home.setOnClickListener {
            listener?.onFooterClicked("home")
            settings.setImageResource(R.drawable.settings)
            bookmark.setImageResource(R.drawable.bookmark)
            home.setImageResource(R.drawable.home_active)
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFooter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFooter().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}