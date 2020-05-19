/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.io;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Output {
    public static void WriteListOrderedPair(String sFile, List<? extends OrderedPair> lop, boolean bHeader) {
        List<String[]> lsSave = new ArrayList<String[]>();

        if (bHeader) {
            {
                String[] saTemp = new String[3];
                saTemp[0] = "Position x, ";
                saTemp[1] = "Position y, ";
                saTemp[1] = "Value";
                lsSave.add(saTemp);
            }
        }

        for (OrderedPair op : lop) {
            if (op != null) {
                String[] saTemp = new String[2];
                saTemp[0] = String.valueOf(op.x) + ",";
                saTemp[0] = String.valueOf(op.y) + ",";
                saTemp[1] = String.valueOf(op.dValue);
                lsSave.add(saTemp);
            }
        }

        Writer oWriter = new Writer(sFile);

        oWriter.writels(lsSave);
    }
}
