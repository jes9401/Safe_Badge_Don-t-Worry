# Generated by Django 2.1.3 on 2020-04-16 06:12

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('web', '0005_info_code'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='info',
            name='code',
        ),
        migrations.AddField(
            model_name='info',
            name='personal_code',
            field=models.TextField(default='null', verbose_name='CODE2'),
        ),
        migrations.AddField(
            model_name='info',
            name='public_code',
            field=models.TextField(default='null', verbose_name='CODE1'),
        ),
    ]
