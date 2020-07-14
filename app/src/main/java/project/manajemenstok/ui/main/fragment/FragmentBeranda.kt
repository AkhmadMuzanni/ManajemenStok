package project.manajemenstok.ui.main.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import project.manajemenstok.R
/**
 * A simple [Fragment] subclass.
 */
class FragmentBeranda : Fragment() {

    private lateinit var viewFragmentBeranda: View
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmen
        viewFragmentBeranda = inflater.inflate(R.layout.fragment_beranda, container, false)

        return viewFragmentBeranda
    }


}
