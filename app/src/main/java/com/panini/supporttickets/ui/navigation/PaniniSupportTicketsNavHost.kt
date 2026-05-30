package com.panini.supporttickets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.panini.supporttickets.ui.login.LoginScreen
import com.panini.supporttickets.ui.tickets.create.CreateTicketScreen
import com.panini.supporttickets.ui.tickets.detail.TicketDetailScreen
import com.panini.supporttickets.ui.tickets.list.TicketListScreen

@Composable
fun PaniniSupportTicketsNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Login.route
    ) {
        composable(AppRoute.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppRoute.TicketList.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(AppRoute.TicketList.route) {
            TicketListScreen(
                onTicketClick = { ticketId ->
                    navController.navigate(AppRoute.TicketDetail.create(ticketId))
                },
                onCreateClick = {
                    navController.navigate(AppRoute.CreateTicket.route)
                }
            )
        }
        composable(AppRoute.CreateTicket.route) {
            CreateTicketScreen(
                onBack = { navController.popBackStack() },
                onCreated = { navController.popBackStack() }
            )
        }
        composable(
            route = AppRoute.TicketDetail.route,
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId").orEmpty()
            TicketDetailScreen(
                ticketId = ticketId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
