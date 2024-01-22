import java.util.*;

public class Batch extends Timetable {
    static Random rand = new Random();
    static ArrayList<Integer> list = new ArrayList<Integer>();
    HashSet<String> assignedCourses = new HashSet<String>(); 

    boolean add(Course x, Professor y) {
        int i, j, k, m, n;

        list.clear();

        for (i = 0; i < 6; i++) {
            for (j = 0; j < 7; j++) {
                if (j != 3 && y.a[i][j] == null && this.a[i][j] == null) {
                    list.add(i * 10 + j);
                }
            }
        }

        boolean isTwoCredits = x.getCredits() == 2;

        if (assignedCourses.contains(x.getName() + y.getName())) {
            System.out.println("Course and Professor are already assigned to this batch.");
            return false;
        }

        if (isTwoCredits && list.size() >= 2) {
            for (k = 0; k < list.size() - 1; k++) {
                m = list.get(k) / 10; 
                n = list.get(k) % 10;

                if (list.get(k + 1) / 10 == m && list.get(k + 1) % 10 == n + 1) {
                    this.a[m][n] = x.getName() + "(" + y.getName() + ")";
                    y.a[m][n] = this.getName() + "(" + x.getName() + ")";
                    this.a[m][n + 1] = x.getName() + "(" + y.getName() + ")";
                    y.a[m][n + 1] = this.getName() + "(" + x.getName() + ")";

                    assignedCourses.add(x.getName() + y.getName());
                    
                    return true;
                }
            }
        } else if (!isTwoCredits && list.size() >= x.getCredits()) {
            for (i = 0; i < x.getCredits(); i++) {
                k = rand.nextInt(list.size());
                n = list.get(k) % 10;
                m = list.get(k) / 10;

                this.a[m][n] = x.getName() + "(" + y.getName() + ")";
                y.a[m][n] = this.getName() + "(" + x.getName() + ")";
                list.remove(k);
            }

            assignedCourses.add(x.getName() + y.getName());

            return true;
        }
        for (i = 0; i < 6; i++) {
            for (j = 0; j < 6; j++) { 
                if (this.a[i][j] != null && this.a[i][j + 1] != null) {
                    list.remove(Integer.valueOf(i * 10 + j));
                    list.remove(Integer.valueOf(i * 10 + (j + 1)));
                }
            }
        }


        list.clear();
        return false;
    }
}
