/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.specials;

import com.tivconsultancy.opentiv.math.interfaces.Normable;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class NameObject<T> implements Normable<NameObject>{
    public String name;
    public T o;
    
    public NameObject(String name, T o){
        this.name = name;
        this.o = o;
    }

    @Override
    public Double getNorm(NameObject o2) {
        return 1.0*Math.abs(name.hashCode() - o2.name.hashCode());
    }

    @Override
    public Double getNorm2(NameObject o2, String sNormType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        return name + ", " + o.toString();
    }        
    
    
    
}
