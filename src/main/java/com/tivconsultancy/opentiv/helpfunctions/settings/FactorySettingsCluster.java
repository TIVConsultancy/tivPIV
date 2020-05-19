/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tivconsultancy.opentiv.helpfunctions.settings;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class FactorySettingsCluster {

    public static SettingsCluster getStandardCluster(String name, String[] values, String sDescription, Settings set){
        SettingsCluster standardManufacture = new SettingsCluster(name, 
                values
                , set);
        standardManufacture.setDescription(sDescription);        
        return standardManufacture;
    }
    
}
