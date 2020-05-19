/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.interfaces;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Grid {    
    public Double getCellSize();
    public Double getValue(int i, int j);
    public Double setValue(int i, int j);
    public Double getdfdx(int i, int j);
    public Double getdfdy(int i, int j);    
}
