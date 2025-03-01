
import com.architektura.architektura_procesor.Components.ALU;
import com.architektura.architektura_procesor.Components.GeneralRegisters;
import com.architektura.architektura_procesor.Components.ProcessorRegisters;
import com.architektura.architektura_procesor.Enums.AluOperation;
import com.architektura.architektura_procesor.Main;
import com.architektura.architektura_procesor.Services.BitService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author brune
 */
@SpringBootTest(classes = {ALU.class, GeneralRegisters.class, ProcessorRegisters.class,BitService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AluTest {
    
    @Autowired
    GeneralRegisters generalRegisters;
    
    @Autowired
    ALU alu;
    
    @Autowired
    ProcessorRegisters processorRegisters;
    
    @Autowired
    BitService bitService;
    
    
    
    @BeforeEach
    public void resetRegisters() {
        processorRegisters.setCarry((byte) 0);
        processorRegisters.setZero((byte) 0);
        processorRegisters.setNegative((byte) 0);
        processorRegisters.setOverflow((byte) 0);
    }

     @Test
     public void aluAddsTwoRegistersTogetherWithCorrectValue(){
         short exptectedValue = 20;
         generalRegisters.setRegister((short)0000,(short)5);
         generalRegisters.setRegister((short)0001,(short)15);
         
         short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         assertEquals(exptectedValue,givenValue);
         
     }
     
     
     @Test
     public void overflowExistOnAddiionOfTwoBigPositives(){
         byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)30000);
         generalRegisters.setRegister((short)0001,(short)30000);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getOverflow());
                 
     }
     
     
     @Test
     public void overflowtExistOnAdditionOfTwoBigNegatives(){
         byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)-30000);
         generalRegisters.setRegister((short)0001,(short)-30000);
              
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getOverflow());
     }
     
     
     @Test
     public void overflowDoNotExistOnCorrectSignedResultIsAccrossZero(){
         byte exptectedValue = 0;
         
         generalRegisters.setRegister((short)0000,(short)100);
         generalRegisters.setRegister((short)0001,(short)-200);
         
         short givenValue1 = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue1);
         
         assertEquals(exptectedValue,processorRegisters.getOverflow());
               
     }
     
     @Test
     public void overflowDoNotExistOnCorrectSignedResultIsZero(){
         byte exptectedValue = 0;
         
         generalRegisters.setRegister((short)0000,(short)7);
         generalRegisters.setRegister((short)0001,(short)-7);
         
         short givenValue1 = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue1);
         
         assertEquals(exptectedValue,processorRegisters.getOverflow());  
     }
     
     @Test
     public void overflowDoNotExistOnSmallAditionOfPositives(){
         
          byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)200);
         generalRegisters.setRegister((short)0001,(short)300);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
        
         assertEquals(exptectedValue,processorRegisters.getOverflow());    
     }
     
     @Test
     public void overflowDoNotExistOnSmallAditionOfNegatives(){
         byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)-200);
         generalRegisters.setRegister((short)0001,(short)-200);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
        
         assertEquals(exptectedValue,processorRegisters.getOverflow());
     }
     
     @Test
     public void negativeExistOnAddiionWhenOneNumberIsBiggerNegative(){
         byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)-20);
         generalRegisters.setRegister((short)0001,(short)5);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getNegative());
         
     }
     

     
     
     @Test
     public void negativeDoNotExist(){
         byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)-20);
         generalRegisters.setRegister((short)0001,(short)30);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getNegative());
         
     }
     
     @Test
     public void carryExistsWhenResultIsZero(){
        byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)32768);
         generalRegisters.setRegister((short)0001,(short)32768);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getCarry());
        
     }
     
     
     @Test
     public void carryExistsWhenResultIsGreaterThanZero(){
         byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)65535);
         generalRegisters.setRegister((short)0001,(short)20000);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getCarry());
     }
     

     
     @Test
     public void carryDoNotExistsForBasicAddition(){
        byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)20);
         generalRegisters.setRegister((short)0001,(short)30);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getCarry());
     }
     
     @Test
     public void carryDoNotExistsForOverflowing(){
         byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)32767);
         generalRegisters.setRegister((short)0001,(short)30);
         
         short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getCarry());
     }
     
     
     @Test
     public void carryDoNotExistsWhenNothingIsAdded(){
         byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)65536);
         generalRegisters.setRegister((short)0001,(short)0);
         
         short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getCarry());
     }
     
     
     
     
     
      @Test
     public void zeroExistForUnsigned(){
         byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)0);
         generalRegisters.setRegister((short)0001,(short)0);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getZero());      
     }
     
     @Test
     public void zeroExistForSigned(){
         
         byte exptectedValue = 1;
         generalRegisters.setRegister((short)0000,(short)300);
         generalRegisters.setRegister((short)0001,(short)-300);
         
         short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getZero());
         
     }
     
     
     @Test
      public void zeroDoNotExist(){
         byte exptectedValue = 0;
         generalRegisters.setRegister((short)0000,(short)300);
         generalRegisters.setRegister((short)0001,(short)0);
         
          short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         System.out.println("Result: " + givenValue);
         
         assertEquals(exptectedValue,processorRegisters.getZero());
       }
    
}
