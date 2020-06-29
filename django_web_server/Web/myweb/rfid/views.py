from django.shortcuts import render
from .models import RFID
from django.http import JsonResponse
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.models import User
from datetime import datetime

@csrf_exempt
def rfid(request,personal_id):
    if request.method == 'GET':
        rfid = RFID.objects.filter(personal_code=personal_id).last()
        rfid_data={}
        rfid_data['state']=rfid.state
        return JsonResponse(rfid_data,safe=False,json_dumps_params={'ensure_ascii': False})
    elif request.method == 'POST':
        name = request.POST.get('name', None)
        phone = request.POST.get('phone', None)
        kindergarden=request.POST.get('kindergarden',None)
        personal_code = request.POST.get('personal_id',None)
        rfid_data=RFID.objects.all()
        count=0
        for i in range(len(rfid_data)):
            if rfid_data[i].personal_code==str(personal_id):
                count=count+1
        if count>=1:
            rfid = RFID.objects.filter(personal_code=str(personal_id)).last()
            rfid.published_date=datetime.now()
            if rfid.state=="1":
                rfid.state="0"
            else:
                rfid.state="1"
            rfid.save()
        else:
            RFID.objects.create(name=name,phone=phone,kindergarden=kindergarden,personal_code=personal_id,state="1")

        rfid_data={}
        rfid_data['personal_id']=personal_id
        return JsonResponse(rfid_data)

