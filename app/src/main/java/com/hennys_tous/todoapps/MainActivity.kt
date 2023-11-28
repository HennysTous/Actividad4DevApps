package com.hennys_tous.todoapps


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hennys_tous.todoapps.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener {

    lateinit var binding: ActivityMainBinding

    var listaTareas: MutableList<Tarea> = mutableListOf()

    lateinit var adaptador: AdaptadorTareas

    lateinit var room: Database

    lateinit var tarea: Tarea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTareas.layoutManager = LinearLayoutManager(this)


        room = Room.databaseBuilder(
            this,
            Database::class.java, "DB_Name"
        )
            .fallbackToDestructiveMigration()
            .build();
        obtenerTareas(room)

        binding.btnAddUpdate.setOnClickListener {
            if (binding.etTitulo.text.isNullOrEmpty() || binding.etDescripcion.text.isNullOrEmpty()) {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.btnAddUpdate.text.equals("agregar")) {
                tarea = Tarea(

                    binding.etTitulo.text.toString().trim(),
                    binding.etDescripcion.text.toString().trim()
                )

                agregarTarea(room, tarea)

            } else if (binding.btnAddUpdate.text.equals("actualizar")) {
                tarea.descripcion = binding.etDescripcion.text.toString().trim()
                actualizarTarea(room, tarea)
            }

        }
    }

    fun obtenerTareas(room: Database) {
        lifecycleScope.launch {

            listaTareas = room.daoTarea().obtenerTareas()
            adaptador = AdaptadorTareas(listaTareas, this@MainActivity)
            binding.rvTareas.adapter = adaptador
        }
    }

    fun agregarTarea(room: Database, tarea: Tarea) {
        lifecycleScope.launch {
            room.daoTarea().agregarTarea(tarea)
            obtenerTareas(room)
            limpiarCampos()
        }
    }

    fun actualizarTarea(room: Database, tarea: Tarea) {
        lifecycleScope.launch {
            room.daoTarea().actualizarTarea(tarea.descripcion, tarea.titulo)
            obtenerTareas(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos() {
        tarea.titulo = ""
        tarea.descripcion = ""
        binding.etTitulo.setText("")
        binding.etDescripcion.setText("")

        if (binding.btnAddUpdate.text.equals("actualizar")) {
            binding.btnAddUpdate.setText("agregar")
            binding.etTitulo.isEnabled = true
            binding.etDescripcion.isEnabled = true
        }
    }

    override fun onEditItemClick(tarea: Tarea) {
        binding.btnAddUpdate.setText("actualizar")
        binding.etTitulo.isEnabled = false
        binding.etDescripcion.isEnabled = true
        this.tarea = tarea
        binding.etTitulo.setText(this.tarea.titulo)
        binding.etDescripcion.setText(this.tarea.descripcion)

    }

    override fun onDeleteItemClick(tarea: Tarea) {
        lifecycleScope.launch {
            room.daoTarea().borrarTarea(tarea.titulo)
            adaptador.notifyDataSetChanged()
            obtenerTareas(room)
        }
    }
}



