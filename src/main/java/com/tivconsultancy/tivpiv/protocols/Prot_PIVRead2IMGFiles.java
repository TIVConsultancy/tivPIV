/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.tivpiv.protocols;

import com.tivconsultancy.opentiv.highlevel.protocols.NameSpaceProtocolResults1D;
import com.tivconsultancy.opentiv.highlevel.protocols.UnableToRunException;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Reader;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
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
public class Prot_PIVRead2IMGFiles extends PIVProtocol {

    private static final long serialVersionUID = -5062728987649688264L;

    File imgFile;
    File imgFile2;
    ImageInt imgRead;
    ImageInt imgRead2;
    private String name = "Read In";

    public Prot_PIVRead2IMGFiles(String name) {
        this();
        this.name = name;
    }

    public Prot_PIVRead2IMGFiles() {
        super();
        imgRead = new ImageInt(50, 50, 150);
        imgRead2 = new ImageInt(50, 50, 150);
        buildLookUp();
        initSettings();
        buildClusters();
    }

    private ImageInt readImage() throws IOException {
        imgRead.setImage(IMG_Reader.readImageGrayScale(imgFile));
        return imgRead;
    }

    private ImageInt readImage2() throws IOException {
        imgRead2.setImage(IMG_Reader.readImageGrayScale(imgFile2));
        return imgRead;
    }

    private void buildLookUp() {
        ((PIVController) StaticReferences.controller).getDataPIV().setImage(name, imgRead.getBuffImage());
    }

    @Override
    public void setImage(BufferedImage bi) {
        imgRead = new ImageInt(bi);
        imgRead2 = new ImageInt(bi);
        buildLookUp();
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
    public void run(Object... input) throws UnableToRunException {
        if (((PIVMethod) StaticReferences.controller.getCurrentMethod()).bReadFromSQL) {
            runSQL(input);
            return;
        }

        if (input != null && input.length == 1 && input[0] != null && input[0] instanceof File) {
            imgFile = (File) input[0];
            try {
                readImage();
            } catch (IOException ex) {
                throw new UnableToRunException("Cannot read imgage: " + imgFile, ex);
            }
        } else if (input != null && input.length >= 2 && input[0] != null && input[1] != null && input[0] instanceof File && input[1] instanceof File) {
            imgFile = (File) input[0];
            imgFile2 = (File) input[1];
            try {
                readImage();
                readImage2();
            } catch (IOException ex) {
                throw new UnableToRunException("Cannot read image: " + imgFile, ex);
            }
        } else {
            throw new UnableToRunException("Input is not a file", new IOException());
        }
        buildLookUp();
//        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
//        data.iaReadInFirst = imgRead.iaPixels;
//        data.iaReadInSecond = imgRead2.iaPixels;
    }

    private void runSQL(Object... input) throws UnableToRunException {
        if (input != null && input.length == 1 && input[0] != null) {
            PIVMethod method = ((PIVMethod) StaticReferences.controller.getCurrentMethod());
            try {
                imgFile = (File) input[0];
                imgRead = new ImageInt(((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readIMGFromSQL(method.experimentSQL, imgFile.getName()));
            } catch (SQLException | IOException ex) {
                throw new UnableToRunException("Cannot read image from SQL: " + imgFile, ex);
            }
        }else if (input != null && input.length == 2 && input[0] != null && input[1] != null) {
            PIVMethod method = ((PIVMethod) StaticReferences.controller.getCurrentMethod());
            try {
                imgFile = (File) input[0];
                imgFile2 = (File) input[1];
                imgRead = new ImageInt(((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readIMGFromSQL(method.experimentSQL, imgFile.getName()));
                imgRead2 = new ImageInt(((tivPIVSubControllerSQL) StaticReferences.controller.getSQLControler(null)).readIMGFromSQL(method.experimentSQL, imgFile2.getName()));
            } catch (SQLException | IOException ex) {
                throw new UnableToRunException("Cannot read image from SQL: " + imgFile, ex);
            }
        }
        buildLookUp();
//        DataPIV data = ((PIVController) StaticReferences.controller).getDataPIV();
//        data.iaPreProcFirst = imgRead.iaPixels;
//        data.iaPreProcSecond = imgRead2.iaPixels;
    }

    @Override
    public String getType() {
        return name;
    }

    @Override
    public void buildClusters() {
//        SettingsCluster CutImage = new SettingsCluster("Cut Image",
//                                                       new String[]{"BcutyTop", "cutyTop", "BcutyBottom",
//                                                           "cutyBottom", "BcutxLeft", "cutxLeft", "BcutxRight",
//                                                           "cutxRight"}, this);
//        CutImage.setDescription("Cut image");
//        lsClusters.add(CutImage);
    }

    /**
     * Cuts the image
     *
     * @param oInput Input image in the openTIV ImageInt format
     * @param oSettings Settings object containing the settings information
     * @return
     */
    private void initSettings() {
//        this.loSettings.add(new SettingObject("Cut Top", "BcutyTop", false, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Value", "cutyTop", 0, SettingObject.SettingsType.Integer));
//        this.loSettings.add(new SettingObject("Cut Bottom", "BcutyBottom", false, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Value","cutyBottom", 600, SettingObject.SettingsType.Integer));
//        this.loSettings.add(new SettingObject("Cut Left", "BcutxLeft", false, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Value","cutxLeft", 0, SettingObject.SettingsType.Integer));
//        this.loSettings.add(new SettingObject("Cut Right", "BcutxRight", false, SettingObject.SettingsType.Boolean));
//        this.loSettings.add(new SettingObject("Value","cutxRight", 10, SettingObject.SettingsType.Integer));
    }

    @Override
    public Object[] getResults() {
        return new Object[]{imgRead.clone(), imgRead2.clone()};
    }

}
