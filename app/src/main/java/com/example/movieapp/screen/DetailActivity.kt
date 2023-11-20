package com.example.movieapp.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.movieapp.ImageViewModel
import com.example.movieapp.MainActivity
import com.example.movieapp.components.AppHeaderbar
import com.example.movieapp.data.Movie
import com.example.movieapp.data.getMovieByTitle
import com.example.movieapp.ui.theme.MovieAppTheme

class DetailActivity: ComponentActivity(){

    private val viewModel by viewModels<ImageViewModel>()
//    val movieTitle = intent.getStringExtra("title")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MovieAppTheme {
                PrevDetailPage()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModel.updateUri(uri)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(movie: Movie){
    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_EMAIL, "test@gmail.com")
        putExtra(Intent.EXTRA_SUBJECT, movie.title)
        putExtra(Intent.EXTRA_TEXT, movie.description)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    MovieAppTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            topBar = { AppHeaderbar(title = "Detail Page") },
            bottomBar = {
                Button(modifier = Modifier.fillMaxWidth(), shape = RectangleShape, onClick = {
                context.startActivity(shareIntent)
            }) {
                Text(text = "Share", color = Color.White)
            } }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item{
                    Image(
                        painter = painterResource(movie.image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .padding(10.dp),
                    )
                }
                item {
                    Text(text = movie.title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                }
                item {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Rating : ${movie.rating}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = " | ", fontSize = 16.sp)
                        Text(text = "Genre : ${movie.genre}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
                item {
                    movie.description?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevDetailPage(){
    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
    val mContext = LocalContext.current
    val activity = mContext.findActivity()
    val intent = activity?.intent

    val movieTitle = intent?.getStringExtra("title")
    val movie = getMovieByTitle(movieTitle ?: "Saw X")
    DetailPage(movie = movie)
}