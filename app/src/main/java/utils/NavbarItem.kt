package utils

import com.example.roomer.R

enum class NavbarItem(val iconUnSelected: Int,val iconSelected:Int, val description: String) {
    Home(
        R.drawable.homeun,
        R.drawable.homein,
        "Home"
    ),
    Favourite(
        R.drawable.favun,
        R.drawable.favin,
        "Favourite",
    ),
    Post(
        R.drawable.postun,
        R.drawable.postin,
        "Post"
    ),
    Chats(
        R.drawable.chatun,
        R.drawable.chatin,
        "Chat",
    ),
    Profile(
        R.drawable.profun,
        R.drawable.profin,
        "Profile"
    )
}