


package com.tivconsultancy.opentiv.imageproc.algorithms.algorithms;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.math.grids.CellRec;
import com.tivconsultancy.opentiv.math.grids.RecOrtho2D;
import com.tivconsultancy.opentiv.math.interfaces.Position;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class NeighborIterator {
    
    public RecOrtho2D grid;
    
    public static void iterate(int lengthI, int lengthJ, List<MatrixEntry> lme, double maxNorm, Action performer){
        double startX = 0.0;
        double startY = 0.0;
        double endX = 1.0*lengthJ;
        double endY = 1.0*lengthI;
        
        int cellCountX = (int) Math.ceil(endX/maxNorm);
        int cellCountY = (int) Math.ceil(endY/maxNorm);
        
        RecOrtho2D grid = new RecOrtho2D(startX, endX,  endY, startY, cellCountX, cellCountY);
        grid.addContent(lme);
        
        for(MatrixEntry me : lme){
            for(CellRec o : grid.getNeighbors(new OrderedPair(me.j, me.i))){
                for(Position pos : o.loContent){
                    performer.perform(me, (MatrixEntry) pos);
                }
            }
        }        
    }
    
    public interface Action{
        
        public void perform(MatrixEntry me1, MatrixEntry me2); 
        
    }
    
    
}
