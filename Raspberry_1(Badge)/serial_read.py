#-*-conding:utf-8-*-
#i/usr/bin/env python
import requests
import json
import time
import os
import sys
import subprocess
import time
import serial

from datetime import datetime


def main():
        ser=serial.Serial(port='/dev/ttyS0',baudrate=9600,parity=serial.PARITY_NONE, stopbits = serial.STOPBITS_ONE, bytesize=serial.EIGHTBITS,timeout=1)
        
        while True:
            x=ser.readline()
            x=str(x).split(',')
            if(x[0]=="b'$GPGGA"):
                print(x)
            if(x[0]=="b'$GPGGA" and x[2]!="" and x[4]!=""):
                a=float(x[2])
                b=float(x[4])
                a1=a//100+a%100/60
                b1=b//100+b%100/60
                print(a1,b1)
                gps=str(a1)+","+str(b1)
        
main()
