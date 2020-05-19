/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.helpfunctions;

import com.tivconsultancy.opentiv.imageproc.primitives.ImageInt;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataBoolean;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataDouble;
import com.tivconsultancy.tivpiv.helpfunctions.DataTypes.getDataInt;


/**
 *
 * @author Thomas Ziegenhein
 */
public class Voronoi implements Area{
    
//    public static void 
    

    @Override
    public int[][] getValuesInAreaInt(getDataInt o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[][] getValuesInAreaDouble(getDataDouble o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean[][] getValuesInAreaBoolean(getDataBoolean o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderedPair getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ImageInt paintOngrid(ImageInt oGrid, int iValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[][] getValuesInAreaDouble(getDataDouble o, OrderedPair opSubPixelShift) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
