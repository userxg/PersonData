package DataReader;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.time.DateTimeException;

class Person {
    private Name name;
    private BirthInfo birthInfo;

    public Person() {
        this.name = new Name();
        this.birthInfo = new BirthInfo();
    }

    public void inputPersonData() {
        name.fullNameInput();
        name.defineGender();
        birthInfo.inputDateOfBirth();
    }

    public void getBriefInfo() {
        System.out.println("Full Name: " + name.getSurnameInitials());
        System.out.println("Gender: " + name.getGender());
        System.out.println("Current Age: " + birthInfo.getCurrentAge());
    }

    class Name {
        private String firstName;
        private String surname;
        private String patronymic;
        private String gender;

        public void fullNameInput() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your First Name: ");
            firstName = scanner.nextLine().trim();
            System.out.print("Enter your Surname: ");
            surname = scanner.nextLine().trim();
            System.out.print("Enter your Patronymic: ");
            patronymic = scanner.nextLine().trim();

            if (!isValidName(firstName) || !isValidName(surname) || !isValidName(patronymic)) {
                System.out.println("Invalid input. Please enter valid names.");
                fullNameInput();
            }
        }

        private boolean isValidName(String name) {
            return name.matches("[А-Яа-яЁё]+");
        }

        public String getSurname() {
            return surname;
        }

        public String getName() {
            return firstName;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void defineGender() {
            if (patronymic.endsWith("ич")) {
                gender = "Male";
            } else if (patronymic.endsWith("на")) {
                gender = "Female";
            } else {
                gender = "Unknown";
            }
        }

        public String getSurnameInitials() {
            return surname + " " + firstName.charAt(0) + ". " + patronymic.charAt(0) + ".";
        }

        public String getGender() {
            return gender;
        }
    }

    class BirthInfo {
        private int year;
        private int month;
        private int day;

        public void inputDateOfBirth() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your Date of Birth (YYYY-MM-DD): ");
            String[] dob = scanner.nextLine().split("-");

            if (dob.length != 3 || !isValidDate(dob)) {
                System.out.println("Invalid date format. Please enter a valid date.");
                inputDateOfBirth();
                return;
            }

            year = Integer.parseInt(dob[0]);
            month = Integer.parseInt(dob[1]);
            day = Integer.parseInt(dob[2]);
        }

        private boolean isValidDate(String[] dob) {
            try {
                int y = Integer.parseInt(dob[0]);
                int m = Integer.parseInt(dob[1]);
                int d = Integer.parseInt(dob[2]);
                LocalDate.of(y, m, d); // This will throw an exception if the date is invalid
                LocalDate currentDate = LocalDate.now();
                Period age = Period.between(LocalDate.of(y, m, d), currentDate);
                if (age.getYears() < 0 || age.getMonths() < 0 || age.getDays() < 0){
                    System.out.println("You cannot be born in the future");
                    throw new DateTimeException("");
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }

        public String getCurrentAge() {
            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(birthDate, currentDate);
            return formatAge(age.getYears(), age.getMonths(), age.getDays());
        }

        private String formatAge(int years, int months, int days) {
            StringBuilder ageString = new StringBuilder();

            ageString.append(years).append(" ").append(getYearWordForm(years)).append(", ");

            ageString.append(months).append(" ").append(getMonthWordForm(months)).append(", ");

            ageString.append(days).append(" ").append(getDayWordForm(days));



            return ageString.toString();
        }

        private String getYearWordForm(int years) {
            if (years % 10 == 1 && years % 100 != 11) return "год";
            else if ((years % 10 >= 2 && years % 10 <= 4) && (years % 100 < 10 || years % 100 >= 20)) return "года";
            else return "лет";
        }

        private String getMonthWordForm(int months) {
            if (months == 1) return "месяц";
            else if (months >= 2 && months <= 4) return "месяца";
            else return "месяцев";
        }

        private String getDayWordForm(int days) {
            if (days == 1) return "день";
            else if (days >= 2 && days <= 4) return "дня";
            else return "дней";
        }
    }
}
