/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import com.tivconsultancy.opentiv.helpfunctions.settings.Settings;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Model_Settings {
    public List<Settings> getAllSettings();
    public Settings getSettings(Enum SettingName);
    
}
