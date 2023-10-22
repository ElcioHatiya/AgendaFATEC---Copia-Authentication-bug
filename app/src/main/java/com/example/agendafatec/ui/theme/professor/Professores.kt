package com.example.agendafatec.ui.theme.professor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agendafatec.databinding.ActivityProfessoresBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

private lateinit var o: ActivityProfessoresBinding

class Professores : AppCompatActivity(),ProfessorAdapter.OnItemClickListener {

    private var db = Firebase.firestore
    private lateinit var lista:ArrayList<ProfessorModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        o= ActivityProfessoresBinding.inflate(layoutInflater)
        setContentView(o.root)

        o.lstProf.layoutManager=LinearLayoutManager(this)

        lista = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("Professor").get().addOnSuccessListener {

            if(!it.isEmpty){

                lista.clear()
                for(data in it.documents){

                    val prof:ProfessorModel? = data.toObject(ProfessorModel::class.java)
                    if(prof != null) lista.add(prof)
                }
                o.lstProf.adapter = ProfessorAdapter(lista,this)
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        o.btnNovoProf.setOnClickListener {

            val prof = ProfessorModel()
            prof.nivel = "NOVO"
            val intent = Intent(this, ProfessorForm::class.java)
            intent.putExtra("objeto",prof)
            startActivity(intent)
        }
    }

    override fun onItemClick(position: Int) {

    }

    override fun onLongClick(position: Int) {

        val prof:ProfessorModel = lista[position]

        val intent = Intent(this,ProfessorForm::class.java)
        intent.putExtra("objeto",prof)
        startActivity(intent)
    }
}