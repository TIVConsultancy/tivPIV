package com.tivconsultancy.opentiv.imageproc.primitives;

import com.tivconsultancy.opentiv.helpfunctions.matrix.MatrixEntry;

/**
 *
 * @author Thomas Ziegenhein
 */
public class ImagePointInt extends MatrixEntry {

    public boolean bMarker = false;

    public ImagePointInt(int i, int j) {
        super(i, j);
    }

    public ImagePointInt(MatrixEntry me) {
        super(me.i, me.j);
    }

    public ImagePointInt(ImagePoint op) {
        super(op.getPos().y, op.getPos().x);
    }

    @Override
    public boolean equals(Object o) {
        if(this == null || o == null){
            return false;
        }
        if (!(o instanceof ImagePointInt)) {
            return false;
        }
        if (((ImagePointInt) o).i == this.i
                && ((ImagePointInt) o).j == this.j) {
            return true;
        }
        return false;
    }

}
