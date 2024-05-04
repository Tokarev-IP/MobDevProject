<font size="5"> Ilia Tokarev's project "Menu". <br>
<font size="4"> Video:  https://youtu.be/oT0z_EOd59E <br>
<font size="3"> I use kotlin, compose, MVI, firebase, hilt, navigation compose, coroutines, firebase auth, firestore, firebase storage, view model. <br>

There are two branches: master branch and developer (where I did all work).<br>

This app about restaurant menu.<br>

The first activity is registration/login Activity. Log In using firebase auth using mobile number. <br>

After successful login step, it will be Content Activity with bottom navigation menu. There are 3 nav menu items: List of menu, Create your own menu and Settings.<br>

List of menu: user can see existing menu in firestore db. Click on menu item and this menu will be opened in view mode. Also, it is possible to open a menu using QR code scanner (icon on the top right).<br>

Create menu: user can create a menu - menu name and main picture and dishes data (name, price and picture). On the top right there is a icon button to create and save your menu's QR code. Using this QR it is possible to open the menu fast. <br>

Settings menu - there is sign out button. <br>

There is an opportunity to edit menu items and menu data - change photo or name and price. <br>
