package com.bolhy91.firebaseapp.utils

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object LoginNav: Destination("login", emptyList())
    object RegisterNav: Destination("register", emptyList())
//    object FoodDetail: Destination(
//        "foodDetail",
//        arguments = listOf(
//            navArgument("foodId"){
//                type = NavType.StringType
//                defaultValue = "0"
//            }
//        )
//    )
    object Home: Destination("home", emptyList())
}