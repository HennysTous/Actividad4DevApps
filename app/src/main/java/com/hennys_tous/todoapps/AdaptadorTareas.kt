package com.hennys_tous.todoapps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorTareas(
    val listaTareas:MutableList<Tarea>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorTareas.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_tarea, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listaTareas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var tarea = listaTareas[position]


        holder.tvTitulo.text = tarea.titulo
        holder.tvDescripcion.text = tarea.descripcion

        holder.cvTarea.setOnClickListener{
            listener.onEditItemClick(tarea)
        }

        holder.btnTerminada.setOnClickListener{
            listener.onDeleteItemClick(tarea)
        }
   }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val cvTarea = itemView.findViewById<CardView>(R.id.cvTarea)
        val tvTitulo = itemView.findViewById<TextView>(R.id.tvTitulo)
        val tvDescripcion = itemView.findViewById<TextView>(R.id.tvDescripcion)
        val btnTerminada = itemView.findViewById<Button>(R.id.btnTerminada)

    }

}