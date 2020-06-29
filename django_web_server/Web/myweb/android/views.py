from django.http import HttpResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from .models import Settings
from django.http import JsonResponse
from datetime import datetime
import json
from django.core.serializers.json import DjangoJSONEncoder
from django.forms.models import model_to_dict

@csrf_exempt
def raspOnOff(request,personal_id): # 라즈베리파이 종료 처리
    if request.method == 'GET':
        setting = Settings.objects.filter(personal_code=personal_id).last()
        res_data={}
        res_data['power']=setting.powerOnOff
        return JsonResponse(res_data,safe=False,json_dumps_params={'ensure_ascii': False})
    elif request.method == 'POST':
        powerOnOff = request.POST.get('powerOnOff', None)
        personal_id = request.POST.get('personal_id', None)
        res_data = {}
        setting = Settings.objects.all()
        count=0
        for i in range(len(setting)):
            if setting[i].personal_code==personal_id:
                count=count+1
        if count>=1:
            setting=Settings.objects.filter(personal_code=personal_id).last()
            setting.powerOnOff=int(powerOnOff)
            setting.save()
        else:
            setting.objects.create(personal_code=personal_id,powerOnOff=powerOnOff)
        res_data['success'] = True
        return JsonResponse(res_data)
        
