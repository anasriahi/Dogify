package com.riahi.dogify.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.lifecycleScope
import com.riahi.dogify.model.Breed
import com.riahi.dogify.usecase.FetchBreedsUseCase
import com.riahi.dogify.usecase.GetBreedsUseCase
import com.riahi.dogify.usecase.ToggleFavouriteStateUseCase
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

suspend fun greet() =
    "${FetchBreedsUseCase().invoke()}\n" +
    "${GetBreedsUseCase().invoke()}\n" +
    "${ToggleFavouriteStateUseCase().invoke(Breed
        ("toggle favourite state test",""))}\n"

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
