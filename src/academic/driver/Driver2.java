package academic.driver;

import academic.model.Course;
import academic.model.Enrollment;
import academic.model.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @autor 12S23008 Ranty Insen Pakpahan
 * @autor 12S23048 Grace Caldera Situmorang
 */
public class Driver2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Enrollment> enrollments = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("---")) {
                break; // Keluar dari loop jika input adalah ---
            }

            // Memisahkan input berdasarkan simbol #
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

                            // Cek apakah course sudah ada
                            boolean exists = false;
                            for (int i = 0; i < courses.size(); i++) {
                                if (courses.get(i).getCode().equals(code)) {
                                    exists = true;
                                    break;
                                }
                            }

                            if (!exists) {
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

                            // Cek apakah student sudah ada
                            boolean exists = false;
                            for (int i = 0; i < students.size(); i++) {
                                if (students.get(i).getNim().equals(nim)) {
                                    exists = true;
                                    break;
                                }
                            }

                            if (!exists) {
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

                            // Validasi apakah kodeMatkul dan nim ada
                            boolean studentExists = students.stream().anyMatch(student -> student.getNim().equals(nim));
                            boolean courseExists = courses.stream().anyMatch(course -> course.getCode().equals(kodeMatkul));

                            // Output jika student atau course tidak ada
                            if (!studentExists) {
                                System.out.println("invalid student|" + nim);
                            }
                            if (!courseExists) {
                                System.out.println("invalid course|" + kodeMatkul);
                            }

                            // Hanya jika keduanya valid, maka enrollment ditambahkan
                            if (studentExists && courseExists) {
                                enrollments.add(new Enrollment(nim, kodeMatkul, tahunAjaran, semester, status));
                            }
                        }
                        break;
                }
            }
        }

        // Mengurutkan dan menampilkan semua courses yang tersimpan
        Collections.sort(courses, Comparator.comparing(Course::getCode));
        for (Course course : courses) {
            System.out.println(course.getCode() + "|" + course.getCourseName() + "|" + course.getKredit() + "|" + course.getGrade());
        }

        // Mengurutkan dan menampilkan semua students yang tersimpan
        Collections.sort(students, Comparator.comparing(Student::getNama).reversed());
        for (Student student : students) {
            System.out.println(student.getNim() + "|" + student.getNama() + "|" + student.getTahun() + "|" + student.getJurusan());
        }

        // Mengurutkan dan menampilkan semua enrollments yang tersimpan
        Collections.sort(enrollments, Comparator.comparing(Enrollment::getKodeMatkul).reversed()
                .thenComparing(Enrollment::getNim)
                .thenComparing(Enrollment::getTahunAjaran)
                .thenComparing(Enrollment::getSemester));
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment.getNim() + "|" + enrollment.getKodeMatkul() + "|" + enrollment.getTahunAjaran() + "|" + enrollment.getSemester() + "|" + enrollment.getStatus());
        }

        scanner.close();
    }
}
