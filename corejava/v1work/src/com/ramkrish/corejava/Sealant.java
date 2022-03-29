package com.ramkrish.corejava;

/**
 * Demo of sealed class
 * Refer: https://www.labcwarranty.co.uk/blog/sealant-types-and-uses/
 */
public abstract sealed class Sealant
        permits SealLatex, SealAcrylic
        // SealButyl, SealPolysulfide, SealSilicone, SealPolyIsobutylene, SealPolyUrethane
          {
    private String name;
    private double fConsistency;
    private double fDurability;
    private double fHardness;
    private double fExposureResistance;
    private double fMovement;
    private double fModulus;
    private double fAdhesion;
    private double fStaining;
    private double fVocContent;
    private double fEaseOfApplication;
    private double fCost;

    public abstract String getAttributes();

    public Sealant(String name, double fConsistency, double fDurability, double fHardness, double fExposureResistance, double fMovement, double fModulus, double fAdhesion, double fStaining, double fVocContent, double fEaseOfApplication, double fCost) {
        this.name = name;
        this.fConsistency = fConsistency;
        this.fDurability = fDurability;
        this.fHardness = fHardness;
        this.fExposureResistance = fExposureResistance;
        this.fMovement = fMovement;
        this.fModulus = fModulus;
        this.fAdhesion = fAdhesion;
        this.fStaining = fStaining;
        this.fVocContent = fVocContent;
        this.fEaseOfApplication = fEaseOfApplication;
        this.fCost = fCost;
    }

    public String getName() {
        return this.name;
    }

    public double getConsistency ()  {
        return this.fConsistency;
    }

    public double getAdhesion() {
        return this.fAdhesion;
    }
}
