/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package com.tivconsultancy.opentiv.helpfunctions.matrix;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Cast {
    public static byte[] castToByteprimitive(int[] iaInput) {

        byte[] byReturn = new byte[iaInput.length];

        for (int i = 0; i < byReturn.length; i++) {

            byReturn[i] = (byte) iaInput[i];

        }

        return byReturn;
    }
}
