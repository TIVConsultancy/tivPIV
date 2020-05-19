/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delete.com.tivconsultancy.opentiv.devgui.mvc_model;

import delete.com.tivconsultancy.opentiv.devgui.feature.Feature;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Model_Features {
    
    public List<Feature> getFeatures(Enum featureName);
    public List<Feature> getAllFeatures();
    
}
