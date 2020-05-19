/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.hpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Thomas Ziegenhein
 */
public final class Stopwatch {
    
    public static List<StopUnit> loUnits = new ArrayList<>();
    
    
    public static void clearUnits(){
        loUnits.clear();
    }
    
    public static void addTimmer(String sName){
        loUnits.add(new StopUnit(sName));       
    }
    
    public static void stopLast(){
        loUnits.get(loUnits.size()-1).end();
    }
    
    public static void stop(String sName){
        for(StopUnit o : loUnits){
            if(o.sName.equals(sName)){
                o.end();
            }
        }        
    }
    
    public static void write(){
        Collections.sort(loUnits, new Comparator<StopUnit>() {

            @Override
            public int compare(StopUnit o1, StopUnit o2) {
                return (int) Math.signum((o2.fEndTime - o2.fStartTime) - (o1.fEndTime - o1.fStartTime));
            }
        });
        for(StopUnit o : loUnits){
            System.out.println(o.toString());
        }
    }
    
}
