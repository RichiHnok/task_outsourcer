#python  src/main/resources/files/samples/script.py 480 240 rendered_image
# os.startfile('C:\\ForInstallation\\Blender Foundation\\Blender 4.3\\blender.exe')
#py -m mouseinfo
# os.startfile('project.blend')

import sys
print('Hello')
resX = sys.argv[1]
resY = sys.argv[2]
imageName = sys.argv[3]
import pyautogui as pg, os, time
os.startfile('C:\\RichiFolder\\Projects\\RichisAppForServer\\richis_app\\src\\main\\resources\\files\\users\\R\\102380218122024\\input\\project.blend')
time.sleep(4)

pg.click(1604,493)
time.sleep(1)
# set resolution X
pg.moveTo(1824,485)
pg.click()
pg.write(resX)
# set resolution Y
pg.moveTo(1817,503)
pg.click()
pg.click()
pg.write(resY)
pg.press('enter')

pg.press('f12')
time.sleep(6)
pg.keyDown('shift')
pg.keyDown('alt')
pg.press('s')
pg.keyUp('shift')
pg.keyUp('alt')

time.sleep(1)
pg.moveTo(421,940)
pg.click()
pg.write(imageName)
time.sleep(0.5)
pg.press('enter')
pg.press('enter')
time.sleep(1)

pg.moveTo(1894,13)
pg.click()
time.sleep(0.5)

pg.moveTo(1000,573)
pg.click()
time.sleep(0.5)
