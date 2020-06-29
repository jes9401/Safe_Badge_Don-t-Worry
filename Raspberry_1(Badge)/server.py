#-*-coding:utf-8-*-
#i/usr/bin/env python
import requests
from requests import get
import json
import time
import os
import sys
import vlc
import subprocess
import serial
from datetime import datetime

m4a_type=".m4a"


def download(url, file_name = None):
    if not file_name:
        file_name = url.split('/')[-1]
        with open(file_name, "wb") as file:
            response = get(url)
            file.write(response.content)
            
def main():
    past_file_data={}
    ser=serial.Serial(port='/dev/ttyS0',baudrate=9600,parity=serial.PARITY_NONE, stopbits = serial.STOPBITS_ONE, bytesize=serial.EIGHTBITS,timeout=1)
    while True:
        past_remote_data = {}
        rs3 = requests.get('http://jes9401.pythonanywhere.com/web/raspberry/101/')
        gps_data=rs3.json()
        past_gps=gps_data['gps']
        #while True:
        #    x=ser.readline()
        #    x=str(x).split(',')
        #    if(x[0]=="b'$GPGGA" and x[2]!="" and x[4]!=""):
        #        a=float(x[2])
        #        b=float(x[4])
        #        a1=a//100+a%100/60
        #        b1=b//100+b%100/60
        #        gps=str(a1)+","+str(b1)
        #        break
        gps="Null"
        ip = subprocess.check_output('hostname -I', shell=True)
        ip=ip[0:14]
        if gps=="Null":
            print("cctvuRL : ",ip,"GPS : ",past_gps)
            info_data = {'cctvURL':ip, 'gps':past_gps,'public_code':'성결유치원','personal_code':'101'} 
        else:
            print("cctvuRL : ",ip,"GPS : ",gps)
            info_data = {'cctvURL':ip, 'gps':gps,'public_code':'성결유치원','personal_code':'101'} 
        rs = requests.post('http://jes9401.pythonanywhere.com/web/raspberry/101/',data=info_data) # target server
        print("Raspberrypi Data request done")


        rs2 = requests.get('http://jes9401.pythonanywhere.com/audio/getFile/')
        new_file_data=rs2.json()
        new_data=new_file_data['audio_file']
        new_data=new_data.split(',')
        if new_file_data == past_file_data or past_file_data=={}:
            print('audio same')
        elif len(new_data)>1:
            print('data change')
            if __name__ == '__main__':
                url = "http://jes9401.pythonanywhere.com/media/"+new_data[-2]
                file_name=new_data[-2][6:]
                print(file_name)
                download(url)

                if m4a_type in file_name:
                    instance = vlc.Instance()
                    player = instance.media_player_new()
                    media = instance.media_new(file_name)

                    player.set_media(media)
                    player.play()
                else:
                    print("it is not m4a type")

                    
        past_file_data=new_file_data
        
        time.sleep(3)

main()
