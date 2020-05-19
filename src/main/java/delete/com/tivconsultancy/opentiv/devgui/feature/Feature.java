/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.feature;

import java.io.Serializable;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Feature extends Serializable {    
    public String getName();
    public String getDescription();
    public String getToolDescription();
    public String getSettingsText1();
    public String getSettingsDescriptions1();
    public String getValueDescriptions1();
    public String getSettings1();
    public void setSettings1(Object o);
    public String getSettingsText2();    
    public String getSettingsDescriptions2();
    public String getValueDescriptions2();
    public String getSettings2();
    public void setSettings2(Object o);
    public void removeFeature();
    public String toString();
    public Feature clone();
    public boolean equals(Object o);
}
