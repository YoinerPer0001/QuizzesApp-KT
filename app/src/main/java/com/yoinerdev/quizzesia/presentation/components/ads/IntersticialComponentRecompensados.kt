package com.yoinerdev.linternapro.presenter.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.yoinerdev.quizzesia.IdsAdmob

@Composable
fun IntersticialComponentRecompensados(
    id: String,
    onAdLoaded: (RewardedAd?) -> Unit
) {
    val TAG = "AdIntersticial"
    val context = LocalContext.current
    var rewardedAd by remember { mutableStateOf<RewardedAd?>(null) }

    // Solo cargar el anuncio una vez o cuando cambie el ID
    LaunchedEffect(id) {
        RewardedAd.load(
            context,
            id,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    rewardedAd = ad
                    onAdLoaded(ad)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "Ad failed to load: ${adError.message}")
                    rewardedAd = null
                    onAdLoaded(null)
                }
            }
        )
    }

    // Asignar los callbacks del ciclo de vida del anuncio
    rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            Log.d(TAG, "Ad dismissed.")
            rewardedAd = null
            onAdLoaded(null)
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            Log.e(TAG, "Ad failed to show: ${adError.message}")
            rewardedAd = null
            onAdLoaded(null)
        }

        override fun onAdShowedFullScreenContent() {
            Log.d(TAG, "Ad showed fullscreen content.")
        }
    }
}
