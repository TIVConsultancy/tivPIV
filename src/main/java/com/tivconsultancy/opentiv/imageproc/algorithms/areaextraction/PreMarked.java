/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.imageproc.algorithms.areaextraction;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.imageproc.algorithms.algorithms.Morphology;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.shapes.ArbStructure2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class PreMarked {
    public static List<ArbStructure2> getAreasBlackOnWhite(ImageInt Input){        
        Input.resetMarkers();
        List<ArbStructure2> lsStructures = new ArrayList<>();
        MatrixEntry me_Fill = getNextBlackPoint(Input, new MatrixEntry(0, 0));
        while(me_Fill != null){
            ArbStructure2 Structure = new ArbStructure2((new Morphology()).markFillN4(Input, me_Fill.i, me_Fill.j));
            if(Structure != null){
                if(!Structure.loPoints.isEmpty()){
                    lsStructures.add(Structure);
                }                
            }
            me_Fill = getNextBlackPoint(Input, me_Fill);
        }
        return lsStructures;
    }        
    
    public static MatrixEntry getNextBlackPoint(ImageInt Input, MatrixEntry meStart){
        for(int i = 0 ; i < Input.iaPixels.length; i++){
            for(int j = 0 ; j < Input.iaPixels[i].length; j++){
                if(Input.iaPixels[i][j] == 0 && !Input.baMarker[i][j]){
                    return new MatrixEntry(i, j);
                }
            }
        }
        return null;
    }
        
}
