# Generated by Django 2.1.3 on 2020-04-19 03:56

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0011_auto_20200418_1442'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='post',
            options={'verbose_name': '공지사항', 'verbose_name_plural': '공지사항'},
        ),
        migrations.AlterModelOptions(
            name='post2',
            options={'verbose_name': '알림장', 'verbose_name_plural': '알림장'},
        ),
        migrations.AlterField(
            model_name='post',
            name='public_code',
            field=models.CharField(choices=[('성결유치원', '성결유치원'), ('의석유치원', '의석유치원')], max_length=80, null=True),
        ),
    ]
