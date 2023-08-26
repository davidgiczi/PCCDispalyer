package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

import hu.mvmxpert.david.giczi.pcc.displayers.model.Point;
import hu.mvmxpert.david.giczi.pcc.displayers.service.AzimuthAndDistance;
import hu.mvmxpert.david.giczi.pcc.displayers.service.PolarPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PillarBaseDifferenceDisplayer {

    private final AnchorPane pane = new AnchorPane();
    public Stage stage;
    public MeasuredPillarDataController measuredPillarDataController;
    private ComboBox<String> scaleComboBox;
    private Point topCenterPoint;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private static double SCALE;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);

    public PillarBaseDifferenceDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        SCALE = 10;
        stage = new Stage();
        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
        stage.setOnCloseRequest(windowEvent ->
                measuredPillarDataController.pillarBaseDisplayer.setDataToClipboard()
                );
        pane.setStyle("-fx-background-color: white");
        addContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(FileProcess.FOLDER_PATH + "\\" + FileProcess.PROJECT_FILE_NAME + ".plr");
        stage.getIcons().add(new Image("file:images/MVM.jpg"));
        stage.setWidth(950);
        stage.setHeight(600);
        stage.setResizable(true);
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
        topCenterPoint = new Point("topCenterPoint",
                1000 * (measuredPillarDataController.measuredPillarData
                        .getPillarTopCenterPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData
                        .getPillarBaseCenterPoint().getX_coord()
                        ) * MILLIMETER / SCALE,
                1000 *  (measuredPillarDataController.measuredPillarData
                        .getPillarTopCenterPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData
                        .getPillarBaseCenterPoint().getY_coord()) * MILLIMETER / SCALE);
        addCenterPillarDifferenceDataForForwardDirection();
        addComboBoxForScaleValue();
        addCircleForBaseAndTopCenter();
        addNextPillarDirection();
        addPreviousPillarDirection();
        addForwardDifferences();
        if( measuredPillarDataController.measuredPillarData.radRotation != Math.PI ){
            addCenterPillarDifferenceDataForBackwardDirection();
            addBackwardDifferences();
        }
    }

    private void addCenterPillarDifferenceDataForForwardDirection(){
        Text pillarHeightText = new Text("Magasság [m]");
        pillarHeightText.xProperty().bind(pane.widthProperty().divide(20).multiply(4));
        pillarHeightText.setY(5 * MILLIMETER);
        pillarHeightText.setFont(boldFont);
        Text pillarHeight = new Text(String.format("%.2f",
                (measuredPillarDataController.measuredPillarData.getPillarTopCenterPoint().getZ_coord() -
                measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getZ_coord()))
                .replace(",", "."));
        pillarHeight.xProperty().bind(pane.widthProperty().divide(20).multiply(4));
        pillarHeight.setY(10 * MILLIMETER);
        pillarHeight.setFont(normalFont);

        Text frontDiffXText = new Text("Nyomvonal irányában [cm]");
        frontDiffXText.setFont(boldFont);
        frontDiffXText.xProperty().bind(pane.widthProperty().divide(20).multiply(8));
        frontDiffXText.setY(5 * MILLIMETER);
        Text frontDiffX = new Text(String.format("%+3.1f", 100 * measuredPillarDataController
                .measuredPillarData.getXDifferenceOnMainLine()).replace(",", "."));
        frontDiffX.setFill(Color.LIMEGREEN);
        frontDiffX.xProperty().bind(pane.widthProperty().divide(20).multiply(8));
        frontDiffX.setY(10 * MILLIMETER);
        frontDiffX.setFont(normalFont);

        Text frontDiffYText = new Text("Nyomvonalra merőlegesen [cm]");
        frontDiffYText.setFont(boldFont);
        frontDiffYText.xProperty().bind(pane.widthProperty().divide(20).multiply(14));
        frontDiffYText.setY(5 * MILLIMETER);
        Text frontDiffY = new Text(String.format("%+3.1f", 100 * measuredPillarDataController
                .measuredPillarData.getYDifferenceOnMainLine()).replace(",", "."));
        frontDiffY.setFill(Color.LIMEGREEN);
        frontDiffY.setFont(normalFont);
        frontDiffY.xProperty().bind(pane.widthProperty().divide(20).multiply(14));
        frontDiffY.setY(10 * MILLIMETER);

        pane.getChildren().addAll(pillarHeightText, frontDiffXText, frontDiffYText,
                pillarHeight, frontDiffX, frontDiffY);

        copyText(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID() + "." + "\t" +
                String.format("%.2f",
                                (measuredPillarDataController.measuredPillarData.getPillarTopCenterPoint().getZ_coord() -
                                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getZ_coord()))
                        .replace(",", ".") + "\t" +
                String.format("%.1f", 100 * measuredPillarDataController
                        .measuredPillarData.getXDifferenceOnMainLine()).replace(",", ".") + "\t" +
                String.format("%.1f", 100 * measuredPillarDataController
                        .measuredPillarData.getYDifferenceOnMainLine()).replace(",", "."));
    }

    private void addCenterPillarDifferenceDataForBackwardDirection(){

        Text backDiffX = new Text(String.format("%+3.1f", 100 * measuredPillarDataController
                .measuredPillarData.getXDifferenceOnBackwardLine()).replace(",", "."));
        backDiffX.setFill(Color.BLUE);
        backDiffX.xProperty().bind(pane.widthProperty().divide(20).multiply(8));
        backDiffX.setY(15 * MILLIMETER);
        backDiffX.setFont(normalFont);

        Text backDiffY = new Text(String.format("%+3.1f", 100 * measuredPillarDataController
                .measuredPillarData.getYDifferenceOnBackwardLine()).replace(",", "."));
        backDiffY.setFill(Color.BLUE);
        backDiffY.setFont(normalFont);
        backDiffY.xProperty().bind(pane.widthProperty().divide(20).multiply(14));
        backDiffY.setY(15 * MILLIMETER);

        pane.getChildren().addAll(backDiffX, backDiffY);
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void addComboBoxForScaleValue(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "4",
                        "10"
                );
        HBox hbox = new HBox();
        scaleComboBox = new ComboBox<>(options);
        scaleComboBox.setValue("10");
        scaleComboBox
                .setStyle("-fx-background-color: white;-fx-font: 16px \"Book-Antique\";-fx-font-weight: bold;");
        scaleComboBox.setCursor(Cursor.HAND);
        scaleComboBox.setOnAction(e -> setOnActionEvent());
        Label textM = new Label("M= 1: ");
        textM.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        textM.setPadding(new Insets(5.5, 0 , 0 , 0));
        hbox.getChildren().addAll(textM, scaleComboBox);
        hbox.setLayoutX(5 * MILLIMETER);
        hbox.setLayoutY(2 * MILLIMETER);
        pane.getChildren().add(hbox);
    }
    private void setOnActionEvent(){
        String selectedScale = scaleComboBox.getSelectionModel().getSelectedItem();
        SCALE = Integer.parseInt(selectedScale);
        pane.getChildren().clear();
        addContent();
        scaleComboBox.setValue(selectedScale);
    }

    private void addCircleForBaseAndTopCenter(){
        Circle center = new Circle();
        center.setRadius(5);
        center.setStroke(Color.MAGENTA);
        center.setStrokeWidth(2);
        center.setFill(Color.TRANSPARENT);
        center.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5));
        center.centerYProperty().bind(pane.heightProperty().divide(2));
        Tooltip centerTooltip = new Tooltip(measuredPillarDataController.measuredPillarData
                .getPillarBaseCenterPoint().getPointID()
                +    "\tY=" + String.format("%.3fm",
                measuredPillarDataController.measuredPillarData.
                        getPillarBaseCenterPoint().getX_coord()).replace(",", ".") +
                "\tX=" + String.format("%.3fm",
                measuredPillarDataController.measuredPillarData.
                        getPillarBaseCenterPoint().getY_coord()).replace(",", ".") +
                "\th=" + String.format("%.3fm",
                measuredPillarDataController.measuredPillarData.
                        getPillarBaseCenterPoint().getZ_coord()).replace(",", "."));
        Tooltip.install(center, centerTooltip);
        center.setCursor(Cursor.HAND);
        Circle topCenter = new Circle();
        topCenter.setRadius(5);
        topCenter.setStroke(Color.BLUE);
        topCenter.setStrokeWidth(2);
        topCenter.setFill(Color.TRANSPARENT);
        topCenter.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5).add(topCenterPoint.getX_coord()));
        topCenter.centerYProperty().bind(pane.heightProperty().divide(2).subtract(topCenterPoint.getY_coord()));
        Tooltip topCenterTooltip = new Tooltip(measuredPillarDataController.measuredPillarData
                .getPillarTopCenterPoint().getPointID()
                +    "\tY=" + String.format("%.3fm",
                measuredPillarDataController.measuredPillarData.
                        getPillarTopCenterPoint().getX_coord()).replace(",", ".") +
                "\tX=" + String.format("%.3fm",
                measuredPillarDataController.measuredPillarData.
                        getPillarTopCenterPoint().getY_coord()).replace(",", ".") +
                "\th=" + String.format("%.3fm",
                measuredPillarDataController.measuredPillarData.
                        getPillarTopCenterPoint().getZ_coord()).replace(",", "."));
        Tooltip.install(topCenter, topCenterTooltip);
        topCenter.setCursor(Cursor.HAND);
        pane.getChildren().addAll(center, topCenter);
    }

    private void addNextPillarDirection(){
        Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPillarPoint = new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
        AzimuthAndDistance mainLineDirection =
                new AzimuthAndDistance(pillarCenterPoint, directionPillarPoint);
        PolarPoint startPoint = new PolarPoint(pillarCenterPoint,
                3 * MILLIMETER, mainLineDirection.calcAzimuth(),
                "forwardDirection");

        PolarPoint endPoint = new PolarPoint(startPoint.calcPolarPoint(),
                (1000 * Math.abs(measuredPillarDataController
                        .measuredPillarData.getXDifferenceOnMainLine()) + 30) * MILLIMETER / SCALE,
                mainLineDirection.calcAzimuth(),
                "forwardDirection");

        Line forwardDirection = new Line();
        forwardDirection.setStrokeWidth(2);
        forwardDirection.setStroke(Color.MAGENTA);
        forwardDirection.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(startPoint.calcPolarPoint().getX_coord()));
        forwardDirection.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(startPoint.calcPolarPoint().getY_coord()));
        forwardDirection.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        forwardDirection.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        pane.getChildren().add(forwardDirection);
        addArrow(endPoint.calcPolarPoint(), startPoint.calcPolarPoint());
        setText(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID(),
                endPoint.calcPolarPoint(), Color.RED, 16);
    }

    private void addPreviousPillarDirection(){
        Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPoint = new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
        AzimuthAndDistance baseLineData = new AzimuthAndDistance(pillarCenterPoint, directionPoint);

        PolarPoint startPoint = new PolarPoint(pillarCenterPoint, 3 * MILLIMETER,
                baseLineData.calcAzimuth() + measuredPillarDataController.measuredPillarData.radRotation,
                "prevPoint");

        PolarPoint endPoint = new PolarPoint(startPoint.calcPolarPoint(),
                (1000 * Math.abs(measuredPillarDataController
                        .measuredPillarData.getXDifferenceOnMainLine()) + 30) * MILLIMETER / SCALE,
                baseLineData.calcAzimuth() + measuredPillarDataController.measuredPillarData.radRotation,
                "backwardDirection");

        Line backwardDirection = new Line();
        backwardDirection.setStrokeWidth(2);
        backwardDirection.setStroke(Color.MAGENTA);
        backwardDirection.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(startPoint.calcPolarPoint().getX_coord()));
        backwardDirection.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(startPoint.calcPolarPoint().getY_coord()));
        backwardDirection.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        backwardDirection.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        pane.getChildren().add(backwardDirection);
        addArrow(endPoint.calcPolarPoint(), startPoint.calcPolarPoint());
        int centerID = Integer
                .parseInt(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID());
        int directionID = Integer
                .parseInt(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID());
        setText(centerID < directionID ? String.valueOf((centerID - 1)) :
                        String.valueOf((centerID + 1)),
                endPoint.calcPolarPoint(), Color.MAGENTA, 16);
    }

    private void addArrow(Point startPoint, Point endPoint){
        AzimuthAndDistance baseLineData = new AzimuthAndDistance(startPoint, endPoint);
        PolarPoint slavePoint1 = new PolarPoint(startPoint, 5 * MILLIMETER,
                baseLineData.calcAzimuth() + Math.PI / 6, "arrow1");
        PolarPoint slavePoint2 = new PolarPoint(startPoint, 5 * MILLIMETER,
                baseLineData.calcAzimuth() - Math.PI / 6, "arrow2");
        Line arrow1 = new Line();
        arrow1.setStroke(Color.MAGENTA);
        arrow1.setStrokeWidth(2);
        arrow1.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint.getX_coord()));
        arrow1.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint.getY_coord()));
        arrow1.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(slavePoint1.calcPolarPoint().getX_coord()));
        arrow1.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(slavePoint1.calcPolarPoint().getY_coord()));
        Line arrow2 = new Line();
        arrow2.setStroke(Color.MAGENTA);
        arrow2.setStrokeWidth(2);
        arrow2.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint.getX_coord()));
        arrow2.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint.getY_coord()));
        arrow2.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(slavePoint2.calcPolarPoint().getX_coord()));
        arrow2.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(slavePoint2.calcPolarPoint().getY_coord()));
        pane.getChildren().addAll(arrow1, arrow2);
    }

    private void setText(String textData, Point startPoint, Color color, int size){
        Text text = new Text(textData);
        text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, size));
        text.setFill(color);
        text.xProperty()
                .bind(pane.widthProperty()
                        .divide(10).multiply(5)
                        .add(startPoint.getX_coord())
                        .add(2 * MILLIMETER));
        text.yProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(startPoint.getY_coord())
                        .subtract(2 * MILLIMETER));
        pane.getChildren().add(text);
    }

    private void addForwardDifferences(){
        Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPillarPoint = new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord()  -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
        AzimuthAndDistance mainLineDirection =
                new AzimuthAndDistance(pillarCenterPoint, directionPillarPoint);
        PolarPoint endPoint = new PolarPoint(pillarCenterPoint,
             1000 * measuredPillarDataController.measuredPillarData.getXDifferenceOnMainLine()
                     * MILLIMETER / SCALE,
                mainLineDirection.calcAzimuth(),
                "forwardXDifference");

        Line forwardXDifference = new Line();
        forwardXDifference.setStrokeWidth(2);
        forwardXDifference.setStroke(Color.LIMEGREEN);
        forwardXDifference.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(pillarCenterPoint.getX_coord()));
        forwardXDifference.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(pillarCenterPoint.getY_coord()));
        forwardXDifference.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        forwardXDifference.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        Tooltip forwardXDistanceTooltip = new Tooltip(String.format("%+3.1fcm", 100 * measuredPillarDataController
                .measuredPillarData.getXDifferenceOnMainLine()).replace(",", ".") );
        Tooltip.install(forwardXDifference, forwardXDistanceTooltip);
        forwardXDifference.setCursor(Cursor.CLOSED_HAND);
        Line forwardYDifference = new Line();
        forwardYDifference.setStrokeWidth(2);
        forwardYDifference.setStroke(Color.LIMEGREEN);
        forwardYDifference.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        forwardYDifference.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        forwardYDifference.endXProperty()
                        .bind(pane.widthProperty()
                                .divide(10)
                                .multiply(5).add(topCenterPoint.getX_coord()));
        forwardYDifference.endYProperty()
                        .bind(pane.heightProperty()
                                .divide(2)
                                .subtract(topCenterPoint.getY_coord()));
        Tooltip forwardYDistanceTooltip = new Tooltip(String.format("%+3.1fcm", 100 * measuredPillarDataController
                .measuredPillarData.getYDifferenceOnMainLine()).replace(",", ".") );
        Tooltip.install(forwardYDifference, forwardYDistanceTooltip);
        forwardYDifference.setCursor(Cursor.CLOSED_HAND);
        Line forwardDistance = new Line();
        forwardDistance.setStrokeWidth(2);
        forwardDistance.setStroke(Color.LIMEGREEN);
        forwardDistance.getStrokeDashArray().addAll(10d);
        forwardDistance.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(pillarCenterPoint.getX_coord()));
        forwardDistance.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(pillarCenterPoint.getY_coord()));
        forwardDistance.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(topCenterPoint.getX_coord()));
        forwardDistance.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(topCenterPoint.getY_coord()));
        Tooltip forwardDistanceTooltip = new Tooltip(String.format("%3.1fcm",
                100 * Math.sqrt(
                        Math.pow(measuredPillarDataController.measuredPillarData
                                .getPillarTopCenterPoint().getX_coord() -
                                measuredPillarDataController.measuredPillarData
                                        .getPillarBaseCenterPoint().getX_coord(), 2) +
                                Math.pow(measuredPillarDataController.measuredPillarData
                                        .getPillarTopCenterPoint().getY_coord() -
                                        measuredPillarDataController.measuredPillarData
                                                .getPillarBaseCenterPoint().getY_coord(), 2)))
                .replace(",", ".") );
        Tooltip.install(forwardDistance, forwardDistanceTooltip);
        forwardDistance.setCursor(Cursor.CLOSED_HAND);

        pane.getChildren().addAll(forwardXDifference, forwardYDifference, forwardDistance);
    }
    private void addBackwardDifferences(){

        Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPillarPoint = new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord()  -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
        AzimuthAndDistance mainLineDirection =
                new AzimuthAndDistance(pillarCenterPoint, directionPillarPoint);
        PolarPoint endPoint = new PolarPoint(pillarCenterPoint,
                1000 * measuredPillarDataController.measuredPillarData.getXDifferenceOnBackwardLine()
                        * MILLIMETER / SCALE,
                mainLineDirection.calcAzimuth() + measuredPillarDataController.measuredPillarData.radRotation,
                "forwardXDifference");

        Line backwardXDifference = new Line();
        backwardXDifference.setStrokeWidth(2);
        backwardXDifference.setStroke(Color.BLUE);
        backwardXDifference.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(pillarCenterPoint.getX_coord()));
        backwardXDifference.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(pillarCenterPoint.getY_coord()));
        backwardXDifference.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        backwardXDifference.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        Tooltip backwardXDistanceTooltip = new Tooltip(String.format("%+3.1fcm", 100 * measuredPillarDataController
                .measuredPillarData.getXDifferenceOnBackwardLine()).replace(",", ".") );
        Tooltip.install(backwardXDifference, backwardXDistanceTooltip);
        backwardXDifference.setCursor(Cursor.CLOSED_HAND);
        Line backwardYDifference = new Line();
        backwardYDifference.setStrokeWidth(2);
        backwardYDifference.setStroke(Color.BLUE);
        backwardYDifference.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        backwardYDifference.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        backwardYDifference.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(topCenterPoint.getX_coord()));
        backwardYDifference.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(topCenterPoint.getY_coord()));
        Tooltip backwardYDistanceTooltip = new Tooltip(String.format("%+3.1fcm", 100 * measuredPillarDataController
                .measuredPillarData.getYDifferenceOnBackwardLine()).replace(",", ".") );
        Tooltip.install(backwardYDifference, backwardYDistanceTooltip);
        backwardYDifference.setCursor(Cursor.CLOSED_HAND);
        Line backwardDistance = new Line();
        backwardDistance.setStrokeWidth(2);
        backwardDistance.setStroke(Color.BLUE);
        backwardDistance.getStrokeDashArray().addAll(10d);
        backwardDistance.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(pillarCenterPoint.getX_coord()));
        backwardDistance.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(pillarCenterPoint.getY_coord()));
        backwardDistance.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(topCenterPoint.getX_coord()));
        backwardDistance.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(topCenterPoint.getY_coord()));
        Tooltip backwardDistanceTooltip = new Tooltip(String.format("%3.1fcm",
                        100 * Math.sqrt(
                                Math.pow(measuredPillarDataController.measuredPillarData
                                        .getPillarTopCenterPoint().getX_coord() -
                                        measuredPillarDataController.measuredPillarData
                                                .getPillarBaseCenterPoint().getX_coord(), 2) +
                                        Math.pow(measuredPillarDataController.measuredPillarData
                                                .getPillarTopCenterPoint().getY_coord() -
                                                measuredPillarDataController.measuredPillarData
                                                        .getPillarBaseCenterPoint().getY_coord(), 2)))
                .replace(",", ".") );
        Tooltip.install(backwardDistance, backwardDistanceTooltip);
        backwardDistance.setCursor(Cursor.CLOSED_HAND);
        pane.getChildren().addAll(backwardXDifference, backwardYDifference, backwardDistance);

    }
}
