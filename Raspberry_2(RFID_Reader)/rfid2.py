# -*- coding: utf-8 -*-
import serial
import pymysql
import requests 
import json


ser= serial.Serial(port='/dev/ttyACM0',baudrate=9600)
while True:
    if ser.readable():
        res = ser.readline()
        #print(res)
        res = res.rstrip()
        
        res_list=res.split("/")
        #print(res_list)
        if(len(res_list)>=3):
            
            name= res_list[0]
            phone = res_list[1]
            kindergarden = res_list[2]
            personal_id = res_list[3]
            #print(res_list[0])
            rfid_data={'name':name,'phone':phone,'kindergarden':kindergarden,'personal_id':personal_id}
            #print(rfid_data)
            rs=requests.post("http://jes9401.pythonanywhere.com/rfid/"+personal_id+"/",data=rfid_data)
            print(name)
            print('post done')