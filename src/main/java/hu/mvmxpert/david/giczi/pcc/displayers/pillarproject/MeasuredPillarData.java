package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.Point;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MeasuredPillarData {

    private final List<Point> pillarBasePoints;
    private final List<Point> pillarTopPoints;

    public List<Point> getPillarBasePoints() {
        return pillarBasePoints;
    }

    public List<Point> getPillarTopPoints() {
        return pillarTopPoints;
    }

    public MeasuredPillarData(List<String> measData) {
        pillarBasePoints = new ArrayList<>();
        pillarTopPoints = new ArrayList<>();
        getMeasuredData(measData);
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
                Point basePoint = new Point(baseData[0],
                        Double.parseDouble(baseData[1]),
                        Double.parseDouble(baseData[2]),
                        Double.parseDouble(baseData[3]));
                if ( !pillarBasePoints.contains(basePoint) ) {
                        pillarBasePoints.add(basePoint);
                }
            }
            if( topData.length > 1 ) {
                Point topPoint = new Point(topData[0],
                        Double.parseDouble(topData[1]),
                        Double.parseDouble(topData[2]),
                        Double.parseDouble(topData[3]));
                if ( !pillarTopPoints.contains(topPoint) ) {
                    pillarTopPoints.add(topPoint);
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
