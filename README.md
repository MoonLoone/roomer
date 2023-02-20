<h1>Roomer</h1>
<h2>Приложение для поиска соседей и комнат для совместного проживания.</h2>
<h2>Функционал</h2>
<ul>
  <li>Регистрация</li>
  <li>Чат с пользователями</li>
  <li>Отзывы о пользователях</li>
  <li>Список избранных комнат</li>
  <li>Просмотр информации о доступных комнатах и пользователях</li>
</ul>
<h2>Документация</h2>
<h3>Навигация</h3>
<p>Composable верстка панели навигации: NavbarHostContainer. Все подграфы основного графа навигации необходимо добавлять как функции-расширения класса NavGraphBuilder</p>
<p>Навигация в Compose работает на основе класса NavGraph. Навигация работает на основе deeplink. </p>
<p>Вложенная навигация должна располагаться в отдельных методах</p>
<p>Для перехода на страницу необходимо использовать метод NavController.navigate(deeplink)</p>
<p>(пример - fun NavGraphBuilder.chatGraph(navController: NavHostController))</p>. 
<pre>
<code>
fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation(
        startDestination = NavbarItem.Profile.destination, route = NavbarItem.Profile.name
    ) {
        composable(NavbarItem.Profile.destination){ NavbarItem.Profile.composeViewFunction.invoke() } //Стартовый экран Profile
        //Внутренняя навигация страницы Profile
        composable(Screens.Account.name) { Screens.Account.composeViewFunction.invoke() }
        composable(Screens.Location.name) { Screens.Location.composeViewFunction.invoke() }
        composable(Screens.Logout.name) { Screens.Logout.composeViewFunction.invoke() }
        composable(Screens.Rating.name) { Screens.Rating.composeViewFunction.invoke() }
        composable(Screens.Settings.name) { Screens.Settings.composeViewFunction.invoke() }
    }
}
</code>
</pre>
В данных методах нужно прописывать startDestination как базовую страницу для навигации (например Profile) и все ссылки на другие страницы внутри страницы навигации. </p>
<p>Ссылки на страницы добавляются в граф навигации при помощи метода NavGraphBuilder.composable(deeplink){ ComposeFunction.invoke()} </p>
<p>Передавать в дочерние Compose функции NavController - не лучшая практика. Необходимо передавать методы navigateTo{navcontroller.navigate(deeplink)} из родителя. </p>
