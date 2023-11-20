package com.example.movieapp.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ImageViewModel
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.example.movieapp.components.AppHeaderbar
import com.example.movieapp.ui.theme.MovieAppTheme

class AboutActivity: ComponentActivity() {
    
    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AboutActivityContent()
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
@Preview
@Composable
fun AboutActivityContent() {
    val context = LocalContext.current
    MovieAppTheme {
        Scaffold (
            topBar = { AppHeaderbar("About", Modifier.fillMaxSize()) }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center){
                LazyColumn (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    item {
                        Image(painter = painterResource(
                            id = R.drawable.profile),
                            contentDescription = "Profile Author",
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                        )
                    }
                    item {
                        Text(
                            text = "Fikri Ilham Arifin",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(top = 16.dp)
                        )
                    }
                    item{
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                val urlIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.github.com/UnFik")
                                )
                                try {
                                    context.startActivity(urlIntent)
                                } catch (e: ActivityNotFoundException) {
                                    e.printStackTrace()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "Localized description"
                                )
                            }
                            Text(
                                text = "github.com/UnFik",
                            )
                        }
                    }
                }
            }
        }
    }
}
