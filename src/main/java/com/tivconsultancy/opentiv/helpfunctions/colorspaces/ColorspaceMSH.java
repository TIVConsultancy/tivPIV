/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.colorspaces;

import java.awt.color.ColorSpace;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ColorspaceMSH extends ColorSpace {

    /*
    https://github.com/kennethmoreland-com/kennethmoreland-com.github.io/blob/master/color-advice/smooth-cool-warm/smooth-cool-warm.ipynb
    */
    
    public ColorspaceMSH(){
        super(TYPE_3CLR, 3);
    }
    
    @Override
    public float[] toRGB(float[] colorvalue) {
        float[] fLAB = new float[3];
        fLAB[0] = colorvalue[0] * (float) Math.cos(colorvalue[1]);
        fLAB[2] = colorvalue[0] * (float) Math.sin(colorvalue[1]) * (float) Math.cos(colorvalue[2]);
        fLAB[2] = colorvalue[0] * (float) Math.sin(colorvalue[1]) * (float) Math.sin(colorvalue[2]);
        return (new ColorSpaceCIEELab()).toRGB(fLAB);
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        float[] fColor = new float[3];
        float[] fLAB = (new ColorSpaceCIEELab()).fromRGB(rgbvalue);
        fColor[0] = (float) Math.sqrt(fLAB[0]*fLAB[0] + fLAB[1]*fLAB[1] + fLAB[2]*fLAB[2]);
        fColor[1] = (float) Math.acos(fLAB[0]/fColor[0]);
        fColor[2] = (float) Math.atan2(fLAB[2], fLAB[1]);
        return fColor;
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
