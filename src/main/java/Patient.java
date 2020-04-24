import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Patient {
    private String name;
    private String surname;
    private Long pesel;
    private Corona corona;
    private double wallet;


}

