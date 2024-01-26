package lv.lu.df.combopt.solver;
import lv.lu.df.combopt.domain.Pack;
import lv.lu.df.combopt.domain.Vehicle;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

import java.util.function.Function;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.*;





public class StreamCalculator implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return  new Constraint[]{
                everyPack(constraintFactory),
                requiredSpace(constraintFactory),
                vehicleCost(constraintFactory),
                packTooLarge(constraintFactory),
                packOversize(constraintFactory)
        };
    }

    public Constraint everyPack(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .penalize(HardSoftScore.ONE_SOFT, pack -> 1)
                .asConstraint("everyPack");
    }
    // Jāpārliecinās, lai kopējā paciņu izmēru summa nepārsniedz Vehicle capacity
    public Constraint requiredSpace(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .groupBy(Pack::getVehicle, sum(Pack::getSize))
                .filter((vehicle, size) -> size > vehicle.getCapacity())
                .penalize(HardSoftScore.ONE_HARD,(vehicle, size) -> size - vehicle.getCapacity())
                .asConstraint("requiredSpace");
    }
    // Katras jaunas mašīnas izmantošanai ir maksa cost
    public Constraint vehicleCost(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Vehicle.class)
                .ifExists(Pack.class, equal(Function.identity(), Pack::getVehicle))
                .penalize(HardSoftScore.ONE_SOFT, Vehicle::getCost)
                .asConstraint("vehicleCost");
    }
    // 4x1 kasti navar ietūcīt 3x3 mašīnā. if True -> ONE_HARD
    // Vispirms pārbaudām, vai kāda no x,y dimensijām nesaiet.
    public Constraint packTooLarge(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .filter(Pack::isGoodsConstraintBroken)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("packTooLarge");
    }
    // Pārbauda paciņas laukumu
    public Constraint packOversized(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .filter(pack -> pack.getVehicle() == null)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("packLeftOut");
    }
    public Constraint packOversize(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .filter(Pack::oversized)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("packLeftOut");
    }
}
