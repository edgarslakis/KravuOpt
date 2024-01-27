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
import java.util.Random;
import java.util.function.BinaryOperator;

// Katram jaunam risinājumam tiek sākta jauna šīs klases instance
@PlanningSolution
@Getter @Setter @NoArgsConstructor
public class BinPackingSolution {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinPackingSolution.class);

    private String solutionId;

    @PlanningScore
    private HardSoftScore score;

    // Saraksts ar visām paciņām (plānošanas entītēm). Timefold ņem paciņas no šejienes (collection) un piekārto kādai mašīnai.
    @PlanningEntityCollectionProperty // Te var atrast lietas, ko var pārvietot, mainīt.
    private List<Pack> packList = new ArrayList<>();

    // Saraksts ar visām mašīnām. Mašīnu nevar mainīt/pārvietot, tāpēc tās ir Problem FACT.
    @ProblemFactCollectionProperty //Fakti - lietas, kas risināšanas gaitā nemainās
    @ValueRangeProvider()   //SARAKSTS ar plānošanas vērtībām. te tās būs sakārtotas.
    private List<Vehicle> vechicleList = new ArrayList<>();

    public void print() {
        this.getVechicleList().forEach(vehicle -> {
            LOGGER.info(vehicle.getRegNr() + "(" + vehicle.getCapacity() + ")");
            for (Pack pack: this.getPackList()) {
                if (pack.getVehicle() == vehicle) {
                    LOGGER.info(pack.getId() + ". paciņa ar laukumu " + pack.getSize());}
            };
        });
    }
    private static int problemId = 0;
    private static Integer getProblemId() {problemId++; return problemId;}

    public static BinPackingSolution generateData(int scale) {
        BinPackingSolution problem = new BinPackingSolution();
        problem.setSolutionId(BinPackingSolution.getProblemId().toString());

        Random random = new Random();

        // vehicles: scale / 5 +1
        for (int i = 1; i <= scale / 5 + 1; i++) {
            Vehicle v1 = new Vehicle();
            v1.setRegNr("AA" + i);
            v1.setLength(40 + random.nextInt(25) - 5);
            v1.setWidth(10 + random.nextInt(20) - 3);
            v1.setCost(100);

            problem.getVechicleList().add(v1);
        }

        for (int i = 1; i <= scale; i++) {
            Pack p1 = new Pack();
            p1.setId(i);
            p1.setX(10 + random.nextInt(35) - 5);
            p1.setY(5 + random.nextInt(10) - 3);

            problem.getPackList().add(p1);
        }
        return problem;
    }

    public static BinPackingSolution generateData2(){
        BinPackingSolution problem = new BinPackingSolution();
        problem.setSolutionId("P1");

        Vehicle v1 = new Vehicle();
        v1.setRegNr("AA0000");
        v1.setLength(5);
        v1.setWidth(4);
        v1.setCost(1);

        Vehicle v2 = new Vehicle();
        v2.setRegNr("BB1111");
        v2.setLength(10);
        v2.setWidth(4);
        v2.setCost(1);

        Vehicle v3 = new Vehicle();
        v3.setRegNr("CC2222");
        v3.setLength(2);
        v3.setWidth(4);
        v3.setCost(1);

        Pack p1 = new Pack();
        p1.setId(1);
        p1.setX(8);
        p1.setY(1);

        Pack p2 = new Pack();
        p2.setId(2);
        p2.setX(10);
        p2.setY(2);

        Pack p3 = new Pack();
        p3.setId(3);
        p3.setX(4);
        p3.setY(2);

        Pack p4 = new Pack();
        p4.setId(4);
        p4.setX(6);
        p4.setY(2);

        Pack p5 = new Pack();
        p5.setId(5);
        p5.setX(4);
        p5.setY(2);

        Pack p6 = new Pack();
        p6.setId(6);
        p6.setX(1);
        p6.setY(10);

        Pack p7 = new Pack();
        p7.setId(7);
        p7.setX(2);
        p7.setY(1);

        Pack p8 = new Pack();
        p8.setId(8);
        p8.setX(8);
        p8.setY(1);

        Pack p9 = new Pack();
        p9.setId(9);
        p9.setX(4);
        p9.setY(1);

        problem.getVechicleList().addAll(List.of(v1, v2, v3));
        problem.getPackList().addAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8,p9));

        return problem;
    }
}
