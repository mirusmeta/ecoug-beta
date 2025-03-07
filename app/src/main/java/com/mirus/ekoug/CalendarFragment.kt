package com.mirus.ekoug

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Calendar

class CalendarFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var no_events:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById(R.id.calendarView)
        no_events = view.findViewById(R.id.no_events)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfCurrentMonth = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endOfNextMonth = calendar.timeInMillis

        calendarView.minDate = startOfCurrentMonth
        calendarView.maxDate = endOfNextMonth

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
        }
        val db = Firebase.firestore

        db.collection("События/Ростовская Область/Ростов-на-Дону").get().addOnSuccessListener {
            //if (it.isEmpty){
                no_events.visibility = View.VISIBLE
            //}

        }


    }
}
