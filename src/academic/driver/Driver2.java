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
        List<String> enrollmentRequests = new ArrayList<>();

        Set<String> displayedErrors = new LinkedHashSet<>(); // Menggunakan LinkedHashSet agar urutan error tetap

        
        // Tahap 1: Menyimpan semua input
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

                            if (courses.stream().noneMatch(c -> c.getCode().equals(code))) {
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

                            if (students.stream().noneMatch(s -> s.getNim().equals(nim))) {
                                students.add(new Student(nim, nama, tahun, jurusan));
                            }
                        }
                        break;

                    case "enrollment-add":
                        enrollmentRequests.add(input); // Simpan request untuk diproses nanti
                        break;
                }
            }
        }

        // Tahap 2: Memproses enrollment setelah semua data course dan student tersedia
        for (String request : enrollmentRequests) {
            String[] parts = request.split("#");

            if (parts.length == 5 || parts.length == 6) {
                String kodeMatkul = parts[1];
                String nim = parts[2];
                String tahunAjaran = parts[3];
                String semester = parts[4];
                String status = (parts.length == 6) ? parts[5] : "None";

                boolean courseExists = courses.stream().anyMatch(c -> c.getCode().equals(kodeMatkul));
                boolean studentExists = students.stream().anyMatch(s -> s.getNim().equals(nim));

                if (!studentExists) {
                    displayedErrors.add("invalid student|" + nim);
                }
                if (!courseExists) {
                    displayedErrors.add("invalid course|" + kodeMatkul);
                }
                if (courseExists && studentExists) {
                    enrollments.add(new Enrollment(kodeMatkul, nim, tahunAjaran, semester, status));
                }
            }
        }

        // Cetak error dalam urutan pertama kali ditemukan
        displayedErrors.forEach(System.out::println);

        // Tahap 3: Menampilkan hasil sesuai format
        courses.sort(Comparator.comparing(Course::getCode));
        courses.forEach(c -> System.out.println(c.getCode() + "|" + c.getCourseName() + "|" + c.getKredit() + "|" + c.getGrade()));

        students.sort(Comparator.comparing(Student::getNama)); // Urutkan berdasarkan nama
        students.forEach(s -> System.out.println(s.getNim() + "|" + s.getNama() + "|" + s.getTahun() + "|" + s.getJurusan()));

        enrollments.sort(Comparator.comparing(Enrollment::getKodeMatkul).reversed()
                .thenComparing(Enrollment::getNim, Comparator.reverseOrder())
                .thenComparing(Enrollment::getTahunAjaran)
                .thenComparing(Enrollment::getSemester));
        enrollments.forEach(e -> System.out.println(e.getNim() + "|" + e.getKodeMatkul() + "|" + e.getTahunAjaran() + "|" + e.getSemester() + "|" + e.getStatus()));

        scanner.close();
    }
}
