/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.imageproc.testing;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Writer;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.shapes.Circle;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Shapes {
    
    public static void test_Points_InEllipse() throws IOException{
        String sPWD = "E:\\Sync\\openTIV\\Tests\\ImageProc\\Shapes\\PointsInsideEllipse";
        ImageInt oBlackBoard = new ImageInt(300, 300, 0);
        Circle oEllipse = new Circle(new MatrixEntry(150, 150), 50, 80, 0.5);
        oBlackBoard.setPoints(oEllipse.lmeCircle, 255);
        MatrixEntry meOutside = new MatrixEntry(80, 120);
        MatrixEntry meOnContour = new MatrixEntry(125, 150);
        MatrixEntry meInside = new MatrixEntry(144, 144);
//        oBlackBoard.setPoint(meOutside, 127);
//        oBlackBoard.setPoint(meOnContour, 127);
//        oBlackBoard.setPoint(meInside, 127);        
        for(int i = 0; i < oBlackBoard.iaPixels.length; i++){
            for(int j = 0; j< oBlackBoard.iaPixels[0].length; j++){
                MatrixEntry meCheck = new MatrixEntry(i, j);
                double iCheck = oEllipse.checkpoint(meCheck);
                if(iCheck > 1){
                    oBlackBoard.setPoint(meCheck, 55);
                }
                if(iCheck == 1){
                    oBlackBoard.setPoint(meCheck, 200);
                }
                if(iCheck < 1){
                    oBlackBoard.setPoint(meCheck, 127);
                }
            }
        }
        for(MatrixEntry meEllipse : oEllipse.lmeCircle){
            oBlackBoard.iaPixels[meEllipse.i][meEllipse.j] = oBlackBoard.iaPixels[meEllipse.i][meEllipse.j] + 55;
        }
        IMG_Writer.PaintGreyPNG(oBlackBoard, new File(sPWD + File.separator + "Output.png"));
        
    }
    
}
