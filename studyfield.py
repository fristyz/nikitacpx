from enum import Enum

class StudyField(Enum):
    MECHANICAL_ENGINEERING = 1
    SOFTWARE_ENGINEERING = 2
    FOOD_TECHNOLOGY = 3
    URBANISM_ARCHITECTURE = 4
    VETERINARY_MEDICINE = 5

# Example usage:
chosen_field = StudyField.SOFTWARE_ENGINEERING
print(chosen_field.name)
