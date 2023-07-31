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
import javafx.scene.paint.Color;
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
        idText.xProperty().bind(pane.widthProperty().divide(22).multiply(5));
        idText.setY(10 * MILLIMETER);
        idText.setFont(boldFont);
        idText.setFill(Color.MAGENTA);
        Text designedXText = new Text("EOV Y (tervezett)");
        designedXText.setFont(boldFont);
        designedXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        designedXText.setY(5 * MILLIMETER);
        Text designedX = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()).replace(",", "."));
        designedX.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        designedX.setY(10 * MILLIMETER);
        designedX.setFont(normalFont);
        Text designedYText = new Text("EOV X (tervezett)");
        designedYText.setFont(boldFont);
        designedYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        designedYText.setY(5 * MILLIMETER);
        Text designedY = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()).replace(",", "."));
        designedY.setFont(normalFont);
        designedY.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        designedY.setY(10 * MILLIMETER);
        Text measXText = new Text("EOV X (mért)");
        measXText.setFont(boldFont);
        measXText.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
        measXText.setY(5 * MILLIMETER);
        Text measX = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getX_coord()).replace(",", "."));
       measX.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
       measX.setY(10 * MILLIMETER);
       measX.setFont(normalFont);
        Text measYText = new Text("EOV Y (mért)");
        measYText.setFont(boldFont);
        measYText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        measYText.setY(5 * MILLIMETER);
       Text measY = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getY_coord()).replace(",", "."));
        measY.xProperty().bind(pane.widthProperty().divide(22).multiply(12));
        measY.setY(10 * MILLIMETER);
        measY.setFont(normalFont);
        Text deltaXText = new Text("ΔY [cm]");
        deltaXText.setFont(boldFont);
        deltaXText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaXText.setY(5 * MILLIMETER);
        Text deltaX = new Text(String.format("%+20.1f", 100 * (measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()
                - measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord())
        ).replace(",", "."));
        deltaX.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaX.setY(10 * MILLIMETER);
        deltaX.setFont(normalFont);
        Text deltaYText = new Text("ΔX [cm]");
        deltaYText.setFont(boldFont);
        deltaYText.xProperty().bind(pane.widthProperty().divide(21).multiply(16));
        deltaYText.setY(5 * MILLIMETER);
        Text deltaY = new Text(String.format("%+20.1f", 100 * (measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()
                - measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord())
        ).replace(",", "."));
        deltaY.xProperty().bind(pane.widthProperty().divide(22).multiply(16));
        deltaY.setY(10 * MILLIMETER);
        deltaY.setFont(normalFont);
        pane.getChildren().addAll(idText, designedXText, designedX,
                designedYText, designedY, measXText, measX, measYText, measY,
                deltaXText, deltaX, deltaYText, deltaY);
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
