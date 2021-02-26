/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Reader;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Writer;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.masking.main.OpenTIV_Masking;
import com.tivconsultancy.opentiv.preprocessor.OpenTIV_PreProc;
import com.tivconsultancy.tivGUI.StaticReferences;
import com.tivconsultancy.tivpiv.PIVController;
import com.tivconsultancy.tivpiv.PIVMethod;
import com.tivconsultancy.tivpiv.data.DataPIV;
import com.tivconsultancy.tivpiv.tivPIVSubControllerSQL;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_PIVObjectMasking extends PIVProtocol {

    ImageInt masking1;
    ImageInt masking2;
    ImageInt intersec1;
    ImageInt intersec2;

    ImageInt totMask;

    private String name = "Masking";

    public Prot_PIVObjectMasking() {
        super();
        masking1 = new ImageInt(50, 50, 0);
        masking2 = new ImageInt(50, 50, 0);
        intersec1 = new ImageInt(50, 50, 0);
        intersec2 = new ImageInt(50, 50, 0);
        totMask = new ImageInt(50, 50, 0);
        buildLookUp();
        initSettins();
        buildClusters();
    }

    private void buildLookUp() {
        ((PIVController) StaticReferences.controller).getDataPIV().setImage(name, totMask.getBuffImage());
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
    public void setImage(BufferedImage bi) {
        totMask = new ImageInt(bi);
        buildLookUp();
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return null;
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        if (input != null && input.length >= 2 && input[0] != null && input[1] != null && input[0] instanceof ImageInt && input[1] instanceof ImageInt) {
//            ImageInt pic = (ImageInt) input[0];
            ImageInt mask1 = (ImageInt) input[0];
            ImageInt mask2 = (ImageInt) input[1];
//            int iHeight = mask1.iaPixels.length;
//            int iWidth = mask1.iaPixels[0].length;
            File oF1 = (File) input[2];
            File oF2 = (File) input[3];
            PIVMethod method = ((PIVMethod) StaticReferences.controller.getCurrentMethod());
            if ((boolean) this.getSettingsValue("ML-BubbleDetect") && method.bReadFromSQL && !(boolean) this.getSettingsValue("use_mask_Path")) {
                try {
                    mask1 = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readPredictionFromSQL(method.experimentSQL, oF1.getName(), mask1.iaPixels.length, mask1.iaPixels[0].length)[0];
                    intersec1 = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readPredictionFromSQL(method.experimentSQL, oF1.getName(), mask1.iaPixels.length, mask1.iaPixels[0].length)[1];
                    mask2 = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readPredictionFromSQL(method.experimentSQL, oF2.getName(), mask1.iaPixels.length, mask1.iaPixels[0].length)[0];
                    intersec2 = ((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readPredictionFromSQL(method.experimentSQL, oF2.getName(), mask1.iaPixels.length, mask1.iaPixels[0].length)[1];
                } catch (SQLException ex) {
                    Logger.getLogger(Prot_PIVObjectMasking.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Prot_PIVObjectMasking.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ((boolean) this.getSettingsValue("ML-BubbleDetect") && (boolean) this.getSettingsValue("use_mask_Path")) {
                String sPath = (String) this.getSettingsValue("mask_Path");
                try {
                    mask1.setImage(IMG_Reader.readImageGrayScale(new File(sPath + "\\Mask" + oF1.getName())));
                    intersec1.setImage(IMG_Reader.readImageGrayScale(new File(sPath + "\\Intersec" + oF1.getName())));
                    mask2.setImage(IMG_Reader.readImageGrayScale(new File(sPath + "\\Mask" + oF2.getName())));
                    intersec2.setImage(IMG_Reader.readImageGrayScale(new File(sPath + "\\Intersec" + oF2.getName())));
                } catch (IOException ex) {
                    Logger.getLogger(Prot_PIVObjectMasking.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                mask1 = OpenTIV_Masking.performMasking((ImageInt) input[0], this);
                mask2 = OpenTIV_Masking.performMasking((ImageInt) input[1], this);
            }

            if (mask1 == null) {
                mask1 = new ImageInt(((ImageInt) input[0]).iaPixels.length, ((ImageInt) input[0]).iaPixels[0].length, 255);
                mask1.baMarker = new boolean[((ImageInt) input[0]).iaPixels.length][((ImageInt) input[0]).iaPixels[0].length];
            }
            if (mask2 == null) {
                mask2 = new ImageInt(((ImageInt) input[1]).iaPixels.length, ((ImageInt) input[1]).iaPixels[0].length, 255);
                mask2.baMarker = new boolean[((ImageInt) input[1]).iaPixels.length][((ImageInt) input[1]).iaPixels[0].length];
            }
            mask1 = OpenTIV_PreProc.performTransformation((Settings) input[4], mask1);
            mask2 = OpenTIV_PreProc.performTransformation((Settings) input[4], mask2);
            intersec1 = OpenTIV_PreProc.performTransformation((Settings) input[4], intersec1);
            intersec2 = OpenTIV_PreProc.performTransformation((Settings) input[4], intersec2);
            masking1.setImage(mask1.getBuffImage());
            masking1.setBoolean(mask1.baMarker);
            masking2.setImage(mask2.getBuffImage());
            masking2.setBoolean(mask2.baMarker);

            totMask.setImage(new ImageInt(((ImageInt) input[0]).iaPixels.length, ((ImageInt) input[0]).iaPixels[0].length, 255).getBuffImage());

            for (int i = 0; i < Math.min(masking1.iaPixels.length, masking2.iaPixels.length); i++) {
                for (int j = 0; j < Math.min(masking1.iaPixels[0].length, masking2.iaPixels[0].length); j++) {
                    totMask.baMarker[i][j] = false;
                    if (masking1.iaPixels[i][j] >= 1 || masking2.iaPixels[i][j] >= 1) {
                        if (masking1.iaPixels[i][j] >= 126) {
                            masking1.setPoint(new MatrixEntry(i, j), 255);
                            totMask.iaPixels[i][j] = 0;
                            totMask.baMarker[i][j] = true;
                        } else {
                            masking1.setPoint(new MatrixEntry(i, j), 0);
                        }
                        if (masking2.iaPixels[i][j] >= 126) {
                            masking2.setPoint(new MatrixEntry(i, j), 255);
                            totMask.iaPixels[i][j] = 127;
                            totMask.baMarker[i][j] = true;
                        } else {
                            masking2.setPoint(new MatrixEntry(i, j), 0);
                        }
                    }
                    if (intersec1.iaPixels[i][j] >= 126) {
                        intersec1.setPoint(new MatrixEntry(i, j), 255);
                    } else {
                        intersec1.setPoint(new MatrixEntry(i, j), 0);
                    }
                    if (intersec2.iaPixels[i][j] >= 126) {
                        intersec2.setPoint(new MatrixEntry(i, j), 255);
                    } else {
                        intersec2.setPoint(new MatrixEntry(i, j), 0);
                    }

                }
            }
        } else {
            throw new UnableToRunException("Wrong input", new Exception());
        }
        buildLookUp();
        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
        data.baMask = totMask.baMarker;
    }

    @Override
    public Object[] getResults() {
        return new Object[]{totMask, masking1, masking2, intersec1, intersec2};
    }

    @Override
    public String getType() {
        return name;
    }

    private void initSettins() {
        this.loSettings.add(new SettingObject("Ziegenhein2018", "Ziegenhein2018", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("ML-BubbleDetect", "ML-BubbleDetect", true, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Use Mask Path", "use_mask_Path", true, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Mask Path", "mask_Path", "C:\\Users\\Nutzer\\Desktop\\TestNN\\MaskPath", SettingObject.SettingsType.String));
    }

    @Override
    public void buildClusters() {
        SettingsCluster IMGFilter = new SettingsCluster("Object Masking",
                new String[]{"Ziegenhein2018", "ML-BubbleDetect", "use_mask_Path", "mask_Path"}, this);
        IMGFilter.setDescription("Masks objects in pictures based on edge detecting");
        lsClusters.add(IMGFilter);
    }

}
