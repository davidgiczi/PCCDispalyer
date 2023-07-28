package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;

public class MeasuredPillarDataController {

    public final FileProcess fileProcess;
    public final MeasuredPillarData measuredPillarData;
    public MeasPointListDisplayer measuredPointListDisplayer;
    public PillarDisplayer pillarDisplayer;
    public InputPillarDataWindow inputPillarDataWindow;

    public MeasuredPillarDataController(){
        this.fileProcess = new FileProcess();
        this.measuredPillarData = new MeasuredPillarData();
    }

    public void init(){
      measuredPillarData.getMeasPillarPoints().clear();
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
        this.inputPillarDataWindow = new InputPillarDataWindow();
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

}
