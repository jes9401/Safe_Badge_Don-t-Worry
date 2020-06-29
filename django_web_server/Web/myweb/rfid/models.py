from django.db import models
from django.utils import timezone

class RFID(models.Model):
    name  = models.TextField(verbose_name="name",default='null')
    phone = models.TextField(verbose_name="phone",default = 'null')
    personal_code = models.TextField(verbose_name="personal_code",default='null')
    kindergarden = models.TextField(verbose_name="kindergarden",default = 'null')
    state = models.TextField(verbose_name="state",default='null')
    published_date = models.DateTimeField(
            default=timezone.now)
    class Meta:
        db_table = 'RFID'
        verbose_name = 'RFID_데이터'
