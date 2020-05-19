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
public interface Area {

    public int[][] getValuesInAreaInt(getDataInt o);
    public double[][] getValuesInAreaDouble(getDataDouble o);
    public double[][] getValuesInAreaDouble(getDataDouble o, OrderedPair opSubPixelShift);
    public boolean[][] getValuesInAreaBoolean(getDataBoolean o);
    public double getSize();
    public OrderedPair getPosition();
    public ImageInt paintOngrid(ImageInt oGrid, int iValue);
    
}
