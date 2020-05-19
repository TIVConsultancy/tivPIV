/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package delete.trash;

import delete.trash.SettingsFeatureObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public class Settings_FeatureBased {
    public List<SettingsFeatureObject> loSettings = new ArrayList<>();

    public Settings_FeatureBased() {
        setDefaultSetttings();
    }

    private void setDefaultSetttings() {
    }

    public void addSettingsObject(SettingsFeatureObject o) {
        this.loSettings.add(o);
    }

    public final SettingsFeatureObject getSettingsValue(String sSettingName) {        
        for (SettingsFeatureObject oS : loSettings) {            
            if (oS.getName().equals(sSettingName)) {
                return oS;
            }
        }
//        System.out.println("Warning!! No Setting found for: " + sSettingName);
        return null;
    }

    public final List<SettingsFeatureObject> getALLSettingsValues(String sSettingName) {
        List<SettingsFeatureObject> lo = new ArrayList<>();
        for (SettingsFeatureObject oS : loSettings) {
            if (oS.getName().equals(sSettingName)) {
                lo.add(oS);
            }
        }
        if (lo.isEmpty()) {
//            System.out.println("Warning!! No Setting found for: " + sSettingName);
        }
        return lo;
    }
    
    public List<SettingsFeatureObject> getOutput() {
        return loSettings;
    }

    public void setReadIn(List<SettingsFeatureObject> lsIn) {
        for (SettingsFeatureObject s : lsIn) {
             loSettings.add(s);
        }
    }
}
