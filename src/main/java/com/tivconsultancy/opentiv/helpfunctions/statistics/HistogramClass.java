/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package com.tivconsultancy.opentiv.helpfunctions.statistics;

import com.tivconsultancy.opentiv.math.interfaces.*;
import com.tivconsultancy.opentiv.math.sets.Set1D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class HistogramClass extends Set1D implements Iterator<Object> {
    public List<Object> loContent = new ArrayList<>(); //~
    public Double dHelp = null; // Helpparameter that helps for easily implement many algorithms
    
    public HistogramClass(double dLeft, double dRight){
        super(dLeft, dRight);        
    }
    
    public boolean addContent(Object o, Value oValue){
        if(isInsideLR(oValue.getValue(o))){
            loContent.add(o);
            return true;
        }
        return false;
    }
    
    public List<Object> getContent(){
        return loContent;
    }
    
    public int getCount(){
        return loContent.size();        
    }
    
    public Double getAverage(Value oValue){
        return Basics.Arithmetic(loContent, oValue);
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object next() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
