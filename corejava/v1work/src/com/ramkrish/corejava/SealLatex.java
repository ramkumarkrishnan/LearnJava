/**
 * Demo subclass of a sealed class
 * Note: A subclass of a sealed class must specify whether it is sealed, final,
 * or open for subclassing. In the latter case, it must be declared as non-sealed
 */
package com.ramkrish.corejava;

public final class SealLatex extends Sealant {
    private double waterDensity;

    public SealLatex(String name, double fConsistency, double fDurability, double fHardness, double fExposureResistance, double fMovement, double fModulus, double fAdhesion, double fStaining, double fVocContent, double fEaseOfApplication, double fCost) {
        super(name, fConsistency, fDurability, fHardness, fExposureResistance, fMovement, fModulus, fAdhesion, fStaining, fVocContent, fEaseOfApplication, fCost);
    }

    @Override
    public String getAttributes() {
        return ("Adhesion: " + this.getAdhesion() + " Water Density: " + this.waterDensity);
    }

    public void setWaterDensity(double waterDensity) {
        this.waterDensity = waterDensity;
    }

    public double getWaterDensity() {
        return this.waterDensity;
    }
}
