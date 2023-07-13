package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MeasuredPillarData {

    private final List<MeasPoint> measPillarPoints;
    private List<MeasPoint> pillarBasePoints;
    private List<MeasPoint> pillarTopPoints;

    public List<MeasPoint> getMeasPillarPoints() {
        return measPillarPoints;
    }

    public MeasuredPillarData(List<String> measData) {
        measPillarPoints = new ArrayList<>();
        getMeasuredData(measData);
    }

    public List<MeasPoint> getPillarBasePoints() {
        return pillarBasePoints;
    }

    public List<MeasPoint> getPillarTopPoints() {
        return pillarTopPoints;
    }

    public void calcPillarLegsPoint(){
        pillarBasePoints = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            List<MeasPoint> pillarLegPoints = new ArrayList<>();
        for (MeasPoint measPillarPoint : measPillarPoints) {
                if( PointType.ALAP == measPillarPoint.getPointType() &&
                        i == measPillarPoint.getGroupID() ){
                    pillarLegPoints.add(measPillarPoint);
                }
            }
            double x = 0.0;
            double y = 0.0;
            double z = 0.0;
            MeasPoint pillarLegPoint = new MeasPoint(null, x, y, z, PointType.ALAP);
            pillarBasePoints.add(pillarLegPoint);
        }
    }

    public void calcPillarTopPoints(){

    }

    public MeasPoint getPillarBaseCenterPoint(){
        return null;
    }

    public MeasPoint getPillarTopCenterPoint(){
        return null;
    }

    private void getMeasuredData(List<String> measData){
        measData.forEach(data -> {
            String[] baseData = data.split(",");
            String[] topData = data.split(";");
            if( baseData.length == 1 && topData.length == 1){
                getInfoAlert("Nem megfelelő mérési fájl formátum",
                        "Az elválasztó karakter a mérési fájlban \",\" vagy \";\" lehet.");
                return;
            }
            if( baseData.length > 1) {
                MeasPoint basePoint = new MeasPoint(baseData[0],
                        Double.parseDouble(baseData[1]),
                        Double.parseDouble(baseData[2]),
                        Double.parseDouble(baseData[3]),
                        PointType.ALAP);
                if ( !measPillarPoints.contains(basePoint) ) {
                        measPillarPoints.add(basePoint);
                }
            }
            if( topData.length > 1 ) {
                MeasPoint topPoint = new MeasPoint(topData[0],
                        Double.parseDouble(topData[1]),
                        Double.parseDouble(topData[2]),
                        Double.parseDouble(topData[3]),
                        PointType.CSUCS);
                if ( !measPillarPoints.contains(topPoint) ) {
                    measPillarPoints.add(topPoint);
                }
            }
        });
    }

    public void getInfoAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        alert.initOwner(FXHomeWindow.HOME_STAGE);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
