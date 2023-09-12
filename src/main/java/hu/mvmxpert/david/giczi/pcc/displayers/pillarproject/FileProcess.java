package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;


import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileProcess {

	public static String PROJECT_FILE_NAME;
	public MeasuredPillarDataController measuredPillarDataController;
	public static String FOLDER_PATH;
	public static String MEAS_FILE_NAME;
	private List<String> pillarBaseMeasData;
	private String delimiter = ";";

	public FileProcess(MeasuredPillarDataController measuredPillarDataController){
		this.measuredPillarDataController = measuredPillarDataController;
	}

	public List<String> getPillarBaseMeasData() {
		return pillarBaseMeasData;
	}


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		directoryChooser.setTitle("Válassz mentési mappát");
		File selectedFile =	directoryChooser.showDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getAbsolutePath();
		}
	}
	public List<String> openPillarBaseProject() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz projekt fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Projekt fájlok (*.plr)", "*.plr");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getParent();
			PROJECT_FILE_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
		}
		return getPillarBaseProjectFileData();
	}

	private List<String> getPillarBaseProjectFileData(){

		List<String> projectData = new ArrayList<>();

		if(PROJECT_FILE_NAME == null || FOLDER_PATH == null)
			return projectData;

		File file = new File(FOLDER_PATH + "/" + PROJECT_FILE_NAME+ ".plr");

		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while( row != null ) {
				projectData.add(row);
				row = reader.readLine();
			}
		}
		catch (IOException e) {

		}

		return projectData;
	}

	public List<String> openIntersectionProject() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz projekt fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Projekt fájlok (*.ins)", "*.plr");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getParent();
			PROJECT_FILE_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
		}
		return getIntersectionFileData();
	}

	private List<String> getIntersectionFileData(){

		List<String> projectData = new ArrayList<>();

		if(PROJECT_FILE_NAME == null || FOLDER_PATH == null)
			return projectData;

		File file = new File(FOLDER_PATH + "/" + PROJECT_FILE_NAME+ ".ins");

		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while( row != null ) {
				projectData.add(row);
				row = reader.readLine();
			}
		}
		catch (IOException e) {

		}

		return projectData;
	}

	public void getIntersectionMeasureFileData() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz mérési fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Mérési fájlok (*.txt)", "*.txt");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			setIntersectionData(selectedFile);
			FOLDER_PATH = selectedFile.getParent();
		}
	}

	private void setIntersectionData(File selectedFile){

		try(BufferedReader reader = new BufferedReader(
				new FileReader(selectedFile, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while (row != null) {
				String[] rowData = row.split(delimiter);
				if(!measuredPillarDataController
						.intersectionInputDataWindow.startPointIdField.getText().isEmpty() &&
						rowData[5].equalsIgnoreCase(measuredPillarDataController
						.intersectionInputDataWindow.startPointIdField.getText().trim())) {

				measuredPillarDataController.intersectionInputDataWindow.startField_X.setText(rowData[7]);
				measuredPillarDataController.intersectionInputDataWindow.startField_Y.setText(rowData[8]);
				}
				if(!measuredPillarDataController
						.intersectionInputDataWindow.endPointIdField.getText().isEmpty() &&
						rowData[5].equalsIgnoreCase(measuredPillarDataController
						.intersectionInputDataWindow.endPointIdField.getText().trim())) {
					measuredPillarDataController.intersectionInputDataWindow.endField_X.setText(rowData[7]);
					measuredPillarDataController.intersectionInputDataWindow.endField_Y.setText(rowData[8]);
				}

					if(!measuredPillarDataController
							.intersectionInputDataWindow.standingAIdField.getText().isEmpty() &&
							rowData[0].equalsIgnoreCase(measuredPillarDataController
									.intersectionInputDataWindow.standingAIdField.getText().trim())) {
						measuredPillarDataController.intersectionInputDataWindow
								.standingAPointField_X.setText(rowData[1]);
						measuredPillarDataController.intersectionInputDataWindow
								.standingAPointField_Y.setText(rowData[2]);
						measuredPillarDataController.intersectionInputDataWindow
								.standingAPointField_Z.setText(rowData[3]);
					}
						if(!measuredPillarDataController
								.intersectionInputDataWindow.standingBIdField.getText().isEmpty() &&
								rowData[0].equalsIgnoreCase(measuredPillarDataController
										.intersectionInputDataWindow.standingBIdField.getText().trim())) {
							measuredPillarDataController.intersectionInputDataWindow
									.standingBPointField_X.setText(rowData[1]);
							measuredPillarDataController.intersectionInputDataWindow
									.standingBPointField_Y.setText(rowData[2]);
							measuredPillarDataController.intersectionInputDataWindow
									.standingBPointField_Z.setText(rowData[3]);
					}
						if(!measuredPillarDataController
								.intersectionInputDataWindow.newPointIdField.getText().isEmpty() &&
						rowData[0].equalsIgnoreCase(measuredPillarDataController.
								intersectionInputDataWindow.standingAIdField.getText()) &&
						rowData[6].equalsIgnoreCase(measuredPillarDataController
								.intersectionInputDataWindow.newPointIdField.getText())){
							String[] azimuthData = rowData[10].split("\\.");
							measuredPillarDataController
									.intersectionInputDataWindow
									.standingAPointAzimuthAngleField.setText(azimuthData[0]);
							measuredPillarDataController
									.intersectionInputDataWindow
									.standingAPointAzimuthMinField.setText(azimuthData[1].substring(0, 2));
							measuredPillarDataController
									.intersectionInputDataWindow
									.standingAPointAzimuthSecField.setText(azimuthData[1].substring(2, 4));
							String[] elevationData = rowData[11].split("\\.");
							measuredPillarDataController
									.intersectionInputDataWindow
									.standingAPointElevationAngleField.setText(elevationData[0]);
							measuredPillarDataController
									.intersectionInputDataWindow
									.standingAPointElevationMinField.setText(elevationData[1].substring(0, 2));
							measuredPillarDataController
									.intersectionInputDataWindow
									.standingAPointElevationSecField.setText(elevationData[1].substring(2, 4));
						}
				if(!measuredPillarDataController
						.intersectionInputDataWindow.newPointIdField.getText().isEmpty() &&
						rowData[0].equalsIgnoreCase(measuredPillarDataController.
								intersectionInputDataWindow.standingBIdField.getText()) &&
						rowData[6].equalsIgnoreCase(measuredPillarDataController
								.intersectionInputDataWindow.newPointIdField.getText())){
					String[] azimuthData = rowData[10].split("\\.");
					measuredPillarDataController
							.intersectionInputDataWindow
							.standingBPointAzimuthAngleField.setText(azimuthData[0]);
					measuredPillarDataController
							.intersectionInputDataWindow
							.standingBPointAzimuthMinField.setText(azimuthData[1].substring(0, 2));
					measuredPillarDataController
							.intersectionInputDataWindow
							.standingBPointAzimuthSecField.setText(azimuthData[1].substring(2, 4));
					String[] elevationData = rowData[11].split("\\.");
					measuredPillarDataController
							.intersectionInputDataWindow
							.standingBPointElevationAngleField.setText(elevationData[0]);
					measuredPillarDataController
							.intersectionInputDataWindow
							.standingBPointElevationMinField.setText(elevationData[1].substring(0, 2));
					measuredPillarDataController
							.intersectionInputDataWindow
							.standingBPointElevationSecField.setText(elevationData[1].substring(2, 4));
				}
				row = reader.readLine();
			}
		}
		catch (IOException e) {
		}
	}

	public void getPillarBaseMeasureFileData() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz mérési fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Mérési fájlok (*.txt)", "*.txt");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			setPillarBaseData(selectedFile);
			MEAS_FILE_NAME = selectedFile.getName();
			FOLDER_PATH = selectedFile.getParent();
		}
	}

	private void setPillarBaseData(File selectedFile){
		pillarBaseMeasData = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(
				new FileReader(selectedFile, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while (row != null) {
				if (row.endsWith(PointType.alap.name()) ||
						row.endsWith(PointType.ALAP.name()) ||
						row.endsWith(PointType.CSUCS.name() + delimiter) ||
						row.endsWith(PointType.csucs.name() + delimiter)) {
					pillarBaseMeasData.add(row);
				}
				row = reader.readLine();
			}
		}
		catch (IOException e) {
		}
	}
	public void savePillarProjectData(){
		File file = new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + ".plr");
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))){
			writer.write(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID());
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getX_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getY_coord()));
			writer.newLine();
			writer.write(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID());
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getAngleRotation()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getMinRotation()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getSecRotation()));
			writer.newLine();
			if(  measuredPillarDataController.measuredPillarData.getPillarBasePoints() != null ){
				for (MeasPoint basePoint : measuredPillarDataController.measuredPillarData.getPillarBasePoints()) {
					writer.write(basePoint.toString());
					writer.newLine();
				}
			}
			if(  measuredPillarDataController.measuredPillarData.getPillarTopPoints() != null ) {
				for (MeasPoint topPoint : measuredPillarDataController.measuredPillarData.getPillarTopPoints()) {
					writer.write(topPoint.toString());
					writer.newLine();
				}
			}
		}catch (IOException e){
		}
	}

	public void saveIntersectionData(){
		File file = new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + ".ins");
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))){
			if( !measuredPillarDataController.intersectionInputDataWindow.startPointIdField.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow
						.startPointIdField.getText().toUpperCase());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.startField_X.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.startField_X.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.startField_Y.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.startField_Y.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.endPointIdField.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow
						.endPointIdField.getText().toUpperCase());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.endField_X.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.endField_X.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.endField_Y.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.endField_Y.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.newPointIdField.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow
						.newPointIdField.getText().toUpperCase());
				writer.newLine();
			}
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAIdField.getText().toUpperCase());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointField_X.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointField_Y.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointField_Z.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointAzimuthAngleField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointAzimuthMinField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointAzimuthSecField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointElevationAngleField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointElevationMinField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointElevationSecField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBIdField.getText().toUpperCase());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointField_X.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointField_Y.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointField_Z.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthAngleField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthMinField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthSecField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationAngleField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationMinField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationSecField.getText());
			writer.newLine();
		}
		catch (IOException e){
		}
	}

	public static boolean isExistedProjectFile(String extension){
		return  new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + "." + extension).exists();
	}

}
