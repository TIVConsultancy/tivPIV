/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.data;

import com.tivconsultancy.opentiv.datamodels.Result1D;
import com.tivconsultancy.opentiv.datamodels.ResultsImageShowAble;
import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import com.tivconsultancy.tivpiv.helpfunctions.InterrGrid;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.tivconsultancy.opentiv.datamodels.overtime.DataBaseEntry;
import com.tivconsultancy.opentiv.datamodels.overtime.DatabaseRAM;
import com.tivconsultancy.tivGUI.StaticReferences;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author Thomas Ziegenhein
 */
public class DataPIV implements DataBaseEntry, Serializable, ResultsImageShowAble {

    private static final long serialVersionUID = 7041378570022471116L;

    // Output Images
    protected LookUp<Object> outPutImages;

    // 1D Data
    public Result1D results1D;

    //Data
    public int[][] iaPreProcFirst;
    public int[][] iaPreProcSecond;
    public int[][] iaReadInFirst;
    public int[][] iaReadInSecond;
    public double[][] iaGreyIntensity1;
    public double[][] iaGreyIntensity2;
    public boolean[][] baMask;
    public InterrGrid oGrid;

    //Settings
    public List<String> Pictures = new ArrayList<>();
    public String sPWDIn = "";
    public String sPWDOut = "";
    public final String sDebugFolder = "Debug";
    public final String sOutputFolder = "Data";
    public final String sPicFolder = "Pictures";
//    public int cutyTop = -1;
//    public int cutyBottom = -1;
//    public int cutxLeft = 50;
//    public int cutxRight = 1220;

    //PreProc
    //Sharpening
    // outsorced in protocols
//    public boolean bsharpen = true;
//    public int iHistCut = 50;
//    public int iThresholdSharp = 70;
//    public boolean bBinarize = false;
//    public int imax = 255;
//    //Increasing Contrast
//    public boolean bhighcont = false;
    //Masking
    // outsorced in protocols
//    public boolean bMask = true;
//    public boolean bFixedMask = true;
//    public int iEdgeThreshold = 70;
//    public int iSmallestStructure = 300;
    //PIV
    public int PIV_columns;
    public int PIV_rows;
    public int PIV_WindowSize = 32;
    public String sGridType = "Standard";
    public boolean Hart1998 = false;
    public double Hart1998Divider = 2.0;
    public String sSubPixelType = "Gaussian";
    //Multipass
    public boolean bMultipass = false;
    public boolean bMultipass_BiLin = true;
    public int iMultipassCount = 3;
    //Refine
    public String sRefine = "Disable";
    public boolean bOverlap = false;
    //PostPIV
    //Vector Validation
    public boolean bValidate = true;
    public String sValidationType = "MedianComp";
    public int iStampSize = 2;
    public double dValidationThreshold = 5;
    public boolean bInterpolation = false;
    //Display
    public double dStretch = 7;
    public boolean AutoStretch = true;
    public double AutoStretchFactor = 2;
    // Leap and Burst
    public int iLeap = 1;
    public int iBurstLength = -1;

    public DataPIV(int index) {
        results1D = new Result1D(index);
        outPutImages = new LookUp<>();
    }

//    @Override
//    public Double getRes(String name) {
//        return results1D.getRes(name);
//    }
//
//    @Override
//    public String getName(Double value) {
//        return results1D.getName(value);
//    }
//
//    @Override
//    public boolean setResult(String name, Double d) {
//        return results1D.setResult(name, d);
//    }
//    
//    @Override
//    public void addResult(String name, Double d){
//        results1D.addResult(name, d);
//    }
    @Override
    public BufferedImage getImage(String sIdent) {
        if (StaticReferences.controller.getDataBase() instanceof DatabaseRAM) {
            return (BufferedImage) outPutImages.get(sIdent);
        } else {
            byte[] imgIByte = (byte[]) outPutImages.get(sIdent);
            if (imgIByte == null) {
                return null;
            }

            try {
                return ImageIO.read(new ByteArrayInputStream((byte[]) outPutImages.get(sIdent)));
            } catch (IOException ex) {
                StaticReferences.getlog().log(Level.WARNING, "Cannot get image from storeage: " + sIdent, ex);
                return null;
            }
        }
    }

    @Override
    public void setImage(String sIdent, BufferedImage img) {

        if (StaticReferences.controller.getDataBase() instanceof DatabaseRAM) {
            if (!outPutImages.set(sIdent, img)) {
                outPutImages.add(new NameObject<>(sIdent, img));
            }
        } else {

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();

                if (!outPutImages.set(sIdent, imageInByte)) {
                    outPutImages.add(new NameObject<>(sIdent, imageInByte));
                }
            } catch (IOException ex) {
                StaticReferences.getlog().log(Level.WARNING, "Cannot store image: " + sIdent, ex);
            }
        }

    }

}
