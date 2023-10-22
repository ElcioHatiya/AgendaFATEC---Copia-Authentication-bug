package com.example.agendafatec.ui.theme.professor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agendafatec.R

class ProfessorAdapter(private val profList:ArrayList<ProfessorModel>,private val listener:OnItemClickListener) : RecyclerView.Adapter<ProfessorAdapter.ProfViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_view_professor,parent,false)
        return ProfViewHolder(itemView)

    }

    override fun getItemCount(): Int {

        return profList.size

    }

    override fun onBindViewHolder(holder: ProfViewHolder, position: Int) {

        val prof = profList[position]

        holder.nome.text = prof.nome
        holder.acesso.text = prof.acesso
        holder.nivel.text = prof.nivel
    }

    inner class ProfViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {

        val nome:TextView = itemView.findViewById(R.id.txtNome)
        val acesso:TextView = itemView.findViewById(R.id.txtAcesso)
        val nivel:TextView = itemView.findViewById(R.id.txtNivel)

        init{itemView.setOnClickListener(this)}
        init{itemView.setOnLongClickListener(this)}

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(p0: View?):Boolean {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onLongClick(position)
            }
            return true
        }
    }

    interface OnItemClickListener {

        fun onItemClick(position: Int)
        fun onLongClick(position: Int)
    }
}