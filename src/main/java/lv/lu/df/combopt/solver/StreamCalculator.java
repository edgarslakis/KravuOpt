package lv.lu.df.combopt.solver;
import lv.lu.df.combopt.domain.Pack;
import lv.lu.df.combopt.domain.Vehicle;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;

import java.util.function.Function;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.*;





public class StreamCalculator implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return  new Constraint[]{
                everyPack(constraintFactory),
                requiredSpace(constraintFactory),
                vehicleCost(constraintFactory)
        };
    }

    public Constraint everyPack(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .penalize(HardSoftScore.ONE_SOFT, pack -> 1)
                .asConstraint("everyPack");
    }

    public Constraint requiredSpace(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .groupBy(Pack::getVehicle, sum(Pack::getSize))
                .filter((vehicle, size) -> size > vehicle.getCapacity())
                .penalize(HardSoftScore.ONE_HARD,(vehicle, size) -> size - vehicle.getCapacity())
                .asConstraint("requiredSpace");
    }
    public Constraint vehicleCost(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Vehicle.class)
                .ifExists(Pack.class, equal(Function.identity(), Pack::getVehicle))
                .penalize(HardSoftScore.ONE_SOFT, Vehicle::getCost)
                .asConstraint("vehicleCost");
    }
}
