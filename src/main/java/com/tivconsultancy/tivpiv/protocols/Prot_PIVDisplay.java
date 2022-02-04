/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.colorspaces.ColorSpaceCIEELab;
import com.tivconsultancy.opentiv.helpfunctions.colorspaces.Colorbar;
import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixGenerator;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.postproc.vector.PaintVectors;
import com.tivconsultancy.opentiv.velocimetry.helpfunctions.VelocityGrid;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.helpfunctions.InterrGrid;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVDisplay extends PIVProtocol {

    private static final long serialVersionUID = 7647922054505532283L;

    BufferedImage VectorDisplay;

    private String name = "Vectors";

    public Prot_PIVDisplay() {
        super();
        VectorDisplay = new ImageInt(50, 50, 0).getBuffImage();
        buildLookUp();
        initSettins();
        buildClusters();
    }

    private void buildLookUp() {
        ((PIVController) StaticReferences.controller).getDataPIV().setImage(name, VectorDisplay);
    }

    @Override
    public NameSpaceProtocolResults1D[] get1DResultsNames() {
        return new NameSpaceProtocolResults1D[0];
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
    public void setImage(BufferedImage bi) {
        VectorDisplay = bi;
        buildLookUp();
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        if ((boolean) StaticReferences.controller.getCurrentMethod().getSystemSetting(null).getSettingsValue("tivGUI_dataStore")) {
            PIVController controller = (PIVController) StaticReferences.controller;
            DataPIV data = controller.getDataPIV();

            ImageInt oSourceImage = new ImageInt(data.iaReadInFirst);
            int iGreyValueVec = Integer.valueOf(getSettingsValue("tivPIVGreyValueVec").toString());
            boolean bBlankBackground = Boolean.valueOf(getSettingsValue("tivBlankBackground").toString());
            int iBackGroundValue = Integer.valueOf(getSettingsValue("BlanckBackgroundGrayValue").toString());
            double dSize = data.oGrid.getCellSize();
            data.dStretch = (Double) this.getSettingsValue("VecStretch");
            try {
                int[][] iaBackground = oSourceImage.iaPixels;
                if (bBlankBackground) {
                    iaBackground = MatrixGenerator.createMatrix(oSourceImage.iaPixels.length, oSourceImage.iaPixels[0].length, iBackGroundValue);
                }
//                int iOffSet = 0;
//                if ("50Overlap".equals(data.sGridType)) {
//                    dSize = dSize / 2.0;
//                    iOffSet = (int) (dSize / 2.0);
//                }
//                VelocityGrid oOutputGrid = new VelocityGrid(iOffSet, oSourceImage.iaPixels[0].length, oSourceImage.iaPixels.length, iOffSet, (int) (oSourceImage.iaPixels[0].length / dSize), (int) (oSourceImage.iaPixels.length / dSize));
//                if ("PIV_Ziegenhein2018".equals(data.sGridType)) {
//                    VectorDisplay = data.oGrid.paintVecs(iaBackground, getColorbar(), data);
//                } else {
//                    VectorDisplay = data.oGrid.paintVecs(iaBackground, getColorbar(), oOutputGrid, );
//                }
                Colorbar oColBar2 = new Colorbar.StartEndLinearColorBar(0.0, (double) data.PIV_WindowSize, getColorbar(), new ColorSpaceCIEELab(), (Colorbar.StartEndLinearColorBar.ColorOperation<Double>) (Double pParameter) -> pParameter);

                VectorDisplay=PaintVectors.paintOnImage(data.oGrid.getVectors(), oColBar2, iaBackground, null, data.dStretch);
                String sFileName = controller.getCurrentFileSelected().getName().substring(0, controller.getCurrentFileSelected().getName().indexOf("."));
                File oPath = new File(controller.getCurrentFileSelected().getParent() + System.getProperty("file.separator") + "ResultImages");
                if (!oPath.exists()) {
                    oPath.mkdir();
                }
                try {
                    ImageIO.write(VectorDisplay, "png", new File(oPath.getPath() + System.getProperty("file.separator") + sFileName + "PIV.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(Prot_PIVDisplay.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception ex) {
                throw new UnableToRunException("Unable to run PIVDisplay process", ex);
            }

            buildLookUp();
        }
    }

    public List<Color> getColorbar() {
        String colbar = getSettingsValue("tivPICColorBar").toString();
        if (colbar.equals("Brown")) {
            return Colorbar.StartEndLinearColorBar.getBrown();
        }
        if (colbar.equals("ColdCutRainbow")) {
            return Colorbar.StartEndLinearColorBar.getColdCutRainbow();
        }
        if (colbar.equals("ColdRainbow")) {
            return Colorbar.StartEndLinearColorBar.getColdRainbow();
        }
        if (colbar.equals("ColdToWarm")) {
            return Colorbar.StartEndLinearColorBar.getColdToWarm();
        }
        if (colbar.equals("ColdToWarmRainbow")) {
            return Colorbar.StartEndLinearColorBar.getColdToWarmRainbow();
        }
        if (colbar.equals("ColdToWarmRainbow2")) {
            return Colorbar.StartEndLinearColorBar.getColdToWarmRainbow2();
        }
        if (colbar.equals("Grey")) {
            return Colorbar.StartEndLinearColorBar.getGrey();
        }
        if (colbar.equals("Jet")) {
            return Colorbar.StartEndLinearColorBar.getJet();
        }
        if (colbar.equals("LightBlue")) {
            return Colorbar.StartEndLinearColorBar.getLightBlue();
        }
        if (colbar.equals("LightBrown")) {
            return Colorbar.StartEndLinearColorBar.getLightBrown();
        }
        if (colbar.equals("Pink")) {
            return Colorbar.StartEndLinearColorBar.getPink();
        }
        if (colbar.equals("WarmToColdRainbow")) {
            return Colorbar.StartEndLinearColorBar.getWarmToColdRainbow();
        }
        if (colbar.equals("darkGreen")) {
            return Colorbar.StartEndLinearColorBar.getdarkGreen();
        }
        if (colbar.equals("veryLightBrown")) {
            return Colorbar.StartEndLinearColorBar.getveryLightBrown();
        }
        int iGreyValueVec = Integer.valueOf(getSettingsValue("tivPIVGreyValueVec").toString());
        return Colorbar.StartEndLinearColorBar.getCustom(iGreyValueVec, iGreyValueVec, iGreyValueVec, iGreyValueVec, iGreyValueVec, iGreyValueVec);

    }

    public List<SettingObject> getHints() {
        List<SettingObject> ls = super.getHints();
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "None", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "Jet", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "ColdCutRainbow", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "ColdRainbow", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "ColdToWarm", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "ColdToWarmRainbow", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "Grey", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "LightBlue", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "Brown", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "LightBrown", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "veryLightBrown", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "Pink", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "WarmToColdRainbow", SettingObject.SettingsType.String));
        ls.add(new SettingObject("Colorbar", "tivPICColorBar", "darkGreen", SettingObject.SettingsType.String));
        return ls;
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
        this.loSettings.add(new SettingObject("Vector Gray Value", "tivPIVGreyValueVec", 255, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Blank Background", "tivBlankBackground", true, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Background Gray Value", "BlanckBackgroundGrayValue", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Colorbar", "tivPICColorBar", "ColdToWarmRainbow2", SettingObject.SettingsType.String));
        this.loSettings.add(new SettingObject("Vector Stretch Factor", "VecStretch", 5.0, SettingObject.SettingsType.Double));
//        this.loSettings.add(new SettingObject("Background Gray Value", "MaxVec", 0, SettingObject.SettingsType.Integer));
    }

    @Override
    public void buildClusters() {
        SettingsCluster IMGFilter = new SettingsCluster("Display",
                new String[]{"tivPIVGreyValueVec", "tivBlankBackground", "BlanckBackgroundGrayValue", "tivPICColorBar", "VecStretch"}, this);
        IMGFilter.setDescription("Masks objects in pictures based on edge detecting");
        lsClusters.add(IMGFilter);

    }

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
}
