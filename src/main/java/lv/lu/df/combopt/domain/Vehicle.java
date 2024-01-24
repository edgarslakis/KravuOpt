package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@PlanningEntity
@Getter @Setter @NoArgsConstructor
public class Vehicle {

    private String regNr;
    private Integer length;
    private Integer width;
    private Integer cost = 10;

    //Jāpārliecinās, lai krava nenoslīd zem 0. t.i. -1
    // Jāpārliecinās, lai kopējā krava nepārsniedz 10
    // Jāpārliecinās, lai visas paciņas ir iekrautas

    @PlanningListVariable
    private List<Pack> content = new ArrayList<>();



}
