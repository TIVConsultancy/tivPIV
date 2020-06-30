/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv;

import delete.com.tivconsultancy.opentiv.devgui.main.ImagePath;
import com.tivconsultancy.opentiv.helpfunctions.strings.StringWorker;
import com.tivconsultancy.opentiv.highlevel.methods.Method;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.Prot_SystemSettings;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.protocols.Prot_tivPIV1DPostProc;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVCalcDisplacement;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVDataHandling;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVDisplay;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVInterrAreas;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVObjectMasking;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVPreProcessor;
import com.tivconsultancy.tivpiv.protocols.Prot_PIVRead2IMGFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class PIVMethod implements Method {

    
    protected File imageFile1 = null;
    protected File imageFile2 = null;

    protected LookUp<Protocol> methods;

    public PIVMethod() {
        initProtocols();
    }

    private void initProtocols() {
        methods = new LookUp<>();
        methods.add(new NameObject<>("read", new Prot_PIVRead2IMGFiles()));
        methods.add(new NameObject<>("preproc", new Prot_PIVPreProcessor()));
        methods.add(new NameObject<>("mask", new Prot_PIVObjectMasking()));
        methods.add(new NameObject<>("inter areas", new Prot_PIVInterrAreas()));
        methods.add(new NameObject<>("calculate", new Prot_PIVCalcDisplacement()));
        methods.add(new NameObject<>("display", new Prot_PIVDisplay()));
//        methods.add(new NameObject<>("postproc", new Prot_tivPIV1DPostProc()));
        methods.add(new NameObject<>("data", new Prot_PIVDataHandling()));
        methods.add(new NameObject<>("system", new Prot_SystemSettings()));
    }

    @Override
    public List<ImagePath> getInputImages() {
        return new ArrayList<>();
    }

    @Override
    public List<Protocol> getProtocols() {
        return methods.getValues();
    }

    public void readInFileForView(File f) throws Exception {
        List<String> nameOfFileSep = StringWorker.cutElements2(f.getName(), ".");
        if (nameOfFileSep.isEmpty()) {
            return;
        }

        if (nameOfFileSep.contains("png") || nameOfFileSep.contains("jpg") || nameOfFileSep.contains("jpeg") || nameOfFileSep.contains("bmp")) {
            for (Protocol p : getProtocols()) {
                try {
                    if (p instanceof Prot_PIVRead2IMGFiles) {
                        p.run(f);
                    }
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    @Override
    public void setFiles(File f[]) {
        imageFile1 = f[0];
        imageFile2 = f[1];
    }

    @Override
    public void run() throws Exception {
//        StaticReferences.controller.getViewController(null).update();

        try {
            getProtocol("read").run(new Object[]{imageFile1, imageFile2});
            getProtocol("preproc").run(getProtocol("read").getResults());
            getProtocol("mask").run(getProtocol("preproc").getResults());
            PIVStaticReferences.calcIntensityValues(((PIVController) StaticReferences.controller).getDataPIV());
            getProtocol("inter areas").run();
            getProtocol("calculate").run();
            getProtocol("display").run();
//            getProtocol("postproc").run();
            getProtocol("data").run();

            for (NameSpaceProtocolResults1D e : getProtocol("data").get1DResultsNames()) {
                StaticReferences.controller.get1DResults().setResult(e.toString(), getProtocol("data").getOverTimesResult(e));
            }
            StaticReferences.controller.getPlotAbleOverTimeResults().refreshObjects();

        } catch (Exception ex) {
            throw ex;
        }
//            for (NameSpaceProtocolResults1D e : p.get1DResultsNames()) {
//                results1D.setResult(e.toString(), p.getOverTimesResult(e));
//            }

    }   

    @Override
    public Protocol getProtocol(String ident) {
        return methods.get(ident);
    }

    @Override
    public void runParts(String ident) throws Exception {
        if (ident.equals("preproc")) {
            getProtocol("read").run(new Object[]{imageFile1, imageFile2});
            getProtocol("preproc").run(getProtocol("read").getResults());
        }
    }
    
    public boolean checkBurst(int i){
        int burstLength = Integer.valueOf(methods.get("calculate").getSettingsValue("tivPIVBurstLength").toString());
        if(i < burstLength || burstLength <= 1) return false;        
        return i%burstLength == 0;
    }
    
    public int getLeapLength(){
        int leapLength = Integer.valueOf(methods.get("calculate").getSettingsValue("tivPIVInternalLeap").toString());
        return Math.max(leapLength, 1);
    }
    
    public int getBurstLength(){
        int burst = Integer.valueOf(methods.get("calculate").getSettingsValue("tivPIVBurstLength").toString());
        return burst;
    }
    
    @Override
    public Prot_SystemSettings getSystemSetting(String ident) {
        return (Prot_SystemSettings) methods.get("system");
    }
    
}
