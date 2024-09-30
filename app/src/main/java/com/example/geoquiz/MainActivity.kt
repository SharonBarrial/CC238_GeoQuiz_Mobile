package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var questions: ArrayList<Question>
    lateinit var progressBar: ProgressBar
    lateinit var txtScore: TextView

    var position = 0
    var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questions = ArrayList() // Inicializar la lista de preguntas
        loadQuestions()
        setUpViews()
    }

    private fun loadQuestions() {
        questions.add(Question("¿Es Arequipa capital de Lima de Perú?", false))
        questions.add(Question("¿Es Abril capital de Perú?", true))
        questions.add(Question("¿Es Cusco capital de Perú?", true))
        questions.add(Question("¿Es Puno capital de Perú?", false))
        questions.add(Question("¿Es Tacna capital de Perú?", false))
    }

    private fun setUpViews() {
        // vincular los componentes con la lógica
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btYes = findViewById<Button>(R.id.btYes)
        val btNo = findViewById<Button>(R.id.btNo)
        val btNext = findViewById<Button>(R.id.btNext)
        val btnBefore = findViewById<Button>(R.id.btnBefore)
        progressBar = findViewById(R.id.progressBar)
        txtScore = findViewById(R.id.txtScore)

        //Progress Bar
        progressBar.max = questions.size
        progressBar.progress = position + 1

        //SCORE
        updateResult()

        // doy acciones a los botones
        btYes.setOnClickListener {
            if (!questions[position].answer) {
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show()
            }
            updateResult()
        }

        btNo.setOnClickListener {
            if (questions[position].answer) {
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show()
            }
            updateResult()
        }

        btNext.setOnClickListener {
            if (position < questions.size - 1) {
                position++
                tvQuestion.text = questions[position].sentence
                progressBar.progress = position + 1
            } else {
                Toast.makeText(this, "No hay más preguntas", Toast.LENGTH_SHORT).show()
            }
        }

        btnBefore.setOnClickListener {
            if (position > 0) {
                position--
                tvQuestion.text = questions[position].sentence
                progressBar.progress = position - 1
            } else {
                Toast.makeText(this, "No hay más preguntas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateResult() {
        txtScore.text = getString(R.string.score, correctAnswers, questions.size)
    }
}