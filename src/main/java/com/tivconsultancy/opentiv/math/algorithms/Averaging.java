/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.algorithms;

import com.tivconsultancy.opentiv.math.interfaces.*;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Averaging {
    
    public static double getMeanAverage(List<?> lo, Value<Object> oValue){
        if(lo.isEmpty()) return Double.NaN;
        double dIntegrate = 0.0;
        for(Object o : lo){
            dIntegrate = dIntegrate + oValue.getValue(o);
        }
        return dIntegrate/(1.0*lo.size());
    }

    public static List<OrderedPair> LinearSmooth(List<OrderedPair> lop, String sType) {

        if (sType.equals("FWD")) {
            List<OrderedPair> lopSmooth = new ArrayList<>();

            for (int i = 0; i < lop.size() - 1; i++) {
                double dX = (lop.get(i).x + lop.get(i + 1).x) / 2;
                double dY = (lop.get(i).y + lop.get(i + 1).y) / 2;
                double dVal = (lop.get(i).dValue + lop.get(i + 1).dValue) / 2;
                OrderedPair op = new OrderedPair(dX, dY);
                op.dValue = dVal;
                lopSmooth.add(op);
            }
            return lopSmooth;
        }

        throw new UnsupportedOperationException("not yet implemented for type: " + sType);

    }
    
    public static Double linear(List<? extends Number> lo){
        double dSum = 0.0;
        double dWeight = 0.0;
        for(Number o : lo){
            dSum = dSum + o.doubleValue();
            dWeight = dWeight + 1.0;
        }
        return dSum/dWeight;
    }        

}
