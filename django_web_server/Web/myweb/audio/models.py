from django.db import models
from django.conf import settings
import os.path

class Song(models.Model):
    title = models.CharField(max_length=100, default='null')
    audio_file = models.FileField(upload_to='audio')
    audio_file.verbose_name= '파일'

    def __str__(self):
        return self.title