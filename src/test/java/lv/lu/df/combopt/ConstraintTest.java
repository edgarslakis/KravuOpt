package lv.lu.df.combopt;
import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;
import lv.lu.df.combopt.domain.Pack;
import lv.lu.df.combopt.domain.BinPackingSolution;
import lv.lu.df.combopt.domain.Vehicle;
import lv.lu.df.combopt.solver.StreamCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;
public class ConstraintTest {
    Vehicle VEHICLE = new Vehicle();
    Pack PACK1 = new Pack();
    Pack PACK2 = new Pack();
    public ConstraintTest() {
        VEHICLE.setLength(5);
        VEHICLE.setWidth(2);
        VEHICLE.setCost(10);

        PACK1.setId(1);
        PACK1.setX(5);
        PACK1.setY(1);
        PACK1.setVehicle(VEHICLE);

        PACK2.setId(2);
        PACK2.setX(5);
        PACK2.setY(3);
        PACK2.setVehicle(VEHICLE);


    }
    ConstraintVerifier<StreamCalculator, BinPackingSolution> constraintVerifier = ConstraintVerifier.build(
            new StreamCalculator(), BinPackingSolution.class, Pack.class);
    @Test
    void everyPackTest() {
        constraintVerifier.verifyThat(StreamCalculator::everyPack)
                .given(PACK1, PACK2)
                .penalizesBy(2);
    }
    @Test
    void spaceTest() {
        constraintVerifier.verifyThat(StreamCalculator::requiredSpace)
                .given(PACK1, PACK2, VEHICLE)
                .penalizesBy(10);
    }
    @Test
    void costTest() {
        constraintVerifier.verifyThat(StreamCalculator::vehicleCost)
                .given(PACK1, PACK2, VEHICLE)
                .penalizesBy(10);
    }
    @Test
    void tooLargeTest() {
        constraintVerifier.verifyThat(StreamCalculator::packTooLarge)
                .given(PACK2, VEHICLE)
                .penalizesBy(1);
    }
    @Test
    void oversized1() {
        constraintVerifier.verifyThat(StreamCalculator::packOversize)
                .given(PACK1, VEHICLE)
                .penalizesBy(0);
    }
    @Test
    void oversized2() {
        constraintVerifier.verifyThat(StreamCalculator::packOversize)
                .given(PACK2, VEHICLE)
                .penalizesBy(1);
    }
}
