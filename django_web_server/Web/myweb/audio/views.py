from django.shortcuts import render, redirect
from django.views.generic import TemplateView
from django.core.files.storage import FileSystemStorage
from .forms import AudioForm
from .models import Song
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from rest_framework.views import APIView

def upload(request):
    context={}
    if request.method == 'POST':
        uploaded_file = request.FILES['document']
        fs=FileSystemStorage()
        name=fs.save(uploaded_file.name, uploaded_file)
        context['url']=fs.url(name)
    return render(request, 'audio/upload.html',context)

def audio_list(request):
    songs=Song.objects.all()
    return render(request,'audio/audio_list.html',{
        'songs':songs})

def upload_audio(request):
    if request.method == 'POST':
        form = AudioForm(request.POST, request.FILES)
        if form.is_valid():
            form.save()
            #return redirect('audio_list')
            return render(request, 'audio/audio_null.html',{})
    else:
        form=AudioForm()
    return render(request, 'audio/upload_audio.html',{
        'form':form
        })

def getFile(request):
    if request.method == 'GET':

        file = Song.objects.all()
        file_data={}
        file_name=""
        file_name2={}
        num=[]
        for i in range(len(file)):
            file_name2[i]=str(file[i].audio_file)
        for i in range(len(file_name2)):
            file_name+=file_name2[i]+","
        file_data['audio_file']=file_name

        return JsonResponse(file_data, safe=False, json_dumps_params={'ensure_ascii': False}) # json으로 응답

@csrf_exempt
def Android(request):
    if request.method == 'POST':
        title2 = requests.POST['title']
        audio_file2=requests.POST['audio_file']
        song = Song.objects.filter(title=title2).last()
        song.title = title2
        song.audio_file = audio_file2
        song.save()
    elif request.method == 'GET':
        return HttpResponse('hello')


class RecordingList(APIView):
    def get(self,request):
        pass

    def post(self,request):
        print(request.__dict__)
        if request.FILES['myfile']:
            audio_file = request.FILES['myfile']
            fs = FileSystemStorage()
            title = fs.save(audio_file.name, audio_file)
            uploaded_file_url = fs.url(title)

            return HttpResponse('hello')













