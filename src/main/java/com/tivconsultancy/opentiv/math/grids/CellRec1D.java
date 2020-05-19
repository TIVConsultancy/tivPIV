/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.grids;

import com.tivconsultancy.opentiv.math.interfaces.*;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class CellRec1D extends Set1D {
    public List<Object> loContent = new ArrayList<>();
    public Double dAverage = null;
    public Double dSTDDev = null;
    
    public CellRec1D(double dLeftBorder, double dRightBorder){
        super(dLeftBorder, dRightBorder);
    }
    
    public void addContent(Object o, Value oValue) {
        if (o != null && isInside(o, oValue)) {
            loContent.add(o);
        }
    }
    
    public boolean isInside (Object o, Value oValue) {
        if(oValue == null)  return super.isInside((Double) o);
        return super.isInside(oValue.getValue(o));
    }
    
    public Double average(Value oValue, Value oWeight) {
        double dIntegrant = 0.0;
        double dWeight = 0.0;
        for (Object oPos : loContent) {
            if(oPos == null) continue;
            dIntegrant = dIntegrant + (oValue.getValue(oPos) * oWeight.getValue(oPos));
            dWeight = dWeight + oWeight.getValue(oPos);
        }
        if (dWeight != 0) {
            this.dAverage = dIntegrant / dWeight;
        }
        return this.dAverage;
    }
    
    public Double STDDev(Value oValue, Value oWeight) {
        double dIntegrant = 0.0;
        double dWeight = 0.0;
        this.average(oValue, oWeight);
        for (Object oPos : loContent) {
            dIntegrant = dIntegrant + oWeight.getValue(oPos) * (oValue.getValue(oPos) - this.dAverage) * (oValue.getValue(oPos) - this.dAverage);
            dWeight = dWeight + oWeight.getValue(oPos);
        }
        if (dWeight != 0) {
            this.dSTDDev = dIntegrant / dWeight;
        }
        return this.dSTDDev;
    }
    
}
