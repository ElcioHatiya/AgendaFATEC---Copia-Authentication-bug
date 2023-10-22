package com.example.agendafatec.ui.theme.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import com.example.agendafatec.MainActivity
import com.example.agendafatec.R
import com.example.agendafatec.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

    private lateinit var o: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(o.root)

        o.chkSenha.setOnClickListener {
            if(o.chkSenha.isChecked) o.senha.inputType = 1
            else o.senha.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        o.btnLogin.setOnClickListener {

            val email = o.email.text.toString()
            val senha = o.senha.text.toString()

            if(email.isEmpty()||senha.isEmpty()){

                val snackbar = Snackbar.make(it, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(resources.getColor(R.color.snackAmarelo))
                snackbar.setTextColor(resources.getColor(R.color.black))
                snackbar.show()

            }else{

                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener {

                    if(it.isSuccessful){

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                }.addOnFailureListener{erro->

                    val snackbar = Snackbar.make(it, "Erro de acesso, Email ou Senha inválidos", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(resources.getColor(R.color.snackAmarelo))
                    snackbar.setTextColor(resources.getColor(R.color.black))
                    snackbar.show()

                }
            }
        }
    }
}