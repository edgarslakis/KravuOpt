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
    private Integer cost;
    private Integer capacity;

    public Integer getCapacity() {
        Integer capacity = 0;
        capacity = (this.getLength()) * (this.getWidth());
        return capacity;
    }

    @Override
    public String toString() {
        return this.getRegNr();
    }
}
