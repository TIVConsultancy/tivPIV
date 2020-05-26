/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tivconsultancy.tivpiv;

import com.tivconsultancy.tivpiv.data.DataPIV;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class PIVStaticReferences {

    private static Logger logger = Logger.getLogger("tivPIV");
    
    public static Logger getlog() {
        return logger;
    }
    
    /**
     * Put here the conversion from the image information (greyscale, int space) to the information for the FFT (double space)
     * @param Data 
     */
    public static void calcIntensityValues(DataPIV Data) {
        
        Data.iaGreyIntensity1 = new double[Data.iaReadInFirst.length][Data.iaReadInFirst[0].length];
        Data.iaGreyIntensity2 = new double[Data.iaReadInSecond.length][Data.iaReadInSecond[0].length];

        for (int i = 0; i < Data.iaGreyIntensity1.length; i++) {
            for (int j = 0; j < Data.iaGreyIntensity1[0].length; j++) {
                Data.iaGreyIntensity1[i][j] = (double) Data.iaReadInFirst[i][j];
            }
        }

        for (int i = 0; i < Data.iaGreyIntensity2.length; i++) {
            for (int j = 0; j < Data.iaGreyIntensity2[0].length; j++) {
                Data.iaGreyIntensity2[i][j] = (double) Data.iaReadInSecond[i][j];
            }
        }

    }
    
}
