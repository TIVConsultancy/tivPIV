/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tivconsultancy.opentiv.helpfunctions.settings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SettingsCluster {
    
    String sName;
    String[] lsIdent;
    List<SettingObject> lsSettings = new ArrayList<>();
    String sDescription = "Description";
    
    public SettingsCluster(String sName, String[] lsIdent, Settings oSet){
        this.sName = sName;
        this.lsIdent = lsIdent;
        buildCluster(oSet);
    }
    
    private void buildCluster(Settings oSet){
        for(String s : lsIdent){
            lsSettings.add(oSet.getSettingsObject(s));
        }
    }
    
    public String getName(){
        return sName;
    }
    
    public void setDescription(String sDescription){
        this.sDescription = sDescription;
    }
    
    public String getDescription(){
        return this.sDescription;
    }
    
    public List<SettingObject> getSettings(){
        return lsSettings;
    }
    
    @Override
    public String toString(){
        return getName();
    }
}
