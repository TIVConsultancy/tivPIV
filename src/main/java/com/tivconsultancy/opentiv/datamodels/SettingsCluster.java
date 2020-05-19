
package com.tivconsultancy.opentiv.datamodels;

import com.tivconsultancy.opentiv.helpfunctions.settings.SettingObject;
import com.tivconsultancy.opentiv.logging.TIVLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public abstract class SettingsCluster {
    List<SettingObject> ls;
    String name;
    
    public SettingsCluster(List<SettingObject> ls, String name){
        this.ls = ls;
        this.name = name;
    }

    public abstract List<SettingObject> getSettings();
    public abstract String getName();
    public abstract void setName(String name);
    public abstract void setSettings(List<SettingObject> ls);

    public void save(File saveFile) {
        List<SettingObject> allSettings = getSettings();

        try (FileOutputStream fos = new FileOutputStream(saveFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(allSettings);
        } catch (Exception ioe) {
            TIVLog.tivLogger.log(Level.SEVERE, "Cannot save settings", ioe);
        }
    }
    
    public SettingsCluster loadSettings(InputStream fis) {
        try (ObjectInputStream ois = new ObjectInputStream(fis);) {
            List<SettingObject> loSettings = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            HashSet loHash = new HashSet(loSettings);
            loSettings = new ArrayList<>(loHash);
            setSettings(loSettings);
        } catch (IOException ioe) {
            TIVLog.tivLogger.log(Level.SEVERE,"Cannot load settings from file", ioe);
        } catch (ClassNotFoundException c) {
            TIVLog.tivLogger.log(Level.SEVERE,"Settings file corrupt or old version", c);
        }
        return null;
    }

}
