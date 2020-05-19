/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.io;

import com.tivconsultancy.opentiv.helpfunctions.operations.Convert;
import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Input {
    
    public static List<OrderedPair> readIn(String sFile) throws IOException {
        Reader oRead = new Reader(sFile);
        List<OrderedPair> lopReadIn = new ArrayList<>();
        List<String[]> lsIn = oRead.readFileStringa();
        for (int i = 1; i < lsIn.size(); i++) {
            lopReadIn.add(Convert.convertToOP(lsIn.get(i),0,1,2));
        }

        return lopReadIn;
    }
    
}
