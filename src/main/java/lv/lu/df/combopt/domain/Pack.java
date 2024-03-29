package lv.lu.df.combopt.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public Integer getSize() {
        Integer size = 0;
        size = (this.getX()) * (this.getY());
        return size;
    }

    @PlanningVariable
    @JsonIdentityReference() //pārvietoju uz Solution class
    private Vehicle vehicle;

    //Jāpārliecinās, lai paciņas x, y izmēri nepārsniedz length, width
    @JsonIgnore
    public Boolean isGoodsConstraintBroken() {
        if (vehicle.getLength() < this.getX()) return true;
        else return vehicle.getWidth() < this.getY();
    }

    //Ja paciņas laukums pārsniedz vehicle Capacity, tā netiek piekārtota mašīnai. t.i. vehicle = null
    @JsonIgnore
    public Boolean oversized() {
        if (vehicle.getCapacity() < this.getSize()) {
            //this.setVehicle(null);
            return true;
        }
        else return false;
    }

    @Override
    @JsonIgnore
    public String toString() {
        return this.getX().toString();
    }
}
