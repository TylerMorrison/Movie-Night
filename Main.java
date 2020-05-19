import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application
{
	private static File file;
	private static FileWriter writer;
	
	private final static Label movieTitleLabel = new Label("Movie Title: ");
	private final static Label tagsLabel = new Label("Tags");
	private final static Button submit = new Button("Submit");
	private final static Label directorLabel = new Label("Director");
	private final static Label genreLabel = new Label("Genre");
	private final static Button save = new Button("Save");
	private final static Button edit = new Button("Edit");
	private final static Button random = new Button("Random");
	private final static Button remove = new Button("Remove");
	private static TextField movieTitleInput = new TextField();
	private static ComboBox<String> genreBox = new ComboBox<String>();
	private static ComboBox<String> directorBox = new ComboBox<String>();
	private static ObservableList<String> genreTags = FXCollections.observableArrayList();
	private static ObservableList<String> directorTags = FXCollections.observableArrayList();
	private static Button addTag = new Button("Add Tag");
	private static String tagsToAdd = "";
	private static TextField searchText = new TextField();
	private static Button search = new Button("Search");
	private final static Label filterLabel = new Label("Filter: ");
	private static ComboBox<String> filter = new ComboBox<String>();
	private static Button clear = new Button("Clear");
	private static Text tagsToAddSpacing = new Text("                                               ");
	private static String newTagsToAdd = "";
	
	private static VBox root;
	private static VBox movieInput;
	private static VBox tagsVBox;
	private static HBox tagsHBox;
	private static VBox directorVBox;
	private static VBox genreVBox;
	private static HBox top;
	private static HBox searchHBox;
	private static HBox filterHBox;
	private static VBox movieListVBox;
	private static VBox tagListVBox;
	private static HBox bottomButtons;
	private static VBox tagsToAddVBox;
	
	private static ListView<String> movieList = new ListView<String>();
	private static ListView<String> tagList = new ListView<String>();
	private static HBox bottom;
	
	private static ObservableList<String> movies = FXCollections.observableArrayList();
	private static ObservableList<String> tags = FXCollections.observableArrayList();
	
	@SuppressWarnings({ "resource" })
	public void start(Stage primaryStage)
	{
		//Load the saved movies and their tags into program at start up
		file = new File("movies.txt");
		try { //Load file into ListViews 
			Scanner fileReader = new Scanner(file).useDelimiter("\\s*;\\s*");
			//read director tags
			while(fileReader.hasNextLine())
			{
				String checkEnd = fileReader.next();
				if(checkEnd.equals("|"))
					break;
//				String[] tempTags = tempTag.split("\\s+");
				directorTags.add(checkEnd);
			}
			//read genre tags
			while(fileReader.hasNextLine())
			{
				String checkEnd = fileReader.next();
				if(checkEnd.equals("|"))
					break;
//				String[] tempTags = tempTag.split("\\s+");
				genreTags.add(checkEnd);
			}
			//read lists
			while(fileReader.hasNextLine())
			{
				String checkEnd = fileReader.next();
				if(checkEnd.equals("|"))
					break;
//				String[] tempTags = tempTag.split("\\s+");
				movies.add(checkEnd);
				tags.add(fileReader.next());
			}
			fileReader.close();
		} catch (FileNotFoundException e) { //If file not found, send error and close program
			Alert fileNotFoundAlert = new Alert(AlertType.ERROR);
			fileNotFoundAlert.setHeaderText("File not found!");
			fileNotFoundAlert.setContentText("Be sure that you have a movie.txt file in the same location as the program");
			fileNotFoundAlert.setTitle("File Error");
			fileNotFoundAlert.showAndWait();
			return;
		}
		
		
		genreBox.setItems(genreTags);
		directorBox.setItems(directorTags);
		
		genreBox.setMaxWidth(100);
		genreBox.setMinWidth(100);
		directorBox.setMaxWidth(100);
		directorBox.setMinWidth(100);
		
		directorVBox = new VBox(5, directorLabel, directorBox);
		genreVBox = new VBox(5, genreLabel, genreBox);
		
		directorVBox.setMaxWidth(50);
		genreVBox.setMaxWidth(50);
		directorVBox.setMinWidth(50);
		genreVBox.setMinWidth(50);
		
		directorVBox.setAlignment(Pos.CENTER);
		genreVBox.setAlignment(Pos.CENTER);
		
		tagsToAddVBox = new VBox(10, tagsToAddSpacing);
		tagsToAddVBox.setAlignment(Pos.CENTER);
		tagsHBox = new HBox(55, directorVBox, genreVBox);
		tagsVBox = new VBox(5, tagsLabel, tagsHBox, addTag);
		
		tagsHBox.setAlignment(Pos.CENTER);
		tagsVBox.setAlignment(Pos.CENTER);		
		
		movieInput = new VBox(5, movieTitleLabel, movieTitleInput, submit);
		movieInput.setTranslateY(11);
		movieInput.setAlignment(Pos.CENTER);
		
		top = new HBox(200, movieInput, tagsVBox, tagsToAddVBox);
		top.setAlignment(Pos.CENTER);
		
		movieList.setItems(movies);
		tagList.setItems(tags);
		movieList.setMinWidth(475);
		tagList.setMinWidth(475);
		
		filter.setMaxWidth(150);
		filter.setMinWidth(150);
		filter.setMinHeight(25);
		filter.setMaxHeight(25);
		
		searchHBox = new HBox(5, searchText, search);
		filterHBox = new HBox(5, filterLabel, filter, clear);
		searchHBox.setAlignment(Pos.CENTER_LEFT);
		filterHBox.setAlignment(Pos.CENTER_RIGHT);
		
		movieListVBox = new VBox(5, searchHBox, movieList);
		tagListVBox = new VBox(5, filterHBox, tagList);
		
		ObservableList<String> filterItems = FXCollections.observableArrayList();
		filterItems.addAll(directorBox.getItems());
		filterItems.addAll(genreBox.getItems());
		filter.setItems(filterItems);
		
		bottom = new HBox(movieListVBox, tagListVBox);
		bottom.setAlignment(Pos.CENTER);
		
		bottomButtons = new HBox(50, save, edit, random, remove);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.setPadding(new Insets(5));
		save.setMinWidth(50);
		edit.setMinWidth(50);
		save.setMaxWidth(50);
		edit.setMaxWidth(50);
		
		root = new VBox(50, top, bottom, bottomButtons);
		root.setAlignment(Pos.CENTER);
		Scene myScene = new Scene(root, 1200, 700);
		myScene.getStylesheets().add("style.css");
		
		//Add movie to ListViews
		submit.setOnAction(event -> {
			if(!movieTitleInput.getText().isEmpty())
			{
				if(!movies.contains(movieTitleInput.getText()))
				{
					movies.add(movieTitleInput.getText());
					tagList.getItems().add(tagsToAdd);
					directorBox.getSelectionModel().clearSelection();
					genreBox.getSelectionModel().clearSelection();
					movieTitleInput.clear();
					tagsToAdd = "";
					tagsToAddVBox.getChildren().clear();
					tagsToAddVBox.getChildren().add(tagsToAddSpacing);
				}
				else
				{
					Alert dupMovie = new Alert(AlertType.INFORMATION);
					dupMovie.setTitle("Duplicate Movie!");
					dupMovie.setHeaderText("The movie is already on the list");
					dupMovie.showAndWait();
				}
			}
			else
			{
				Alert noMovie = new Alert(AlertType.INFORMATION);
				noMovie.setTitle("No Movie!");
				noMovie.setHeaderText("Add a movie name in the text field");
				noMovie.showAndWait();
			}
		});
		
		genreBox.setOnAction(event ->{
			tagsToAdd += genreBox.getSelectionModel().getSelectedItem() + " ";
			Text text = new Text((String)genreBox.getSelectionModel().getSelectedItem());
			text.getStyleClass().add("text-color");
			text.setOnMouseClicked(mouseEvent -> {
				tagsToAddVBox.getChildren().remove(text);
				tagsToAdd= tagsToAdd.replace(text.getText(), "");
			});
			tagsToAddVBox.getChildren().add(text);
			
		});
		
		directorBox.setOnAction(event ->{
			tagsToAdd += directorBox.getSelectionModel().getSelectedItem() + " ";
			Text text = new Text((String)directorBox.getSelectionModel().getSelectedItem());
			text.getStyleClass().add("text-color");
			text.setOnMouseClicked(mouseEvent -> {
				tagsToAddVBox.getChildren().remove(text);
				tagsToAdd= tagsToAdd.replace(text.getText(), "");
			});
			tagsToAddVBox.getChildren().add(text);
		});
		
		save.setOnAction(event ->{
			try {
				writer = new FileWriter("movies.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i<directorTags.size(); i++)
			{
				try {
					writer.write(directorTags.get(i) + " ; ");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				writer.write("\n|;\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i<genreTags.size(); i++)
			{
				try {
					writer.write(genreTags.get(i) + " ; ");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				writer.write("\n|;\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i<movies.size(); i++)
        	{
				try {
					writer.write(movies.get(i) + " ; " + tags.get(i) + ";\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
			
			try {
				writer.write("|;");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		movieList.setOnMouseClicked(event ->{
			tagList.getSelectionModel().select(movieList.getSelectionModel().getSelectedIndex());
		});
		
		tagList.setOnMouseClicked(event ->{
			movieList.getSelectionModel().select(tagList.getSelectionModel().getSelectedIndex());
		});
		
		search.setOnAction(event->{
			filter.getSelectionModel().clearSelection();
			movieList.setItems(movies);
			tagList.setItems(tags);
			String text = searchText.getText().toLowerCase();
			ObservableList<String> searchMovie = FXCollections.observableArrayList();
			ObservableList<String> searchTag = FXCollections.observableArrayList();
			for(int i = 0; i<movieList.getItems().size(); i++)
			{
				if(movieList.getItems().get(i).toLowerCase().contains(text)) 
				{
					searchMovie.add(movies.get(i));
					searchTag.add(tags.get(i));
				}
			}
			movieList.setItems(searchMovie);
			tagList.setItems(searchTag);
		});
		
		filter.setOnAction(event ->{
			String text = (String) filter.getSelectionModel().getSelectedItem();
			ObservableList<String> filterMovie = FXCollections.observableArrayList();
			ObservableList<String> filterTag = FXCollections.observableArrayList();
			for(int i = 0; i<tags.size(); i++)
			{
				String currTag = tags.get(i);
				String[] currTags = currTag.split("\\s+");
				for(String j : currTags)
				{
					if(j.equals(text))
					{
						filterMovie.add(movies.get(i));
						filterTag.add(tags.get(i));
					}
				}
			}
			movieList.setItems(filterMovie);
			tagList.setItems(filterTag);
		});
		
		clear.setOnAction(event -> {
			filter.getSelectionModel().clearSelection();
			searchText.clear();
			movieList.setItems(movies);
			tagList.setItems(tags);
			
		});
		
		remove.setOnAction(event->{
			String movieRemoved = movieList.getSelectionModel().getSelectedItem();
			String tagRemoved = tagList.getSelectionModel().getSelectedItem();
			movieList.getItems().remove(movieRemoved);
			tagList.getItems().remove(tagRemoved);
	
			movies.remove(movieRemoved);
			tags.remove(tagRemoved);
		});
		
		addTag.setOnAction(event ->{
			addTag.setDisable(true);
			TextField newTagTF = new TextField();
			newTagTF.setMaxWidth(100);
			Button enter = new Button("Enter");
			RadioButton directorRB = new RadioButton("Director");
			RadioButton genreRB = new RadioButton("Genre");
			ToggleGroup category = new ToggleGroup();
			directorRB.setToggleGroup(category);
			genreRB.setToggleGroup(category);
			 
            VBox secondaryLayout = new VBox(10, newTagTF, directorRB, genreRB, enter);
            secondaryLayout.setAlignment(Pos.CENTER);
            
            Scene secondScene = new Scene(secondaryLayout, 300, 200);
            secondScene.getStylesheets().add("style.css");

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Add a Tag");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() + primaryStage.getWidth()/2 - secondScene.getWidth()/2);
	        newWindow.setY(primaryStage.getY() + primaryStage.getHeight()/2 - secondScene.getHeight()/2);

            newWindow.show();
            
            newWindow.setOnCloseRequest(closeEvent ->{
            	addTag.setDisable(false);
            });
            
            enter.setOnAction(enterEvent ->{
            	String newTag = newTagTF.getText();
            	boolean exists = false;
            	if(newTag.trim().isEmpty() || !directorRB.isSelected() || !genreRB.isSelected())
            	{
            		if(directorRB.isSelected())
            		{
            			for(String i : directorTags)
            			{
            				if(i.equals(newTag)) 
        					{
            					exists = true;
            					break;
        					}
            			}
            			if(!exists)
            			{
	            			directorTags.add(newTag);
	            			filterItems.add(newTag);
	            			//directorBox.setItems(directorTags);
            			}
            			else
            			{
            				Alert dupTag = new Alert(AlertType.INFORMATION);
            				dupTag.setTitle("Duplicate Tag");
            				dupTag.setHeaderText("The tag already exists");
            				dupTag.showAndWait();
            			}
            		}
            		
            		if(genreRB.isSelected())
            		{
            			for(String i : genreTags)
            			{
            				if(i.equals(newTag)) 
        					{
            					exists = true;
            					break;
        					}
            			}
            			if(!exists)
            			{
            				genreTags.add(newTag);
            				filterItems.add(newTag);
	            			//genreBox.setItems(genreTags);
            			}
            			else
            			{
            				Alert dupTag = new Alert(AlertType.INFORMATION);
            				dupTag.setTitle("Duplicate Tag");
            				dupTag.setHeaderText("The tag already exists");
            				dupTag.showAndWait();
            			}
            		}
            		
            	}
            	addTag.setDisable(false);
            	newWindow.close();
            });
			
		});
		
		edit.setOnAction(event ->{
			if(movieList.getSelectionModel().getSelectedIndex() > -1)
			{
				edit.setDisable(true);
				newTagsToAdd = tagList.getSelectionModel().getSelectedItem();
				Label newMovieTitleLabel = new Label("Movie Title: ");
				Label newTagsLabel = new Label("Tags: ");
				TextField newMovieTitle = new TextField();
				newMovieTitle.setText(movieList.getSelectionModel().getSelectedItem());
				newMovieTitle.setMaxWidth(100);
				Button enter = new Button("Enter");
				 
				ComboBox<String> newDirectorBox = new ComboBox<String>();
				ComboBox<String> newGenreBox = new ComboBox<String>();
				newDirectorBox.setItems(directorTags);
				newGenreBox.setItems(genreTags);
				newDirectorBox.setMaxWidth(100);
				newDirectorBox.setMinWidth(100);
				newGenreBox.setMaxWidth(100);
				newGenreBox.setMinWidth(100);
				
				VBox newDirectorTag = new VBox(5, directorLabel, newDirectorBox);
				VBox newGenreTag = new VBox(5, genreLabel, newGenreBox);
				HBox newTagHBox = new HBox(5, newDirectorTag, newGenreTag);
				newDirectorTag.setAlignment(Pos.CENTER);
				newGenreTag.setAlignment(Pos.CENTER);
				newTagHBox.setAlignment(Pos.CENTER);
				
				HBox newTagsToAddHBox = new HBox(5);
				newTagsToAddHBox.setAlignment(Pos.CENTER);
				
				String[] oldTags = tagList.getSelectionModel().getSelectedItem().split("\\s+");
				for(String i : oldTags)
				{
					Text text = new Text(i + " ");
					text.getStyleClass().add("text-color");
					newTagsToAddHBox.getChildren().add(text);
					text.setOnMouseClicked(mouseEvent ->{
						newTagsToAddHBox.getChildren().remove(text);
						newTagsToAdd= newTagsToAdd.replace(text.getText(), "");
					});
				}
				
		        VBox secondaryLayout = new VBox(10, newMovieTitleLabel, newMovieTitle, newTagsLabel, newTagsToAddHBox, newTagHBox, enter);
		        secondaryLayout.setAlignment(Pos.CENTER);
		        
		        Scene secondScene = new Scene(secondaryLayout, 400, 300);
		        secondScene.getStylesheets().add("style.css");
		        
		        // New window (Stage)
		        Stage newWindow = new Stage();
		        newWindow.setTitle("Edit a movie");
		        newWindow.setScene(secondScene);
		
		        // Set position of second window, related to primary window.
		        newWindow.setX(primaryStage.getX() + primaryStage.getWidth()/2 - secondScene.getWidth()/2);
		        newWindow.setY(primaryStage.getY() + primaryStage.getHeight()/2 - secondScene.getHeight()/2);
		
		        newWindow.show();
		        
		        newDirectorBox.setOnAction(directorEvent ->{
		        	Text text = new Text(newDirectorBox.getSelectionModel().getSelectedItem());
		        	text.getStyleClass().add("text-color");
		        	newTagsToAddHBox.getChildren().add(text);
		        	newTagsToAdd += newDirectorBox.getSelectionModel().getSelectedItem() + " ";
		        	text.setOnMouseClicked(mouseEvent ->{
		        		newTagsToAddHBox.getChildren().remove(text);
		        		newTagsToAdd= newTagsToAdd.replace(text.getText(), "");
		        	});
		        });
		        
		        newGenreBox.setOnAction(genreEvent->{
		        	Text text = new Text(newGenreBox.getSelectionModel().getSelectedItem());
		        	text.getStyleClass().add("text-color");
		        	newTagsToAddHBox.getChildren().add(text);
		        	newTagsToAdd += newGenreBox.getSelectionModel().getSelectedItem() + " ";
		        	text.setOnMouseClicked(mouseEvent ->{
		        		newTagsToAddHBox.getChildren().remove(text);
		        		newTagsToAdd= newTagsToAdd.replace(text.getText(), "");
		        	});
		        });
		        
		        newWindow.setOnCloseRequest(closeEvent ->{
		        	edit.setDisable(false);
		        });
		        
		        enter.setOnAction(enterEvent ->{
		        	
		        	movies.set(movieList.getSelectionModel().getSelectedIndex(), newMovieTitle.getText());
		        	tags.set(tagList.getSelectionModel().getSelectedIndex(), newTagsToAdd);
		        	
		        	newTagsToAddHBox.getChildren().clear();
		        	edit.setDisable(false);
		        	newWindow.close();
		        });
			}
		});
		
		random.setOnAction(event->{
			if(!movieList.getItems().isEmpty())
			{
				Random ran = new Random();
				int ranNum = ran.nextInt(movieList.getItems().size());
				Alert ranMovie = new Alert(AlertType.CONFIRMATION);
				ranMovie.setTitle("Random Movie");
				ranMovie.setHeaderText(movieList.getItems().get(ranNum));
				ranMovie.showAndWait();
			}
		});
		
		primaryStage.setOnCloseRequest(event ->{
			ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
			Alert saveAlert = new Alert(AlertType.CONFIRMATION, "", yes, no);
			saveAlert.setTitle("Save file");
			saveAlert.setHeaderText("Do you wish to save your movies and tag list?");
			Optional<ButtonType> result = saveAlert.showAndWait();
			if(result.isPresent())
			{
				if(result.get().getText() == "Yes")
				{
					save.fire();
				}
			}
			
		});
		
		primaryStage.setScene(myScene);
		primaryStage.setTitle("Peachy Movie Night");
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
