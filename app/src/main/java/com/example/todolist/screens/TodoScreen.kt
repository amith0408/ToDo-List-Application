package com.example.todolist.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.todolist.presentation.TodoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// Add these at the top of the file after the imports
// Add these at the top of the file after the imports
val MyYellow = Color(0xFFFFEB3B) // Primary Yellow
val DarkYellow = Color(0xFFFBC02D) // For Buttons and Icons
val BackgroundYellow = Color(0xFFFFFDE7) // Very light yellow for the background

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel) {
    var task by remember { mutableStateOf("") }
    val todoList by todoViewModel.todos.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BackgroundYellow, // Light yellow background
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "To Do List",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                            fontSize = 24.sp
                    )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = task,
                    onValueChange = { task = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Enter task ") },
                    shape = RoundedCornerShape(size = 22.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = DarkYellow,
                        unfocusedIndicatorColor = MyYellow,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Button(
                    onClick = {
                        if (task.isNotBlank()) {
                            todoViewModel.addTodo((task))
                            task = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(DarkYellow)
                ) {
                    Text("Add", color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = DarkYellow, thickness = 2.dp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = todoList, key = { it.id }) { todo ->
                    var isEditing by remember(todo.id) { mutableStateOf(false) }
                    var newTitle by remember(todo.id) { mutableStateOf(todo.title) }

                    androidx.compose.material3.Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        color = Color(0xFFF1F1F1),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            if (isEditing) {
                                // Expanded Edit Mode
                                OutlinedTextField(
                                    value = newTitle,
                                    onValueChange = { newTitle = it },
                                    modifier = Modifier.weight(1f), // Takes all available space
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = DarkYellow,
                                        unfocusedIndicatorColor = MyYellow,
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White
                                    )
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        if (newTitle.isNotBlank()) {
                                            todoViewModel.editTodo(todo, newTitle)
                                            isEditing = false
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(DarkYellow),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Save", color = Color.Black, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                            } else {
                                // Normal Display Mode
                                Checkbox(
                                    checked = todo.isDone,
                                    onCheckedChange = { todoViewModel.toggleTodoDone(todo) },
                                    colors = CheckboxDefaults.colors(checkedColor = DarkYellow)
                                )

                                Text(
                                    text = todo.title,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                        textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None
                                    ),
                                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                                )

                                Row {
                                    IconButton(onClick = { isEditing = true }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
                                    }
                                    IconButton(onClick = { todoViewModel.deleteTodo(todo) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }
                }
            }
        }
    }


