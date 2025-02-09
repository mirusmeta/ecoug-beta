package com.mirus.ekoug

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Все действия выполняются только здесь, заданные для фрагмента Home

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
