package com.example.agendafatec.ui.theme.professor

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.io.Serializable

private lateinit var db: FirebaseFirestore

class ProfessorModel (var id:String="", var nome:String="", var email:String="", var senha:String="",
                      var telefone:String="", var nivel:String="", var acesso:String=""):Serializable

    fun salvar(model: ProfessorModel, Uid: String){

        db= FirebaseFirestore.getInstance()

        db.firestoreSettings= FirebaseFirestoreSettings.Builder().build()

        db.collection("Professor").document(Uid).set(model)

       /* val doc = if(model.id.isEmpty()){
            //insert
            db.collection("Professor").document(Uid)
        }else{
            //update
            //db.collection("Professor").document(model.id)
        }
        //model.id = doc.id
        //val handle = doc.set(model)
        //handle.addOnSuccessListener {        }.addOnFailureListener {        }*/
    }

    fun apagar(model: ProfessorModel, ID: String){

        db = FirebaseFirestore.getInstance()

        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        db.collection("Professor").document(ID).delete()

    }