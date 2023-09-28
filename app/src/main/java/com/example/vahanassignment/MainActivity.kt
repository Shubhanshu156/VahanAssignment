package com.example.vahanassignment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vahanassignment.AlarmManager.AlarmItem
import com.example.vahanassignment.AlarmManager.AlarmScheduler
import com.example.vahanassignment.AlarmManager.AlarmSchedulerImp
import com.example.vahanassignment.composables.UniversityListingComposable
import com.example.vahanassignment.composables.UniversityViewwmodel
import com.example.vahanassignment.composables.University_Page
import com.example.vahanassignment.ui.theme.VahanAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: UniversityViewwmodel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmScheduler : AlarmScheduler = AlarmSchedulerImp(this)
        val alarmitem=AlarmItem(LocalDateTime.now().plusSeconds(10),"This is my message for alarm")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            alarmitem?.let(alarmScheduler::schedule)
            VahanAssignmentTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "UNIVERSITY_LIST") {
                    composable("UNIVERSITY_LIST") {
                        UniversityListingComposable(viewModel) { url ->
                            val route = "UNIVERSITY_PAGE/${url}"
                            Log.d("route is", "onCreate: $route")
                            navController.navigate(route)
                        }

                    }
                    composable(
                        route = "UNIVERSITY_PAGE/{url}",
                        arguments = listOf(navArgument("url") {
                            type = NavType.StringType
                            defaultValue = "google.com"
                        })
                    ) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url")
                        Log.d("inside mainactivity", "onCreate: $url")
                        University_Page(url = url)

                    }


                }
            }
        }
    }


}