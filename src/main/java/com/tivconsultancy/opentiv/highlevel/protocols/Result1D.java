/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tivconsultancy.opentiv.highlevel.protocols;

import com.tivconsultancy.opentiv.math.specials.LookUp;
import com.tivconsultancy.opentiv.math.specials.NameObject;
import java.util.List;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class Result1D {
    
    private LookUp<Double> result;
    
    public Result1D(){
        result = new LookUp<>();
    }
    
    public void addResult(String name, Double d){
        NameObject<Double> o = new NameObject<>(name,d);
        result.add(o);
    }
    
    public boolean setResult(String name, Double d){
        if(result.get(name) != null){
            result.getEntry(name).o = d;
            return true;
        }
        return false;
    }
    
    public Double getRes(String name){
        return result.get(name);
    }
    
    public String getName(Double value){
        return result.get(value).name;
    }
    
    public List<String> getAllNames(){
        return result.getNames();
    }
    
    public void remove(String name){
        result.remove(name);
    }
    
}
