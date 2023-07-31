package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PillarDisplayer {
    private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);


    public PillarDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        Stage stage = new Stage();
        stage.setOnCloseRequest(windowEvent -> {
            measuredPillarDataController.fxHomeWindow.homeStage.show();
            measuredPillarDataController.init();
        });
        pane.setStyle("-fx-background-color: white");
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if( mouseEvent.getButton() == MouseButton.SECONDARY ){

                }
            }
        });
        addContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(FileProcess.FOLDER_PATH + "\\" + FileProcess.PROJECT_FILE_NAME + ".plr");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setMinWidth(1050);
        stage.setMinHeight(750);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    private ScrollPane getScrollPane(AnchorPane content){
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }

    private void addContent(){
        addNorthSign();
        addCenterPillarData();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("file:images/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.xProperty().bind(pane.widthProperty().divide(20));
        northSign.setY(5 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void addCenterPillarData(){

      Text idText = new Text(measuredPillarDataController
                .measuredPillarData
                .getPillarCenterPoint()
                .getPointID());
        idText.xProperty().bind(pane.widthProperty().divide(21).multiply(5));
        idText.setY(10 * MILLIMETER);
        idText.setFont(boldFont);
        Text designedXText = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()).replace(",", "."));
        designedXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        designedXText.setY(10 * MILLIMETER);
        designedXText.setFont(normalFont);
        Text designedYText = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()).replace(",", "."));
        designedYText.setFont(normalFont);
        designedYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        designedYText.setY(10 * MILLIMETER);
        Text measXtext = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getX_coord()).replace(",", "."));
       measXtext.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
       measXtext.setY(10 * MILLIMETER);
       measXtext.setFont(normalFont);
       Text measYText = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getY_coord()).replace(",", "."));
        measYText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        measYText.setY(10 * MILLIMETER);
        measYText.setFont(normalFont);
        Text deltaXText = new Text(String.format("%+20.3f", 100 * (measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()
                - measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord())
        ).replace(",", "."));
        deltaXText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaXText.setY(10 * MILLIMETER);
        deltaXText.setFont(normalFont);
        Text deltaYText = new Text(String.format("%+20.3f", 100 * (measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()
                - measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord())
        ).replace(",", "."));
        deltaYText.xProperty().bind(pane.widthProperty().divide(21).multiply(16));
        deltaYText.setY(10 * MILLIMETER);
        deltaYText.setFont(normalFont);
        pane.getChildren().addAll(idText, designedXText, designedYText,
                measXtext, measYText, deltaXText, deltaYText);
        copyText( idText.getText() + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint()
                        .getX_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint()
                        .getY_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarBaseCenterPoint()
                        .getX_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarBaseCenterPoint()
                        .getY_coord()).replace(",", ".") + "\t" +
                String.format("%+3.1f", 100 * (measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint().getX_coord()
                        - measuredPillarDataController.measuredPillarData
                        .getPillarBaseCenterPoint().getX_coord())).replace(",", ".") + "\t" +
                String.format("%+3.1f", 100 * (measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint().getY_coord()
                        - measuredPillarDataController.measuredPillarData
                        .getPillarBaseCenterPoint().getY_coord())).replace(",", "."));
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

}
