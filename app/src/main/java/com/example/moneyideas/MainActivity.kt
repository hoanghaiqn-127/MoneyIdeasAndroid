package com.example.moneyideas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var rvIdeas: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var btnExport: Button
    private lateinit var adapter: IdeasAdapter

    private val prefsName = "moneyideas_prefs"
    private val keyIdeas = "ideas_list_json"

    private var ideas = mutableListOf<Idea>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvIdeas = findViewById(R.id.rvIdeas)
        btnAdd = findViewById(R.id.btnAdd)
        btnExport = findViewById(R.id.btnExport)

        loadIdeas()

        adapter = IdeasAdapter(ideas) { idea ->
            showIdeaDialog(idea)
        }
        rvIdeas.layoutManager = LinearLayoutManager(this)
        rvIdeas.adapter = adapter

        btnAdd.setOnClickListener {
            showAddDialog { newIdea ->
                ideas.add(0, Idea(text = newIdea, tried = false))
                adapter.notifyItemInserted(0)
                saveIdeas()
            }
        }

        btnExport.setOnClickListener {
            exportJson()
        }
    }

    private fun showIdeaDialog(idea: Idea) {
        val items = arrayOf("Chia sẻ", if (idea.tried) "Bỏ đánh dấu đã thử" else "Đánh dấu đã thử")
        AlertDialog.Builder(this)
            .setTitle(idea.text)
            .setItems(items) { _, which ->
                when (which) {
                    0 -> shareText(idea.text)
                    1 -> toggleTried(idea)
                }
            }
            .show()
    }

    private fun shareText(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

    private fun toggleTried(idea: Idea) {
        idea.tried = !idea.tried
        adapter.notifyDataSetChanged()
        saveIdeas()
    }

    private fun showAddDialog(onAdd: (String) -> Unit) {
        val edit = android.widget.EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Thêm ý tưởng mới")
            .setView(edit)
            .setPositiveButton("Thêm") { _, _ ->
                val v = edit.text.toString().trim()
                if (v.isNotEmpty()) onAdd(v)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun saveIdeas() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(ideas)
        prefs.edit().putString(keyIdeas, json).apply()
    }

    private fun loadIdeas() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val json = prefs.getString(keyIdeas, null)
        if (json == null) {
            ideas = mutableListOf(
                Idea("Bán đồ cũ trên Facebook Marketplace hoặc Shopee", false),
                Idea("Làm freelance viết bài/thiết kế trên Fiverr hoặc Upwork", false),
                Idea("Tham gia khảo sát trả tiền (Survey)", false),
                Idea("Bán ảnh stock trên trang như Shutterstock", false),
                Idea("Làm affiliate cho sản phẩm số hoặc vật lý", false),
                Idea("Tạo nội dung ngắn (TikTok, YouTube Shorts) và bật kiếm tiền", false),
                Idea("Gia sư online (TOEIC, tiếng Anh)", false),
                Idea("Dropshipping nhẹ với sản phẩm ngách", false),
                Idea("Dịch thuật và transcribe audio", false),
                Idea("Kiếm tiền bằng mã coupon/referral", false)
            )
            return
        }
        val gson = Gson()
        val listType = object : TypeToken<MutableList<Idea>>() {}.type
        ideas = gson.fromJson(json, listType)
    }

    private fun exportJson() {
        val gson = Gson()
        val json = gson.toJson(ideas)
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, json)
            type = "application/json"
        }
        startActivity(Intent.createChooser(sendIntent, "Export JSON"))
    }
}
