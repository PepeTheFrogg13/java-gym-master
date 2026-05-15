package ru.yandex.practicum.gym;

import java.util.Objects;

public class TrainingSession implements Comparable<TrainingSession> {

    //группа
    private Group group;
    //тренер
    private Coach coach;
    //день недели
    private final DayOfWeek dayOfWeek;
    //время начала занятия
    private final TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public int compareTo(TrainingSession o) {
        return this.timeOfDay.compareTo(o.timeOfDay);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        TrainingSession ts = (TrainingSession) object;
        return Objects.equals(group, ts.group) && Objects.equals(coach, ts.coach) && dayOfWeek == ts.dayOfWeek && Objects.equals(timeOfDay, ts.timeOfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, coach, dayOfWeek, timeOfDay);
    }
}
