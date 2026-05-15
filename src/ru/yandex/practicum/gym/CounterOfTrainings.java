package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private Coach coach;

    private Integer count;

    public CounterOfTrainings(Coach coach, Integer count) {
        this.coach = coach;
        this.count = count;
    }

    public CounterOfTrainings(Coach coach) {
        this.coach = coach;
        this.count = 0;
    }

    public Coach getCoach() {
        return coach;
    }

    public Integer getCount() {
        return count;
    }

    public void incCount() {
        this.count++;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return -this.count.compareTo(o.count);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) object;
        return Objects.equals(coach, that.coach) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }
}
