package com.maxmudbek.collapsiblechatthread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maxmudbek.collapsiblechatthread.model.Comment
import com.maxmudbek.collapsiblechatthread.ui.components.CommentThread
import com.maxmudbek.collapsiblechatthread.ui.theme.CollapsibleChatThreadTheme
import com.maxmudbek.collapsiblechatthread.utils.SampleData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollapsibleChatThreadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatThreadScreen(
                        comments = SampleData.getSampleComments(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatThreadScreen(comments: List<Comment>, modifier: Modifier = Modifier) {
    CommentThread(comments = comments, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun ChatThreadPreview() {
    CollapsibleChatThreadTheme {
        ChatThreadScreen(comments = SampleData.getSampleComments())
    }
}