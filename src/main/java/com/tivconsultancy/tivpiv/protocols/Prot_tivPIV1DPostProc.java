/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.Protocol;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.math.algorithms.Averaging;
import com.tivconsultancy.opentiv.math.interfaces.Value;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.opentiv.physics.vectors.VelocityVec;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_tivPIV1DPostProc extends PIVProtocol {

    private String name = "Post Processor";
    LookUp<Double> results1D = new LookUp<>();
    

    public Prot_tivPIV1DPostProc() {
        super();
        buildLookUp();
        initSettins();
        buildClusters();
    }

    private void buildLookUp() {
    }

    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return NameSpaceProtocol.class.getEnumConstants();
    }

    @Override
    public List<String> getIdentForViews() {
        return new ArrayList<>();
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return results1D.get(ident.toString());        
    }

    @Override
    public void run(Object... input) throws UnableToRunException {

        results1D = new LookUp<>();
        
        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
        
        List<VelocityVec> loVec = data.oGrid.getVectors();
        
        double avgx = Averaging.getMeanAverage(loVec, new Value<Object>() {
            @Override
            public Double getValue(Object pParameter) {
                return ((VelocityVec) pParameter).getVelocityX();
            }
        });
        
        results1D.addDuplFree(new NameObject<>(NameSpaceProtocol.avgx.toString(), avgx));
        
        double avgy = Averaging.getMeanAverage(loVec, new Value<Object>() {
            @Override
            public Double getValue(Object pParameter) {
                return ((VelocityVec) pParameter).getVelocityY();
            }
        });
        
        results1D.addDuplFree(new NameObject<>(NameSpaceProtocol.avgy.toString(), avgy));
        
        
        buildLookUp();
    }
    
    @Override
    public void setImage(BufferedImage bi){
        buildLookUp();
    }

    @Override
    public Object[] getResults() {
        return new Object[]{};
    }

    @Override
    public String getType() {
        return name;
    }

    private void initSettins() {
//        this.loSettings.add(new SettingObject("Hart1998", "tivPIVHart1998", false, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Hart1998Divider", "tivPIVHart1998Divider", 2.0, SettingObject.SettingsType.Double));
//        this.loSettings.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "Gaussian", SettingObject.SettingsType.String));
//        this.loSettings.add(new SettingObject("Multipass", "tivPIVMultipass", false, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Multipass BiLinear", "tivPIVMultipass_BiLin", true, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Multipass Count", "tivPIVMultipassCount", 3, SettingObject.SettingsType.Integer));
//        this.loSettings.add(new SettingObject("Refinement", "tivPIVInterrAreaRefine", false, SettingObject.SettingsType.Boolean));
    }

    @Override
    public void buildClusters() {
//        SettingsCluster IMGMultipass = new SettingsCluster("Multipass",
//                                                           new String[]{"tivPIVMultipass", "tivPIVMultipassCount"}, this);
//        IMGMultipass.setDescription("Multiple runs of the displacements");
//        lsClusters.add(IMGMultipass);
//
//        SettingsCluster SubPixel = new SettingsCluster("Sub Pixel Accuracy",
//                                                       new String[]{"tivPIVSubPixelType", "tivPIVMultipass_BiLin"}, this);
//        SubPixel.setDescription("Improving the accuracy with sub pixel interpolation");
//        lsClusters.add(SubPixel);
//
//        SettingsCluster Improvements = new SettingsCluster("Improvements",
//                                                           new String[]{"tivPIVHart1998", "tivPIVHart1998Divider", "tivPIVInterrAreaRefine"}, this);
//        Improvements.setDescription("Other strategies to improve the results");
//        lsClusters.add(Improvements);

    }
    
//    public List<SettingObject> getHints(){
//        List<SettingObject> ls = super.getHints();
//        ls.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "None", SettingObject.SettingsType.String));
//        ls.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "Parabolic", SettingObject.SettingsType.String));
//        ls.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "Centroid", SettingObject.SettingsType.String));
//        return ls;
//    }

//    public static InterrGrid posProc(InterrGrid oGrid, DataPIV Data) {
//        /*
//         put everything that is used to improve the result after the standard FFT        
//         */
//
//        if (Data.bValidate) {
//            oGrid.validateVectors(Data.iStampSize, Data.dValidationThreshold, Data.sValidationType);
//            if (Data.bInterpolation) {
//                oGrid.reconstructInvalidVectors(5);
//            }
//        }
//
//        if (Data.bMultipass || Data.bMultipass_BiLin) {
//            for (int i = 0; i < Data.iMultipassCount; i++) {
////                oGrid.shiftAndRecalc();
//                if (Data.bMultipass_BiLin) {
//                    oGrid.shiftAndRecalcSubPix(Data);
//                } else {
//                    oGrid.shiftAndRecalc(Data);
//                }
//                if (Data.bValidate) {
//                    oGrid.validateVectors(Data.iStampSize, Data.dValidationThreshold, Data.sValidationType);
//                    if (Data.bInterpolation) {
//                        oGrid.reconstructInvalidVectors(5);
//                    }
//                }
//            }
//        }
//        if (Data.bRefine) {
//            for (int i = 0; i < oGrid.oaContent.length; i++) {
//                for (int j = 0; j < oGrid.oaContent[0].length; j++) {
//                    oGrid.oaContent[i][j].refine();
//                }
//            }
//            InterrGrid oRefine = oGrid.getRefinesGrid();
//            oRefine.checkMask(Data);
//
//            if (Data.bMultipass || Data.bMultipass_BiLin) {
//                for (int i = 0; i < Data.iMultipassCount; i++) {
//                    if (Data.bMultipass_BiLin) {
//                        oGrid.shiftAndRecalcSubPix(Data);
//                    } else {
//                        oGrid.shiftAndRecalc(Data);
//                    }
//                    oRefine.validateVectors(Data.iStampSize, Data.dValidationThreshold, Data.sValidationType);
//                    oRefine.reconstructInvalidVectors(5);
//                }
//            }
//
//            return oRefine;
//
//        }
//
//        return oGrid;
//
//    }
    
    private enum NameSpaceProtocol implements NameSpaceProtocolResults1D{
       avgx, avgy, tkeX, tkey
    }

}
