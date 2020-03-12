import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/**
 * Lab 9
 * Recursion & Directory Tree Walking
 * @version 11/6/18
 */
public class Lab9 extends Application {
	// Window attributes
	private Stage stage;
	private Scene scene;
	private VBox root = new VBox(8);

	// GUI
	private TextArea taLog = new TextArea();

	private Label lblDir = new Label("Directory: ");
	private Label lblFiles = new Label("Files: ");
	private Label lblDirectories = new Label("Directories: ");
	private Label lblBytes = new Label("Bytes: ");

	private TextField tfDir = new TextField();
	private TextField tfFiles = new TextField();
	private TextField tfDirectories = new TextField();
	private TextField tfBytes = new TextField();

	private Button btnSelect = new Button("Select");

	private int dirCount = 0;
	private int fileCount = 0;
	private long totalBytes = 0L;

	/*
		Main
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
		Start
	 */
	public void start(Stage _stage) {
		stage = _stage;
		stage.setTitle("Directory Info");

		// Disable TextFields
		tfFiles.setDisable(true);
		tfDir.setDisable(true);
		tfDirectories.setDisable(true);
		tfBytes.setDisable(true);

		// Top FP
		FlowPane fpTop = new FlowPane(8, 8);
			fpTop.setAlignment(Pos.CENTER);
			fpTop.getChildren().addAll(lblDir, tfDir);

		// Middle FP
		FlowPane fpMid = new FlowPane(8, 8);
			fpMid.setAlignment(Pos.CENTER);
			fpMid.getChildren().addAll(lblFiles, tfFiles, lblDirectories, tfDirectories, lblBytes, tfBytes);

		// Bottom FP
		FlowPane fpBot = new FlowPane(8, 8);
			fpBot.setAlignment(Pos.CENTER);
			fpBot.getChildren().addAll(btnSelect);

		// TextArea configs
		taLog.setPrefHeight(400);
		taLog.setFont(Font.font("MONOSPACED"));
		taLog.setWrapText(true);
		root.getChildren().addAll(fpTop, fpMid, fpBot, taLog);

		// btnSelect Handler
		btnSelect.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				doSelect();
			}
		});

		scene = new Scene(root, 1000, 400);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Opens DirectoryChooser
	 * Select a directory
	 * If NULL, cancel DirectoryChooser
	 * If NOT NULL, call doRecursion(dirName, dirTypeString) to list files in chosen directory
	 */
	private void doSelect() {
		// Directory chooser
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Directory Chooser");

		// Selected dir
		File selectedDir = dc.showDialog(stage);
		String dirName = selectedDir.getAbsolutePath();

		// Cancel dc on null selectDir
		if(selectedDir == null) {
			// NOTE: this is blank on purpose to cancel dc
		}
		else {
			// Recursively list the files in directory
			doRecursion(dirName);
		}
	}

	/**
	 * Recursively list directories
	 * @param dir name of directory selected
	 */
	private void doRecursion(String dir) {
		// List and find files in directory
		File dirFile = new File(dir); // Set dir as a file obj
		File[] files = dirFile.listFiles(); // List files in the dir

		// Loop through each file in files
		for(File file : files) {
			// For type directory
			if(file.isDirectory()) {
				// Print information
				String type = "d - ";
				taLog.appendText(type + file.getName() + "\n");
				dirCount++;

				// // Recursively call this method again to list all the files in the directory
// 				doRecursion(file.getAbsolutePath());
			}

			// For type file
			else if(file.isFile()) {
				// Print information
				String type = "f - ";
				taLog. appendText(type + file.getName() + "\n");
				fileCount++;
				totalBytes += file.length();
			}
		}

		// Set up GUI
		tfDir.setText(dir);
		tfFiles.setText(Integer.toString(fileCount));
		tfDirectories.setText(Integer.toString(dirCount));
		tfBytes.setText(Long.toString(totalBytes));
	}
}