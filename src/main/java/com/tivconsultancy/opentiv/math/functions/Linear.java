/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.functions;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.opentiv.math.sets.Set1D;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Linear {
    
    public Double dydx;    
    public OrderedPair opStart;
    public Set1D Intveral;
    public byte sign = 0;
    
//    public Linear(Double dydx, OrderedPair opStart, Set1D Interval){
//        this.dydx = dydx;
//        this.opStart = opStart;
//    }
    
    public Linear(OrderedPair opStart, OrderedPair opEnd){        
        this.opStart = opStart;
        this.Intveral = new Set1D(opStart.x, opEnd.x);        
        this.sign = (byte) Math.signum(opEnd.x-opStart.x);
        if(opEnd.x == opStart.x){   
            if( (opEnd.y - opStart.y) > 0) this.dydx = Double.POSITIVE_INFINITY;
            else this.dydx = Double.NEGATIVE_INFINITY;
        }else{
            this.dydx = (opEnd.y - opStart.y)/(opEnd.x-opStart.x);
        }
    }
    
    public double getVlaue(double x){
        if(dydx.isInfinite()) return opStart.y;
        return opStart.y + (x - opStart.x) * dydx;
    }
    
    public OrderedPair getTangent(){
        if(dydx.isInfinite()) return new OrderedPair(0, Math.signum(dydx) * 1.0);
        double dNorm = Math.sqrt(Math.pow(dydx, 2) + 1);
        double Tx = Math.signum(sign) * 1.0/dNorm;
        double Ty = Math.signum(sign) * dydx/dNorm;
        return new OrderedPair(Tx, Ty);
    }
    
    
}
