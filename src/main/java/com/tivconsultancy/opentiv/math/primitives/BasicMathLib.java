/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.primitives;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;

/**
 *
 * @author Thomas Ziegenhein
 */
public class BasicMathLib {

    public static double SecondCartesian(double x1, double y1, double x2, double y2) {
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    public static OrderedPair pqformulat(double p, double q) {
        double w = (Math.pow(p, 2) - 4.0 * q);
        if (w < 0) {
            return null;
        }
        return new OrderedPair(0.5 * (-1.0 * p + Math.sqrt(w)), 0.5 * (-1.0 * p - Math.sqrt(w)));
    }
}
