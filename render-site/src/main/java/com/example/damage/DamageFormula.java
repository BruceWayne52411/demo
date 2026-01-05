package com.example.damage;

public class DamageFormula {

    // Default STAB multiplier
    public static final double DEFAULT_STAB = 1.5;

    // Core base multiplier pieces (kept consistent everywhere)
    private static double core(int level, int power, int attack, int defense) {
        // Use doubles to avoid integer-division truncation
        return level * (power / 10.0) * ((double) attack / Math.max(defense, 1));
    }

    // --- Formula helpers (these mirror your girlfriend's naming) ---

    public static int formulaSESTABNC(int level, int power, int attack, int defense,
                                     double superEffectiveLevel, double stabLevel) {
        return asInt(core(level, power, attack, defense) * stabLevel * superEffectiveLevel);
    }

    public static int formulaSESTABCrit(int level, int power, int attack, int defense,
                                        double superEffectiveLevel, double stabLevel) {
        return asInt(core(level, power, attack, defense) * stabLevel * superEffectiveLevel * 2.0);
    }

    public static int formulaSENC(int level, int power, int attack, int defense,
                                 double superEffectiveLevel) {
        return asInt(core(level, power, attack, defense) * superEffectiveLevel);
    }

    public static int formulaSECrit(int level, int power, int attack, int defense,
                                   double superEffectiveLevel) {
        return asInt(core(level, power, attack, defense) * superEffectiveLevel * 2.0);
    }

    public static int formulaNVESTABNC(int level, int power, int attack, int defense,
                                       double notVeryEffectiveLevel, double stabLevel) {
        return asInt(core(level, power, attack, defense) * stabLevel * notVeryEffectiveLevel);
    }

    public static int formulaNVESTABCrit(int level, int power, int attack, int defense,
                                         double notVeryEffectiveLevel, double stabLevel) {
        return asInt(core(level, power, attack, defense) * stabLevel * notVeryEffectiveLevel * 2.0);
    }

    public static int formulaNVENC(int level, int power, int attack, int defense,
                                   double notVeryEffectiveLevel) {
        return asInt(core(level, power, attack, defense) * notVeryEffectiveLevel);
    }

    public static int formulaNVECrit(int level, int power, int attack, int defense,
                                     double notVeryEffectiveLevel) {
        return asInt(core(level, power, attack, defense) * notVeryEffectiveLevel * 2.0);
    }

    public static int formulaSTABNC(int level, int power, int attack, int defense,
                                    double stabLevel) {
        return asInt(core(level, power, attack, defense) * stabLevel);
    }

    public static int formulaSTABCrit(int level, int power, int attack, int defense,
                                      double stabLevel) {
        return asInt(core(level, power, attack, defense) * stabLevel * 2.0);
    }

    public static int formulaBase(int level, int power, int attack, int defense) {
        return asInt(core(level, power, attack, defense));
    }

    public static int formulaBaseCrit(int level, int power, int attack, int defense) {
        return asInt(core(level, power, attack, defense) * 2.0);
    }

    // --- Main selection method (this replaces all the nested ifs cleanly) ---

    public static int doMath(
            int level,
            int power,
            int attack,
            int defense,
            boolean isSuperEffective,
            double superEffectiveLevel,
            boolean isNotVeryEffective,
            double notVeryEffectiveLevel,
            boolean isSTAB,
            boolean customSTAB,
            double customSTABLevel,
            boolean isCrit
    ) {
        // You can't be both super-effective and not-very-effective in this model.
        // If that happens, return 0 (or you can throw an exception instead).
        if (isSuperEffective && isNotVeryEffective) return 0;

        double stabLevel = isSTAB
                ? (customSTAB ? customSTABLevel : DEFAULT_STAB)
                : 1.0;

        double effectiveness = isSuperEffective
                ? superEffectiveLevel
                : (isNotVeryEffective ? notVeryEffectiveLevel : 1.0);

        double crit = isCrit ? 2.0 : 1.0;

        double result = core(level, power, attack, defense) * stabLevel * effectiveness * crit;
        return asInt(result);
    }

    private static int asInt(double value) {
        // Choose your rounding behavior:
        // floor is common for damage-like calculations.
        return (int) Math.floor(value);
    }
}
