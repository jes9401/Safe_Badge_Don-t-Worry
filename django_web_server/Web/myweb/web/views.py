from django.shortcuts import render
from django.http import HttpResponse
from rest_framework import viewsets
from web.models import Info
from web.serializers import TestSerializer
from django.http import JsonResponse
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt
from accounts.models import Profile
from django.contrib.auth.models import User
from django.contrib import auth
from django.contrib import messages
from datetime import datetime


class TestViewSet(viewsets.ModelViewSet):
    queryset = Info.objects.all()
    serializer_class = TestSerializer
    def post(self, request):
        cctvURL = request.POST.get('cctvURL', None)
        gps = request.POST.get('gps', None)
        personal_id = request.POST.get('personal_code', None)
        public_code=request.POST.get('public_code',None)
        info = Info.objects.filter(personal_code=personal_id).last()
        info.gps = gps
        info.cctvURL = cctvURL
        info.personal_code=personal_id
        info.public_code=public_code
        info.save()
        info_data={}
        info_data['personal_id']=personal_id
        return JsonResponse(info_data)
#    def post(self, request, format=None):
#        return Response("ok")
    def put(self, request, format=None):
        return Response("ok")

def getInfo(request,personal_id):
    if request.method == 'GET':
        info = Info.objects.filter(personal_code=personal_id).last()
        info_data = {}

        info_data['cctvURL'] = info.cctvURL
        info_data['gps'] = info.gps

        return JsonResponse(info_data, safe=False, json_dumps_params={'ensure_ascii': False})
    elif request.method == 'POST':
        pass


def getUser(request):
    if request.method == 'GET':
        user = Profile.objects.order_by('id')
        user_data={}
        public_code =""
        public_code2={}
        public_code3=[]
        for i in range(len(user)):
            public_code2[i]=user[i].public_code
        for i in range(len(public_code2)):
            if public_code2[i] not in public_code3:
                public_code3.append(public_code2[i])
        for i in range(len(public_code3)):
            public_code+=public_code3[i]+"/"
        user_data['public_code']=public_code



        personal_code =""
        personal_code2={}
        for i in range(len(user)):
            personal_code2[i]=user[i].personal_code
        for i in range(len(personal_code2)):
            personal_code+=personal_code2[i]+"/"
        user_data['personal_code']=personal_code
        data=""
        for i in range(len(user)):
            data = data+user[i].public_code+"&&"+user[i].personal_code+"/"
        user_data['data']=data

        name=""
        name2={}
        for i in range(len(user)):
            name2[i]=user[i].user
        for i in range(len(name2)):
            name+=str(name2[i])+"/"
        user_data['name']=name


        return JsonResponse(user_data, safe=False, json_dumps_params={'ensure_ascii': False})
    elif request.method == 'POST':
        pass

@csrf_exempt
def raspberry(request,personal_id):
    if request.method == 'GET':
        info_data={}
        info=Info.objects.filter(personal_code=personal_id).last()
        info_data['gps']=info.gps
        return JsonResponse(info_data)

    elif request.method == 'POST':
        cctvURL = request.POST.get('cctvURL', None)
        gps = request.POST.get('gps', None)
        public_code=request.POST.get('public_code',None)
        info = Info.objects.filter(personal_code=personal_id).last()
        if gps!="Null":
            info.gps = gps
        info.cctvURL = cctvURL
        info.personal_code=personal_id
        info.save()
        info_data={}
        info_data['personal_id']=personal_id
        return JsonResponse(info_data)


def jes(request):
    return render(request, 'web/mypage.html',{});

