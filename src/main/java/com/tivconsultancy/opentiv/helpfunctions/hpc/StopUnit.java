/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.helpfunctions.hpc;

/**
 *
 * @author Thomas Ziegenhein
 */
public class StopUnit {

    long fStartTime = 0;
    long fEndTime = 0;
    String sName;    
    
    public StopUnit(String sName) {
        this.sName = sName;
        fStartTime = System.currentTimeMillis();
    }

    public long end() {
        if (fEndTime == 0) {
            fEndTime = System.currentTimeMillis();
        }
        return (fEndTime - fStartTime);
    }

    @Override
    public String toString() {
        if (fEndTime > 0) {
            return (fEndTime - fStartTime) + " [ms]";
        } else {
            return "StopUnit " + sName + " running for: " + fStartTime;
        }
    }
    
//    public String toString() {
//        if (fEndTime > 0) {
//            return sName + ": " + (fEndTime - fStartTime) + " [ms]";
//        } else {
//            return "StopUnit " + sName + " running for: " + fStartTime;
//        }
//    }
}
