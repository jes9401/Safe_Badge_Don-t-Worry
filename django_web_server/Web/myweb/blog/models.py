from django.conf import settings
from django.db import models
from django.utils import timezone
from web.models import Info
from django.contrib.auth.models import User
from accounts.models import Profile


class Post(models.Model):
    SCHOOL_CHOICES = {
      ('성결유치원','성결유치원'), #오른쪽에 있는 것이 화면에 보인다.
      ('의석유치원', '의석유치원'),
      ('승건유치원','승건유치원')
    }
    public_code = models.CharField(max_length=80, choices=SCHOOL_CHOICES, null=True)

    author = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    title = models.CharField(max_length=200)
    text = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            default=timezone.now)

    class Meta:
        verbose_name = '공지사항'
        verbose_name_plural = '공지사항' # 복수형 표현도 설정


    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.title

class Post2(models.Model):
    personal_code = models.CharField(max_length=80,null=True)
    author = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    title = models.CharField(max_length=200)
    text = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            default=timezone.now)

    class Meta:
        verbose_name = '알림장'
        verbose_name_plural = '알림장' # 복수형 표현도 설정

    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.title
