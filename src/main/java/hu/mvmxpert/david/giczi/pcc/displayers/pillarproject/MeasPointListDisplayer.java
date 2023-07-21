package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.MeasPoint;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.List;
import java.util.Optional;

public class MeasPointListDisplayer {

    private final AnchorPane pane;
    private final  VBox vBox;
    private final int[] clickValue;
    private final MeasuredPillarData measuredPillarData;
    private final Font font = Font.font("Arial", FontWeight.BOLD, 12);

    public MeasPointListDisplayer(MeasuredPillarData measuredPillarData){
        this.measuredPillarData = measuredPillarData;
        clickValue = new int[measuredPillarData.getMeasPillarPoints().size()];
        vBox = new VBox();
        Stage stage = new Stage();
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: white");
        addMeasData(measuredPillarData.getMeasPillarPoints());
        pane.getChildren().add(vBox);
        ScrollPane scrollPane = getScrollPane();
        Scene scene = new Scene(scrollPane);
        stage.setOnCloseRequest(windowEvent -> {
            parseDisplayerData();
            if( !getConfirmationAlert("További mérési adatok hozzáadása, vagy új adatok beolvasása",
                    "Kívánsz további mérési eredményeket beolvasni, vagy új listát létrehozni?")){
                measuredPillarData.getMeasPillarPoints().clear();
            }
        });
        stage.setWidth(450);
        stage.setMaxHeight(800);
        stage.setTitle(FileProcess.MEAS_FILE_NAME);
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void parseDisplayerData(){
            for ( int i = 0; i < vBox.getChildren().size(); i++ ) {
                measuredPillarData.getMeasPillarPoints().get(i).setGroupID(clickValue[i]);
                HBox hbox = (HBox) vBox.getChildren().get(i);
                for( int j = 0; j < hbox.getChildren().size(); j++) {
                    if ( hbox.getChildren().get(j) instanceof CheckBox ) {
                        measuredPillarData.getMeasPillarPoints()
                                .get(i).setUsed(((CheckBox) hbox.getChildren().get(j)).isSelected());
                    }
                }
            }
        }

    private void addMeasData(List<MeasPoint> measPointList){
        for (MeasPoint measPont: measPointList) {
            HBox hbox = new HBox(20);
            hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setCursor(Cursor.HAND);
            hbox.setStyle("-fx-border-color: lightgray");
            hbox.setOnMouseClicked(mouseEvent -> {
                int rowIndex = vBox.getChildren().indexOf((Node) mouseEvent.getSource());
                clickValue[rowIndex]++;
                hbox.setBackground(new Background(
                        new BackgroundFill(getColorValue(rowIndex), null, null)));
                if( clickValue[rowIndex] == 5 ){
                    clickValue[rowIndex] = 0;
                }
            });
            Text measID = new Text(measPont.getPointID());
            measID.setFont(font);
            Text x = new Text(String.format("%.3f", measPont.getX_coord()).replace(",", "."));
            x.setFont(font);
            Text y = new Text(String.format("%.3f", measPont.getY_coord()).replace(",", "."));
            y.setFont(font);
            Text z = new Text(String.format("%.3f", measPont.getZ_coord()).replace(",", "."));
            z.setFont(font);
            Text type = new Text(measPont.getPointType().name());
            type.setFont(font);
            CheckBox check = new CheckBox("Használ");
            check.setFont(font);
            check.setSelected(true);
            hbox.getChildren().addAll(measID, x, y, z, type, check);
            vBox.getChildren().add(hbox);
        }
    }

    private Color getColorValue(int rowIndex) {

        switch (clickValue[rowIndex]) {
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.PINK;
            case 3:
                return Color.CORNFLOWERBLUE;
            case 4 :
                return  Color.TOMATO;
        }
        return Color.TRANSPARENT;
    }

    private ScrollPane getScrollPane(){
        ScrollPane scroller = new ScrollPane(pane);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public boolean getConfirmationAlert(String title, String text) {
        ButtonType addData = new ButtonType("További adatok hozzáadása", ButtonBar.ButtonData.OK_DONE);
        ButtonType newList = new ButtonType("Új lista létrehozása", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, addData, newList);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        alert.initOwner(FXHomeWindow.HOME_STAGE);
        alert.setTitle(title);
        alert.setHeaderText(text);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get() == ButtonType.OK;
    }
}
