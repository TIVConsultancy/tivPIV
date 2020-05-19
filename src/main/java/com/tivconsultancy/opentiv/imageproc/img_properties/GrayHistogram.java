/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.img_properties;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.opentiv.helpfunctions.statistics.Histogram;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;
import com.tivconsultancy.opentiv.math.interfaces.Value;

/**
 *
 * @author Thomas Ziegenhein
 */
public class GrayHistogram extends Histogram {
    
    public GrayHistogram(ImageGrid oGrid){
        super(0,256,1);        
        for(ImagePoint o : oGrid.oa){
            addContent(o, new Value() {

                @Override
                public Double getValue(Object pParameter) {
                    return (double) ((ImagePoint) pParameter).iValue;
                }
            });
        }
    }
    
    public GrayHistogram(ImageInt oGrid){
        super(0,256,1);        
        for(int i = 0; i < oGrid.iaPixels.length; i++){
            for(int j = 0; j< oGrid.iaPixels[0].length; j++){                
            addContent(new MatrixEntry(i, j, oGrid.iaPixels[i][j]), null);
            }
        }
    }

    @Override
    public void addContent(Object oContent, Value oValue) {
        
        if(oContent instanceof MatrixEntry){
            loClasses.get( (int) ((MatrixEntry) oContent).dValue).loContent.add(oContent);
        }
        
        if(oContent instanceof ImagePoint){
            loClasses.get(((ImagePoint) oContent).iValue).loContent.add(oContent);
        }
                
                
    }
    
    
    
}
