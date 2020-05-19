/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.highlevel.protocols;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Reader;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.opentiv.preprocessor.OpenTIV_PreProc;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Prot_ReadIMGFiles extends Protocol {

    File imgFile;
    ImageInt imgRead;
    private String name = "Read In";

    public Prot_ReadIMGFiles(String name) {
        this();
        this.name = name;
    }
    
    public Prot_ReadIMGFiles() {
        super();
        imgRead = new ImageInt(50, 50, 150);
        buildLookUp();
        initSettings();
        buildClusters();
    }


    private ImageInt readImage() throws IOException {
        imgRead.setImage(IMG_Reader.readImageGrayScale(imgFile));
        return imgRead;
    }

    private void buildLookUp() {
        outPutImages = new LookUp<>();
        outPutImages.add(new NameObject<>(name, imgRead));
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
    public ImageInt getView(String identFromViewer) {
        return outPutImages.get(identFromViewer);
    }

    @Override
    public Double getOverTimesResult(NameSpaceProtocolResults1D ident) {
        return null;
    }

    @Override
    public void run(Object... input) throws UnableToRunException {
        if (input != null && input.length != 0 && input[0] != null && input[0] instanceof File) {
            imgFile = (File) input[0];
            try {
                readImage();                
            } catch (IOException ex) {
                throw new UnableToRunException("Cannot read imgage: " + imgFile, ex);
            }
            try {
                imgRead.setImage(OpenTIV_PreProc.performTransformation(this, imgRead).getBuffImage());
            } catch (Exception ex) {
                throw new UnableToRunException("Cannot transform image: " + imgFile, ex);
            }
        }else{
            throw new UnableToRunException("Input is not a file", new IOException());
        }
        buildLookUp();
    }

    @Override
    public String getType() {
        return name;
    }

    @Override
    public void buildClusters() {
        SettingsCluster CutImage = new SettingsCluster("Cut Image",
                                                       new String[]{"BcutyTop", "cutyTop", "BcutyBottom",
                                                           "cutyBottom", "BcutxLeft", "cutxLeft", "BcutxRight",
                                                           "cutxRight"}, this);
        CutImage.setDescription("Cut image");
        lsClusters.add(CutImage);
    }
    
    /**
     * Cuts the image
     *
     * @param oInput Input image in the openTIV ImageInt format
     * @param oSettings Settings object containing the settings information
     * @return
     */  
    
        
    private void initSettings(){
        this.loSettings.add(new SettingObject("Cut Top", "BcutyTop", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value", "cutyTop", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cut Bottom", "BcutyBottom", true, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value","cutyBottom", 600, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cut Left", "BcutxLeft", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value","cutxLeft", 0, SettingObject.SettingsType.Integer));
        this.loSettings.add(new SettingObject("Cut Right", "BcutxRight", false, SettingObject.SettingsType.Boolean));
        this.loSettings.add(new SettingObject("Value","cutxRight", 10, SettingObject.SettingsType.Integer));
    }

    @Override
    public Object[] getResults() {
        return new Object[]{imgRead};
    }

}
