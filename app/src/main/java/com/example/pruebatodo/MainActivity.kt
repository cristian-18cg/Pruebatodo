package com.example.pruebatodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pruebatodo.ui.theme.PruebatodoTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.modifier.modifierLocalConsumer


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebatodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                 ListaTareas()
                }
            }
        }
    }
}
data class tareas(
    val Descripcion: String,
    var tareacompletada: Boolean
)
@Composable
fun ListaTareas() {
    var listadetarea= remember { mutableStateListOf<tareas>() }
    var nuevatarea = remember { mutableStateOf("") }
    var tareacompleta = remember { mutableStateOf(false) }

    Column{
        // Agregar una tarea a la lista
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = nuevatarea.value,
                onValueChange = { nuevatarea.value = it },
                label = { Text("Ingrese nueva tarea") },
                modifier = Modifier.weight(1f),
                trailingIcon= {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.clickable { //Agregamos con el click la nueva tarea
                            listadetarea.add(
                                tareas(
                                    Descripcion = nuevatarea.value, //se le asigna lo escrito por el usuario
                                    tareacompletada =  false
                                )
                            )
                            nuevatarea.value = "" //dejamos nuevamente vacia la tarea
                        },
                    )
                }
            )


        }
        Box(modifier = Modifier.fillMaxSize()) {
        // Mostrar la lista de tareas
        LazyColumn{
            itemsIndexed(listadetarea) { index, task ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(task.Descripcion)
                Checkbox(
                    checked = listadetarea[index].tareacompletada,
                    onCheckedChange = { isChecked ->
                        listadetarea[index].tareacompletada= isChecked },
                )
            }
         }
        }

        Button(
            onClick = {
                listadetarea.removeAll { task ->
                    task.tareacompletada
                }
            },
            enabled = listadetarea.any { task ->
                task.tareacompletada },

            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Eliminar tareas completadas")
        }
        }}
    }


