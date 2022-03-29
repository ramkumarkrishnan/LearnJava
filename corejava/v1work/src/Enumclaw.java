import java.util.Scanner;
import static java.lang.System.out;

public class Enumclaw {
    public static void main(String[] args) {
        // demo the use of the enum class defined below
        Size s = Enum.valueOf(Size.class, "S");
        out.println("This size is: " + s.getExpansion());
        Size[] sizes = Size.values();
        out.println("Sizes " + sizes.toString());

        // more demo of the enum class
        var in = new Scanner(System.in);
        out.println("Enter T-shirt size (S, M, L, XL):");
        String inSize = in.next().toUpperCase();

        Size ss = Enum.valueOf(Size.class, inSize);
        out.println("You chose " + ss.getExpansion() + " T-shirt");
        if (ss == Size.XL)
            out.println("Sorry, we ran out of " + ss.getExpansion() + " T-shirts");
        else
            out.println("Here is your size " + ss.getExpansion() + " T-shirt");
    }

    public enum Size {
        S("Small"), M("Medium"), L("Large"), XL("Xtra-Large");

        private String expansion;

        Size(String expansion) {
            this.expansion = expansion;
        }

        public String getExpansion() {
            return expansion;
        }
    }
}
