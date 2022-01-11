package com.example.composedemo1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composedemo1.compose.TvShowListItem
import com.example.composedemo1.data.TvShowList
import com.example.composedemo1.model.TvShow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*LazyColumnDemo {
                toast(it)
            }*/

            DisplayTvShows {
//                toast(it.name)
                startActivity(InfoActivity.intent(this, it))
            }
        }
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

@Composable
fun ScrollableColumnDemo() {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        for (i in 1..100) {
            Text(
                text = "User Name $i",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(10.dp),
            )
            Divider(color = Color.Black, thickness = 5.dp)
        }
    }
}

@Composable
fun LazyColumnDemo(selectItem: (String) -> Unit) {
    LazyColumn {
        items(100) {
            Text(
                text = "User Name $it",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        selectItem("$it selected")
                    }
            )
            Divider(color = Color.Black, thickness = 5.dp)
        }
    }
}

@Composable
fun DisplayTvShows(selectItem: (TvShow) -> Unit) {
    val tvShows = remember {
        TvShowList.tvShows
    }

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(items = tvShows, itemContent = {
            TvShowListItem(tvShow = it, selectItem)
        })
    }
}


