package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Plānošanas entīte - klase plānošanas laikā mainīsies
@PlanningEntity
@Getter @Setter @NoArgsConstructor
public class Pack {

    private Integer id;
    private Integer x;
    private Integer y;
    private Integer size;

    public Integer getSize() {
        Integer size = 0;
        size = (this.getX()) * (this.getY());
        return size;
    }

    @PlanningVariable
    private Vehicle vehicle;

    @Override
    public String toString() {
        return this.getX().toString();
    }
}
