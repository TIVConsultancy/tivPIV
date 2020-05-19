/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public abstract class Settings {

    public List<SettingObject> loSettings = new ArrayList<>();
    public List<SettingsCluster> lsClusters = new ArrayList<>();

    public Settings() {
        setDefaultSetttings();        
    }

    private void setDefaultSetttings() {
    }
    
    public List<SettingObject> getAllSettings(){
        return loSettings;
    }

    public void addSettingsObject(SettingObject o) {
        this.loSettings.add(o);
    }

    public final Object getSettingsValue(String sSettingName) {
        Object o = null;
        for (SettingObject oS : loSettings) {
            o = oS.getValue(sSettingName);
            if (o != null) {
                return o;
            }
        }
//        System.out.println("Warning!! No Setting found for: " + sSettingName);
        return o;
    }

    public final SettingObject getSettingsObject(String sSettingName) {
        Object o = null;
        for (SettingObject oS : loSettings) {
            o = oS.getValue(sSettingName);
            if (o != null) {
                return oS;
            }
        }
//        System.out.println("Warning!! No Setting found for: " + sSettingName);
        return null;
    }

    public final List<Object> getALLSettingsValues(String sSettingName) {
        List<Object> lo = new ArrayList<>();
        for (SettingObject oS : loSettings) {
            if(oS == null) continue;
            Object o = oS.getValue(sSettingName);
            if (o != null) {
                lo.add(o);
            }
        }
        if (lo.isEmpty()) {
//            System.out.println("Warning!! No Setting found for: " + sSettingName);
        }
        return lo;
    }

    public final void setSettingsValue(String sSettingName, Object oValue) {
        Object o;
        for (SettingObject oS : loSettings) {
            o = oS.getValue(sSettingName);
            if (o != null) {
                oS.setValue(oValue.toString());
                return;
            }
        }
//        System.out.println("Warning!! No Setting found for: " + sSettingName);
    }

    public final void setSettingsValue(SettingObject oSettig) {
        Object o;
        for (SettingObject oS : loSettings) {
            o = oS.getValue(oSettig.sName);
            if (o != null) {
                oS.setValue(oSettig.sValue.toString());
                return;
            }
        }
//        System.out.println("Warning!! No Setting found for: " + oSettig.sName);
    }

    public List<SettingObject> getOutput() {
        return loSettings;
    }

    public void setReadIn(List<SettingObject> lsIn) {
        for (SettingObject s : lsIn) {
            this.setSettingsValue(s);
        }
    }

    public List<SettingsCluster> getClusters() {
        return lsClusters;
    }

    // Unique identifier
    public abstract String getType();

    public abstract void buildClusters();

}
