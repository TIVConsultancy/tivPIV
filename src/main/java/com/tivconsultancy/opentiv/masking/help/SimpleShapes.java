/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.masking.help;

import com.tivconsultancy.opentiv.helpfunctions.hpc.Stopwatch;
import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SimpleShapes {

    public int iPosX = 0;
    public int iPosY = 0;
    public double dNorm;
    public double dNormPot;

    public SimpleShapes(int iPosX, int iPosY, double dNormMax, double dNormPot) {
        this.iPosX = iPosX;
        this.iPosY = iPosY;
        this.dNorm = dNormMax;
        this.dNormPot = dNormPot;
    }

//    public void setOnGrid(ImageGrid oInput) {        
//        ImagePoint Center = new ImagePoint(iPosX, iPosY, 255, oInput);
//        OrderedPair opCenter = Center.getPos();
////        ImageGrid oReturn = new ImageGrid(oInput.getMatrix());        
//        for (int i = 0; i < oInput.oa.length; i++) {
//            ImagePoint o = oInput.oa[i];
//            OrderedPair op = o.getPos();
//            if ((op.x - opCenter.x) < dNorm && (op.y - opCenter.y) < dNorm) {
//                if ((o.getNorm2(Center, String.valueOf(dNormPot))) < dNorm) {
//                    oInput.oa[i].bMarker = true;
//                    oInput.oa[i].iValue = 0;
//                }
//            }
//        }      
    public void setOnGrid(ImageInt oInput) {
        MatrixEntry Center = new MatrixEntry(iPosY, iPosX);
        Stopwatch.addTimmer("getArea");
        List<MatrixEntry> oArea = oInput.getsubArea(iPosY, iPosX, (int) dNorm);
        Stopwatch.stop("getArea");

        if (dNormPot >= 10) {
            for (MatrixEntry me : oArea) {
                oInput.iaPixels[me.i][me.j] = 0;
                oInput.baMarker[me.i][me.j] = true;
            }
        } else {
            for (MatrixEntry me : oArea) {
                if ((me.getNorm2(Center, String.valueOf(dNormPot))) < dNorm) {
                    oInput.iaPixels[me.i][me.j] = 0;
                    oInput.baMarker[me.i][me.j] = true;
                }

            }
        }
    }

}
