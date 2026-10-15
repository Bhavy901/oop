public class PayrollSystem {

    public abstract static class Employee {
        private final String id;
        private final String name;

        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public abstract double monthlySalary();
    }

    public static class FullTime extends Employee {
        private final double fixedSalary;

        public FullTime(String id, String name, double fixedSalary) {
            super(id, name);
            this.fixedSalary = fixedSalary;
        }

        @Override
        public double monthlySalary() {
            return fixedSalary;
        }
    }

    public static class PartTime extends Employee {
        private final double hoursWorked;
        private final double hourlyRate;

        public PartTime(String id, String name, double hoursWorked, double hourlyRate) {
            super(id, name);
            this.hoursWorked = hoursWorked;
            this.hourlyRate = hourlyRate;
        }

        @Override
        public double monthlySalary() {
            return hoursWorked * hourlyRate;
        }
    }

    public static class Intern extends Employee {
        private final double stipend;
        private final String university;

        public Intern(String id, String name, double stipend, String university) {
            super(id, name);
            this.stipend = stipend;
            this.university = university;
        }

        public String getUniversity() {
            return university;
        }

        @Override
        public double monthlySalary() {
            return stipend;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Employee Payroll System ===");

        Employee[] employees = {
            new FullTime("EMP101", "Alice Sharma", 75000),
            new PartTime("EMP102", "Bob Patel", 80, 450),
            new Intern("EMP103", "Charlie Dave", 15000, "CHARUSAT"),
            new FullTime("EMP104", "Diana Desai", 92000),
            new Intern("EMP105", "Ethan Kumar", 18000, "IIT Bombay")
        };

        double grandTotal = 0;

        for (Employee emp : employees) {
            double salary = emp.monthlySalary();
            grandTotal += salary;

            System.out.printf("[%s] %-15s | Salary: Rs. %9.2f", emp.getId(), emp.getName(), salary);

            if (emp instanceof Intern intern) {
                System.out.print("  [NOTE: College Intern from " + intern.getUniversity() + " - Stipend Only]");
            }
            System.out.println();
        }

        System.out.printf("%nTotal Monthly Payroll: Rs. %.2f%n", grandTotal);
    }
}
