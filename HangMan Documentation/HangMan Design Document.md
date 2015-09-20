# HangMan Design Document
This document describes the technical design of the HangMan Android application. The application is build in Java with the use of default packages available since version 2.1 

### Version
0.2

### Tech
The HangMan application is based on a singleton that initiates and handles all dependencies. The application needs to be started through this handler and the dependencies should not be ran outside this handler.  Below is the class diagram with it's public methods and relations. Protected methods and attributes are not displayed.

![Class Diagram](https://github.com/ruben-kruiver/Native-App-Studio/tree/master/HangMan_02/doc/HangMan.png)

### Development and collaboration
Coding standards
Protected attributes
Reduced amount of public methods

### Packages
The following packages are used within the HangMan application

### Screenshots
![Class Diagram](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_02/doc/MainMenu.png "Main Menu")
![Class Diagram](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_02/doc/SinglePlayer.png "Single Player game")
![Class Diagram](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_02/doc/MultiPlayer.png "Multiplayer game")
![Class Diagram](https://raw.githubusercontent.com/ruben-kruiver/Native-App-Studio/master/HangMan_02/doc/Settings.png "The settings menu")

### Todos

 - Implement MultiPlayer mode
 - Fix display bug for current word and feedback
 - Display the Figure in the Canvas

License
----

MIT