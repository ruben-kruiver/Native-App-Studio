# HangMan

HangMan is a simple game where you have to guess a word using single letters.

This game has the following features:
 - Automated selection from a large number of words
 - Choice in difficulty (easy, intermediate, hard, very hard)
 - Stick figure building on wrong guesses

### Game play
When starting the application the user can select a difficulty rating from a drop-down menu. When the right difficulty rating has been selected the user can start a new game by pressing the button "New game". The application will choose a word from an integrated dictionary list, consisting over 6500 different words, that matches de difficulty rating. The player will see a string of underscore characters matching the number of letters the guessable word contains and is prompted to enter a letter to start playing.

If the player chooses correctly the chosen letter will be displayed on the correct position(s) in the word that is revealed and the player can continue playing. If the player chooses incorrectly the Hangman figure will be appended until the figure is complete and one guess is decremented. The player will get a total of 7 failed guesses until the game is over.

The player is allowed to guess the whole word during each stage of the game by pushing the button "I know the word!". If the word is guessed correctly it will display a confirmation message. If the word is guessed wrong number of tries will be decremented by 2. When the player has guessed all the letters correctly the user will be notified with a confirmation message.

### Version
0.1 Beta

### Tech
HangMan uses a number of open source projects to work properly:

* 

And of course HangMan itself is open source with a [public repository](https://github.com/ruben-kruiver/Native-App-Studio/) on GitHub.

### Installation


### Plugins


### Development


### Todos


License
----

MIT