
import com.architektura.architektura_procesor.Components.Memory;
import com.architektura.architektura_procesor.Computer;
import com.architektura.architektura_procesor.Configuration.MemoryConfiguration;
import com.architektura.architektura_procesor.Main;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author brune
 */
@SpringBootTest(classes = Main.class)
public class MemoryTest {
    
    @SpyBean
    Computer computer;
    @Autowired
    Memory memory;
    @Autowired
    MemoryConfiguration memoryConfig;
  
     @Test
    public void memoryContainsRightContentAfterReadingFromFileTest() throws Exception {
        Method privateMethod = Computer.class.getDeclaredMethod("loadBytesToMemoryFromFile", String.class);
        privateMethod.setAccessible(true);
        System.out.println(memoryConfig.path);
        privateMethod.invoke(computer, memoryConfig.path);

        byte[] memoryContent = memory.getMemory();

        byte[] expectedContent = new byte[] {
            (byte) 0xE0, (byte) 0x00, (byte) 0x22, (byte) 0x2F,
            (byte) 0xE2, (byte) 0x00, (byte) 0x0A, (byte) 0x2F,
            (byte) 0x00, (byte) 0x21, (byte) 0x00, (byte) 0x21
        };
        assertArrayEquals(expectedContent, Arrays.copyOf(memoryContent, expectedContent.length));
   }

    
}
