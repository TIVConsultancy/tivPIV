/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.tivpiv.helpfunctions;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;
import com.tivconsultancy.tivpiv.data.DataPIV;


/**
 *
 * @author Thomas Ziegenhein
 */
public interface AreaInSecondFrame extends Area {
    public void shift(MatrixEntry meDisplacement, DataPIV Data);
//    public void shiftSubPix(OrderedPair meDisplacement);
    public MatrixEntry getShift();
}
