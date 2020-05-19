/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import com.tivconsultancy.opentiv.math.sets.Set2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ArbStructure2 implements Shape {

    public List<MatrixEntry> loPoints = new ArrayList<>();

    public ArbStructure2(List<?> loInput) {
        if (loInput == null || loInput.isEmpty()) {
            return;
        }
        if (loInput.get(0) instanceof MatrixEntry) {
            for (Object o : loInput) {
                loPoints.add((MatrixEntry) o);
            }
        }
    }
    
    public OrderedPair getPosition(){
        double dX = 0.0;
        double dY = 0.0;
        double dCounter = 0.0;
        for(MatrixEntry me : loPoints){
            dX = dX + me.j;
            dY = dY + me.i;
            dCounter++;
        }
        return new OrderedPair(dX/dCounter, dY/dCounter);
    }

    public List<MatrixEntry> getBoundingBox() {
        double dMaxX = 0;
        double dMinX = Double.MAX_VALUE;

        double dMaxY = 0;
        double dMinY = Double.MAX_VALUE;

        for (MatrixEntry op : loPoints) {
            if (dMaxX < op.j) {
                dMaxX = op.j;
            }
            if (dMinX > op.j) {
                dMinX = op.j;
            }
            if (dMaxY < op.i) {
                dMaxY = op.i;
            }
            if (dMinY > op.i) {
                dMinY = op.i;
            }
        }
        //Coordinates with respect to picture coordinates
        dMinX = Math.max(0, dMinX - 1);
        dMinY = Math.max(0, dMinY - 1);
        dMaxX = Math.max(0, dMaxX + 1);
        dMaxY = Math.max(0, dMaxY + 1);
        MatrixEntry leftTopCorner = new MatrixEntry((int) dMinY, (int) dMinX);
        MatrixEntry rightBottomCorner = new MatrixEntry((int) dMaxY, (int) dMaxX);
        List<MatrixEntry> lmeBox = new ArrayList<>();
        lmeBox.add(leftTopCorner);
        lmeBox.add(rightBottomCorner);
        return lmeBox;
    }
    
    public Set2D getBoundBoxSet(){
        List<MatrixEntry> lmeBoundBox = getBoundingBox();
        Set1D x = new Set1D(lmeBoundBox.get(0).j, lmeBoundBox.get(1).j);
        Set1D y = new Set1D(lmeBoundBox.get(0).i, lmeBoundBox.get(1).i);
        return new Set2D(x, y);
    }

    @Override
    public double getSize() {        
        return Math.sqrt(loPoints.size() / Math.PI);
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
