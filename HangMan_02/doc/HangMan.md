# HangMan
HangMan is a simple game where you have to guess a word using single letters.

This game has the following features:
 - Automated selection from a large number of words
 - Choice in difficulty (easy, intermediate, hard, very hard)
 - Stick figure building on wrong guesses
 - Multiplayer games with manual entry of words to guess

### Game play
When starting the application the player enters the Main Menu. In this menu the player (or players) can select between the options of a Single Player game, a Multi Player game, change the Settings or Quit the application.

![Image](https://github.com/ruben-kruiver/Native-App-Studio/HangMan_02/doc/MainMenu.png)

If the player starts a Single Player game, the player will directly be send to the game where a word is automatically selected from the built-in dictionary. 

![Image](https://github.com/ruben-kruiver/Native-App-Studio/HangMan_02/doc/SinglePlayer.png)

If the player selects the Multi Player game, the player will be directed to a screen where he can enter the players for the game. They may also enter the words for one of their opponents. 

![Image](https://github.com/ruben-kruiver/Native-App-Studio/HangMan_02/doc/MultiPlayer.png)

In the Settings the player may select a different difficulty level, as well as an other Figure to place on the gallow or even a custom dictionary.

![Image](https://github.com/ruben-kruiver/Native-App-Studio/HangMan_02/doc/Settings.png)

The game can be played by entering a letter that might be in the selected word. If the player chooses correctly the chosen letter will be displayed on the correct position(s) in the word that is revealed and the player can continue playing. If the player chooses incorrectly the Hangman figure will be appended until the figure is complete and one guess is decremented. The player will get a total of 7 failed guesses until the game is over.

The player is allowed to guess the whole word during each stage of the game by pushing the button "I know the word!". If the word is guessed correctly it will display a confirmation message. If the word is guessed wrong number of tries will be decremented by 2. When the player has guessed all the letters correctly the user will be notified with a confirmation message.

### Version
0.2 Beta

### Tech
HangMan is based on proprietary code and the use of the standard Android library available since version 2.1. There is no need to install additional software to support this application. The source code is available under MIT license and can be found in a [public repository](https://github.com/ruben-kruiver/Native-App-Studio/) on GitHub.

### Installation
Installation is very simple by starting the HangMan.apk file on your Android mobile device. To function on your device you need to make sure your device is run with Android version 2.1 or later. After installation the application can be started directly.

In the settings the user can choose to use a custom dictionary. The supported formats for those dictionaries are a XML and a text based file. In the XML file, the root node should be called "dictionary" and each child node should be called "word". Those are the only nodes allowed. In the text based files each word should be placed on a seperate line. The minimum word length is 3 and the maximum is 37.

### Plugins
There are no additional plugins required for this application.

### Development
If you are interested in joining the development of this application you can contact the leaddeveloper at email adres ruben.kruiver@hva.nl

### Todos
* Multiplayer support needs to be built in
* Some bugfixes are required

License
----

MIT