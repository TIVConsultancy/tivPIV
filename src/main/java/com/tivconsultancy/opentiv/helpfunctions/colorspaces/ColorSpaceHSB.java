/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.colorspaces;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ColorSpaceHSB extends ColorSpace {
    
    public ColorSpaceHSB(){
        super(ColorSpace.TYPE_HSV, 3);
    }

    @Override
    public float[] toRGB(float[] colorvalue) {
        float[] oRGBCol = new float[3];
        Color oRGBColor = Color.getHSBColor(colorvalue[0], colorvalue[1], colorvalue[2]);
        oRGBCol[0] = (float) (oRGBColor.getRed()/255.0);
        oRGBCol[1] = (float) (oRGBColor.getGreen()/255.0);
        oRGBCol[2] = (float) (oRGBColor.getBlue()/255.0);
        
        return oRGBCol;
    }
    
    public Color toColor(float[] colorvalue) {
        float[] oRGBCol = new float[3];
        Color oRGBColor = Color.getHSBColor(colorvalue[0], colorvalue[1], colorvalue[2]);
        oRGBCol[0] = (float) (oRGBColor.getRed()/255.0);
        oRGBCol[1] = (float) (oRGBColor.getGreen()/255.0);
        oRGBCol[2] = (float) (oRGBColor.getBlue()/255.0);
        
        return new Color(oRGBCol[0], oRGBCol[1], oRGBCol[2]);
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        return (java.awt.Color.RGBtoHSB( (int) (rgbvalue[0] * 255), (int) (rgbvalue[1] * 255), (int) (rgbvalue[2] * 255), null));
    }

    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
