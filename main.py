from enum import Enum


class StudyField(Enum):
    MECHANICAL_ENGINEERING = "MECHANICAL_ENGINEERING"
    SOFTWARE_ENGINEERING = "SOFTWARE_ENGINEERING"
    FOOD_TECHNOLOGY = "FOOD_TECHNOLOGY"
    URBANISM_ARCHITECTURE = "URBANISM_ARCHITECTURE"
    VETERINARY_MEDICINE = "VETERINARY_MEDICINE"


class Student:
    def __init__(self, first_name, last_name, email, faculty, graduated):
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.faculty = faculty
        self.graduated = graduated

    def is_graduated(self):
        return self.graduated


class Faculty:
    faculties = {}

    def __init__(self, name, abbreviation, study_field):
        self.name = name
        self.abbreviation = abbreviation
        self.study_field = study_field
        self.students = []
        Faculty.faculties[abbreviation] = self

    @staticmethod
    def get_faculty_by_abbreviation(abbreviation):
        return Faculty.faculties.get(abbreviation)

    @staticmethod
    def display_student_faculty(email):
        for faculty in Faculty.faculties.values():
            for student in faculty.students:
                if student.email == email:
                    print(f"{student.first_name} {student.last_name} belongs to the {faculty.name} faculty.")
                    return
        print("No student found with that email.")

    @staticmethod
    def display_all_faculties():
        print("Here are all the faculties:")
        for faculty in Faculty.faculties.values():
            print(f"Name: {faculty.name}, Abbreviation: {faculty.abbreviation}, Study Field: {faculty.study_field.value}")

    @staticmethod
    def display_all_faculties_of_a_field(study_field):
        print(f"Here are all the faculties belonging to {study_field.value}")
        for faculty in Faculty.faculties.values():
            if faculty.study_field == study_field:
                print(faculty.name)

    def create_student(self, student):
        self.students.append(student)
        print(f"{student.first_name} {student.last_name} was added to the student list")
        student.graduated = False
        print(student.graduated)

    def graduate_student(self, email):
        for student in self.students:
            if student.email == email:
                print(f"{student.first_name} {student.last_name} has graduated from: {self.name}")
                student.graduated = True
                return
        print("No student found with that email.")

    @staticmethod
    def display_students(abbreviation, is_graduated):
        for faculty in Faculty.faculties.values():
            if faculty.abbreviation == abbreviation:
                for student in faculty.students:
                    if student.is_graduated() == is_graduated:
                        print(f"{student.first_name} {student.last_name}")

    def is_student_from_faculty(self, email):
        for student in self.students:
            if student.email == email:
                print("Student does belong to faculty")
                return
        print("Student does not belong to faculty")

    def get_students(self):
        return self.students

    def get_name(self):
        return self.name

    def get_abbreviation(self):
        return self.abbreviation

    def get_study_field(self):
        return self.study_field


def main():
    should_finish = False


    T = Faculty("Transport", "T", StudyField.MECHANICAL_ENGINEERING)
    CSI = Faculty("Computer Science", "CSI", StudyField.SOFTWARE_ENGINEERING)
    FN = Faculty("Food and Nutrition", "FN", StudyField.FOOD_TECHNOLOGY)
    UD = Faculty("Urban Design", "UD", StudyField.URBANISM_ARCHITECTURE)
    DFSPH = Faculty("Department of Food Safety and Public Health", "DFSPH", StudyField.VETERINARY_MEDICINE)


    student2 = Student("Sebastian", "Finciuc", "sebastian.finciuc@gmail.com", CSI, False)
    CSI.create_student(student2)

    while not should_finish:
        print("TUM Board Command Line \n" +
              "\t1. General Operations \n" +
              "\t2. Faculty Operations \n" +
              "\t3. Quit \n" +
              "Please choose one of the above options: ")
        choice = int(input())
        if choice == 1:
            print("General options: \n" +
                  "\t1. Create a new faculty\n" +
                  "\t2. Search for a student's faculty by email\n" +
                  "\t3. Display all University faculties\n" +
                  "\t4. Display all faculties belonging to a field.")
            sub_choice = int(input())
            if sub_choice == 1:
                name = input("Enter Faculty Name: ")
                abbreviation = input("Enter Faculty Abbreviation: ")
                study_field = input("Enter Study Field (e.g., SOFTWARE_ENGINEERING): ")
                field = getattr(StudyField, study_field, None)
                if field is not None:
                    faculty = Faculty(name, abbreviation, field)
            elif sub_choice == 2:
                email = input("Enter student email: ")
                Faculty.display_student_faculty(email)
            elif sub_choice == 3:
                Faculty.display_all_faculties()
            elif sub_choice == 4:
                study_field = input("Enter a Study Field: ")
                field = getattr(StudyField, study_field, None)
                if field is not None:
                    Faculty.display_all_faculties_of_a_field(field)
        elif choice == 2:
            print("Faculty options: \n" +
                  "\t1. Create a new student\n" +
                  "\t2. Graduate student by email\n" +
                  "\t3. Display all enrolled students in a faculty\n" +
                  "\t4. Display all students graduated from a faculty\n" +
                  "\t5. Check if student belongs to faculty")
            sub_choice = int(input())
            if sub_choice == 1:
                first_name = input("Enter student first name: ")
                last_name = input("Enter student last name: ")
                email = input("Enter student email: ")
                abbreviation = input("Enter Faculty Abbreviation: ")
                faculty = Faculty.get_faculty_by_abbreviation(abbreviation)
                if faculty is not None:
                    student = Student(first_name, last_name, email, faculty, False)
                    print("Student created!!!")
                    faculty.create_student(student)
            elif sub_choice == 2:
                email = input("Enter email of student you'd like to graduate: ")
                Faculty.graduate_student(email)
            elif sub_choice == 3:
                abbreviation = input("Enter faculty abbreviation: ")
                print(f"Students enrolled in {abbreviation}:")
                Faculty.display_students(abbreviation, False)
            elif sub_choice == 4:
                abbreviation = input("Enter faculty abbreviation: ")
                print(f"Students graduated from {abbreviation}:")
                Faculty.display_students(abbreviation, True)
            elif sub_choice == 5:
                s_email = input("Enter student email: ")
                abbreviation = input("Enter faculty abbreviation: ")
                Faculty.is_student_from_faculty(abbreviation, s
