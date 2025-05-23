
#! /usr/bin/env python3
# 2021-04-02
# 2021-04-13    Fix Wrong model for Old Style revision codes
# 2021-12-20    Improve Old Style revision codes; ignore unwanted status bits
# 2022-03-25    Zero 2 W
# 2022-04-07    typo
# 2023-01-16    Include PROGNAME, LIBNAME in border; fix formatting
# 2023-11-25    pinctrl (for Bookworm)
# 2024-01-16    Bookworm & earlier combined program
# 2024-01-31    Pi5 - pinctrl changes
# 2024-02-03    Pi5 - unknown pin value

"""
Read all GPIO
This version for Bookworm/pinctrl or raspi-gpio debug tool
"""
# Bookworm still has raspi-gpio but it has changed (and does not work on Pi5)
import sys, os, time
import subprocess
LIBNAME='pinctrl'
PROGNAME='gpioreadall'

MODES=["IN", "OUT", "ALT5", "ALT4", "ALT0", "ALT1", "ALT2", "ALT3"]
HEADER = ('3.3v', '5v', 2, '5v', 3, 'GND', 4, 14, 'GND', 15, 17, 18, 27, 'GND', 22, 23, '3.3v', 24, 10, 'GND', 9, 25, 11, 8, 'GND', 7, 0, 1, 5, 'GND', 6, 12, 13, 'GND', 19, 16, 26, 20, 'GND', 21)

# https://www.raspberrypi.com/documentation/computers/raspberry-pi.html#new-style-revision-codes
PiModel = {
0: 'A',
1: 'B',
2: 'A+',
3: 'B+',
4: '2B',
6: 'CM1',
8: '3B',
9: 'Zero',
0xa: 'CM3',
0xc: 'ZeroW',
0xd: '3B+',
0xe: '3A+',
0x10: 'CM3+',
0x11: '4B',
0x12: 'Zero2W',
0x13: '400',
0x14: 'CM4',
0x15: 'CM4S',
0x17: '5'
}

RED   = '\033[1;31m'
GREEN = '\033[1;32m'
ORANGE = '\033[1;33m'
BLUE = '\033[1;34m'
LRED = '\033[1;91m'
YELLOW = '\033[1;93m'
RESET = '\033[0;0m'
COL = {
    '3.3v': LRED,
    '5v': RED,
    'GND': GREEN
}

TYPE = 0
rev = 0

def pin_state(g):
    """
    Return "state" of BCM g
    Return is tuple (name, mode, value)
    """
    global LIBNAME
    try:
        result = subprocess.run(['pinctrl', 'get', ascii(g)], stdout=subprocess.PIPE).stdout.decode('utf-8')
    except: # fall back to raspi-gpio
        LIBNAME='raspi-gpio'
        result = subprocess.run(['raspi-gpio', 'get', ascii(g)], stdout=subprocess.PIPE).stdout.decode('utf-8')
        D = {}  # Convert output of raspi-gpio get to dict for convenience
        paras = result.split()
        for par in paras[2:] :
            p, v = par.split('=')
            if (v.isdigit()):
                D[p] = int(v)
            else:
                D[p] = v

        if(D['fsel'] < 2): # i.e. IN or OUT
            name = 'GPIO{}'.format(g)
        else:
            name = D['func']
        mode = MODES[D['fsel']]
        if(D['fsel'] == 0 and 'pull' in D):
            if(D['pull'] == 'UP'):
                mode = 'IN ^'
            if(D['pull'] == 'DOWN'):
                mode = 'IN v'
        value = D['level']
    else:   # pinctrl
        paras = result.split('//')
        name = paras[1].split('=')[0].strip()
        if name.startswith("PIN"):
            name = name.split('/')[1]    # name from last field
        else:
            name = name.split('/')[0]    # name from first field
        fname = paras[1].split('=')[-1].strip()
        paras = paras[0].split()
        value = 1 if paras[-1] == 'hi' else 0
    
        mode = 'UNKNOWN'
        if paras[1] == 'ip':
            mode = 'IN'
            if paras[-3] == 'pu':
                mode = 'IN ^'
            if paras[-3] == 'pd':
                mode = 'IN v'
        elif paras[1] == 'op':
            mode = 'OUT'
        elif paras[1] == 'no':
            mode = ' '
            value = ' '
        else:
            level = paras[1][-1]
            mode = 'ALT' + level
            name = fname    # if not I/O function name  from last field
        
    return name, mode, value  

def print_gpio(pin_state):
    """
    Print listing of Raspberry pins, state & value
    Layout matching Pi 2 row Header
    """
    global TYPE, rev
    GPIOPINS = 40
    try:
        Model = 'Pi ' + PiModel[TYPE]
    except:
        Model = 'Pi ??'
    if rev < 16 :	# older models (pre PiB+)
        GPIOPINS = 26

    print('+----------------------------+{:^10}+----------------------------+'.format(Model) )
    HEAD='| BCM | Name      | Mode | V |  Board   | V | Mode | Name      | BCM |'
    DIV='+-----+-----------+------+---+----++----+---+------+-----------+-----+'
    print(HEAD)
    print(DIV)

    for h in range(1, GPIOPINS, 2):
    # odd pin
        hh = HEADER[h-1]
        if(type(hh)==type(1)):
            print('|{0:4} | {1[0]:<10}| {1[1]:<4} | {1[2]} |{2:3} '.format(hh, pin_state(hh), h), end='|| ')
        else:
#            print('|        {:18}  | {:2}'.format(hh, h), end=' || ')    # non-coloured output
          print('|        {}{:18}  | {:2}{}'.format(COL[hh], hh, h, RESET), end=' || ')    # coloured output
    # even pin
        hh = HEADER[h]
        if(type(hh)==type(1)):
            print('{0:2} | {1[2]:<2}| {1[1]:<5}| {1[0]:<10}|{2:4} |'.format(h+1, pin_state(hh), hh))
        else:
#             print('{:2} |             {:9}      |'.format(h+1, hh))    # non-coloured output
            print('{}{:2} |             {:9}{}      |'.format(COL[hh], h+1, hh, RESET))    # coloured output
    print(DIV)
    print(HEAD)
    print('+{:-^28}+{:^10}+{:-^28}+'.format(LIBNAME, Model, PROGNAME) )

def get_hardware_revision():
    """
    Returns the Pi's hardware revision number.
    """
    with open('/proc/cpuinfo', 'r') as f:
        for line in f.readlines():
            if 'Revision' in line:
                REV = line.split(':')[1]
                REV = REV.strip()   # Revision as string
                return int(REV, base=16)

def main():
    global TYPE, rev
    rev = get_hardware_revision()

    if(rev & 0x800000):   # New Style
        TYPE = (rev&0x00000FF0)>>4
    else:   # Old Style
        rev &= 0x1F
        MM = [0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 3, 6, 2, 3, 6, 2]
        TYPE = MM[rev] # Map Old Style revision to TYPE

    print_gpio(pin_state)

if __name__ == '__main__':
	main()
