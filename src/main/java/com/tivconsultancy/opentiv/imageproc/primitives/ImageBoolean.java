/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tivconsultancy.opentiv.imageproc.primitives;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class ImageBoolean {

    public boolean[][] baMarker;
    public String sIdent;
    
    public ImageBoolean() {
        baMarker = new boolean[1][1];
    }
    
    public ImageBoolean(int iLength, int jLength) {
        baMarker = new boolean[iLength][jLength];
    }
    
    public ImageBoolean(boolean[][] bba) {
        baMarker = bba;
    }
    
    public static ImageBoolean valueOf(ImageInt img) {
        ImageBoolean imgReturn = new ImageBoolean(img.baMarker.length, img.baMarker[0].length);
        for (int i = 0; i < img.baMarker.length; i++) {
            for (int j = 0; j < img.baMarker[0].length; j++) {
                imgReturn.baMarker[i][j] = img.iaPixels[i][j] > 127;
            }
        }
        return imgReturn;
    }
    
}
