#include <AddicoreRFID.h>
#include <SPI.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(9,12);

#define uchar unsigned char
#define uint unsigned int

uchar serNumA[5];
uchar fifobytes;
uchar fifoValue;

AddicoreRFID myRFID;

#define MAX_LEN 16
String phone = "01066658851"; // 유아의 부모님 폰번호 

int personal_id=101; // 유아 code
int speakerpin = 7;

int val;

void setup(){
  Serial.begin(9600);
  while(!Serial){
  }

  mySerial.begin(9600);
  SPI.begin();
  myRFID.AddicoreRFID_Init();
}

void loop(){
  uchar i,tmp,checksum1;
    uchar status;
  uchar str[MAX_LEN];
  uchar RC_size;
  uchar blockAddr;
  String mynum ="";
  //str[1]=0x4400;

    status=myRFID.AddicoreRFID_Request(PICC_REQIDL, str);

  //RFID 충돌방지, RFID 태그의 ID값(시리얼 넘버) 등 저장된 값을 리턴함 4byte.
    status = myRFID.AddicoreRFID_Anticoll(str);
    if(status == MI_OK) // MIFARE 카드일 때만 작동
    {
      checksum1 = str[0] ^ str[1] ^ str[2] ^ str[3];
      /*Serial.println("이 태그의 숫자는: ");
        //Serial.print(2);
        Serial.print(str[0]);
       Serial.print(", "); 
        Serial.print(str[1],BIN);
       Serial.print(", "); 
        Serial.print(str[2],BIN);
       Serial.print(", "); 
        Serial.print(str[3],BIN);
       Serial.print(", ");
        Serial.print(str[4],BIN);
       Serial.print(", ");  */
       
      if(str[0]==84) // BLACK
        {
           personal_id = 201; phone="01033660051";
           Serial.print("정의석/"); 
           Serial.print(phone); 
           Serial.print("/의석유치원/");
           Serial.print(personal_id);
           
        }
        else if(str[0]==07) //BLUE X
        {
            personal_id = 301; phone="01055887751";
           Serial.print("장승건/"); 
           Serial.print(phone); 
           Serial.print("/승건유치원/"); 
           Serial.print(personal_id);
        }
        else if(str[0]==224) // DEF
        {          
           personal_id = 103; phone="01045578851";
           Serial.print("홍길동/"); 
           Serial.print(phone); 
           Serial.print("/성결유치원/");
           Serial.print(personal_id);
        } 
        else if(str[0]==133) //분해키
        {
           personal_id = 101; phone="01012345678";
           Serial.print("김성결/"); 
           Serial.print(phone); 
           Serial.print("/성결유치원/");
           Serial.print(personal_id);
        }

        tone(speakerpin, 700, 300);

        Serial.println();
        delay(1000);
    }
      myRFID.AddicoreRFID_Halt();
}
