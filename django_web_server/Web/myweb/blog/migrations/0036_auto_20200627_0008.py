# Generated by Django 2.1.3 on 2020-06-26 15:08

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0035_auto_20200617_1402'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='public_code',
            field=models.CharField(choices=[('승건유치원', '승건유치원'), ('의석유치원', '의석유치원'), ('성결유치원', '성결유치원')], max_length=80, null=True),
        ),
    ]
