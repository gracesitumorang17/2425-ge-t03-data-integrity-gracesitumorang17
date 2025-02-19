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
        Map<String, Set<String>> enrollmentMap = new HashMap<>(); // Map to track unique course-enrollment combinations

        // Simpan input terlebih dahulu, baru lakukan pengecekan dan pemrosesan
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

                            // Cek apakah course sudah ada
                            boolean exists = false;
                            for (Course course : courses) {
                                if (course.getCode().equals(code)) {
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
                            for (Student student : students) {
                                if (student.getNim().equals(nim)) {
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

                            // Add to enrollments if valid course and student
                            boolean validCourse = false;
                            boolean validStudent = false;

                            // Check if course exists
                            for (Course course : courses) {
                                if (course.getCode().equals(kodeMatkul)) {
                                    validCourse = true;
                                    break;
                                }
                            }

                            // Check if student exists
                            for (Student student : students) {
                                if (student.getNim().equals(nim)) {
                                    validStudent = true;
                                    break;
                                }
                            }

                            if (!validCourse) {
                                invalidCourses.add(kodeMatkul);
                            }

                            if (!validStudent) {
                                invalidStudents.add(nim);
                            }

                            if (validCourse && validStudent) {
                                // To avoid duplicate enrollments for the same student and course
                                if (!enrollmentMap.containsKey(kodeMatkul)) {
                                    enrollmentMap.put(kodeMatkul, new HashSet<>());
                                }
                                if (!enrollmentMap.get(kodeMatkul).contains(nim)) {
                                    enrollmentMap.get(kodeMatkul).add(nim);
                                    enrollments.add(new Enrollment(kodeMatkul, nim, tahunAjaran, semester, status));
                                }
                            }
                        }
                        break;
                }
            }
        }

        // Validasi dan cetak hasil setelah semua input diproses
        // Cek apakah setiap enrollment memiliki course dan student yang valid
        for (String invalidStudent : invalidStudents) {
            System.out.println("invalid student|" + invalidStudent);
        }

        for (String invalidCourse : invalidCourses) {
            System.out.println("invalid course|" + invalidCourse);
        }

        // Sort Course by code (urutan berdasarkan kode mata kuliah)
        courses.sort(Comparator.comparing(Course::getCode));
        for (Course c : courses) {
            System.out.println(c.getCode() + "|" + c.getCourseName() + "|" + c.getKredit() + "|" + c.getGrade());
        }

        // Sort Student by name in reverse (urutan berdasarkan nama mahasiswa dari abjad terbesar)
        students.sort(Comparator.comparing(Student::getNama).reversed());
        for (Student s : students) {
            System.out.println(s.getNim() + "|" + s.getNama() + "|" + s.getTahun() + "|" + s.getJurusan());
        }

        // Sort Enrollment by kodeMatkul in reverse (urutan berdasarkan kode mata kuliah dari terbesar ke terkecil)
        enrollments.sort(Comparator.comparing(Enrollment::getKodeMatkul).reversed()
                .thenComparing(Enrollment::getNim, Comparator.reverseOrder())
                .thenComparing(Enrollment::getTahunAjaran)
                .thenComparing(Enrollment::getSemester));

        // Output Enrollment with filtering for duplicates
        Set<String> displayedEnrollments = new HashSet<>();
        for (Enrollment e : enrollments) {
            String enrollmentKey = e.getKodeMatkul() + "|" + e.getNim();
            if (!displayedEnrollments.contains(enrollmentKey)) {
                System.out.println(e.getKodeMatkul() + "|" + e.getNim() + "|" + e.getTahunAjaran() + "|" + e.getSemester() + "|" + e.getStatus());
                displayedEnrollments.add(enrollmentKey);
            }
        }

        scanner.close();
    }
}
