package lv.lu.df.combopt.solver;
import lv.lu.df.combopt.domain.Pack;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

public class StreamCalculator implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return  new Constraint[]{
                everyPack(constraintFactory)
        };
    }

    public Constraint everyPack(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Pack.class)
                .penalize(HardSoftScore.ONE_SOFT, pack -> 1)
                .asConstraint("everyPack");
    }
}
