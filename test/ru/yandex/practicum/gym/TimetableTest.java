package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    Comparator<TrainingSession> tsComparator;
    Comparator<CounterOfTrainings> ctComparator;

    @BeforeEach
    void beforeEach(){
         tsComparator = new Comparator<>() {
            @Override
            public int compare(TrainingSession ts1, TrainingSession ts2) {
                return ts1.getTimeOfDay().compareTo(ts2.getTimeOfDay());
            }
        };

        ctComparator = new Comparator<>() {
            @Override
            public int compare(CounterOfTrainings ct1, CounterOfTrainings ct2) {
                return -ct1.getCount().compareTo(ct2.getCount());
            }
        };

    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1,timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0,timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1,timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        //Assertions.assertEquals(2,timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        TreeSet<TrainingSession> testSet = new TreeSet<>(tsComparator);
        testSet.add(thursdayAdultTrainingSession);
        testSet.add(thursdayChildTrainingSession);
        Assertions.assertIterableEquals(testSet,timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY));

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0,timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());



    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(13,0)).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(0,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(14,0)).size());
    }

    @Test
    void testEmptyCounter(){
        //Создадим пустое расписание
        Timetable timetable = new Timetable();

        //Проверим, что getCountByCoaches адекватно сработает в таком случае и вернёт список с 0 значений
        Assertions.assertEquals(0,timetable.getCountByCoaches().size());
    }

    @Test
    void testOneCoachCounter(){
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        //Проверим, что в списке счетчиков создался 1 счетчик
        Assertions.assertEquals(1,timetable.getCountByCoaches().size());
        //Проверим, что при добавлении 4 тренировок для тренера, в счетчике их тоже 4
        Assertions.assertEquals(4,timetable.getCountByCoaches().getFirst().getCount());


    }

    @Test
    void testThreeCoachCounter(){
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Coach coach_1 = new Coach("Петрова", "Анна", "Васильевна");
        TrainingSession mondayChildTrainingSession_1 = new TrainingSession(groupChild, coach_1,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession thursdayChildTrainingSession_1 = new TrainingSession(groupChild, coach_1,
                DayOfWeek.THURSDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession_1);
        timetable.addNewTrainingSession(thursdayChildTrainingSession_1);

        Coach coach_2 = new Coach("Иванов", "Виктор", "Петрович");
        TrainingSession saturdayChildTrainingSession_2 = new TrainingSession(groupChild, coach_2,
                DayOfWeek.SATURDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(saturdayChildTrainingSession_2);

        TreeSet<CounterOfTrainings> testSet = new TreeSet<>(ctComparator);
        testSet.add(new CounterOfTrainings(coach,4));
        testSet.add(new CounterOfTrainings(coach_1,2));
        testSet.add(new CounterOfTrainings(coach_2,1));
        Assertions.assertIterableEquals(testSet,timetable.getCountByCoaches());

    }

}
