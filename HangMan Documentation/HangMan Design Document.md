# HangMan Design Document
This document describes the technical design of the HangMan Android application. The application is build in Java with the use of default packages available since version 2.1. No additional packages or plugins are required for this application other then the preinstall software on any smartphone since the adviced Android version.

### Version
0.3

### Tech
The construction of the game rests on three activities and a number of model classes. These activities will
call upon these model classes to retrieve the data and handle the logic. The activities will relay
the results and other visual feedback back to the user interface.

The class diagram can be found at [GitHub](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan%20Documentation/Classdiagram.pdf)

In this design there was chosen to use a bridge class called HangMan that contains a set of attributes that can be used to communicate the same value between activities and models. For example there is a set of class constants that are used to set the result after a guessed letter. This enables the setting of the correct feedback message and what the next action should be e.g. go to the highscores or allow another character to be guessed. This same class handles the instance of the History so that it can be used by both the Highscore activity aswell as the Gameplay model.

### Styleguide
The following styles are applied in the proprietary code of the HangMan application. The code is fully written in Java in combination with the XML format for the Android resources.

**Java**
- A packagename should be a singular word and always in lowercase
- A classes and variable should be a single word describing it's purpose and written in camelcase
- Class constants should be written fully in uppercase where each space is replaced by an underscore
- Abstract classes and interfaces should be placed inside the basemodel package
- Exceptions should be placed in the exception package
- All model classes should be placed inside the model package
- All view classes should be placed inside the view package
- Dependency classess should be placed inside a new package folder named after it's dependent main class
- The scope of each class attribute should be at least protected with the exception of class constants
- The scope of a method should be protected unless outside access is needed. A method should only be private if it may not be overridden by extension
- Each class and method should be provided with commentary explaining the goal of the method or class, with exception of private classes/methods and overridden methods
- Each level of indentation should be written by using 4 empty spaces
- A method length should not exceed 20 lines of executable code unless the reason is specified in the commentary of the method
- If the number of statements within a method of struct is not larger than 1, these may be placed on a single line

**XML**
- For layout files each tag should be placed on a seperate line then it's attributes. These attributes must be placed on a seperate line with an extra indentation of 4 spaces. Before each opening tag must be one blank line.
- Each node within a parent node must be prefixed with 4 space more that it's parent node

### Packages
The HangMan application uses only standard Java and Android libraries. The logic side of the application has been built on proprietary code.

## Rights
The application doesn't require any extra rights to function properly. It also doesn't need an internet connection in order for it to work.

### Screenshots
![New game](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_Documentation/images/NewGame.png "New Game screen")
![False guess](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_Documentation/images/FalseGuess.png "False guess message")
![Settings](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_Documentation/images/Settings.png "Settings screen")
![Highscores](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_Documentation/images/Highscore.png "Highscore screen")

### Todos
 - Display the Figure incrementally in the Canvas

License
----

MIT