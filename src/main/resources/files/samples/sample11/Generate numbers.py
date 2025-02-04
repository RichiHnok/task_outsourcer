import sys
import os

result_save_path = sys.argv[1]
print(result_save_path)
# result_save_path = 'src\\main\\resources\\files\\users\\R\\1119333901022025\\output'
import random

def generate_random_numbers():
    random_numbers = [str(random.randint(0, 9)) for _ in range(10)]
    return ''.join(random_numbers)

def create_and_write_file(directory, filename, content):
    # Создаём папку, если она не существует
    if not os.path.exists(directory):
        os.makedirs(directory)
    
    # Полный путь к файлу
    file_path = os.path.join(directory, filename)
    
    my_file = open(file_path, "w+")
    my_file.write(generate_random_numbers())
    my_file.close()

create_and_write_file(result_save_path, 'result', generate_random_numbers())

# print("end")
