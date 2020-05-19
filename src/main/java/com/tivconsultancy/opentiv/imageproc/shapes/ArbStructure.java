/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ArbStructure implements Shape{
    public List<ImagePoint> loPoints = new ArrayList<>();
    
    public ArbStructure(List<?> loInput){
        if(loInput.isEmpty()){
            return;
        }
        if(loInput.get(0) instanceof ImagePoint){
            loPoints = (List<ImagePoint>) loInput;
        }
        if(loInput.get(0) instanceof MatrixEntry){
            for(Object o : loInput){
                MatrixEntry me = (MatrixEntry) o;
                loPoints.add(new ImagePoint(me.j, me.i, 0, null));
            }            
        }        
    }
    
    public List<MatrixEntry> getBoundingBox(){
        double dMaxX = 0;
        double dMinX = Double.MAX_VALUE;
        
        double dMaxY = 0;
        double dMinY = Double.MAX_VALUE;
        
        for(ImagePoint o : loPoints){
            OrderedPair op = o.getPos();
            if(dMaxX < op.getPosX()){
                dMaxX = op.getPosX();
            }
            if(dMinX > op.getPosX()){
                dMinX = op.getPosX();
            }
            if(dMaxY < op.getPosY()){
                dMaxY = op.getPosY();
            }
            if(dMinY > op.getPosY()){
                dMinY = op.getPosY();
            }
        }
        //Coordinates with respect to picture coordinates
        MatrixEntry leftTopCorner = new MatrixEntry((int) dMinY, (int) dMinX);
        MatrixEntry rightBottomCorner = new MatrixEntry((int) dMaxY, (int) dMaxX);
        List<MatrixEntry> lmeBox = new ArrayList<>();
        lmeBox.add(leftTopCorner);
        lmeBox.add(rightBottomCorner);
        return lmeBox;
    }
        

    @Override
    public double getSize() {
        //Circle equivalnt radius
        return Math.sqrt(loPoints.size()/Math.PI);
    }

    @Override
    public double getMinorAxis() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getMajorAxis() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getFormRatio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getOrientationAngle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPixelCount() {
        return loPoints.size();
    }
}
