/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tivconsultancy.opentiv.highlevel.protocols;

/**
 *
 * @author TZ ThomasZiegenhein@TIVConsultancy.com +1 480 494 7254
 */
public class UnableToRunException extends Exception {

    public UnableToRunException(String msg, Exception cause){
        super(msg, cause);
    }
    
}
