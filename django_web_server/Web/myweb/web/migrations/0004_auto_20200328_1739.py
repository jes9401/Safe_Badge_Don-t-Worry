# Generated by Django 2.1.3 on 2020-03-28 08:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('web', '0003_auto_20200324_2113'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='info',
            options={'verbose_name': '데이터', 'verbose_name_plural': '데이터'},
        ),
        migrations.AddField(
            model_name='info',
            name='gps',
            field=models.TextField(default='null', verbose_name='GPS'),
        ),
        migrations.AlterModelTable(
            name='info',
            table='Info',
        ),
    ]
