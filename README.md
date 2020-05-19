# Movie Night

Movie Night is an application to store and search movies. You can store a movie given a title and any amount of tags you want. This allows you to filter by tags or search by title. You can edit an entry at any time, whether it be the title or its tags. You can also have the program randomly choose a movie for you from the displayed list. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

* Windows only
* Java
* JavaFX

#### Java
To check if you have Java installed, open a command prompt and enter: 

```
java -version
```

If it returns a java verison, you have Java installed.

If nothing is returned, download Java [here](https://www.java.com/en/).

#### JavaFX

Go to [openjfx](https://gluonhq.com/products/javafx/).
Download the JavaFX Windows SDK

Extract that ZIP folder anywhere out of the way. C:\Program Files is recommended 

### Installing

* Download this repository as a ZIP and extract it to a convenient location.
* Edit the _start.bat_ file.
  * Find ' set PATH_TO_FX="path\to\javafx-sdk-14\lib" '
  * Replace ' path\to\javafx-sdk-14\lib ' with the path to the ' lib ' folder in the javafx folder you extracted earlier
  
```
Ex: PATH_TO_FX="C:\Program Files\javafx-sdk-14.0.1\lib"
```

* **Optional:** Create a shortcut to the start.bat file on your Desktop to easily start the application later 

## Running the application

Run or double click the start.bat file 

### Using the application

* Press the **Add Tag** button to add a tag under *Director* or *Genre* for use later
  * Add as many tags you want. You add more later as well.
  
* To enter a new movie into the list
  * Enter a movie title in the textfield under "Movie Title:"
  * Add 0 to many tags by entering the drop down menu and clicking which one you want to add
    * The tags that will added to that movie will be on the right
    * To remove one of those tags, simply click the text on the right on the one you want to remove
  * Click the **Submit** button to enter the movie into the list
  * Be sure to save after entering a new movie 

* To search for a movie title
  * Enter a movie title in the textfield next to the **Search** button 
    * This can be a substring of the movie or the full title and upper or lower case
      * Ex: "Titanic" and "the titanic" will both return "The Titanic"
  * Press the **Search** button to search the list
  * Click the **Clear** button if you want to clear the searches
      
* To filter the list by a tag
  * Enter the drop down menu next to the **Clear** button 
  * Click a tag you want to filter by
  * Click the **Clear** button if you want to clear the filters 
  
* To edit an entry
  * Select the entru
    * An entry is selected when a line is blue without the mouse hovering over it
  * Press the **Edit** button
    * To change the title
       * Edit the textfield under "Movie Title:"
    * To change the tags
      * To remove
        * Click the text of the tag you want to remove
      * To add 
        * Click on the drop down menu and select which tag you want to add
    * Press the **Enter** button

* To remove an entry
  * Select the entry
    * An entry is selected when a line is blue without the mouse hovering over it
  * Press the **Remove** button
  
* To get a random movie 
  * Filter the list to your liking 
    * The random function will only choose from movies that are shown in the list
  * Press the **Random** button

## Built With

* Eclipse - The IDE
* JavaFX - GUI API

## Author

* **Tyler Morrison** 

## Acknowledgments

* Marco Rodriguez for GUI color design.
* Tyler Espiritu for testing.
* Phillip Frem for initial idea. 
