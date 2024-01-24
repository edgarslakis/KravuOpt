package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.lu.df.combopt.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

// Katram jaunam risinājumam tiek sākta jauna šīs klases instance
@PlanningSolution
@Getter @Setter @NoArgsConstructor
public class BinPackingSolution {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinPackingSolution.class);

    private String solutionId;

    @PlanningScore
    private HardSoftScore score;

    // Saraksts ar visām paciņām. Timefold ņem paciņas no šejienes (collection) un piekārto kādai mašīnai.
    @PlanningEntityCollectionProperty
    private List<Pack> packList = new ArrayList<>();

    // Saraksts ar visām mašīnām. Mašīnu nevar mainīt/pārvietot, tāpēc tāS ir Problem FACT.
    @ProblemFactCollectionProperty
    @ValueRangeProvider()
    private List<Vehicle> vechicleList = new ArrayList<>();

    public void print() {
        this.getVechicleList().forEach(vehicle -> {
            LOGGER.info(vehicle.getRegNr() + "(" + vehicle.getLength() + ")");
            vehicle.getContent().forEach(pack -> {
                LOGGER.info("   " + pack.getX() + " ");
            });
        });
    }

    public static BinPackingSolution generateData(){
        BinPackingSolution problem = new BinPackingSolution();
        problem.setSolutionId("P1");

        Vehicle v1 = new Vehicle();
        v1.setRegNr("AA0000");
        v1.setLength(20);

        Vehicle v2 = new Vehicle();
        v2.setRegNr("BB1111");
        v2.setLength(10);

        Pack p1 = new Pack();
        p1.setX(8);

        Pack p2 = new Pack();
        p2.setX(10);

        Pack p3 = new Pack();
        p3.setX(4);

        Pack p4 = new Pack();
        p4.setX(6);

        problem.getVechicleList().addAll(List.of(v1, v2));
        problem.getPackList().addAll(List.of(p1,p2,p3,p4));

        return problem;
    }
}
