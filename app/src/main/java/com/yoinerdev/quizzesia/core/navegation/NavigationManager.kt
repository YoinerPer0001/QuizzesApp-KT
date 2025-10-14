package com.yoinerdev.quizzesia.core.navegation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseUser
import com.yoinerdev.quizzesia.core.helpers.getFirstOpen
import com.yoinerdev.quizzesia.presentation.screens.AttemptsScreen
import com.yoinerdev.quizzesia.presentation.screens.CreateQuizScreen
import com.yoinerdev.quizzesia.presentation.screens.JoinGameScreen
import com.yoinerdev.quizzesia.presentation.screens.LobbyMultiplayerScreen
import com.yoinerdev.quizzesia.presentation.screens.LobbyOtherPlayers
import com.yoinerdev.quizzesia.presentation.screens.LoginScreen
import com.yoinerdev.quizzesia.presentation.screens.MainScreen
import com.yoinerdev.quizzesia.presentation.screens.ModesQuizScreen
import com.yoinerdev.quizzesia.presentation.screens.MyQuizzesScreen
import com.yoinerdev.quizzesia.presentation.screens.PerfilScreen
import com.yoinerdev.quizzesia.presentation.screens.PodiumScreen
import com.yoinerdev.quizzesia.presentation.screens.PublicQuizzesScreen
import com.yoinerdev.quizzesia.presentation.screens.QuizScreen
import com.yoinerdev.quizzesia.presentation.screens.RegisterScreen
import com.yoinerdev.quizzesia.presentation.screens.ResetPassScreen
import com.yoinerdev.quizzesia.presentation.screens.SplashScreen
import com.yoinerdev.quizzesia.presentation.screens.StartScreen
import com.yoinerdev.quizzesia.presentation.viewmodels.AuthViewModel
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesSocketVM
import com.yoinerdev.quizzesia.presentation.viewmodels.QuizzesVM


@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavigationManager(authViewModel: AuthViewModel, onLanguageChange: (String) -> Unit) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val user by authViewModel.user.collectAsState()
    var isFirstOpen by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        getFirstOpen(context).collect{
            isFirstOpen = it
        }
    }

    val startDestination: Any = when {
        isFirstOpen == null -> SplasScreenESC   // o alguna pantalla de carga
        isFirstOpen == true -> StartScreenESC
        user != null -> MainScreen
        else -> LoginScreen
    }

//    val socketVM = hiltViewModel<QuizzesSocketVM>()
    NavHost(navController, startDestination = startDestination) {
        composable<MainScreen> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {
            MainScreen(authViewModel) {
                navController.navigate(it)
            }
        }

        composable<StartScreenESC> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {
            StartScreen {
                navController.navigate(it)
            }
        }

        composable<SplasScreenESC> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {
            SplashScreen()
        }


        composable<LoginScreen> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) {
            LoginScreen(authViewModel) {
                navController.navigate(it) {
                    if (it == MainScreen) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                }
            }
        }
        composable<RegisterEsc> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ){
            RegisterScreen(authViewModel) {
                navController.navigate(it) {}
            }
        }

        composable<ResetPassEsc> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ){
            ResetPassScreen(authViewModel) {
                navController.navigate(it) {}
            }
        }

        composable<PerfilESC> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ){

            PerfilScreen(
                authViewModel,
                navController = { navController.navigate(it) },
                changeLanguage = { onLanguageChange(it) }
            )
        }

        composable<AttemptsScreenESC> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ){ backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
            val quizTitle = backStackEntry.arguments?.getString("quizTitle") ?: ""
            val quizzesVM = hiltViewModel<QuizzesVM>()
            AttemptsScreen(
                quizId,
                quizTitle,
                quizzesVM,
                authViewModel,
                navController = { navController.navigate(it) },
            )
        }

        composable<CreateQuizzesScreen> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ){ backStackEntry ->

            val type = backStackEntry.arguments?.getInt("type") ?: 0
            val uri = backStackEntry.arguments?.getString("uri") ?: ""
            val quizzesVM = hiltViewModel<QuizzesVM>()
            CreateQuizScreen(
                authViewModel,
                navController = { navController.navigate(it) },
                type,
                uri,
                quizzesVM
            )
        }



        composable<MyQuizzesEsc>(
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ) { backStackEntry ->
            val quizzesVM = hiltViewModel<QuizzesVM>()
            MyQuizzesScreen(authViewModel, quizzesVM) {
                navController.navigate(it)
            }
        }

        composable<PublicQuizzesEsc> (
            enterTransition = { fadeIn(tween(400)) },
            exitTransition = { fadeOut(tween(400)) }
        ){ backStackEntry ->
            val quizzesVM = hiltViewModel<QuizzesVM>()
            PublicQuizzesScreen(authViewModel, quizzesVM) {
                navController.navigate(it)
            }
        }

        navigation<SocketGraph>(
            startDestination = LobbyMultiplayerEsc::class
        ) {

            composable<LobbyMultiplayerEsc> (
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ) { backStackEntry ->
                val parentEntry = remember {
                    navController.getBackStackEntry(SocketGraph::class.qualifiedName!!)
                }
                val socketVM = hiltViewModel<QuizzesSocketVM>(parentEntry)
                val id = backStackEntry.arguments?.getString("quizId") ?: ""
                LobbyMultiplayerScreen(id, socketVM) {
                    navController.navigate(it) {

                    }
                }
            }

            composable<ModesQuizScreen> (
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ){ backStackEntry ->
                val parentEntry = remember {
                    navController.getBackStackEntry(SocketGraph::class.qualifiedName!!)
                }
                val socketVM = hiltViewModel<QuizzesSocketVM>(parentEntry)
                val id = backStackEntry.arguments?.getString("id") ?: ""
                ModesQuizScreen(id, authViewModel, socketVM) {
                    navController.navigate(it) {

                    }
                }
            }

            composable<JoinGameScreen> (
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ) {
                val parentEntry = remember {
                    navController.getBackStackEntry(SocketGraph::class.qualifiedName!!)
                }
                val socketVM = hiltViewModel<QuizzesSocketVM>(parentEntry)
                JoinGameScreen(socketVM) {
                    navController.navigate(it) {

                    }
                }
            }

            composable<LobbyOtherPlayersESC> (
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ){
                val parentEntry = remember {
                    navController.getBackStackEntry(SocketGraph::class.qualifiedName!!)
                }
                val socketVM = hiltViewModel<QuizzesSocketVM>(parentEntry)
                LobbyOtherPlayers(socketVM) {
                    navController.navigate(it) {

                    }
                }
            }

            composable<QuizScreenESC> (
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ){ backStackEntry->
                val type = backStackEntry.arguments?.getInt("typeQuiz") ?: 0
                val parentEntry = remember {
                    navController.getBackStackEntry(SocketGraph::class.qualifiedName!!)
                }
                val socketVM = hiltViewModel<QuizzesSocketVM>(parentEntry)
                QuizScreen(type,socketVM) {
                    navController.navigate(it) {

                    }
                }
            }

            composable<PodiumScreenESC> (
                enterTransition = { fadeIn(tween(400)) },
                exitTransition = { fadeOut(tween(400)) }
            ){
                val parentEntry = remember {
                    navController.getBackStackEntry(SocketGraph::class.qualifiedName!!)
                }
                val socketVM = hiltViewModel<QuizzesSocketVM>(parentEntry)
                PodiumScreen(socketVM) {
                    navController.navigate(it) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
        }


    }
}