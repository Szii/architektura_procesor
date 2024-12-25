/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Components;

import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */

@Component
public class GeneralRegisters {
    
    private short rd;
    private short rn
;
    public GeneralRegisters() {
    }
    
    

    public Short getRd() {
        return rd;
    }

    public void setRd(Short rd) {
        this.rd = rd;
    }

    public Short getRn() {
        return rn;
    }

    public void setRn(Short rn) {
        this.rn = rn;
    }
    
    
    
}
