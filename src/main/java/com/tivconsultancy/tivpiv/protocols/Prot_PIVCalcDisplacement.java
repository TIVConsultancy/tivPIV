/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.helpfunctions.InterrGrid;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVCalcDisplacement extends PIVProtocol {

    private String name = "Displacement";


    public Prot_PIVCalcDisplacement() {
        super();
        initSettins();
        buildClusters();
        buildLookUp();
    }

    private void buildLookUp() {
//        ((PIVController) StaticReferences.controller).getDataPIV().setImage(name, img);
    }
    
    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return new NameSpaceProtocolResults1D[0];
    }

    @Override
    public List<String> getIdentForViews() {
        return new ArrayList<>();
    }    

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return null;
    }

    @Override
    public void run(Object... input) throws UnableToRunException {

        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();

        data.Hart1998 = Boolean.valueOf(getSettingsValue("tivPIVHart1998").toString());
        data.Hart1998Divider = Double.valueOf(getSettingsValue("tivPIVHart1998Divider").toString());
        data.sSubPixelType = getSettingsValue("tivPIVSubPixelType").toString();
        data.bMultipass = Boolean.valueOf(getSettingsValue("tivPIVMultipass").toString());
        data.bMultipass_BiLin = Boolean.valueOf(getSettingsValue("tivPIVMultipass_BiLin").toString());
        data.iMultipassCount = Integer.valueOf(getSettingsValue("tivPIVMultipassCount").toString());
        data.bRefine = Boolean.valueOf(getSettingsValue("tivPIVInterrAreaRefine").toString());
        data.iLeap = Integer.valueOf(getSettingsValue("tivPIVInternalLeap").toString());
        data.iBurstLength = Integer.valueOf(getSettingsValue("tivPIVBurstLength").toString());
        data.bValidate = Boolean.valueOf(getSettingsValue("tivPIVValidateVectors").toString());
        data.sValidationType = getSettingsValue("tivPIVValidationType").toString();
        data.dValidationThreshold = Double.valueOf(getSettingsValue("tivPIVValThreshold").toString());
        data.bInterpolation = Boolean.valueOf(getSettingsValue("tivPIVValidateInterpol").toString());

        /*
         Calculate displacement
         */
        data.oGrid.getFFT(data);

        /*
         Improve result
         */
        data.oGrid = posProc(data.oGrid, data);

        buildLookUp();
    }
    
    @Override
    public void setImage(BufferedImage bi){        
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
        this.loSettings.add(new SettingObject("Hart1998", "tivPIVHart1998", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Hart1998Divider", "tivPIVHart1998Divider", 2.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "Gaussian", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Multipass", "tivPIVMultipass", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Multipass BiLinear", "tivPIVMultipass_BiLin", true, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Multipass Count", "tivPIVMultipassCount", 3, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Refinement", "tivPIVInterrAreaRefine", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Leap", "tivPIVInternalLeap", 1, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Burst Length", "tivPIVBurstLength", -1, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Validate", "tivPIVValidateVectors", true, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Type", "tivPIVValidationType", "MedianComp", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Threshold", "tivPIVValThreshold", 5.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("Interpolate New Values", "tivPIVValidateInterpol", true, SettingObject.SettingsType.Boolean));
    }

    @Override
    public void buildClusters() {
        SettingsCluster IMGMultipass = new SettingsCluster("Multipass",
                                                           new String[]{"tivPIVMultipass", "tivPIVMultipassCount"}, this);
        IMGMultipass.setDescription("Multiple runs of the displacements");
        lsClusters.add(IMGMultipass);

        SettingsCluster SubPixel = new SettingsCluster("Sub Pixel Accuracy",
                                                       new String[]{"tivPIVSubPixelType", "tivPIVMultipass_BiLin"}, this);
        SubPixel.setDescription("Improving the accuracy with sub pixel interpolation");
        lsClusters.add(SubPixel);

        SettingsCluster improvements = new SettingsCluster("Improvements",
                                                           new String[]{"tivPIVHart1998", "tivPIVHart1998Divider", "tivPIVInterrAreaRefine"}, this);
        improvements.setDescription("Other strategies to improve the results");
        lsClusters.add(improvements);
        
        SettingsCluster leap = new SettingsCluster("Leap&Burst",
                                                           new String[]{"tivPIVInternalLeap", "tivPIVBurstLength"}, this);
        leap.setDescription("Control the Leaps of the Displacement calculations");
        lsClusters.add(leap);
        
        SettingsCluster validation = new SettingsCluster("Validation",
                                                           new String[]{"tivPIVValidateVectors", "tivPIVValidationType" , "tivPIVValThreshold", "tivPIVValidateInterpol"}, this);
        validation.setDescription("Validation of output vectors");
        lsClusters.add(validation);
        

    }
    
    public List<SettingObject> getHints(){
        List<SettingObject> ls = super.getHints();
        ls.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "None", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "Parabolic", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Sub Pixel Type", "tivPIVSubPixelType", "Centroid", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Type", "tivPIVValidationType", "NormMedian", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Type", "tivPIVValidationType", "MedianComp", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Type", "tivPIVValidationType", "MedianLength", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Type", "tivPIVValidationType", "VecDiff", SettingObject.SettingsType.String));
        
        
        return ls;
    }

    public static InterrGrid posProc(InterrGrid oGrid, DataPIV Data) {
        /*
         put everything that is used to improve the result after the standard FFT        
         */

        if (Data.bValidate) {
            oGrid.validateVectors(Data.iStampSize, Data.dValidationThreshold, Data.sValidationType);
            if (Data.bInterpolation) {
                oGrid.reconstructInvalidVectors(5);
            }
        }

        if (Data.bMultipass || Data.bMultipass_BiLin) {
            for (int i = 0; i < Data.iMultipassCount; i++) {
//                oGrid.shiftAndRecalc();
                if (Data.bMultipass_BiLin) {
                    oGrid.shiftAndRecalcSubPix(Data);
                } else {
                    oGrid.shiftAndRecalc(Data);
                }
                if (Data.bValidate) {
                    oGrid.validateVectors(Data.iStampSize, Data.dValidationThreshold, Data.sValidationType);
                    if (Data.bInterpolation) {
                        oGrid.reconstructInvalidVectors(5);
                    }
                }
            }
        }
        if (Data.bRefine) {
            for (int i = 0; i < oGrid.oaContent.length; i++) {
                for (int j = 0; j < oGrid.oaContent[0].length; j++) {
                    oGrid.oaContent[i][j].refine();
                }
            }
            InterrGrid oRefine = oGrid.getRefinesGrid();
            oRefine.checkMask(Data);

            if (Data.bMultipass || Data.bMultipass_BiLin) {
                for (int i = 0; i < Data.iMultipassCount; i++) {
                    if (Data.bMultipass_BiLin) {
                        oGrid.shiftAndRecalcSubPix(Data);
                    } else {
                        oGrid.shiftAndRecalc(Data);
                    }
                    oRefine.validateVectors(Data.iStampSize, Data.dValidationThreshold, Data.sValidationType);
                    oRefine.reconstructInvalidVectors(5);
                }
            }

            return oRefine;

        }

        return oGrid;

    }

}
