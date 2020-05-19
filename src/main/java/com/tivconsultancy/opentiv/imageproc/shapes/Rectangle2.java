/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.imageproc.shapes;

import com.tivconsultancy.opentiv.imageproc.primitives.ImageGrid;
import com.tivconsultancy.opentiv.imageproc.primitives.ImagePoint;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Rectangle2 {
    public ImagePoint oTopLeft;
    public ImagePoint oTopRight;
    public ImagePoint oBottomLeft;
    public ImagePoint oBottomRight;
    
    public Line2 LineLeft;
    public Line2 LineRight;
    public Line2 LineTop;
    public Line2 LineBottom;
    
    
    public Rectangle2(ImagePoint oTopLeft, ImagePoint oBottomRight){
        this.oBottomLeft = new ImagePoint((int) oTopLeft.getPos().x, (int) oBottomRight.getPos().y, 255, oTopLeft.getGrid());
        this.oTopLeft = oTopLeft;
        this.oTopRight = new ImagePoint((int) oBottomRight.getPos().x, (int) oTopLeft.getPos().y, 255, oTopLeft.getGrid());
        this.oBottomRight = oBottomRight;
        
        this.LineLeft = new Line2(this.oBottomLeft, this.oTopLeft);
        this.LineTop = new Line2(this.oTopLeft, this.oTopRight);
        this.LineRight = new Line2(this.oTopRight, this.oBottomRight);
        this.LineBottom = new Line2(this.oBottomRight, this.oBottomLeft);
    }
    
    public ImageGrid setRectangle(ImageGrid oGrid, int iValue){        
        LineLeft.setLine(oGrid, iValue);
        LineTop.setLine(oGrid, iValue);
        LineRight.setLine(oGrid, iValue);
        LineBottom.setLine(oGrid, iValue);
        return oGrid;
    }
    
}
