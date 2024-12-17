print('Hello')
#python  src/main/resources/files/script.py 480 240 rendered_image
import sys
resX = sys.argv[1]
resY = sys.argv[2]
imageName = sys.argv[3]

import pyautogui as pg
import os
import time
# os.startfile('C:\\ForInstallation\\Blender Foundation\\Blender 4.3\\blender.exe')
os.startfile('C:\\RichiFolder\\Projects\\RichisAppForServer\\richis_app\\src\\main\\resources\\files\\project_to_render.blend')
time.sleep(6)

pg.click(x=1608, y=457)
pg.moveTo(1605,488)
pg.click()
time.sleep(1)
# resolution X
pg.moveTo(1824,485)
pg.click()
pg.write(resX)
time.sleep(1)
# resolution Y
pg.moveTo(1817,503)
pg.click()
pg.click()
pg.write(resY)
pg.press('enter')
time.sleep(1)

pg.press('f12')
# pg.moveTo(168,64, 0.5)
time.sleep(10)
# pg.press(['shift', 'alt', 's'])
pg.keyDown('shift')
pg.keyDown('alt')
pg.press('s')
pg.keyUp('shift')
pg.keyUp('alt')

time.sleep(1)
pg.moveTo(1144,818)
pg.click()
pg.write(imageName)
time.sleep(0.5)

pg.moveTo(1648,816)
pg.click()
pg.click()
time.sleep(0.5)

pg.moveTo(1893,5)
pg.click()
time.sleep(0.5)

pg.moveTo(998,567)
pg.click()
time.sleep(0.5)
