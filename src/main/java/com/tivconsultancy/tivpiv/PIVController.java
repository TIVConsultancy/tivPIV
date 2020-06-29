/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv;

import com.tivconsultancy.opentiv.datamodels.Result1D;
import com.tivconsultancy.opentiv.helpfunctions.io.Reader;
import com.tivconsultancy.opentiv.helpfunctions.io.Writer;
import com.tivconsultancy.opentiv.highlevel.methods.Method;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.opentiv.datamodels.Results1DPlotAble;
import com.tivconsultancy.opentiv.datamodels.SQL.PostgreSQL;
import com.tivconsultancy.opentiv.datamodels.overtime.Database;
import com.tivconsultancy.opentiv.datamodels.overtime.DatabaseArchive;
import com.tivconsultancy.opentiv.datamodels.overtime.DatabaseDisk;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.controller.BasicController;
import com.tivconsultancy.opentiv.datamodels.overtime.DatabaseRAM;
import static com.tivconsultancy.opentiv.highlevel.protocols.Prot_SystemSettings.systemSettings.HEAP;
import static com.tivconsultancy.opentiv.highlevel.protocols.Prot_SystemSettings.systemSettings.RAM;
import com.tivconsultancy.tivGUI.Dialogs.Data.processes.DialogProcessing;
import com.tivconsultancy.tivGUI.controller.ControllerUI;
import com.tivconsultancy.tivGUI.controller.ControllerWithImageInteraction;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerImageTools;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerLog;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerPlots;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerViews;
import com.tivconsultancy.tivpiv.data.DataPIV;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.ujmp.core.collections.list.ArrayIndexList;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class PIVController extends BasicController implements ControllerWithImageInteraction {

    protected File mainFolder;
    protected List<File> ReadInFile;
    protected DataPIV database1Step;
    protected DatabaseRAM dataForPlot;
    Map<String, Dialog> openDialogBoxes = new HashMap<>();
    Thread runningThread = null;

    public PIVController() {
        initDatabase();
        ReadInFile = new ArrayList<>();
    }

    protected void initSubControllers() {
        subViews = new StartUpSubControllerViews(this);
        subPlots = new StartUpSubControllerPlots();
        subMenu = new tivPIVSubControllerMenu();
        subLog = new StartUpSubControllerLog();
        subSQL = new tivPIVSubControllerSQL();
        subImageTools = new StartUpSubControllerImageTools();
    }

    @Override
    public void startNewSession(File inputFolder) {
        if (getCurrentMethod() == null) {
            startNewMethod(new PIVMethod());
        }
        if (inputFolder != null && inputFolder.exists()) {
            mainFolder = inputFolder;
            ReadInFile = new ArrayList<>();
            for (File f : mainFolder.listFiles()) {
                if (f.isDirectory()) {
                    continue;
                }
                ReadInFile.add(f);
            }
        }
        mainFrame.startNewSession();
    }

    @Override
    public Database getPlotAbleOverTimeResults() {
        return dataForPlot;
    }

    @Override
    public List<File> getInputFiles(String name) {
        return ReadInFile;
    }

    @Override
    public void setSelectedFile(File f) {
        this.selectedFile = f;
        File nextFile = f;
        int iLeap = ((PIVMethod) getCurrentMethod()).getLeapLength();
        if (getNextPic(iLeap) != null) {
            nextFile = getNextPic();
        }
        setCurrentDatabase();
        this.getCurrentMethod().setFiles(new File[]{f, nextFile});
        try {
            getCurrentMethod().readInFileForView(f);
        } catch (Exception ex) {
            PIVStaticReferences.getlog().log(Level.SEVERE, null, ex);
        }
        subViews.update();
    }

    protected void setCurrentDatabase() {
        DataPIV currentData = (DataPIV) data.getRes(getSelecedName());
        if (currentData == null) {
            currentData = new DataPIV(getSelecedIndex());
        }
        database1Step = currentData;
    }

    private File getNextPic() {
        int index = getSelecedIndex();
        if (index >= 0 && (ReadInFile.size() - 1) > index) {
            return ReadInFile.get(index + 1);
        }
        return null;
    }

    private File getNextPic(int iLeap) {
        int index = getSelecedIndex();
        if (index >= 0 && (ReadInFile.size() - iLeap) > index) {
            return ReadInFile.get(index + iLeap);
        }
        return null;
    }

    public int getSelecedIndex() {
        try {
            for (File f : ReadInFile) {
                if (f.getName().equals(selectedFile.getName())) {
                    return ReadInFile.indexOf(f);
                }
            }
        } catch (Exception e) {
            StaticReferences.getlog().log(Level.FINE, "Cannot assing index to file", e);
        }
        return -1;
    }

    private String getSelecedName() {
        return selectedFile.getName();
    }

    @Override
    public void startNewMethod(Method newMethod) {
        currentMethod = newMethod;
        ReadInFile = new ArrayList<>();
        initSubControllers();
        createHints(currentMethod);
    }

    @Override
    public void importSettings(File loadFile) {
        try {
            Reader oRead = new Reader(loadFile);
            oRead.setSeperator(";");
            List<String[]> ls = oRead.readFileStringa2();
            for (Protocol p : currentMethod.getProtocols()) {
                p.setFromFile(ls);
            }
        } catch (IOException ioe) {
            StaticReferences.getlog().log(Level.SEVERE, "Cannot load settings file", ioe);
        }
        mainFrame.startNewSettings();
    }

    @Override
    public void exportSettings(File saveFile) {
        List<String> allSettings = new ArrayList<>();
        for (Protocol p : currentMethod.getProtocols()) {
            allSettings.addAll(p.getForFile());
        }

        try {
            Writer oWrite = new Writer(saveFile);
            oWrite.write(allSettings);
        } catch (Exception ioe) {
            StaticReferences.getlog().log(Level.SEVERE, "Cannot save settings", ioe);
        }
    }

    /**
     * Initialization of our two databases, one for our 1 Dimensional results
     * (used to plot graphs) The other one used for internal usage in the PIV
     * algorithms, only valid for one time step
     *
     */
    private void initDatabase() {
//        if(String.valueOf(getCurrentMethod().getSystemSetting(null).getSettingsValue("tivGUI_dataStore")).equals(HEAP.toString())){
            data = new DatabaseRAM<>();
//        } else if(String.valueOf(getCurrentMethod().getSystemSetting(null).getSettingsValue("tivGUI_dataStore")).equals(RAM.toString())){
//            data = new DatabaseArchive();
//        } else{
//            data = new DatabaseDisk();
//        }
        
        database1Step = new DataPIV(getSelecedIndex());
        dataForPlot = new DatabaseRAM();
        PostgreSQL sql = new PostgreSQL();
        sql.connect();
    }

    /**
     * Gets the current database for internal use for the current time step
     *
     * @return
     */
    public DataPIV getDataPIV() {
        return database1Step;
    }

    @Override
    public void loadSession(File f) {
        startNewSession(f);
        File settingsFile = null;
        for (File af : f.listFiles()) {
            if (af.getName().contains(".tiv")) {
                settingsFile = af;
                break;
            }
        }
        if (settingsFile == null) {
            StaticReferences.getlog().log(Level.SEVERE, "Cannot load Settings file: Not existing .tiv file", new Throwable());
            return;
        }
        try {
            importSettings(settingsFile);
        } catch (Exception ex) {
            StaticReferences.getlog().log(Level.SEVERE, "Cannot load Settings file: " + new File(f, "Settings.tiv").toString() + ex.getMessage(), ex);
        }

    }

    @Override
    public void runCurrentStep() {
        blockUIForProceess();
        Dialog dialogProgress = new DialogProcessing();
        StaticReferences.controller.setDialog(ControllerUI.DialogNames_Default.PROCESS, dialogProgress);
        dialogProgress.show();
//        int currentStep = getSelecedIndex();
        Thread running = new Thread() {
            @Override
            public void run() {
                try {
                    startNewIndexStep();
                    getCurrentMethod().run();
                    data.setRes(getSelecedName(), database1Step);
                    subViews.update();
                } catch (Exception ex) {
                    StaticReferences.getlog().log(Level.SEVERE, "Unable to run : " + ex.getMessage(), ex);
                }
                releaseUIAfterProceess();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        StaticReferences.controller.getDialog(ControllerUI.DialogNames_Default.PROCESS).close();
                    }
                });
            }
        };
        setRunningThread(running);
        try {
            running.start();
        } catch (Exception e) {
            StaticReferences.getlog().log(Level.SEVERE, "Thread stopped : " + e.getMessage(), e);
            releaseUIAfterProceess();
        }

    }

    @Override
    public void run() {
        blockUIForProceess();
        Dialog dialogProgress = new DialogProcessing();
        StaticReferences.controller.setDialog(ControllerUI.DialogNames_Default.PROCESS, dialogProgress);
        dialogProgress.show();
        Thread running = new Thread() {
            @Override
            public void run() {
                timeline:
                for (int i : getBurstStarts()) {
                    try {
                        StaticReferences.getlog().log(Level.SEVERE, "Starting for: " + ReadInFile.get(i));
                        setSelectedFile(ReadInFile.get(i));

//                        if (((PIVMethod) getCurrentMethod()).checkBurst(i + 1)) {
//                            System.out.println(i);
//                            continue timeline;
//                        }
                        startNewIndexStep();
                        try {
                            getCurrentMethod().run();
                            storeTempData();
                            subViews.update();
                        } catch (Exception ex) {
                            StaticReferences.getlog().log(Level.SEVERE, "Unable to finish step" + i + " : " + ex.getMessage(), ex);
//                            releaseUIAfterProceess();
//                            StaticReferences.controller.getDialog(ControllerUI.DialogNames_Default.PROCESS).close();
                        }
                    } catch (Exception e) {
                        StaticReferences.getlog().log(Level.SEVERE, "Unable to select file and start new timestep : " + e.getMessage(), e);
//                        releaseUIAfterProceess();
//                        StaticReferences.controller.getDialog(ControllerUI.DialogNames_Default.PROCESS).close();
                    }
                }
                releaseUIAfterProceess();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        StaticReferences.controller.getDialog(ControllerUI.DialogNames_Default.PROCESS).close();
                    }
                });

            }
        };
        setRunningThread(running);
        try {
            running.start();
        } catch (Exception e) {
            StaticReferences.getlog().log(Level.SEVERE, "Thread stopped : " + e.getMessage(), e);
            releaseUIAfterProceess();
        }
    }

    public boolean safeTemporaryData() {
        return Boolean.valueOf(String.valueOf(getCurrentMethod().getSystemSetting(null).getSettingsValue("tivGUI_dataStore")));
    }

    public List<Integer> getBurstStarts() {
        int iLeap = ((PIVMethod) getCurrentMethod()).getLeapLength();
        int iBurst = ((PIVMethod) getCurrentMethod()).getBurstLength();
        List<Integer> startingPoints = new ArrayIndexList<>();
        if (iBurst < 2) {
            for (int i = 0; i < ReadInFile.size() - iLeap; i++) {
                startingPoints.add(i);
            }
        } else {
            for (int i = 0; i < ReadInFile.size() - iBurst; i = i + iBurst) {
                startingPoints.add(i);
            }
        }
        return startingPoints;
    }

    public Database getDataBase() {
        return data;
    }

