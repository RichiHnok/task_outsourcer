import sys
import time

result_save_path = sys.argv[1]
time_to_sleep = sys.argv[2]

import random

time.sleep(time_to_sleep)

def generate_random_numbers():
    random_numbers = [str(random.randint(0, 9)) for _ in range(10)]
    return ''.join(random_numbers)

def create_file(filename):
    random_string = generate_random_numbers()
    with open(filename, 'w') as file:
        file.write(random_string)


random_string = generate_random_numbers()
print("Случайная строка из 10 чисел:", random_string)

with open(result_save_path, "w") as file:
    file.write(random_string)