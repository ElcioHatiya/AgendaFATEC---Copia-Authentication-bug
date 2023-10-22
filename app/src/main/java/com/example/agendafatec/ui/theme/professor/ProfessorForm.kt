package com.example.agendafatec.ui.theme.professor

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.example.agendafatec.R
import com.example.agendafatec.databinding.ActivityProfessorFormBinding
import com.example.agendafatec.ui.theme.login.AdminHome
import com.example.agendafatec.ui.theme.login.Login
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


@Suppress("DEPRECATION", "DEPRECATION")

class ProfessorForm : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var db: FirebaseFirestore
    private lateinit var o: ActivityProfessorFormBinding
    private var msg = arrayOf("Preencha todos os campos!",
                              "Professor cadastrado com sucesso!",
                              "Dados salvos com sucesso!")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o= ActivityProfessorFormBinding.inflate(layoutInflater)
        setContentView(o.root)

        db = FirebaseFirestore.getInstance()
        db.firestoreSettings=FirebaseFirestoreSettings.Builder().build()

        o.chkAtivo.isChecked = true
        o.chkETEC.isChecked = true
        o.progressBar.visibility= View.INVISIBLE

        val obj = intent.getSerializableExtra("objeto") as ProfessorModel

        if(obj.nivel=="NOVO") {
            o.btnExcluir.visibility= View.INVISIBLE
        }
        else{
            o.email.setText(obj.email)
            o.nome.setText(obj.nome)
            o.senha.setText(obj.senha)
            o.telefone.setText(obj.telefone)

            o.email.isEnabled = false
            o.senha.isEnabled = false

            if(obj.nivel=="ADMIN") o.chkAdmin.isChecked=true else if(obj.nivel=="FATEC") o.chkFATEC.isChecked=true else o.chkETEC.isChecked=true
            if(obj.acesso=="ATIVO") o.chkAtivo.isChecked=true else o.chkBloqueado.isChecked=true
        }


        if((o.email.text.toString()).isEmpty()){

        }

         fun carregarDados():ProfessorModel{

             val Nome = o.nome.text.toString()
             val Email = o.email.text.toString()
             val Senha = o.senha.text.toString()
             val Telefone = o.telefone.text.toString()

             val prof = ProfessorModel()
             var Acesso: String
             var Nivel: String
             if(o.chkFATEC.isChecked) Nivel = "FATEC" else if (o.chkETEC.isChecked) Nivel = "ETEC" else Nivel = "ADMIN"
             if(o.chkBloqueado.isChecked) Acesso="BLOQUEADO" else Acesso = "ATIVO"

             with(prof){
                nome=Nome
                email=Email
                senha=Senha
                telefone=Telefone
                nivel=Nivel
                acesso=Acesso
            }
            return prof
        }

        o.chkSenha.setOnClickListener {

            if(o.chkSenha.isChecked) o.senha.inputType = 1
            else o.senha.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        }

        o.btnExcluir.setOnClickListener {

            apagar(obj,obj.id)
            Toast.makeText(this, "Professor Excluído", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,AdminHome::class.java)
            startActivity(intent)

        }



        o.btnSalvar.setOnClickListener {view ->

            val Nome = o.nome.text.toString()
            val Email = o.email.text.toString()
            val Senha = o.senha.text.toString()
            val Telefone = o.telefone.text.toString()

            if(Nome.isEmpty()||Email.isEmpty()||Telefone.isEmpty()||Senha.isEmpty()) {

                val snackBar = Snackbar.make(view, msg[0], Snackbar.LENGTH_SHORT)
                snackBar.setBackgroundTint(resources.getColor(R.color.snackAmarelo))
                snackBar.setTextColor(resources.getColor(R.color.black))
                snackBar.show()

            }else if(obj.nivel=="NOVO"){

                auth.createUserWithEmailAndPassword(Email,Senha).addOnCompleteListener {

                    if(it.isSuccessful){

                        val authID = auth.uid.toString()

                        val prof = ProfessorModel()
                        var Acesso: String
                        var Nivel: String
                        if(o.chkFATEC.isChecked) Nivel = "FATEC" else if (o.chkETEC.isChecked) Nivel = "ETEC" else Nivel = "ADMIN"
                        if(o.chkBloqueado.isChecked) Acesso="BLOQUEADO" else Acesso = "ATIVO"

                        with(prof){
                            nome=Nome
                            email=Email
                            senha=Senha
                            telefone=Telefone
                            nivel=Nivel
                            acesso=Acesso
                        }
                        prof.id = authID
                        salvar(prof,authID)

                        o.progressBar.visibility= View.VISIBLE
                        o.progressBar.max=1000
                        ObjectAnimator.ofInt(o.progressBar,"progress",1000)
                            .setDuration(1500)
                            .start()

                        Handler().postDelayed({

                            val snackbar = Snackbar.make(view,msg[1],Snackbar.LENGTH_INDEFINITE)
                            snackbar.setBackgroundTint(resources.getColor(R.color.snackVerde))
                            snackbar.show()

                        }, 1500)

                        Handler().postDelayed({

                            val intent = Intent(this,Login::class.java)
                            auth.signOut()
                            startActivity(intent)

                        }, 3500)
                    }
                }.addOnFailureListener {
                    val erro = when (it) {
                        is FirebaseAuthWeakPasswordException -> "A senha deve possuir no mínimo 6 caracteres "
                        is FirebaseAuthInvalidCredentialsException -> "Formato de email inválido"
                        is FirebaseAuthUserCollisionException -> "Este email ja está sendo utilizado"
                        is FirebaseNetworkException -> "Sem conexão com a Internet :/"
                        else -> "Erro ao cadastrar usuário"
                    }
                    val snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(resources.getColor(R.color.snackVermelho))
                    snackbar.setTextColor(resources.getColor(R.color.black))
                    snackbar.show()
                }

            }else{


                val prof = ProfessorModel()
                var Acesso: String
                var Nivel: String
                if(o.chkFATEC.isChecked) Nivel = "FATEC" else if (o.chkETEC.isChecked) Nivel = "ETEC" else Nivel = "ADMIN"
                if(o.chkBloqueado.isChecked) Acesso="BLOQUEADO" else Acesso = "ATIVO"

                with(prof){
                    nome=Nome
                    email=Email
                    senha=Senha
                    telefone=Telefone
                    nivel=Nivel
                    acesso=Acesso
                }
                prof.id=obj.id
                salvar(prof,obj.id)

                o.progressBar.visibility= View.VISIBLE
                o.progressBar.max=1000
                ObjectAnimator.ofInt(o.progressBar,"progress",1000)
                    .setDuration(1500)
                    .start()

                Handler().postDelayed({

                    val snackbar = Snackbar.make(view,msg[2],Snackbar.LENGTH_INDEFINITE)
                    snackbar.setBackgroundTint(resources.getColor(R.color.snackVerde))
                    snackbar.show()

                }, 1500)

                Handler().postDelayed({

                    val intent = Intent(this,AdminHome::class.java)
                    startActivity(intent)

                }, 3500)
            }
        }
    }
}