import com.ramkrish.corejava.Person;
import com.ramkrish.corejava.Student;

/*
 * Implement the abstract class
 */
public class PersonTest {
    public static void main(String[] args) {
        Student stud = new Student("Alexander", "Conquest");
        System.out.println(stud.getDescription());
    }
}
