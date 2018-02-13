# Cards And Whatnot

Basic cards game; my first large-ish coding project and first Java project.  Currently runs Hearts, but I plan to implement Uno as well.  Its current state is "working" but there is a lot left to do in terms of clean-up, refactoring, and user experience.  Only has a Console interface at this point, for which I developed my own "graphics engine".

## Getting Started

Download CardsAndWhatnot.jar from the /dist/ folder and run the console command  
java -jar "CardsAndWhatnot.jar" 

Make sure your console window is large enough, otherwise the content won't display properly.

### Prerequisites

Needs I think at least Java 8.

## Authors

* **William Gollinger** 

## License

This project is licensed under the MIT License

## TODO

* Fix Bugs.
* Add "Main Menu" where users can choose number of human players, make new players, load saved players, etc.
* Finish Round Over and Game Over screens, replay prompt.
* Generalize enough so that Uno can be played: maybe cut specific code out of ConsoleCardGraphics and use it to define the subclass HeartsGraphics, then make branching extension UnoGraphics.
* Turn LayeredCharCanvas into LayeredCanvas\<T\>, learn how to write a GUI.
* General clean-up, write tests, comments, etc. 