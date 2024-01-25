package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
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

    public Double getCapacity() {
        Double capacity = 0.0;
        capacity = (double) ((this.getLength()) * (this.getWidth()));
        return capacity;
    }
    //Jāpārliecinās, lai kravas x, y dimensija nepārsniedz length, width
    // Jāpārliecinās, lai kopējā krava nepārsniedz 10
    // Jāpārliecinās, lai visas paciņas ir iekrautas

    @PlanningListVariable
    private List<Pack> content = new ArrayList<>();

//    @InverseRelationShadowVariable(sourceVariableName = "vehicles")
//    private Vehicle vehicle;
//
//    @NextElementShadowVariable(sourceVariableName = "vehicles")
//    private Vehicle next;
//
//    @PreviousElementShadowVariable(sourceVariableName = "vehicles")
//    private Vehicle prev;



}
