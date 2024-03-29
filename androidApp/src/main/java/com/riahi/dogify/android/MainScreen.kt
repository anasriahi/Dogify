package com.riahi.dogify.android

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.riahi.dogify.model.Breed
import kotlinx.coroutines.launch


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    val breeds by viewModel.breeds.collectAsState()
    val events by viewModel.events.collectAsState(Unit)
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val shouldFilterFavourites by viewModel.shouldFilterFavorites.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val snackBarCoroutinesScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = viewModel::refresh
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    Modifier
                        .wrapContentWidth(Alignment.End)
                        .padding(8.dp)) {
                    Text(
                        text = "Filter Favourites",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Switch(
                        checked = shouldFilterFavourites,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onCheckedChange = { viewModel.onToggleFavouriteFilter() }
                    )
                }
                when (state) {
                    MainViewModel.State.LOADING -> {
                        Spacer(modifier = Modifier.weight(1f))
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    MainViewModel.State.NORMAL -> Breeds(
                        breeds = breeds,
                        onFavouriteTapped = viewModel::onFavouriteTapped
                    )
                    MainViewModel.State.ERROR -> {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Oops something went wrong...",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    MainViewModel.State.EMPTY -> {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Oops look like there is no ${ if (shouldFilterFavourites) "favourites" else "dogs"}",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                if (events == MainViewModel.Event.Error) {
                    snackBarCoroutinesScope.launch {
                        scaffoldState.snackbarHostState.apply {
                            currentSnackbarData?.dismiss()
                            showSnackbar("Oops something went wrong...")
                        }
                    }
                }
            }
        }
    }
}

// List of breeds
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Breeds(breeds: List<Breed>, onFavouriteTapped: (Breed) -> Unit = {}) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(breeds) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = rememberImagePainter(it.imageUrl),
                    contentDescription = "${it.name}-image",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )
                Row(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = it.name,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(if (it.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Mark as favourite",
                        modifier = Modifier.clickable { onFavouriteTapped(it) }
                    )
                }
            }
        }
    }
}