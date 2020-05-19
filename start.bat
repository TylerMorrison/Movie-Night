@ECHO OFF 
:: This batch file runs the movie night program
TITLE Peachy Movie Night
ECHO Please wait... Locating all the files.
:: Section 1: Loading JavaFX files.
ECHO ============================
ECHO JavaFX Files
ECHO ============================
set PATH_TO_FX="path\to\javafx-sdk-14\lib"
:: Section 2: Compile program.
ECHO ============================
ECHO Setting up the program
ECHO ============================
javac --module-path %PATH_TO_FX% --add-modules javafx.controls Main.java
javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml Main.java
:: Section 3: Run the program.
ECHO ============================
ECHO Running the program
ECHO ============================
java --module-path %PATH_TO_FX% --add-modules javafx.controls Main
PAUSE
