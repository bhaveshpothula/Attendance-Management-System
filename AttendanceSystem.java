import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int rollNo;
    String name;
    int attendance;

    Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
        this.attendance = 0;
    }

    void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Attendance: " + attendance + "%");
    }

    void updateAttendance(int value) {
        attendance = value;
    }
}

public class AttendanceSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "students.dat";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\nAttendance System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Display Student Attendance");
            System.out.println("3. Update Attendance");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudent();
                    break;
                case 3:
                    updateAttendance();
                    break;
                case 4:
                    saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter Roll No: ");
        int rollNo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        students.add(new Student(rollNo, name));
        System.out.println("Student added.");
    }

    private static void displayStudent() {
        System.out.print("Enter Roll No: ");
        int rollNo = scanner.nextInt();
        for (Student s : students) {
            if (s.rollNo == rollNo) {
                s.display();
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void updateAttendance() {
        System.out.print("Enter Roll No: ");
        int rollNo = scanner.nextInt();
        for (Student s : students) {
            if (s.rollNo == rollNo) {
                System.out.print("Enter attendance percentage: ");
                int att = scanner.nextInt();
                s.updateAttendance(att);
                System.out.println("Attendance updated.");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
