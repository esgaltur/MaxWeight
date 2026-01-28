package cz.esgaltur.maxweight.web.dto;

import cz.esgaltur.maxweight.web.validation.ValidWeek;
import cz.esgaltur.maxweight.web.validation.ValidWeekRange;
import cz.esgaltur.maxweight.web.validation.WeekRange;
import jakarta.validation.constraints.Min;

@ValidWeekRange
public class ProgramRangeRequest implements WeekRange {

    @ValidWeek
    private int fromWeek;

    @ValidWeek
    private int toWeek;

    @Min(1)
    private int maxWeight;

    public int getFromWeek() {
        return fromWeek;
    }

    public void setFromWeek(int fromWeek) {
        this.fromWeek = fromWeek;
    }

    public int getToWeek() {
        return toWeek;
    }

    public void setToWeek(int toWeek) {
        this.toWeek = toWeek;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
}
