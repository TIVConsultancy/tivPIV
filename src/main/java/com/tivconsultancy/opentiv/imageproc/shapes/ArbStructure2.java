/* 
 * Copyright 2020 TIVConsultancy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
