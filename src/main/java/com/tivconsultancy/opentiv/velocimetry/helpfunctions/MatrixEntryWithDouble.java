/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.velocimetry.helpfunctions;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;


/**
 *
 * @author Thomas Ziegenhein
 */
public class MatrixEntryWithDouble extends MatrixEntry {
    public double x = 0;
    public double y = 0;
    public MatrixEntryWithDouble(){
        super(0,0);
    }
    
    public MatrixEntryWithDouble(MatrixEntry me){
        super(me.i,me.j);
    }
            
    public MatrixEntryWithDouble(int i, int j){
        super(i, j);
        this.x = j;
        this.y = i;
    }
    
    public MatrixEntryWithDouble(double x, double y){
        super((int) x, (int) y);
        this.x = x;
        this.y = y;
    }
}
