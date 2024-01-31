package lv.lu.df.combopt.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.calculator.EasyScoreCalculator;
import lv.lu.df.combopt.domain.BinPackingSolution;
import lv.lu.df.combopt.domain.Pack;
import lv.lu.df.combopt.domain.Vehicle;
// Iet cauri katrai mašīnai, katrai paciņai un sasummē.
// Šī rēķina metode ir lēna, jo netiek izmantota inkrementāla aprēķināšana. Constraint streams būs ātrāks.
public class ScoreCalculator implements EasyScoreCalculator<BinPackingSolution, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(BinPackingSolution packingSolution) {
        int hardScore = 0;
        int softScore = 0;
        for (Vehicle vehicle : packingSolution.getVehicleList()) {
            int xUsage = 0;
            boolean used = false;

            for (Pack pack : packingSolution.getPackList()) {
                if (vehicle.equals(pack.getVehicle())) {
                    xUsage += pack.getX();
                    used = true;
                }
            }

            // Hard constraints
            int spaceAvailable = vehicle.getLength() - xUsage;
            if (spaceAvailable < 0) {
                hardScore += spaceAvailable;
            }

            // Soft constraints
            if (used) {
                softScore -= vehicle.getCost();
            }
        }
        // TimeFold veic maksimizācijas uzdevumu nevis minimizāciju. Tāpēc score jāņem ar negatīvu zīmi.
        return HardSoftScore.of(- hardScore, - softScore);
    }
}
