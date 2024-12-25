
import com.architektura.architektura_procesor.Components.ALU;
import com.architektura.architektura_procesor.Components.GeneralRegisters;
import com.architektura.architektura_procesor.Enums.AluOperation;
import com.architektura.architektura_procesor.Main;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author brune
 */
@SpringBootTest(classes = {ALU.class, GeneralRegisters.class})
public class AluTest {
    
    @Autowired
    GeneralRegisters generalRegisters;
    
    @Autowired
    ALU alu;
    
         
     @Test
     public void aluAddsTwoRegistersTogetherWithCorrectValue(){
         short exptectedValue = 20;
         generalRegisters.setRegister((short)0000,(short)5);
         generalRegisters.setRegister((short)0001,(short)15);
         
         short givenValue = alu.pass((short)0b0010, generalRegisters.getRegister((short)0000), generalRegisters.getRegister((short)0001));
         
         assertEquals(exptectedValue,givenValue);
         
     }
    
}
