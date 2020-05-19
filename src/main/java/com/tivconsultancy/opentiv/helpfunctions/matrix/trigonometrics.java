/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.matrix;

/**
 *
 * @author Thomas Ziegenhein
 */
public class trigonometrics {

    public static int getDiscreteDiretion(int iX, int iY) {

        /*
         * Analysis in the first and second quadrant. Returns the multiplication of pi/4
         */

        int iReturn = 0;

        if (iX != 0) {
            
            double dFraction = (double) iY / (double) iX;

            double dArc = Math.atan(dFraction);

            if (Math.abs(dArc) > (Math.PI * 3.0 / 8.0)) {
                iReturn = 2;
            }

            if (Math.abs(dArc) < (Math.PI * 1.0 / 8.0)) {
                iReturn = 0;
            }

            if (Math.abs(dArc) <= (Math.PI * 3.0 / 8.0) && Math.abs(dArc) >= (Math.PI * 1.0 / 8.0)) {

                if (dArc < 0) {
                    iReturn = 3;
                } else {
                    iReturn = 1;
                }

            }
        } else {
            
            if(iY != 0){
                iReturn = 2;
            } else {
                iReturn = -1;
            }
            
            
        }

        /*if (x != 0) {

         if (Math.abs(Math.tan(((double) y) / ((double) x))) < Math.tan(1.0 / 8.0 * Math.PI)) {
         iReturn = 0;
         }

         if (Math.tan(((double) y) / ((double) x)) <= Math.tan(3.0 / 8.0 * Math.PI) && Math.tan(((double) y) / ((double) x)) >= Math.tan(1.0 / 8.0 * Math.PI)) {
         iReturn = 1;
         }

         if (Math.abs(Math.tan(((double) y) / ((double) x))) > Math.tan(3.0 / 8.0 * Math.PI)) {
         iReturn = 2;
         }

         if (Math.tan(((double) y) / ((double) x)) <= Math.tan(-1.0 / 8.0 * Math.PI) && Math.tan(((double) y) / ((double) x)) >= Math.tan(-3.0 / 8.0 * Math.PI)) {
         iReturn = 3;
         }
         }*/



        return iReturn;

    }

    public static int[][] getDirection(int[][] iaInputGradientX, int[][] iaInputGradientY) {
        /*
         * Analysis the direction of all X, Y Values
         */
        int[][] iaReturn = new int[iaInputGradientX.length][iaInputGradientX[0].length];

        for (int i = 0; i < iaInputGradientX.length; i++) {

            for (int j = 0; j < iaInputGradientX[0].length; j++) {

                iaReturn[i][j] = getDiscreteDiretion(iaInputGradientX[i][j], iaInputGradientY[i][j]);

            }

        }

        return iaReturn;

    }
    
    public static int[] getDirection(int[] iaInputGradientX, int[] iaInputGradientY) {
        /*
         * Analysis the direction of all X, Y Values
         */
        int[]iaReturn = new int[iaInputGradientX.length];

        for (int i = 0; i < iaInputGradientX.length; i++) {


                iaReturn[i] = getDiscreteDiretion(iaInputGradientX[i], iaInputGradientY[i]);

            

        }

        return iaReturn;

    }
}
