from random import randrange
import sys
weapons = ["Knife","Sword","Bow","Axe"]
prefixes = []

chnce = randrange(1,3)
if chnce ==1:
	dmg = randrange(15,40)
else:
	dmg = randrange(10,30)

with open("Prefix-names.txt") as txt:
	line = txt.readline()
	ctr = 1
	while line:
		prefixes.append(line.strip())
		line= txt.readline()
		ctr += 1

weaponnum = randrange(0,3)
prefixnum = randrange(0,len(prefixes)-1)
weapon = prefixes[prefixnum]+"-"+weapons[weaponnum]

file = open("Weapon.txt","w")
file.write(weapon)
file.close()


#sys.stdout.flush()
#for i in range(0,)