//    protected void createHints(Method newMethod) {
//        hints = new Settings() {
//            @Override
//            public String getType() {
//                return "hints";
//            }
//
//            @Override
//            public void buildClusters() {
//            }
//        };
//        for (Protocol p : newMethod.getProtocols()) {
//            for (SettingsCluster c : p.lsClusters) {
//                for (SettingObject o : c.getSettings()) {
//                    if (p instanceof Prot_PIVInterrAreas && o.getName().contains("PIV_GridType")) {
//                        hints.addSettingsObject(new SettingObject("Grid Type", "PIV_GridType", "Standard", SettingObject.SettingsType.String));
//                        hints.addSettingsObject(new SettingObject("Grid Type", "PIV_GridType", "50Overlap", SettingObject.SettingsType.String));
//                        hints.addSettingsObject(new SettingObject("Grid Type", "PIV_GridType", "PIV_Ziegenhein2018", SettingObject.SettingsType.String));
//                    } else {
//                        if (o.ident.equals(SettingObject.SettingsType.Boolean)) {
//                            hints.addSettingsObject(o);
//                            hints.addSettingsObject(new SettingObject(o.getName(), !((Boolean) o.sValue), SettingObject.SettingsType.Boolean));
//                        } else {
//                            hints.addSettingsObject(o);
//                        }
//                    }
//
//                }
//            }
//        }
//    }
    @Override
    public Results1DPlotAble get1DResults() {
        Results1DPlotAble r = (Results1DPlotAble) dataForPlot.getRes(getSelecedName());
        if (r == null) {
            r = new Result1D(getSelecedIndex());
            dataForPlot.setRes(getSelecedName(), r);
        }
        return r;
//        return (Results1DPlotAble) dataForPlot.getRes(getSelecedIndex());
//        return database1Step.results1D;
    }

    @Override
    public void startNewIndexStep() {
        database1Step = new DataPIV(getSelecedIndex());
        for (Protocol pro : getCurrentMethod().getProtocols()) {
            for (NameSpaceProtocolResults1D e : pro.get1DResultsNames()) {
                get1DResults().addResult(e.toString(), Double.NaN);
            }
//            if (MainFrame.loadGif != null) {
//                pro.setImage(SwingFXUtils.fromFXImage(MainFrame.loadGif, null));
//            }

        }
    }

    @Override
    public void clickOnImage(int i, int j, MouseEvent evt, String ident) {
        getsubControllerImageTools(null).clickOnImage(i, j, ident);
    }

    @Override
    public void buttonPressed(KeyEvent evt, String ident) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dialog getDialog(Enum ident) {
        return openDialogBoxes.get(ident.toString());
    }

    @Override
    public void setDialog(Enum ident, Dialog dialogBox) {
        openDialogBoxes.put(ident.toString(), dialogBox);
    }

    public Thread getRunningThread() {
        return runningThread;
    }

    public void setRunningThread(Thread running) {
        runningThread = running;
    }

    public void blockUIForProceess() {
        mainFrame.deactivateImageTree();
    }

    public void releaseUIAfterProceess() {
        mainFrame.activateImageTree();
    }

    @Override
    public void storeTempData() {
        if (safeTemporaryData()) {
            data.setRes(getSelecedName(), database1Step);
        }
    }

}
