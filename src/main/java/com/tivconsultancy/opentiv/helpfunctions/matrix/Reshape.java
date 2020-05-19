/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.matrix;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Reshape {
    public static int[] reshape(int[][] naInput) {
        /**
         *
         */
        int[] iaExtractedArray = new int[naInput.length * naInput[0].length];

        int iZae = 0;
        for (int i = 0; i < naInput.length; i++) {
            for (int j = 0; j < naInput[0].length; j++) {

                iaExtractedArray[iZae] = (int) naInput[i][j];

                iZae = iZae + 1;

            }

        }

        return (iaExtractedArray);
    }
}
