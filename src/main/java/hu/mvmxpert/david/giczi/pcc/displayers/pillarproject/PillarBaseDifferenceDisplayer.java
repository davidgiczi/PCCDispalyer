package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PillarBaseDifferenceDisplayer {

    private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private ComboBox<String> scaleComboBox;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private static double SCALE;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);

    public PillarBaseDifferenceDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        SCALE = 100;
        Stage stage = new Stage();
        stage.setOnCloseRequest(windowEvent ->
                measuredPillarDataController.pillarBaseDisplayer.setDataToClipboard()
                );
        pane.setStyle("-fx-background-color: white");
        ScrollPane scrollPane = getScrollPane(pane);
        addCenterPillarDifferenceData();
        Scene scene = new Scene(scrollPane);
        stage.setTitle(FileProcess.FOLDER_PATH + "\\" + FileProcess.PROJECT_FILE_NAME + ".plr");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setWidth(950);
        stage.setHeight(600);
        stage.setResizable(false);
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

    private void addCenterPillarDifferenceData(){
        Text pillarHeightText = new Text("magasság [m]");
        pillarHeightText.xProperty().bind(pane.widthProperty().divide(20).multiply(3));
        pillarHeightText.setY(5 * MILLIMETER);
        pillarHeightText.setFont(boldFont);
        Text pillarHeight = new Text(String.format("%.2f",
                (measuredPillarDataController.measuredPillarData.getPillarTopCenterPoint().getZ_coord() -
                measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getZ_coord()))
                .replace(",", "."));
        pillarHeight.xProperty().bind(pane.widthProperty().divide(20).multiply(3));
        pillarHeight.setY(10 * MILLIMETER);
        pillarHeight.setFont(normalFont);

        Text frontDiffXText = new Text("Nyomvonal irányában [cm]");
        frontDiffXText.setFont(boldFont);
        frontDiffXText.xProperty().bind(pane.widthProperty().divide(20).multiply(7));
        frontDiffXText.setY(5 * MILLIMETER);
        Text frontDiffX = new Text(String.format("%.1f", 100 * Math.abs(measuredPillarDataController
                .measuredPillarData.getXDifferenceOnMainLine())).replace(",", "."));
        frontDiffX.xProperty().bind(pane.widthProperty().divide(20).multiply(7));
        frontDiffX.setY(10 * MILLIMETER);
        frontDiffX.setFont(normalFont);

        Text frontDiffYText = new Text("Nyomvonalra merőlegesen [cm]");
        frontDiffYText.setFont(boldFont);
        frontDiffYText.xProperty().bind(pane.widthProperty().divide(20).multiply(13));
        frontDiffYText.setY(5 * MILLIMETER);
        Text frontDiffY = new Text(String.format("%.1f", 100 * Math.abs(measuredPillarDataController
                .measuredPillarData.getYDifferenceOnMainLine())).replace(",", "."));
        frontDiffY.setFont(normalFont);
        frontDiffY.xProperty().bind(pane.widthProperty().divide(20).multiply(13));
        frontDiffY.setY(10 * MILLIMETER);

        pane.getChildren().addAll(pillarHeightText, frontDiffXText, frontDiffYText,
                pillarHeight, frontDiffX, frontDiffY);

        copyText(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID() + "\t" +
                String.format("%.2f",
                                (measuredPillarDataController.measuredPillarData.getPillarTopCenterPoint().getZ_coord() -
                                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getZ_coord()))
                        .replace(",", ".") + "\t" +
                String.format("%.1f", 100 * Math.abs(measuredPillarDataController
                        .measuredPillarData.getXDifferenceOnMainLine())).replace(",", ".") + "\t" +
                String.format("%.1f", 100 * Math.abs(measuredPillarDataController
                        .measuredPillarData.getYDifferenceOnMainLine())).replace(",", "."));
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

}
