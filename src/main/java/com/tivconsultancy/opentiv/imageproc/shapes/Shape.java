/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.shapes;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Shape {
    
    public double getSize();
    public double getMinorAxis();
    public double getMajorAxis();
    public double getFormRatio();
    public double getOrientationAngle();
    public int getPixelCount();
    
}
