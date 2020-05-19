/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.main;

import delete.com.tivconsultancy.opentiv.devgui.feature.Feature;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface CallingFeatureWindow {
    public void addFeature(Feature oF);
    public Enum getCategory();
}
