# Generated by Django 2.1.3 on 2020-05-21 01:38

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('rfid', '0002_rfid_personal_code'),
    ]

    operations = [
        migrations.AddField(
            model_name='rfid',
            name='state',
            field=models.TextField(default='null', verbose_name='state'),
        ),
    ]
