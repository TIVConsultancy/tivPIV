/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.settings.FactorySettingsCluster;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.preprocessor.OpenTIV_PreProc;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVPreProcessor extends PIVProtocol {

    ImageInt preproc;
    ImageInt preproc2;
    private String name = "Image Correction";

    public Prot_PIVPreProcessor(String name) {
        this();
        this.name = name;
    }

    public Prot_PIVPreProcessor() {
        super();
        preproc = new ImageInt(50, 50, 150);
        preproc2 = new ImageInt(50, 50, 150);
        buildLookUp();
        initSettings();
        buildClusters();
    }

    public Prot_PIVPreProcessor(File fNull) {

        preproc = new ImageInt(50, 50, 150);
        preproc2 = new ImageInt(50, 50, 150);
//        initSettings();
    }

    private void buildLookUp() {
        ((PIVController) StaticReferences.controller).getDataPIV().setImage(name, preproc.getBuffImage());
    }

    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return new NameSpaceProtocolResults1D[0];
    }

    @Override
    public void setImage(BufferedImage bi) {
        preproc = new ImageInt(bi);
        preproc2 = new ImageInt(bi);
        buildLookUp();
    }

    @Override
    public List<String> getIdentForViews() {
        return Arrays.asList(new String[]{name});
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return null;
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        if (input != null && input.length >= 2 && input[0] != null && input[1] != null && input[0] instanceof ImageInt && input[1] instanceof ImageInt) {

            try {
                DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
                preproc.setImage(OpenTIV_PreProc.performTransformation(this, (ImageInt) input[0]).getBuffImage());
                preproc2.setImage(OpenTIV_PreProc.performTransformation(this, (ImageInt) input[1]).getBuffImage());
                data.iaReadInFirst = preproc.iaPixels;
                data.iaReadInSecond = preproc2.iaPixels;
                preproc.setImage((OpenTIV_PreProc.performPreProc(this, preproc.clone())).getBuffImage());
                preproc2.setImage((OpenTIV_PreProc.performPreProc(this, preproc2.clone())).getBuffImage());
                data.iaPreProcFirst = preproc.iaPixels;
                data.iaPreProcSecond = preproc2.iaPixels;
            } catch (Exception ex) {
                throw new UnableToRunException("Cannot transform image:", ex);
            }

        } else {
            throw new UnableToRunException("Wrong input", new Exception());
        }
        buildLookUp();

    }
    
    public void runSkript(Object... input) throws UnableToRunException {
        if (input != null && input.length >= 3 && input[0] != null && input[1] != null && input[0] instanceof ImageInt && input[1] instanceof ImageInt
                && input[2] != null && input[2] instanceof PIVController) {

            try {
                DataPIV data = ((PIVController) input[2]).getDataPIV();
                preproc.setImage(OpenTIV_PreProc.performTransformation(this, (ImageInt) input[0]).getBuffImage());
                preproc2.setImage(OpenTIV_PreProc.performTransformation(this, (ImageInt) input[1]).getBuffImage());
                data.iaReadInFirst = preproc.iaPixels;
                data.iaReadInSecond = preproc2.iaPixels;
                preproc.setImage((OpenTIV_PreProc.performPreProc(this, preproc.clone())).getBuffImage());
                preproc2.setImage((OpenTIV_PreProc.performPreProc(this, preproc2.clone())).getBuffImage());
                data.iaPreProcFirst = preproc.iaPixels;
                data.iaPreProcSecond = preproc2.iaPixels;
            } catch (Exception ex) {
                throw new UnableToRunException("Cannot transform image:", ex);
            }

        } else {
            throw new UnableToRunException("Wrong input", new Exception());
        }
//        buildLookUp();

    }

    @Override
    public String getType() {
        return name;
    }

    @Override
    public Object[] getResults() {
        return new Object[]{preproc.clone(), preproc2.clone()};
    }

    public void buildClusters() {

        SettingsCluster CutImage = new SettingsCluster("Cut Image",
                new String[]{"BcutyTop", "cutyTop", "BcutyBottom",
                    "cutyBottom", "BcutxLeft", "cutxLeft", "BcutxRight",
                    "cutxRight"}, this);
        CutImage.setDescription("Cut image");
        lsClusters.add(CutImage);

        lsClusters.add(FactorySettingsCluster.getStandardCluster("Curve Correction",
                new String[]{
                    "CurveCorrection",
                    "GreyOldValues",
                    "GreyNewValues"},
                "Curve correction "
                + "with spline "
                + "interpolation",
                this));

        SettingsCluster NR1 = new SettingsCluster("Noise Reduction",
                new String[]{"NRSimple1", "NRSimple1Threshold", "NRType"}, this);
        NR1.setDescription("Simple noise reduction that are based on a "
                + "threshold. \n NRSimple1: Turn on/off \n NRSimple1Threshold:"
                + " Threshold \n NRType: Type of noise reduction"
                + " (Available: Simple1) ");
        lsClusters.add(NR1);

        SettingsCluster IMGFilter = new SettingsCluster("Image Filter",
                new String[]{"SFGauss", "SF5x5Gauss", "SF3x3Box"}, this);
        IMGFilter.setDescription("Standard image filters of different sizes");
        lsClusters.add(IMGFilter);

        SettingsCluster Brightness = new SettingsCluster("Brightness",
                new String[]{"HGBrightness", "Brightness"}, this);
        Brightness.setDescription("Shifts the brighntess +- to the value");
        lsClusters.add(Brightness);

        SettingsCluster Equalize = new SettingsCluster("Equalize",
                new String[]{"HGEqualize", "Equalize"}, this);
        Equalize.setDescription("Equalizes the histogram to the "
                + "given maximum value");
        lsClusters.add(Equalize);

        SettingsCluster Contrast = new SettingsCluster("Contrast",
                new String[]{"HGContrast", "BlackMin", "WhiteMax"}, this);
        Contrast.setDescription("Increase the contrast by normalizing to the "
                + "possible black and white values");
        lsClusters.add(Contrast);

        SettingsCluster Sharpen = new SettingsCluster("Sharpen",
                new String[]{"Sharpen", "SharpenThresh", "HistMin"}, this);
        Sharpen.setDescription("Increase the contrast of sharp particles "
        );
        lsClusters.add(Sharpen);

//        SettingsCluster HGBlackStretch = new SettingsCluster("Black Stretch",
//                                                             new String[]{"HGBlackStretch", "BlackStretchFactor"}, this);
//        HGBlackStretch.setDescription("Stretches the histogram towards "
//                + "black values by the given factor");
//        lsClusters.add(HGBlackStretch);
//
//        SettingsCluster HGWhiteStretch = new SettingsCluster("White Stretch",
//                                                             new String[]{"HGWhiteStretch", "WhiteStretchFactor"}, this);
//        HGWhiteStretch.setDescription("Stretches the histogram towards "
//                + "black values by the given factor");
//        lsClusters.add(HGWhiteStretch);
    }

    private void initSettings() {
        /*
         ____ Abbrevations
         NR : Noise Reduction
         SF : Smoothing Filter
         HG : Histogram
         */

 /*
         The logic behind this settings are
         ...
         Feature(n) activate false/true
         Feature(n) values
         Feature(n+1) activate false/true
         Feature(n+1) values
         ....
         */
        this.loSettings.add(new SettingObject("Cut Top", "BcutyTop", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value", "cutyTop", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cut Bottom", "BcutyBottom", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value", "cutyBottom", 600, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cut Left", "BcutxLeft", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value", "cutxLeft", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cut Right", "BcutxRight", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value", "cutxRight", 10, SettingObject.SettingsType.Integer));

        this.loSettings.add(new SettingObject("Use Noise Reduction", "NRSimple1", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Threshold", "NRSimple1Threshold", 50, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Algorithm", "NRType", "Simple1", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Standard Gauss", "SFGauss", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Large Gauss", "SF5x5Gauss", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Box Filter", "SF3x3Box", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("SFType", "Gauss", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("HGType", "Brightness", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Brighntess Correction", "HGBrightness", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value", "Brightness", 50, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("HGType", "Equalize", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Equalize", "HGEqualize", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Max White Value", "Equalize", 255, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("HGType", "Contrast", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Contrast Correction", "HGContrast", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Min Black Value", "BlackMin", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Max White Value", "WhiteMax", 255, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("HGType", "BlackStretch", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("HGBlackStretch", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("BlackStretchFactor", 1.0, SettingObject.SettingsType.Double));
        this.loSettings.add(new SettingObject("HGType", "WhiteStretch", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("HGWhiteStretch", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("WhiteStretchFactor", 1.0, SettingObject.SettingsType.Double));

        this.loSettings.add(new SettingObject("Curve Correction", "CurveCorrection", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Old Values", "GreyOldValues", "0, 75, 255", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("New Values", "GreyNewValues", "0, 150, 255", SettingObject.SettingsType.String));

//        this.loSettings.add(new SettingObject("SharpAlgorithm", "NRType", "Simple1", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Sharpen", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("SharpenThresh", 50, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("HistMin", 50, SettingObject.SettingsType.Integer));
    }

}
