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

    // Saraksts ar visām paciņām (plānošanas entītēm). Timefold ņem paciņas no šejienes (collection) un piekārto kādai mašīnai.
    @PlanningEntityCollectionProperty // Te var atrast lietas, ko var pārvietot, mainīt.
    private List<Pack> packList = new ArrayList<>();

    // Saraksts ar visām mašīnām. Mašīnu nevar mainīt/pārvietot, tāpēc tās ir Problem FACT.
    @ProblemFactCollectionProperty //Fakti - lietas, kas risināšanas gaitā nemainās
    @ValueRangeProvider()   //SARAKSTS ar plānošanas vērtībām. te tās būs sakārtotas.
    private List<Vehicle> vechicleList = new ArrayList<>();

    public void print() {
        this.getVechicleList().forEach(vehicle -> {
            LOGGER.info(vehicle.getRegNr() + "(" + vehicle.getLength() + ")");
            for (Pack pack: this.getPackList()) {
                if (pack.getVehicle() == vehicle) {
                    LOGGER.info(pack.getId() + ". paciņa ar laukumu " + pack.getSize());}
            };
            System.out.println("---");
        });
    }
//    public void print() {
//        this.getPackList().forEach(pack -> {
//            LOGGER.info(pack.getSize() + " lieluma paciņa ir " + pack.getVehicle() + " mašīnā");
//        });
//    }

    public static BinPackingSolution generateData(){
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
        v2.setWidth(2);
        v2.setCost(1);

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
        p6.setX(4);
        p6.setY(2);

        problem.getVechicleList().addAll(List.of(v1, v2));
        problem.getPackList().addAll(List.of(p1,p2,p3,p4,p5,p6));

        return problem;
    }
}
