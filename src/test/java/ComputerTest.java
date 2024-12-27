
import com.architektura.architektura_procesor.Components.ALU;
import com.architektura.architektura_procesor.Components.GeneralRegisters;
import com.architektura.architektura_procesor.Components.Memory;
import com.architektura.architektura_procesor.Components.ProcessorRegisters;
import com.architektura.architektura_procesor.Computer;
import com.architektura.architektura_procesor.Configuration.MemoryConfiguration;
import com.architektura.architektura_procesor.Enums.Opcode;
import com.architektura.architektura_procesor.Main;
import com.architektura.architektura_procesor.Services.BitService;
import java.lang.reflect.Method;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class ComputerTest {
    
  
    @Test
    public void processorExecuteAddingNumbersCorrectly() throws Exception {
        
        
        ProcessorRegisters processorRegisters = new ProcessorRegisters();
        GeneralRegisters generalRegisters = new GeneralRegisters();
        Memory memory = new Memory();
        MemoryConfiguration memoryConfig = new MemoryConfiguration();
        BitService bitService = new BitService();
        ALU alu = new ALU(processorRegisters,bitService);
        Computer computer = new Computer(alu,generalRegisters,memory,processorRegisters,bitService,memoryConfig);

        
        memoryConfig.path = "./testBytes.txt";
        
        
        
        Method privateMethod = Computer.class.getDeclaredMethod("executeInstruction",Opcode.class);
        privateMethod.setAccessible(true);
        
        generalRegisters.setRegister((short)0000,(short)15);
        generalRegisters.setRegister((short)0001,(short)5);
         
         
        short exptectedValue = 20;
         
         
        short instruction = (short) 0b0000000000100001;
            
        processorRegisters.setInstructionFetchRegister(instruction);
        
        privateMethod.invoke(computer, Opcode.fromOpcode(instruction));
        
        assertEquals(exptectedValue,generalRegisters.getRegister((short)0000));
           

   }
    
    public static short parseMachineCode(String binaryCode) {
    return (short) Integer.parseInt(binaryCode, 2);
}
    
    
    
}
