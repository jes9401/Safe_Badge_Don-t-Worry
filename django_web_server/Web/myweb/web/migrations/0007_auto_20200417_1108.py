# Generated by Django 2.1.3 on 2020-04-17 02:08

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('web', '0006_auto_20200416_1512'),
    ]

    operations = [
        migrations.AlterField(
            model_name='info',
            name='personal_code',
            field=models.TextField(default='null', verbose_name='personal_code'),
        ),
        migrations.AlterField(
            model_name='info',
            name='public_code',
            field=models.TextField(default='null', verbose_name='public_code'),
        ),
    ]
