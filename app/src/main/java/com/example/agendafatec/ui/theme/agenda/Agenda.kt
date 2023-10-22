package com.example.agendafatec.ui.theme.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agendafatec.databinding.ActivityAgendaBinding
import com.example.agendafatec.ui.theme.login.Login
import com.google.firebase.auth.FirebaseAuth

    private lateinit var o: ActivityAgendaBinding
    private val auth = FirebaseAuth.getInstance()

class Agenda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o=ActivityAgendaBinding.inflate(layoutInflater)
        setContentView(o.root)

        o.btnLogout.setOnClickListener {

            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
    }
}