/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.exceptions;

/**
 *
 * @author Thomas Ziegenhein
 */
public class EmptySetException extends Exception {
    public EmptySetException(String sMSG){
        super(sMSG);
    }

    public EmptySetException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
