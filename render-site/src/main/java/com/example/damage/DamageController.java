package com.example.damage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DamageController {

    @GetMapping("/api/damage")
    public int calculate(
            @RequestParam int level,
            @RequestParam int power,
            @RequestParam int attack,
            @RequestParam int defense,
            @RequestParam(defaultValue = "1.0") double effectiveness,
            @RequestParam(defaultValue = "1.0") double stab,
            @RequestParam(defaultValue = "false") boolean crit
    ) {
        boolean isSuperEffective = effectiveness > 1.0;
        boolean isNotVeryEffective = effectiveness < 1.0;

        // If stab is not 1.0, treat that as “STAB enabled”.
        boolean isSTAB = stab != 1.0;

        return DamageFormula.doMath(
                level, power, attack, defense,
                isSuperEffective, effectiveness,
                isNotVeryEffective, effectiveness,
                isSTAB, true, stab,
                crit
        );
    }
}