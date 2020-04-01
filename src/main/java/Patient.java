import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Patient {
    private String name;
    private String surname;
    private long pesel;
}
