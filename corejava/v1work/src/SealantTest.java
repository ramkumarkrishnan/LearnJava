/**
 * Test for sealed class
 */
import com.ramkrish.corejava.SealAcrylic;
import com.ramkrish.corejava.SealLatex;

public class SealantTest {
    public static void main(String[] args) {
        System.out.println("Test sealed classes");
        SealLatex l = new SealLatex("Latex",0.0,0.1, 0.2, 0.3,
                0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);
        l.setWaterDensity(1.2);
        System.out.println("SealLatex Attributes: " + l.getAttributes());

        SealAcrylic a = new SealAcrylic("Acrylic", 0.1, 0.2, 0.3, 0.4,
                0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1);
        a.setViscosity(2.3);
        System.out.println("SealAcrylic Attributes: " + a.getAttributes());
    }

}

// SealAcrylic, SealButyl, SealPolysulfide, SealSilicone, SealPolyIsobutylene, SealPolyUrethane
