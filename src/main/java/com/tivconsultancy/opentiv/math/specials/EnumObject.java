/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.specials;

import com.tivconsultancy.opentiv.math.interfaces.Normable;

/**
 *
 * @author Thomas Ziegenhein
 */
public class EnumObject implements Normable<EnumObject>{
    public Double dEnum;
    public Object o;
    
    public EnumObject(Double dEnum, Object o){
        this.dEnum = dEnum;
        this.o = o;
    }

    @Override
    public Double getNorm(EnumObject o2) {
        return Math.abs(this.dEnum - o2.dEnum);
    }

    @Override
    public Double getNorm2(EnumObject o2, String sNormType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        return dEnum + ", " + o.toString();
    }
    
}
