package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.theme.MovieAppTheme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepo
import com.example.movieapp.data.generateRandomMovie
import com.example.movieapp.screen.AboutActivity
import com.example.movieapp.screen.DetailActivity

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                MainPageContent()
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(title: String){
    val mContext = LocalContext.current
    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                "List Movie App",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = {
                mContext.startActivity(Intent(mContext, AboutActivity::class.java))
            }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

@Composable
fun RecentMovie(
    @DrawableRes drawable: Int,
    @StringRes movTitle: Int,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = "loki trailer",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Text( text = stringResource(movTitle), modifier = Modifier.padding(top = 5.dp),
            style = MaterialTheme.typography.bodyMedium )
    }
}

@Composable
fun MovieListItem(movie:Movie, onClick: ()-> Unit){
    Row (
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ){
        Image(painter = painterResource(id = movie.image), contentDescription = null )
        Column (verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            movie.genre?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium, color = Color.Black) }
            Text(text = "Rating : ${movie.rating}", style = MaterialTheme.typography.bodyMedium, color = Color.Black.copy(0.5f))
        }
    }
}

@Preview
@Composable
fun PrevMovieListItem(){
    val movie = remember {
        generateRandomMovie()
    }
    MovieListItem(movie = movie, onClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainPageContent() {
    val mContext = LocalContext.current
    val movies = remember {
        MovieRepo.getMovies()
    }
    MovieAppTheme {
        Scaffold { innerPadding ->
            LazyColumn(contentPadding = innerPadding ){
                item {
                    HeaderBar(title = "List Movie App")
                }
                items(movies){ movie ->
                    MovieListItem(movie = movie, onClick = {
                        val intent = Intent(mContext, DetailActivity::class.java)
                        intent.putExtra("title", movie.title)
                        mContext.startActivity(intent)
                    })
                }
            }
        }
    }
}