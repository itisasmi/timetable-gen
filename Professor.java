import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Professor extends Timetable {
    private ArrayList<String> courses = new ArrayList<>();
    private Map<String, Integer> classesPerDay = new HashMap<>();

    void addCourses(String s) {
        this.courses.add(s);
    }

    boolean canTeach(String day) {
        int classesTaught = classesPerDay.getOrDefault(day, 0);
        return classesTaught < 3;
    }

    void incrementClassesTaught(String day) {
        int classesTaught = classesPerDay.getOrDefault(day, 0);
        classesPerDay.put(day, classesTaught + 1);
    }
}