import pymysql
import serial

conn=pymysql.connect(host="localhost",user="seon",passwd="1234",db="rfid_db")
ser= serial.Serial(port='/dev/ttyACM0',baudrate=9600)

try :
    with conn.cursor() as cur :
        sql="select * from collect_data"
        cur.execute(sql)
        for row in cur.ferchall():
            print(row[0],row[1],row[2])
    while True:
        print("insert op: ggggggg성적입력", end=' ')
        op = input()
        ser.write(op.encode())
finally:
    conn.close()
