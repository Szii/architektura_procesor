
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
    public void testGetAllBitsFromPosition() {
        // Arrange
        short value = (short) 0b1111000000001111; // Example value
        byte position = 3; // MSB-first

        // Act
        short result = bitService.getAllBitsFromPosition(value, position);

        // Assert
        assertEquals((short) 0b1111000000000000, result, "Bits from position 3 to MSB should match.");

        // Additional test case
        position = 7;
        result = bitService.getAllBitsFromPosition(value, position);
        assertEquals((short) 0b1111000000000000, result, "Bits from position 7 to MSB should match.");
    }

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

    @Test
    public void testSetBitOnPosition() {
        // Arrange
        short value = (short) 0b0000000000000000; // All bits cleared
        byte position = 3; // MSB-first

        // Act
        bitService.setBitOnPosition(value, position);

        // Assert
        assertEquals((short) 0b0001000000000000, value, "Bit at position 3 should be set.");

        // Test setting another bit
        position = 7;
        bitService.setBitOnPosition(value, position);
        assertEquals((short) 0b0001000100000000, value, "Bits at positions 3 and 7 should be set.");
    }
}