package streams;

import java.util.*;
import java.util.stream.Collectors;


class Scratch {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("John", "Smith", "Miami", 8.38, 19, "Civil"),
                new Student("Mike", "Miles", "New York", 8.4, 21, "IT"),
                new Student("Michael", "Peterson", "New York", 7.5, 20, "Civil"),
                new Student("James", "Robertson", "Miami", 9.1, 20, "IT"),
                new Student("John", "Miller", "Miami", 7.83, 20, "Civil")
        );

        // Find all Students from Miami with a grade > 8.0
        students.stream().filter(s -> s.getCity().equals("Miami"))
                .filter(s -> s.getGrade() > 8.0)
                .forEach(System.out::println);

        // Find the student with the highest grade
        students.stream()
                .max(Comparator.comparing(Student::getGrade))
                .ifPresentOrElse(System.out::println, () -> {});


        // Count the number of students in each department
        students.stream()
                .collect(Collectors.groupingBy(Student::getDeparment))
                .forEach((s, students1) -> System.out.println(s + ": " + students1.size()));

        // Find the average rating of students in each department
        students.stream()
                .collect(Collectors.groupingBy(Student::getDeparment,
                        Collectors.averagingDouble(Student::getGrade)))
                .forEach((s, aDouble) -> System.out.println(s + ": " + aDouble));

        // Sort students by age and then by grade
        students.stream()
                .sorted(Comparator.comparing(Student::getAge).thenComparing(Student::getGrade))
                .forEach(System.out::println);

        // Get a comma separated list of student names
        System.out.println(students.stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", ")));

        // Check if all the students are above 18
        System.out.println(students.stream()
                .map(Student::getAge)
                .allMatch(age -> age > 18));

        // find the department with the most students
        students.stream()
                .collect(Collectors.groupingBy(Student::getDeparment, Collectors.counting()))
                .entrySet()
                .stream()
                .max((Comparator.comparing(Map.Entry::getValue)))
                .ifPresentOrElse(System.out::println, () -> {});

        // Partition students into grade > 8.0 and grade <= 8.0
        Map<Boolean, List<Student>> partitionedStudents = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getGrade() > 8.0, Collectors.toList()));
        System.out.println(partitionedStudents);

        // Find the student with the longest full name
        students.stream()
                .max(Comparator.comparing(s -> (s.getFirstName() + " " + s.getLastName()).length()))
                .ifPresentOrElse(System.out::println, () -> {});

        // Find the second-highest grade in all students
        Optional<Student> secondHighestGrade = students.stream()
                .sorted(Comparator.comparing(Student::getGrade).reversed())
                .limit(2)
                .skip(1)
                .findFirst();
        System.out.println("Second Highest graded candidate" + secondHighestGrade);

        // Count the no of students from each city
        students.stream()
                .collect(Collectors.groupingBy(Student::getCity, Collectors.counting()))
                .forEach((s, aLong) -> System.out.println(s + " " + aLong));

        // Find the student with longest lastname
        students.stream()
                .max(Comparator.comparing(student -> student.getLastName().length()))
                //.max((o1, o2) -> o1.getLastName().length() - o2.getLastName().length())
                .ifPresent(student -> System.out.println("Longest Last Name " + student));
    }

}


class Student {
    private String firstName;
    private String lastName;
    private String city;
    private double grade;
    private int age;
    private String deparment;


    public Student(String firstName, String lastName, String city, double grade, int age, String deparment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.grade = grade;
        this.age = age;
        this.deparment = deparment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDeparment() {
        return deparment;
    }

    public void setDeparment(String deparment) {
        this.deparment = deparment;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", grade=" + grade +
                ", age=" + age +
                ", deparment='" + deparment + '\'' +
                '}';
    }
}