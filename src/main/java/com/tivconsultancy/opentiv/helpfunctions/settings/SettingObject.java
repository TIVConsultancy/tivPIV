/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.settings;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class SettingObject implements Serializable {

    protected String sName = "";
    public String viewName = null;
    public Object sValue = "";
    public SettingsType ident;
    public List<String> explanations;

    public SettingObject(String sName, Object sValue, SettingsType ident) {
        this.sName = sName;
        this.sValue = sValue;
        this.ident = ident;
    }
    
    public SettingObject(String viewName, String sName, Object sValue, SettingsType ident) {
        this.sName = sName;
        this.sValue = sValue;
        this.ident = ident;
        this.viewName = viewName;
    }

    public Object getValue(String sName) {
        if (sName.equals(this.sName)) {
            return sValue;
        }
        return null;
    }

    public String getName() {
        return this.sName;
    }
    
    public String getViewName() {
        if(viewName == null){
            return getName();
        }
        return this.viewName;
    }

    public void setValue(Object sValue) {
        if(sValue instanceof String){
            String s = sValue.toString();
            if(ident.equals(SettingsType.String)){
                this.sValue = s;
            }
            if(ident.equals(SettingsType.Double)){
                this.sValue = Double.valueOf((String) s);
            }
            if(ident.equals(SettingsType.Integer)){
                this.sValue = Double.valueOf((String) s).intValue();
            }
            if(ident.equals(SettingsType.Boolean)){
                this.sValue = Boolean.valueOf((String) s);
            }
//            this.sValue = sValue;
        }
        
    }

    public String toString() {
        return sName + "," + sValue.toString();
    }
    
    public String getValueAsString(){
        return sValue.toString();
    }

    public enum SettingsType {

        String, Double, Integer, Boolean, Object
    }

}
