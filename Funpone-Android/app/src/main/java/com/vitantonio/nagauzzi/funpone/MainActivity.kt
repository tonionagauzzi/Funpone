package com.vitantonio.nagauzzi.funpone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.funpone.data.SettingsRepository
import com.vitantonio.nagauzzi.funpone.data.repository
import com.vitantonio.nagauzzi.funpone.ui.SettingsView
import com.vitantonio.nagauzzi.funpone.ui.theme.FunponeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FunponeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val repository: SettingsRepository by repository()
                    val selectedUrl by repository.selectedUrl.collectAsState(initial = "")

                    if (canOpenUrlImmediately(url = selectedUrl)) {
                        openUrl(url = selectedUrl)
                        finish()
                    } else {
                        SettingsView(repository = repository, selectedUrl = selectedUrl)
                    }
                }
            }
        }
    }

    private fun canOpenUrlImmediately(url: String): Boolean {
        return !intent.getBooleanExtra("shortcut", false) && isValid(url = url)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}
