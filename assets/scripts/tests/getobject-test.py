# Test script. Tests getObject() and getObjectRadius()
# Created by Toni Sagrista

import math
from py4j.java_gateway import JavaGateway, GatewayParameters

gateway = JavaGateway(gateway_parameters=GatewayParameters(auto_convert=True))
gs = gateway.entry_point

gs.disableInput()
gs.cameraStop()

# Use the object with the following name
obj = "Betelgeuse"

gs.sleep(1)
gs.goToObject(obj, 3.5)

rad = gs.getObjectRadius(obj)

gs.print("%s radius: %f Km" % (obj, rad))

gs.sleep(1)

body = gs.getObject(obj)
absmag = body.getAbsmag()
appmag = body.getAppmag()
radec = body.getPosSph()

gs.print("Absmag: %f, appmag: %f, RA: %f, DEC: %f" % (absmag, appmag, radec.x(), radec.y()))


# Now stop at a certain distance of earth
dist = 15000 #Km
earthrad = gs.getObjectRadius("Earth")
anglerad = math.acos((earthrad * 2)/dist)

gs.goToObject("Earth", math.degrees(anglerad))

gs.enableInput()

gateway.close()
