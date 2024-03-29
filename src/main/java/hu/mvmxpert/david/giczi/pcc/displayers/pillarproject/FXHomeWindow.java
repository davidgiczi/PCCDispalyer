package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FXHomeWindow extends Application {

	public Stage homeStage;
	private AnchorPane pane;
	public static Menu setBaseData;
	public static Menu controlSteakoutedPoint;
	public MeasuredPillarDataController measuredPillarDataController;

	public FXHomeWindow(){
		this.measuredPillarDataController = new MeasuredPillarDataController(this);
	}

	@Override
	public void stop() {
		System.exit(0);
	}

	@Override
	public void start(Stage stage) {
		homeStage = stage;
		pane = new AnchorPane();
		addBackgroundImage();
		addMenu();
		stage.setTitle("Nagyfeszültségű távvezeték oszlop alapjának kitűzése");
		stage.getIcons().add(new Image("file:images/MVM.jpg"));
		stage.setWidth(550);
		stage.setHeight(800);
		stage.setResizable(false);
		Scene root = new Scene(pane);
		stage.setScene(root);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addMenu() {
		MenuBar menuBar = new MenuBar();
		menuBar.setCursor(Cursor.HAND);
		Menu projectProcess = new Menu("Projekt műveletek");
		MenuItem openProject = new MenuItem("Projekt megnyitása");
		MenuItem createProject = new MenuItem("Új projekt létrehozása");
		MenuItem closeProject = new MenuItem("Program bezárása");
		projectProcess.getItems().addAll(openProject, createProject, new SeparatorMenuItem(), closeProject); 
		setBaseData = new Menu("Alap adatainak megadása");
		setBaseData.setDisable(true);
		MenuItem calcDistanceBetweenLegs = new MenuItem("Oszloplábak távolságának számítása");
		MenuItem calcWeightBasePoints = new MenuItem("Súlyalap pontjainak számítása");
		MenuItem calcPlateBasePoints = new MenuItem("Lemezalap pontjainak számítása");
		setBaseData.getItems().addAll(calcDistanceBetweenLegs, 
				new SeparatorMenuItem(), calcWeightBasePoints, calcPlateBasePoints);
		controlSteakoutedPoint = new Menu("Kitűzés vizsgálata");
		controlSteakoutedPoint.setDisable(true);
		MenuItem controll = new MenuItem("Kitűzött pontok ellenőrzése");
		controlSteakoutedPoint.getItems().add(controll);
		Menu pillarProject = new Menu("Oszlop bemérés");
		MenuItem openPillarProject = new MenuItem("Projekt megnyitása");

		openPillarProject.setOnAction(e -> {
		measuredPillarDataController.openPillarBaseProject();
		});

		MenuItem createPillarProject = new MenuItem("Új projekt létrehozása");
		createPillarProject.setOnAction(e -> {
			measuredPillarDataController.openMeasuredData();
		});
		MenuItem createIntersection = new MenuItem("Előmetszés létrehozása");
		createIntersection.setOnAction( e -> {
			measuredPillarDataController.openIntersectionInputDataWindow();
		});
		MenuItem openIntersection = new MenuItem("Előmetszés megnyitása");
		openIntersection.setOnAction(e -> {
			measuredPillarDataController.openIntersectionProject();
		});
		pillarProject.getItems().addAll(openPillarProject,createPillarProject,
				new SeparatorMenuItem(), createIntersection, openIntersection);
		menuBar.getMenus().addAll(projectProcess, setBaseData,
				controlSteakoutedPoint, pillarProject);
		VBox vBox = new VBox(menuBar);
		vBox.setPrefWidth(550);
		pane.getChildren().add(vBox);
	}
	
	private void addBackgroundImage() {
		Image img = new Image("file:images/pillars" + (int) (Math.random() * 3 + 1)  +".jpg");
		ImageView view = new ImageView(img);
		view.setFitWidth(550);
		view.setFitHeight(800);
		pane.getChildren().add(view);
	}
	
}
