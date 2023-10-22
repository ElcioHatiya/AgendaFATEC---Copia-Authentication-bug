package com.example.agendafatec

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.agendafatec.databinding.ActivityMainBinding
import com.example.agendafatec.ui.theme.agenda.Agenda
import com.example.agendafatec.ui.theme.login.AdminHome
import com.example.agendafatec.ui.theme.login.Login
import com.example.agendafatec.ui.theme.professor.ProfessorForm
import com.example.agendafatec.ui.theme.professor.ProfessorModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var o:ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()
    private var db = Firebase.firestore

    override fun onStart(){

        super.onStart()

        o.progressBar.max=1000
        ObjectAnimator.ofInt(o.progressBar,"progress",1000)
            .setDuration(2000)
            .start()

        var user = auth.currentUser

        Handler().postDelayed({

            if (user == null) {

                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            }else {

                var userID = auth.uid.toString()

                db.collection("Professor").document(userID).get().addOnSuccessListener { snap ->

                    val prof = snap.toObject<ProfessorModel>()
                    var nivel = prof?.nivel.toString()
                    var acesso = prof?.acesso.toString()

                    if (acesso == "BLOQUEADO") {

                        auth.signOut()

                        Toast.makeText(this,"ACESSO BLOQUEADO - Procure o Administrador",Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)

                    } else {

                        if (nivel == "ADMIN") {

                            val intent = Intent(this, AdminHome::class.java)
                            startActivity(intent)

                        } else {

                             val intent = Intent(this, Agenda::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o= ActivityMainBinding.inflate(layoutInflater)
        setContentView(o.root)

    }
}

