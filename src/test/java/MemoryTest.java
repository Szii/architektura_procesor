
import com.architektura.architektura_procesor.Components.ALU;
import com.architektura.architektura_procesor.Components.GeneralRegisters;
import com.architektura.architektura_procesor.Components.Memory;
import com.architektura.architektura_procesor.Components.ProcessorRegisters;
import com.architektura.architektura_procesor.Computer;
import com.architektura.architektura_procesor.Configuration.MemoryConfiguration;
import com.architektura.architektura_procesor.Main;
import com.architektura.architektura_procesor.Services.BitService;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author brune
 */
public class MemoryTest {
    
  
     @Test
    public void memoryContainsRightContentAfterReadingFromFileTest() throws Exception {
        
        
        ProcessorRegisters processorRegisters = new ProcessorRegisters();
        GeneralRegisters generalRegisters = new GeneralRegisters();
        Memory memory = new Memory();
        MemoryConfiguration memoryConfig = new MemoryConfiguration();
        BitService bitService = new BitService();
        ALU alu = new ALU(processorRegisters,bitService);
        Computer computer = new Computer(alu,generalRegisters,memory,processorRegisters,bitService,memoryConfig);

        
        // Example: set the path you need
        memoryConfig.path = "./testBytes.txt";
        
        
        
        
        Method privateMethod = Computer.class.getDeclaredMethod("loadBytesToMemoryFromFile", String.class);
        privateMethod.setAccessible(true);
        System.out.println(memoryConfig.path);
        privateMethod.invoke(computer, memoryConfig.path);

        byte[] memoryContent = memory.getMemory();

        byte[] expectedContent = new byte[] {
            (byte) 0xE0, (byte) 0x00, (byte) 0x22, (byte) 0x2F,
            (byte) 0xE1, (byte) 0x00, (byte) 0x0A, (byte) 0x2F,
            (byte) 0x00, (byte) 0x21, (byte) 0x00, (byte) 0x21
        };
        assertArrayEquals(expectedContent, Arrays.copyOf(memoryContent, expectedContent.length));
   }

    
}
