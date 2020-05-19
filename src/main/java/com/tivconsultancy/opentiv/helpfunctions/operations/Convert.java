/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package com.tivconsultancy.opentiv.helpfunctions.operations;

import com.tivconsultancy.opentiv.math.primitives.OrderedPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Convert {

    public static List<OrderedPair> convert(Double[][] ddaIn, int iXColumn, int iYColumn, int iValueColumn) {

        List<OrderedPair> lop = new ArrayList<>();
        
        for (Double[] da : ddaIn) {
            if (da != null && da[iXColumn] != null && da[iYColumn] != null) {
                OrderedPair op = new OrderedPair(da[iXColumn], da[iYColumn]);
                op.dValue = da[iValueColumn];
                lop.add(op);
            }
        }

        return lop;
    }

    public static List<List<OrderedPair>> convert(List<Double[][]> ldaIn, int iXColumn, int iYColumn, int iValueColumn) {

        List<List<OrderedPair>> llop = new ArrayList<>();
        for (Double[][] dda : ldaIn) {
            List<OrderedPair> lop = convert(dda, iXColumn, iYColumn, iValueColumn);
            llop.add(lop);
        }
        return llop;
    }

    public static List<Double> getValues(List<OrderedPair> lopInput) {
        List<Double> ldReturn = new ArrayList<>();
        for (OrderedPair op : lopInput) {
            ldReturn.add(op.dValue);
        }
        return ldReturn;
    }
    
    public static OrderedPair convertToOP(String[] sa, int iX, int iY, int iValue) {
        OrderedPair op = new OrderedPair();
        if (sa != null && !sa.equals("")) {
            op.x = Double.valueOf(sa[iX]);
            op.y = Double.valueOf(sa[iY]);
            op.dValue = Double.valueOf(sa[iValue]);
        }
        return op;
    }

}
