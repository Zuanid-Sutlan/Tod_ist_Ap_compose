package com.devstudio.todistap

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devstudio.todistap.data.viewModel.MainViewModel
import com.devstudio.todistap.ui.MainView
import com.devstudio.todistap.ui.theme.TodIstApTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


const val AD_UNIT_ID = "ca-app-pub-3967320234242948/3684955252"

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var viewModel: MainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            viewModel = viewModel()

//            Prefs.isDarkTheme = isSystemInDarkTheme()

            TodIstApTheme(darkTheme = viewModel.theme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                        MainView()

                }
            }
        }
    }
}

    @Composable
    fun ShowInterstitialAd() {
        val context = LocalContext.current
        InterstitialAd.load(
            context,
            AD_UNIT_ID, //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(context as Activity)
                }
            }
        )
    }

    @Preview
    @Composable
    fun MyPreview(modifier: Modifier = Modifier) {
        MainView()
    }