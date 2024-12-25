
import com.architektura.architektura_procesor.Services.BitService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BitService.class )
public class BitServiceTest {

    @Autowired
    private BitService bitService;
    @Test
    public void testGetAllBitsBetweenPositions() {
        // Arrange
        short value = (short) 0b0001000000001111; // Example value
        byte from = 0; // MSB-first
        byte to = 3;   // LSB-first

        // Act
        short result = bitService.getAllBitsBetweenPositions(value, from, to);

        // Assert
        assertEquals((short) 0b0001, result, "Expect 1");
       
    }
    
} 