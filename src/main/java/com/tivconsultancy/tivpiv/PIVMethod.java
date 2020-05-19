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
import com.tivconsultancy.opentiv.highlevel.protocols.Prot_ObjectMasking;
import com.tivconsultancy.opentiv.highlevel.protocols.Prot_PreProcessor;
import com.tivconsultancy.opentiv.highlevel.protocols.Prot_ReadIMGFiles;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.opentiv.highlevel.protocols.Result1D;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.tivpiv.protocols.Prot_TIVPreProcessor;
import com.tivconsultancy.tivpiv.protocols.Prot_TIVRead2IMGFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class PIVMethod implements Method {

    protected Result1D results1D;
    private File imageFile1 = null;
    private File imageFile2 = null;

    LookUp<Protocol> methods;

    public PIVMethod() {
        initProtocols();
        startNewTimeStep();
    }

    private void initProtocols() {
        methods = new LookUp<>();
        methods.add(new NameObject<>("read", new Prot_TIVRead2IMGFiles("Read")));
        methods.add(new NameObject<>("preproc", new Prot_TIVPreProcessor()));
        methods.add(new NameObject<>("mask", new Prot_ObjectMasking()));
    }

    private void startNewTimeStep() {
        results1D = new Result1D();
        for (Protocol pro : getProtocols()) {
            for (NameSpaceProtocolResults1D e : pro.get1DResultsNames()) {
                results1D.addResult(e.toString(), Double.NaN);
            }
        }
    }

    @Override
    public List<ImagePath> getInputImages() {
        return new ArrayList<>();
    }

    @Override
    public List<Protocol> getProtocols() {
        return methods.getValues();
    }

    @Override
    public void set1DResult(NameSpaceProtocolResults1D e) {

    }

    @Override
    public Result1D get1DResults() {
        return results1D;
    }

    public void readInFileForView(File f) throws Exception {
        List<String> nameOfFileSep = StringWorker.cutElements2(f.getName(), ".");
        if (nameOfFileSep.isEmpty()) {
            return;
        }

        if (nameOfFileSep.contains("png") || nameOfFileSep.contains("jpg") || nameOfFileSep.contains("jpeg") || nameOfFileSep.contains("bmp")) {
            for (Protocol p : getProtocols()) {
                try {
                    if (p instanceof Prot_TIVRead2IMGFiles) {
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

        startNewTimeStep();

            try {
                getProtocol("read").run(new Object[]{imageFile1, imageFile2});
                getProtocol("preproc").run(getProtocol("read").getResults());
                getProtocol("mask").run(getProtocol("preproc").getResults());
                
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
}
