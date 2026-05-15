package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek,TreeMap<TimeOfDay,TreeSet<TrainingSession>>>  timetable;
    private final HashMap<Coach,CounterOfTrainings> coachCounters;

    //Конструктор расписания, который сразу создаёт пустое расписание на неделю
    public Timetable() {
        this.timetable = new HashMap<>();
        this.coachCounters = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()){
            timetable.put(day,new TreeMap<TimeOfDay,TreeSet<TrainingSession>>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        //Получим время и день тренировки, чтобы не обращаться к геттерам и сеттреам
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        Coach coach = trainingSession.getCoach();
        TreeSet<TrainingSession> trainingSessions;
        //Получим ссылку на список занятий за день
        TreeMap<TimeOfDay,TreeSet<TrainingSession>> trainingsPerDay = timetable.get(day);
        //Если есть запись за время, получаем, список тренировок
        if (trainingsPerDay.containsKey(time)){
            trainingSessions = trainingsPerDay.get(time);
            //Добавили тренировку в список
            trainingSessions.add(trainingSession);
        } else {
            trainingSessions = new TreeSet<>();
            trainingSessions.add(trainingSession);
            trainingsPerDay.put(time,trainingSessions);
        }
        //Работа с счетчиком
        if (coachCounters.containsKey(coach)){
            coachCounters.get(coach).incCount();
        } else {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(coach);
            counterOfTrainings.incCount();
            coachCounters.put(coach,counterOfTrainings);
        }
    }

    public TreeSet<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay,TreeSet<TrainingSession>> trainsPerDay = timetable.get(dayOfWeek);
        TreeSet<TrainingSession> result = new TreeSet<>();
        for (TimeOfDay time : trainsPerDay.navigableKeySet()){
            result.addAll(trainsPerDay.get(time));
        }
        return  result;
    }

    public TreeSet<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay) == null ? new TreeSet<>() : timetable.get(dayOfWeek).get(timeOfDay);
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches(){
        TreeSet<CounterOfTrainings> result = new TreeSet<>();
        for (CounterOfTrainings counter : coachCounters.values()){
            result.add(counter);
        }
        return result;
    }
}
