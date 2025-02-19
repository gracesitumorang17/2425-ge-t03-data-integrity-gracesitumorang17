package academic.driver;

import academic.model.Course;
import academic.model.Enrollment;
import academic.model.Student;
import java.util.*;

/**
 * @author 12S23008 Ranty Insen Pakpahan
 * @author 12S23048 Grace Caldera Situmorang
 */

public class Driver2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Enrollment> enrollments = new ArrayList<>();
        Set<String> invalidStudents = new HashSet<>();
        Set<String> invalidCourses = new HashSet<>();
        Map<String, Set<String>> enrollmentMap = new HashMap<>();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("---")) {
                break;
            }

            String[] parts = input.split("#");
            if (parts.length > 1) {
                String command = parts[0];

                switch (command) {
                    case "course-add":
                        if (parts.length == 5) {
                            String code = parts[1];
                            String courseName = parts[2];
                            int kredit = Integer.parseInt(parts[3]);
                            String grade = parts[4];

                            if (!isCourseExist(courses, code)) {
                                courses.add(new Course(code, courseName, kredit, grade));
                            }
                        }
                        break;

                    case "student-add":
                        if (parts.length == 5) {
                            String nim = parts[1];
                            String nama = parts[2];
                            int tahun = Integer.parseInt(parts[3]);
                            String jurusan = parts[4];

                            if (!isStudentExist(students, nim)) {
                                students.add(new Student(nim, nama, tahun, jurusan));
                            }
                        }
                        break;

                    case "enrollment-add":
                        if (parts.length == 5 || parts.length == 6) {
                            String kodeMatkul = parts[1];
                            String nim = parts[2];
                            String tahunAjaran = parts[3];
                            String semester = parts[4];
                            String status = (parts.length == 6) ? parts[5] : "None";

                            boolean validCourse = isCourseExist(courses, kodeMatkul);
                            boolean validStudent = isStudentExist(students, nim);

                            if (!validCourse) {
                                invalidCourses.add(kodeMatkul);
                            }
                            if (!validStudent) {
                                invalidStudents.add(nim);
                            }

                            if (validCourse && validStudent) {
                                enrollmentMap.putIfAbsent(kodeMatkul, new HashSet<>());
                                if (enrollmentMap.get(kodeMatkul).add(nim)) {
                                    enrollments.add(new Enrollment(kodeMatkul, nim, tahunAjaran, semester, status));
                                }
                            }
                        }
                        break;
                }
            }
        }

        // Output invalid students and courses
        for (String invalidStudent : invalidStudents) {
            System.out.println("invalid student|" + invalidStudent);
        }

        for (String invalidCourse : invalidCourses) {
            System.out.println("invalid course|" + invalidCourse);
        }

        // Sort and output courses
        courses.sort(Comparator.comparing(Course::getCode));
        for (Course c : courses) {
            System.out.println(c.getCode() + "|" + c.getCourseName() + "|" + c.getKredit() + "|" + c.getGrade());
        }

        // Sort and output students (reverse order by name)
        students.sort(Comparator.comparing(Student::getNama).reversed());
        for (Student s : students) {
            System.out.println(s.getNim() + "|" + s.getNama() + "|" + s.getTahun() + "|" + s.getJurusan());
        }

        // Sort and output enrollments (descending order by kodeMatkul, then nim, then tahunAjaran, then semester)
        enrollments.sort(Comparator.comparing(Enrollment::getKodeMatkul).reversed()
                .thenComparing(Enrollment::getNim, Comparator.reverseOrder())
                .thenComparing(Enrollment::getTahunAjaran)
                .thenComparing(Enrollment::getSemester));

        for (Enrollment e : enrollments) {
            System.out.println(e.getKodeMatkul() + "|" + e.getNim() + "|" + e.getTahunAjaran() + "|" + e.getSemester() + "|" + e.getStatus());
        }

        scanner.close();
    }

    // Helper function to check if a course exists
    private static boolean isCourseExist(List<Course> courses, String code) {
        return courses.stream().anyMatch(course -> course.getCode().equals(code));
    }

    // Helper function to check if a student exists
    private static boolean isStudentExist(List<Student> students, String nim) {
        return students.stream().anyMatch(student -> student.getNim().equals(nim));
    }
}
