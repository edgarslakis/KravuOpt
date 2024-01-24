package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@PlanningEntity
@Getter @Setter @NoArgsConstructor
public class Pack {

    private Integer x;
    private Integer y;

    @PlanningVariable
    private Vehicle vehicle;

//    @PlanningVariable
//    public Vehicle getVehicle() {
//        return vehicle;
//    }
//
//    public void setVehicle(Vehicle vehicle) {
//        vehicle = vehicle;
//    }
}
