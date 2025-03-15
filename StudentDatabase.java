import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentDatabase {
    private static final String DATA_FILE = "student_data.txt"; 
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Admin login
        System.out.println("Welcome to the Student Database System");
        System.out.print("Enter Admin Username: ");
        String enteredUsername = scanner.next();

        System.out.print("Enter Admin Password: ");
        String enteredPassword = scanner.next();

        if (isAdminValid(enteredUsername, enteredPassword)) {
            System.out.println("Admin login successful. Access granted.");

            Map<Integer, Student> studentMap = loadStudentData();

            while (true) {
                System.out.println("-------------------------------------------");
		System.out.println("Student Database Menu:");
                System.out.println("1. Add a Student");
                System.out.println("2. Search for a Student");
                System.out.println("3. Display All Students");
                System.out.println("4. Delete a Student");
                System.out.println("5. Save & Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(studentMap, scanner);
                        break;

                    case 2:
                        searchStudent(studentMap, scanner);
                        break;

                    case 3:
                        displayAllStudents(studentMap);
                        break;

                    case 4:
                        deleteStudent(studentMap, scanner);
                        break;

                    case 5:
                        saveStudentData(studentMap);
                        System.out.println("Student data saved. Exiting Student Database. Goodbye!!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Invalid admin credentials. Exiting program.");
        }
    }

    private static boolean isAdminValid(String enteredUsername, String enteredPassword) {
        return ADMIN_USERNAME.equals(enteredUsername) && ADMIN_PASSWORD.equals(enteredPassword);
    }

    private static void addStudent(Map<Integer, Student> studentMap, Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.next();
        System.out.print("Enter CGPA: ");
        double cgpa = scanner.nextDouble();

        Student student = new Student(studentId, firstName, lastName, rollNumber, cgpa);
        studentMap.put(studentId, student);
        System.out.println("Student added successfully.");
    }

    private static void searchStudent(Map<Integer, Student> studentMap, Scanner scanner) {
        System.out.print("Enter student ID to search: ");
        int searchId = scanner.nextInt();

        if (studentMap.containsKey(searchId)) {
            Student foundStudent = studentMap.get(searchId);
            System.out.println("Student found: " + foundStudent);
        } else {
            System.out.println("Student with ID " + searchId + " not found.");
        }
    }

    private static void displayAllStudents(Map<Integer, Student> studentMap) {
        System.out.println("All Students:");
        for (Student s : studentMap.values()) {
            System.out.println(s);
        }
    }

    private static void deleteStudent(Map<Integer, Student> studentMap, Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        int deleteId = scanner.nextInt();

        if (studentMap.containsKey(deleteId)) {
            studentMap.remove(deleteId);
            System.out.println("Student with ID " + deleteId + " deleted.");
        } else {
            System.out.println("Student with ID " + deleteId + " not found.");
        }
    }

    private static Map<Integer, Student> loadStudentData() {
        Map<Integer, Student> studentMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int studentId = Integer.parseInt(parts[0]);
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String rollNumber = parts[3];
                    double cgpa = Double.parseDouble(parts[4]);
                    Student student = new Student(studentId, firstName, lastName, rollNumber, cgpa);
                    studentMap.put(studentId, student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading student data from file: " + e.getMessage());
        }

        return studentMap;
    }

    private static void saveStudentData(Map<Integer, Student> studentMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Student student : studentMap.values()) {
                writer.write(student.getStudentId() + "," + student.getFirstName() + ","
                        + student.getLastName() + "," + student.getRollNumber() + "," + student.getCGPA());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving student data to file: " + e.getMessage());
        }
    }
}

class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String rollNumber;
    private double cgpa;

    public Student(int studentId, String firstName, String lastName, String rollNumber, double cgpa) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNumber = rollNumber;
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + firstName + " " + lastName + "\nRoll Number: " + rollNumber + "\nCGPA: " + cgpa;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public double getCGPA() {
        return cgpa;
    }
}