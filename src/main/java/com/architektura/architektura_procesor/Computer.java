/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor;

import com.architektura.architektura_procesor.Components.ALU;
import com.architektura.architektura_procesor.Components.GeneralRegisters;
import com.architektura.architektura_procesor.Components.Memory;
import com.architektura.architektura_procesor.Components.ProcessorRegisters;
import com.architektura.architektura_procesor.Services.BitService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */


@Component
public class Computer {
    
    ALU alu;
    GeneralRegisters generalRegisters;
    Memory memory;
    ProcessorRegisters processorRegisters;
    BitService bitServic;

    public Computer(ALU alu, GeneralRegisters generalRegisters, Memory memory, ProcessorRegisters processorRegisters, BitService bitServic) {
        this.alu = alu;
        this.generalRegisters = generalRegisters;
        this.memory = memory;
        this.processorRegisters = processorRegisters;
        this.bitServic = bitServic;
    }
    
    
   
    @EventListener(ApplicationReadyEvent.class)
    private void run(){
        System.out.println("Hello");
    }


    private void clock(){
        
    }
    
    private void step(){
        
    }
    
    private void reset(){
        
    }
    
    
    private void loadBytesToMemoryFromFile(String path){
        try {
            System.out.println("invoked");
            String hexContent = Files.readString(Paths.get(path)).replaceAll("\\s+", "");
            System.out.println("size: " + hexContent.length());

            if (hexContent.length() % 2 != 0) {
                throw new IllegalArgumentException("Hex content length must be even to form valid bytes. This is not even: " + hexContent);
            }

            byte[] memoryArray = memory.getMemory();
            for (int i = 0; i < hexContent.length() && i / 2 < memoryArray.length; i += 2) {
                memoryArray[i / 2] = (byte) Integer.parseInt(hexContent.substring(i, i + 2), 16);
            }

            memory.setMemory(memoryArray);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
    }
    
   
    
}
