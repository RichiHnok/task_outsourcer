import os
import sys
import argparse

try:
    import bpy # type: ignore
except ImportError:
    print("Module 'bpy' could not be imported. This probably means you are not using Blender to run this script.")
    sys.exit(1)

prog_name = "Image Render "

if '--' not in sys.argv:
    print(prog_name + "No '--' found in command line arguments. '--' is needed to pass arguments to this script.")
    sys.exit(1)

# Getting arguments
arguments = sys.argv[sys.argv.index("--")+1:]

output_folder = arguments[0] #Обязательно указывать путь сохранения файлов
if not os.path.isdir(output_folder):
    print('Not a folder error: ' + output_folder)
    sys.exit(1)

width = int(arguments[1])
height = int(arguments[2])
input_file_path = arguments[3]
if not os.path.isfile(input_file_path):
    print('Not a file error: ' + input_file_path)
    sys.exit(1)

end_file_name = arguments[4]
samples = int(arguments[5])

# Open project
# path = os.path.dirname(__file__)
template_path = os.path.abspath(input_file_path)
if not os.path.exists(template_path):
    print(prog_name + "could not find this template file: '%s'" % template_path)
    sys.exit(1)
bpy.ops.wm.open_mainfile(filepath=template_path)

# Change render params
bpy.context.scene.render.engine = 'CYCLES'
bpy.context.scene.cycles.samples = samples

# Render output image
end_file_path = os.path.join(output_folder, end_file_name)
bpy.context.scene.render.filepath = end_file_path
bpy.context.scene.render.resolution_x = width
bpy.context.scene.render.resolution_y = height
bpy.ops.render.render(write_still=True)