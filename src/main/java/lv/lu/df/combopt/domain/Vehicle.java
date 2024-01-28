package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@PlanningEntity - vai šeit vajag planning entity? Izskatās, ka ne.
@Getter @Setter @NoArgsConstructor
@JsonIdentityInfo(scope = Vehicle.class, property = "regNr", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Vehicle {

    private String regNr;
    private Integer length;
    private Integer width;
    private Integer cost;
    private Integer capacity;

    @JsonIgnore
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
