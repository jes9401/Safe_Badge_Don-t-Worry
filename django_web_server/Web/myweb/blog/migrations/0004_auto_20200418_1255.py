# Generated by Django 2.1.3 on 2020-04-18 03:55

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0003_auto_20200418_1248'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='public_code',
            field=models.IntegerField(choices=[(1, '성결유치원'), (2, '의석유치원')], default=1),
        ),
    ]