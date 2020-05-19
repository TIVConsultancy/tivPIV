/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.masking.data;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import com.tivconsultancy.opentiv.helpfunctions.settings.SettingsCluster;
import com.tivconsultancy.opentiv.masking.help.SimpleShapes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SettingsMasking extends Settings {
    
    public List<SimpleShapes> loShapes = new ArrayList<>();
    
    public SettingsMasking(){
        this.loSettings.add(new SettingObject("Ziegenhein2018", false, SettingObject.SettingsType.Boolean));
        buildClusters();
    }        

    @Override
    public String getType() {
        return "Image Masking";
    }

    @Override
    public void buildClusters() {
        SettingsCluster Ziegenhein2018 = new SettingsCluster("Ziegenhein2018", new String[]{"Ziegenhein2018"} , this);
        Ziegenhein2018.setDescription("Masking distinct objects in the frame");
        lsClusters.add(Ziegenhein2018);
    }
    
}
