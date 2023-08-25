package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class MeasuredPillarDataController {

    public final FileProcess fileProcess;
    public final MeasuredPillarData measuredPillarData;
    public MeasPointListDisplayer measuredPointListDisplayer;
    public PillarBaseDifferenceDisplayer pillarBaseDifferenceDisplayer;
    public PillarBaseDisplayer pillarBaseDisplayer;
    public InputPillarDataWindow inputPillarDataWindow;
    public FXHomeWindow fxHomeWindow;
    public List<String> projectFileData;

    public MeasuredPillarDataController(FXHomeWindow fxHomeWindow){
        this.fxHomeWindow = fxHomeWindow;
        this.fileProcess = new FileProcess(this);
        this.measuredPillarData = new MeasuredPillarData(this);
    }

    public void init(){
        if( measuredPillarData.getMeasPillarPoints() != null && !
                measuredPillarData.getMeasPillarPoints().isEmpty() ){
            measuredPillarData.getMeasPillarPoints().clear();
        }
        if( fileProcess.getMeasData() != null && !fileProcess.getMeasData().isEmpty()){
            fileProcess.getMeasData().clear();
        }
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

    public boolean getConfirmationAlert(String title, String text) {
        ButtonType yes = new ButtonType("Igen", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION ,null, no, yes);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        alert.initOwner(fxHomeWindow.homeStage);
        alert.setTitle(title);
        alert.setHeaderText(text);
        Optional<ButtonType> option = alert.showAndWait();
        return option.orElse(no) == yes;
    }

    public void openMeasuredData(){
        init();
        fileProcess.getMeasureFileData();
        if( fileProcess.getMeasData() == null || fileProcess.getMeasData().isEmpty() ){
            return;
        }
        measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getMeasData());
        if( measuredPillarData.getMeasPillarPoints() == null || measuredPillarData.getMeasPillarPoints().isEmpty()) {
            getInfoAlert("Nem beolvasható adat",
                    "Nem található beolvasható mérési eredmény a fájlban.");
        }
        this.measuredPointListDisplayer =
                new MeasPointListDisplayer(this);
    }

    public void createNewProject(){
        measuredPointListDisplayer.stage.hide();
        measuredPointListDisplayer.parseDisplayerData();
        measuredPillarData.calcPillarLegsPoint();
        measuredPillarData.calcPillarTopPoints();
        if( measuredPillarData.getPillarBasePoints().size() <  2 ||
                measuredPillarData.getPillarBasePoints().size() > 4){
            getInfoAlert("Nem megfelelő bemeneti adatok",
                    "Az oszlop alapja nem ábrázolható.");
            return;
        }
        this.inputPillarDataWindow = new InputPillarDataWindow(this);
    }

    public void addMoreMeasuredPillarData(){
        measuredPointListDisplayer.stage.hide();
        measuredPointListDisplayer.parseDisplayerData();
        fileProcess.getMeasureFileData();
        measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getMeasData());
        if( measuredPillarData.getMeasPillarPoints() == null || measuredPillarData.getMeasPillarPoints().isEmpty() ){
            getInfoAlert("Nem beolvasható adat",
                    "Nem található beolvasható mérési eredmény a fájlban.");
            measuredPointListDisplayer.stage.show();
            return;
        }
        measuredPointListDisplayer.stage.setTitle(FileProcess.MEAS_FILE_NAME);
        measuredPointListDisplayer.addMeasData();
        measuredPointListDisplayer.stage.show();
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
        int centerPillarID;
       try {
            centerPillarID =
                   InputDataValidator
                           .isValidInputPositiveIntegerValue(inputPillarDataWindow.centerPillarIDField.getText());
       }
       catch (NumberFormatException e){
           getInfoAlert("Nem megfelelő az oszlop száma",
                   "Az oszlop száma csak pozitív egész érték lehet.");
           return;
       }
        double centerPillarX;
        try {
            centerPillarX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.centerPillarField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az oszlop X koordinátája",
                    "Az oszlop X koordinátája csak nem negatív szám lehet.");
            return;
        }
        double centerPillarY;
        try {
            centerPillarY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az oszlop Y koordinátája",
                    "Az oszlop Y koordinátája csak nem negatív szám lehet.");
            return;
        }

        measuredPillarData.setPillarCenterPoint(new MeasPoint(inputPillarDataWindow.centerPillarIDField.getText(),
                centerPillarX, centerPillarY, 0.0, PointType.CENTER));

        int angle;
        try{
            angle = InputDataValidator.isValidAngleValue(inputPillarDataWindow.rotationAngleField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő e forgatás szög értéke",
                    "A forgatás szög értéke csak 360-nál kisebb egész szám lehet.");
            return;
        }
        measuredPillarData.setAngleRotation(angle);
        int min;
        try{
            min = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationMinField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a forgatás szögperc értéke",
                    "A forgatás szögperc értéke csak 59-nél kisebb egész szám lehet.");
            return;
        }
        measuredPillarData.setMinRotation(min);
        int sec;
        try{
            sec = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationSecField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a forgatás szögmásodperc értéke",
                    "A forgatás szögmásodperc értéke csak 59-nél kisebb egész szám lehet.");
            return;
        }
        measuredPillarData.setSecRotation(sec);
        int directionPillarID;
        try {
            directionPillarID =
                    InputDataValidator
                            .isValidInputPositiveIntegerValue(inputPillarDataWindow.directionPillarIDField.getText());
            if( directionPillarID == centerPillarID ){
                getInfoAlert("Nem megfelelő az előző/következő oszlop száma",
                        "Az előző/következő oszlop száma nem lehet egyenlő az oszlop számával.");
                return;
            }
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop száma",
                    "Az oszlop száma csak pozitív egész érték lehet.");
            return;
        }

        double directionPillarX;
        try {
            directionPillarX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.directionPillarField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop X koordinátája",
                    "Az oszlop X koordinátája csak nem negatív szám lehet.");
            return;
        }

        double directionPillarY;
        try {
            directionPillarY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop Y koordinátája",
                    "Az oszlop Y koordinátája csak nem negatív szám lehet.");
            return;
        }
        measuredPillarData.setBaseLineDirectionPoint(new MeasPoint(inputPillarDataWindow.directionPillarIDField.getText(),
                directionPillarX, directionPillarY, 0.0, PointType.DIRECTION));
        measuredPillarData.addIDsForPillarLegs();
        if( FileProcess.NOT_EXISTED_PROJECT){
            fileProcess.savePillarProjectData();
        }
        else {
          if( getConfirmationAlert("Projekt mentése", "Létező projekt, felülírod?") ){
              fileProcess.savePillarProjectData();
          }
        }
        inputPillarDataWindow.stage.hide();
        this.pillarBaseDisplayer = new PillarBaseDisplayer(this);
    }

    public void openProject(){
       projectFileData = fileProcess.openProject();
       if( projectFileData.isEmpty() ){
           return;
       }
        init();
        measuredPillarData.parseProjectFileData(projectFileData);
        if( inputPillarDataWindow != null ){
            inputPillarDataWindow.stage.hide();
        }
        inputPillarDataWindow = new InputPillarDataWindow(this);

    }

}
