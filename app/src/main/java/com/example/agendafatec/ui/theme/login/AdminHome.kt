package com.example.agendafatec.ui.theme.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agendafatec.databinding.ActivityAdminHomeBinding
import com.example.agendafatec.ui.theme.professor.Professores
import com.google.firebase.auth.FirebaseAuth

private lateinit var o: ActivityAdminHomeBinding
    private var auth = FirebaseAuth.getInstance()

class AdminHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o= ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(o.root)

        o.btnProf.setOnClickListener {

            val intent = Intent(this, Professores::class.java)
            startActivity(intent)

        }

        o.btnLogout.setOnClickListener {

            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
    }
}