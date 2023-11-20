package com.example.movieapp.data

import androidx.annotation.DrawableRes
import com.example.movieapp.R

data class Movie(
    val title: String,
    val rating: Double,
    @DrawableRes val image: Int,
    val description: String? = "",
    val genre: String? = "",
)

private val title = listOf(
    "13 Bom di Jakarta",
    "Budi Pekerti",
    "Five Nights at Freddy's",
    "Gadis Kretek",
    "Gampang Cuan",
    "Loki Season 2",
    "One Piece Live Action",
    "Saw X",
    "The Creator",
    "The Marvels",
)

private val image = listOf(
    R.drawable.bom_jakarta,
    R.drawable.budi_pekerti,
    R.drawable.fnaf,
    R.drawable.gadis_kretek,
    R.drawable.gampang_cuan,
    R.drawable.loki,
    R.drawable.op,
    R.drawable.saw_x,
    R.drawable.the_creator,
    R.drawable.the_marvels,
)

private val rating = listOf(
    4.0,
    4.0,
    3.0,
    4.0,
    4.0,
    4.8,
    4.0,
    4.0,
    4.0,
    4.0,
)

private val description = listOf(
    "13 Bom di Jakarta adalah film drama Indonesia tahun 2021 yang disutradarai oleh Ardhan Arga Kusuma dan dibintangi oleh Reza Rahadian, Marsha Timothy, dan Irsyadillah. Film ini diadaptasi dari novel berjudul sama karya Eka Kurniawan.",
    "Budi Pekerti adalah film drama Indonesia tahun 2021 yang disutradarai oleh Hanung Bramantyo dan dibintangi oleh Reza Rahadian, Marsha Timothy, dan Irsyadillah. Film ini diadaptasi dari novel berjudul sama karya Eka Kurniawan.",
    "Five Nights at Freddy's adalah film horor adikodrati Amerika tahun 2023 berdasarkan seri video game dan waralaba dengan nama yang sama yang dibuat oleh Scott Cawthon.",
    "Perjalanan penemuan jati diri wanita perajin kretek terungkap saat ia menentang tradisi di industri rokok kretek Indonesia.",
    "Gampang Cuan adalah sebuah film drama komedi Indonesia tahun 2023 yang disutradarai oleh Rahabi Mandra dan diproduksi oleh Temata Studios. Film tersebut menampilkan Vino G Bastian dan Anya Geraldine",
    "Loki season 2 diawali dengan lanjutan kisah saat Loki terjebak dalam pertempuran besar menyelamatkan diri dari Time Variance Authority (TVA). Agen Mobius, Hunter B-15, dan sekelompok karakter baru yang penuh misteri hadir di season ini.",
    "One Piece Live Action adalah serial televisi web Jepang yang didasarkan pada serial manga One Piece karya Eiichiro Oda. Serial ini diproduksi oleh Tomorrow Studios, yang juga memproduksi serial televisi live-action Cowboy Bebop.",
    "Saw X adalah film horor psikologis Amerika tahun 2023 yang disutradarai oleh Darren Lynn Bousman dan ditulis oleh Josh Stolberg dan Pete Goldfinger. Film tersebut merupakan sekuel dari film Saw tahun 2021 dan merupakan film kesepuluh dalam seri film Saw.",
    "The Creator adalah sebuah film fiksi ilmiah Amerika Serikat tahun 2023 yang disutradarai oleh Gareth Edwards. Film tersebut dirilis di Amerika Serikat pada 29 September 2023. Film tersebut dibintangi oleh John David Washington, Gemma Chan dan Ken Watanabe.",
    "The Marvels adalah film pahlawan super Amerika Serikat tahun 2023 yang berdasarkan pada karakter Marvel Comics dengan nama yang sama. Film tersebut diproduksi oleh Marvel Studios dan didistribusikan oleh Walt Disney Studios Motion Pictures.",
)

private val genre = listOf(
    "Action",
    "Drama",
    "Horror",
    "Drama",
    "Comedy",
    "Action",
    "Action",
    "Horror",
    "Sci-Fi",
    "Action",
)

private fun movies(): List<Movie> {
    val movies = mutableListOf<Movie>()
    for ((index, movie) in title.withIndex()) {
        movies.add(
            Movie(
                movie,
                rating[index],
                image[index],
                description[index],
                genre[index]
            )
        )
    }
    return movies
}

object MovieRepo {
    fun getMovies(): List<Movie> = movies()
}

fun generateRandomMovie(): Movie {
    return movies().random()
}

fun getMovieByTitle(title: String): Movie {
    return movies().find { it.title == title }!!
}


