/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.imageproc.testing;

import com.tivconsultancy.opentiv.helpfunctions.io.Writer;
import com.tivconsultancy.opentiv.imageproc.algorithms.algorithms.LevelSet;
import com.tivconsultancy.opentiv.imageproc.algorithms.algorithms.LevelSet.ImageGridDouble;
import com.tivconsultancy.opentiv.imageproc.img_io.IMG_Reader;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class LVLset {
    public static void testFarstMarching() throws IOException{
        ImageGrid o = IMG_Reader.readImageGrey(new File("E:\\Sync\\openTIV\\ClosedTIV\\BoundaryTracking\\Tests\\LevelSet\\FastMarching\\Test1.jpg"));
        o.binarize(127);
        ImageGridDouble lvlGrid =  LevelSet.fastMarching(o);
        lvlGrid = LevelSet.signLevelSet(lvlGrid);
        List<OrderedPair> lopOut = new ArrayList<>();
        for(ImagePoint op : lvlGrid.oa){
            lopOut.add(lvlGrid.getPosDouble(op));
        }
        Writer.WriteWithValue("E:\\Sync\\openTIV\\ClosedTIV\\BoundaryTracking\\Tests\\LevelSet\\FastMarching\\Out.csv", lopOut, true, null);
        lopOut.clear();
        
        ImageGridDouble oGridCurv = LevelSet.getCurvatureClassic(lvlGrid);
        
        for(ImagePoint op : oGridCurv.oa){
            lopOut.add(lvlGrid.getPosDouble(op));
        }
        Writer.WriteWithValue("E:\\Sync\\openTIV\\ClosedTIV\\BoundaryTracking\\Tests\\LevelSet\\FastMarching\\Curv.csv", lopOut, true, null);
        
    }
}
