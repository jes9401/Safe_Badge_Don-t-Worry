from django.db import models

# Create your models here.

class Settings(models.Model):
    powerOnOff = models.IntegerField(verbose_name='RaspberryPi Power On Off')
    personal_code = models.TextField(verbose_name="personal_code",default='null')
    class Meta:
        verbose_name = '안드로이드 요청 데이터'
        verbose_name_plural = '안드로이드 요청 데이터' # 복수형 표현도 설정
