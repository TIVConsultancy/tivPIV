/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.algorithms.algorithms.N8;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import com.tivconsultancy.opentiv.math.algorithms.Sorting;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class PointCloud implements Shape {

    protected HashSet<ImagePoint> lop;

    public PointCloud(HashSet<ImagePoint> lop) {
        this.lop = lop;
    }

    public HashSet<ImagePoint> getPoints(){
        return lop;
    }
    
    public ImageInt paintOnIMG(ImageInt img) {
        for (ImagePoint oOuter : lop) {
            OrderedPair op = oOuter.getPos();
            int i = (int) op.getPosY();
            int j = (int) op.getPosX();
            MatrixEntry meOuter = new MatrixEntry(i, j);
            int iCounter = 0;
            for (MatrixEntry me : img.getNeighborsN8(i, j)) {
                if (img.iaPixels[me.i][me.j] > 127) {
                    iCounter++;
                }
            }
            if (iCounter > 7) {
                continue;
            }
            for (ImagePoint oInner : lop) {
                OrderedPair opInner = oInner.getPos();
                int iInner = (int) opInner.getPosY();
                int jInner = (int) opInner.getPosX();
                MatrixEntry meInner = new MatrixEntry(iInner, jInner);
                (new Line3(meOuter, meInner)).setLine(img, 255);
            }
        }
        return img;
    }

    public static List<PointCloud> getPointClouds(HashSet<ImagePoint> lop, double maxDist) {
        List<PointCloud> returnList = new ArrayList<>();

        //the visited points will be marked in the following algorithm
        for (ImagePoint op : lop) {
            op.bMarker = false;
        }
        for (ImagePoint op : lop) {
            if (op.bMarker) {
                continue;
            }
            //Initialize the while loop
            HashSet<ImagePoint> neighborhood = new HashSet<>();
            neighborhood.add(op);
            op.bMarker = true;
            HashSet<ImagePoint> newNeighbors = getOtherNeighbors(lop, neighborhood, maxDist);
            neighborhood.addAll(newNeighbors);

            while (!newNeighbors.isEmpty()) {
                newNeighbors = getOtherNeighbors(lop, neighborhood, maxDist);
                neighborhood.addAll(newNeighbors);
            }
            returnList.add(new PointCloud(neighborhood));

        }
        return returnList;
    }

    public static HashSet<ImagePoint> getOtherNeighbors(HashSet<ImagePoint> allPoints, HashSet<ImagePoint> neighbors, double maxdist) {
        HashSet<ImagePoint> newNeighbors = new HashSet<>();
        for (ImagePoint neighbor : neighbors) {
            for (ImagePoint all : allPoints) {
                if (all.bMarker || neighbor.equals(all)) {
                    continue;
                }
                if (neighbor.getNorm(all) <= maxdist) {
                    all.bMarker = true;
                    newNeighbors.add(all);
                }
            }
        }
        return newNeighbors;
    }

    @Override
    public double getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
