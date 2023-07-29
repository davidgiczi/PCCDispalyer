package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MeasuredPillarDataController {

    public final FileProcess fileProcess;
    public final MeasuredPillarData measuredPillarData;
    public MeasPointListDisplayer measuredPointListDisplayer;
    public PillarDisplayer pillarDisplayer;
    public InputPillarDataWindow inputPillarDataWindow;
    public FXHomeWindow fxHomeWindow;

    public MeasuredPillarDataController(FXHomeWindow fxHomeWindow){
        this.fxHomeWindow = fxHomeWindow;
        this.fileProcess = new FileProcess(this);
        this.measuredPillarData = new MeasuredPillarData(this);
    }

    public void init(){
      measuredPillarData.getMeasPillarPoints().clear();
    }
    public void getInfoAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        alert.initOwner(fxHomeWindow.homeStage);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    public void openMeasuredData(){
        fileProcess.getMeasureFileData();
        if( fileProcess.getMeasData().isEmpty() ){
            return;
        }
        measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getMeasData());
        this.measuredPointListDisplayer =
                new MeasPointListDisplayer(this);
    }

    public void createNewProject(){
        measuredPointListDisplayer.stage.hide();
        this.inputPillarDataWindow = new InputPillarDataWindow(this);
    }

    public void addMoreMeasuredPillarData(){
        measuredPointListDisplayer.stage.hide();
        openMeasuredData();
    }

    public void openNewMeasuredPillarData(){
        measuredPointListDisplayer.stage.hide();
        init();
        openMeasuredData();
    }

    public void onlClickCountButtonProcess(){
        fxHomeWindow.homeStage.hide();
       if( !InputDataValidator.isValidProjectName(inputPillarDataWindow.projectNameField.getText() ) ){
           getInfoAlert("Hibás projektnév megadása", "A projekt neve legalább 3 betű karakter lehet.");
           return;
       };
       FileProcess.PROJECT_FILE_NAME = inputPillarDataWindow.projectNameField.getText().trim();
       if( FileProcess.FOLDER_PATH == null ){
           getInfoAlert("Hiányzó mentési mappa","Mentési mappa választása szükséges");
           return;
       }
       try {
           int centerPillarID =
                   InputDataValidator
                           .isValidInputPositiveIntegerValue(inputPillarDataWindow.centerPillarIDField.getText());
       }
       catch (NumberFormatException e){
           getInfoAlert("Nem megfelelő az oszlop száma",
                   "Az oszlop száma csak pozitív egész érték lehet.");
           return;
       }
        try {
            double centerPillarX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue(inputPillarDataWindow.centerPillarField_X.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az oszlop X koordinátája",
                    "Az oszlop X koordinátája csak nem negatív szám lehet.");
            return;
        }
        try {
            double centerPillarY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue(inputPillarDataWindow.centerPillarField_Y.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az oszlop Y koordinátája",
                    "Az oszlop Y koordinátája csak nem negatív szám lehet.");
            return;
        }

        try{
            int angle = InputDataValidator.isValidAngleValue(inputPillarDataWindow.rotationAngleField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő e forgatás szög értéke",
                    "A forgatás szög értéke csak 360-nál kisebb egész szám lehet.");
            return;
        }
        try{
            int min = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationMinField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a forgatás szögperc értéke",
                    "A forgatás szögperc értéke csak 59-nél kisebb egész szám lehet.");
            return;
        }
        try{
            int sec = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationSecField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a forgatás szögmásodperc értéke",
                    "A forgatás szögmásodperc értéke csak 59-nél kisebb egész szám lehet.");
            return;
        }
        try {
            int directionPillarID =
                    InputDataValidator
                            .isValidInputPositiveIntegerValue(inputPillarDataWindow.directionPillarIDField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop száma",
                    "Az oszlop száma csak pozitív egész érték lehet.");
            return;
        }
        try {
            double directionPillarX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue(inputPillarDataWindow.directionPillarField_X.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop X koordinátája",
                    "Az oszlop X koordinátája csak nem negatív szám lehet.");
            return;
        }
        try {
            double directionPillarY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue(inputPillarDataWindow.directionPillarField_Y.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop Y koordinátája",
                    "Az oszlop Y koordinátája csak nem negatív szám lehet.");
            return;
        }
        inputPillarDataWindow.stage.hide();
        this.pillarDisplayer = new PillarDisplayer(this);
    }

}
