package com.mirus.ekoug

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class StatesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val rssUrl = "https://161.ru/rss-feeds/rss.xml"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_states, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.IO).launch {
            val newsList = loadRSS()
            withContext(Dispatchers.Main) {
                recyclerView.adapter = NewsAdapter(newsList)
            }
        }

    }
    private fun loadRSS(): List<NewsItem> {
        val newsList = mutableListOf<NewsItem>()

        try {
            val doc: Document = Jsoup.connect(rssUrl).get()
            val items: List<Element> = doc.select("item")

            for (item in items) {
                val title = item.select("title").text()
                val link = item.select("link").text()
                val pubDate = item.select("pubDate").text()
                val imageUrl = item.select("enclosure").attr("url")
                val category = item.select("category").text() // Получаем категорию

                // Фильтруем только новости по экологии
                if (category.lowercase().contains("экол", ignoreCase = true) || category.lowercase().contains("природ", ignoreCase = true)) {
                    newsList.add(NewsItem(title, link, imageUrl, pubDate))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return newsList
    }

}
data class NewsItem(
    val title: String,
    val link: String,
    val imageUrl: String?,
    val pubDate: String
)