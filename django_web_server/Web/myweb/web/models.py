from django.db import models

from django.utils import timezone


# Create your models here.

class Info(models.Model):
    cctvURL = models.TextField(verbose_name="CCTV URL")
    gps = models.TextField(verbose_name="GPS",default = 'null')
    public_code = models.TextField(verbose_name="public_code",default = 'null')
    personal_code = models.TextField(verbose_name="personal_code",default = 'null')
    class Meta:
        db_table = 'Info' # 테이블명 지정
        verbose_name = '데이터'
        verbose_name_plural = '데이터' # 복수형 표현도 설정


