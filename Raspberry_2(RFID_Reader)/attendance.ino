#include <AddicoreRFID.h>
#include <SPI.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(9,12);

#define uchar unsigned char
#define uint unsigned char

uchar serNumA[5];
uchar fifobytes;
uchar fifoValue;

AddicoreRFID myRFID;
//const int speakerPin = 8;

#define MAX_LEN 16
String phone = "01066658851"; // 유아의 부모님 폰번호 

int personal_id=101; // 유아 code

int val;

void setup(){
  Serial.begin(9600);
  while(!Serial){
  }

  mySerial.begin(9600);
  //Serial.println("CLEARDATA"); // CLEARDATA란 엑셀의 모든 내용을 삭제 초기화 한다 라는 뜻
  //Serial.println("LABEL,time,phone,name"); 
  /* LABEL : 액셀에 표기될 라벨 즉 열의 제목을 입력 한다.(ex.Serial.println(“LABEL,Time,temp”);)
  DATE, TIME : 액셀에 자동으로 현재 시간을 출력한다.
  ROW, SET, K : 액셀의 기록될 행의 위치를 변경한다. 주로 데이터가 무제한으로 쌓이는 것을 방지하기 위해 사용하며, 
  1000개 이상 누적될 때 ‘처음부터 기록’과 같은 명령어로 사용 될 수 있다.*/
  // SPI 라이브러리 시작
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
  str[1]=0x4400;

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
       

        if(str[0]==19) // 유아1의 태그의 고유 번호 앞부분의 값 - 하얀카드(디폴트)
        {
           //Serial.print("DATA,TIME");  //Serial.print(","); 
           personal_id = 101; phone="01033660051";
           Serial.print("kimdefault/"); 
           Serial.print(phone); 
           Serial.print("/sungkyul/");
           Serial.print(personal_id);
           
        }
        else if(str[0]==33) // 유아2의 태그의 고유 번호 앞부분의 값 - 하얀카드(7)
        {
            //Serial.print("DATA,TIME");  Serial.print(","); 
            personal_id = 102; phone="01055887751"
           Serial.print("songseven/"); 
           Serial.print(phone); 
           Serial.print("/likely/"); 
           Serial.print(personal_id);
        }
        else if(str[0]==18) // 유아3의 태그의 고유 번호 앞부분의 값 - 하얀카드(x)
        {
           //Serial.print("DATA,TIME");  Serial.print(","); 
           personal_id = 103; phone="01045578851"
           Serial.print("jeongexo/"); 
           Serial.print(phone); 
           Serial.print("/pink/");
           Serial.print(personal_id);
        }

        Serial.println();
        delay(1000);
    }
      myRFID.AddicoreRFID_Halt();
}
