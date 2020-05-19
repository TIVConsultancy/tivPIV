/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv;

import com.tivconsultancy.opentiv.highlevel.methods.Method;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivGUI.controller.BasicController;
import com.tivconsultancy.tivGUI.startup.Database;
import com.tivconsultancy.tivGUI.startup.StartUpController;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerLog;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerMenu;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerPlots;
import com.tivconsultancy.tivGUI.startup.StartUpSubControllerViews;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class PIVController extends BasicController {

    protected File mainFolder;
    protected List<File> ReadInFile;    
    
    public PIVController(){
        currentMethod = new PIVMethod();
        ReadInFile = new ArrayList<>();
        initDatabase();
        initSubControllers();
        createHints(currentMethod);
    }
    
    private void initSubControllers() {
        subViews = new StartUpSubControllerViews(this);
        subPlots = new StartUpSubControllerPlots();
        subMenu = new StartUpSubControllerMenu();
        subLog = new StartUpSubControllerLog();
    }

    @Override
    public void startNewSession(File inputFolder) {
        startNewMethod(new PIVMethod());
        mainFolder = inputFolder;
        ReadInFile = new ArrayList<>();
        for (File f : mainFolder.listFiles()) {
            if (f.isDirectory()) {
                continue;
            }
            ReadInFile.add(f);
        }
    }

    @Override
    public List<File> getInputFiles(String name) {
        return ReadInFile;
    }
    
    @Override
    public void setSelectedFile(File f) {
        this.selectedFile = f;
        File nextFile = f;
        if(getNextPic() != null){
            nextFile = getNextPic();
        }
        this.getCurrentMethod().setFiles(new File[]{f, nextFile});
        try {            
            getCurrentMethod().readInFileForView(f);            
        } catch (Exception ex) {
            PIVStaticReferences.getlog().log(Level.SEVERE, null, ex);
        }
        subViews.update();
    }
    
    private File getNextPic(){
        int index = getSelecedIndex();
        if( index > 0 && (ReadInFile.size()-1) > index){
            return ReadInFile.get(index+1);
        }
        return null;
    }
    
    private int getSelecedIndex(){
        for(File f : ReadInFile){
            if(f.getName().equals(selectedFile.getName())){
                return ReadInFile.indexOf(f);
            }
        }
        return -1;
    }

    @Override
    public void startNewMethod(Method newMethod) {
        currentMethod = newMethod;
        initDatabase();
    }
    
    private void initDatabase() {
        data = new Database();
    }

    @Override
    public void loadSession(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void runCurrentStep() {
        int currentStep = 0;
        new Thread() {
            @Override
            public void run() {
                try {
                    getCurrentMethod().run();
                    data.set1DRes(currentStep, getCurrentMethod().get1DResults());
                    subViews.update();
                } catch (Exception ex) {
                    StaticReferences.getlog().log(Level.SEVERE, "Unable to run : " + ex.getMessage(), ex);
                }
            }
        }.start();
    }

    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {
                timeline:
                for (int i = 0; i < 10; i++) {
                    try {
                        getCurrentMethod().run();
                        data.set1DRes(i, getCurrentMethod().get1DResults());
                        subViews.update();
                    } catch (Exception ex) {
                        StaticReferences.getlog().log(Level.SEVERE, "Unable to run : " + ex.getMessage(), ex);
                    }
                }
            }
        }.start();
    }

}
