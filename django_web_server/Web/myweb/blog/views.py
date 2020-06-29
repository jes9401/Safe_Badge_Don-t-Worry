from django.shortcuts import render
from django.http import HttpResponse
from .models import Post,Post2
from django.utils import timezone
from django.http import JsonResponse
from django.contrib.auth.models import User
from blog.serializers import TestSerializer
from django.views.decorators.csrf import csrf_exempt
from rest_framework import viewsets


class TestViewSet(viewsets.ModelViewSet):
    queryset = Post.objects.all()
    serializer_class = TestSerializer
    def post(self, request, format=None):
        return Response("ok")
    def put(self, request, format=None):
        return Response("ok")

def post_list(request):
    posts = Post.objects.filter(published_date__lte=timezone.now()).order_by('published_date')
    return render(request, 'blog/post_list.html', {'posts': posts})


@csrf_exempt
def getPost(request):
    if request.method == 'GET':
        post = Post.objects.order_by('created_date')
        post_data = {}
        post_title=""
        post_title2={}
        post_text=""
        post_text2={}
        public_code=""
        public_code2={}

        for i in range(len(post)):
            post_title2[i]=post[i].title
        for i in range(len(post_title2)):
            post_title+=post_title2[i]+","
        post_data['title']=post_title

        for i in range(len(post)):
            post_text2[i]=post[i].text
        for i in range(len(post_text2)):
            post_text+=post_text2[i]+","
        post_data['text']=post_text

        for i in range(len(post)):
           public_code2[i]=post[i].public_code
        for i in range(len(public_code2)):
            public_code+=public_code2[i]+","
        post_data['public_code']=public_code

        post2 = Post2.objects.order_by('created_date')
        post2_title=""
        post2_title2={}
        post2_text=""
        post2_text2={}
        personal_code=""
        personal_code2={}

        for i in range(len(post2)):
            post2_title2[i]=post2[i].title
        for i in range(len(post2_title2)):
            post2_title+=post2_title2[i]+","
        post_data['title2']=post2_title

        for i in range(len(post2)):
            post2_text2[i]=post2[i].text
        for i in range(len(post2_text2)):
            post2_text+=post2_text2[i]+","
        post_data['text2']=post2_text

        for i in range(len(post2)):
           personal_code2[i]=post2[i].personal_code
        for i in range(len(personal_code2)):
            personal_code+=personal_code2[i]+","
        post_data['personal_code']=personal_code

        #return JsonResponse(post_data, safe=False, json_dumps_params={'ensure_ascii': False}) # json으로 응답
        return render(request,'blog/getPost.html',post_data)
    elif request.method == 'POST':
        public_id=request.POST.get('public_id',None)
        personal_id=request.POST.get('personal_id',None)

        post_data = {}

        post = Post.objects.filter(public_code__lte=public_id).order_by('id').last()
        post_data['public_id']=post.public_id
        post_data['personal_id']=post.personal_id
        return JsonResponse(post_data, safe=False, json_dumps_params={'ensure_ascii': False}) # json으로 응답

def public(request,public_id):
    post = Post.objects.filter(public_code=public_id)
    post_data={}
    post_title=""
    post_title2={}
    post_text=""
    post_text2={}

    for i in range(len(post)):
        post_title2[i]=post[i].title
    for i in range(len(post_title2)):
        post_title+=post_title2[i]+","
    post_data['title']=post_title

    for i in range(len(post)):
        post_text2[i]=post[i].text
    for i in range(len(post_text2)):
        post_text+=post_text2[i]+","
    post_data['text']=post_text
    return JsonResponse(post_data,safe=False,json_dumps_params={'ensure_ascii': False})


def personal(request,personal_id):
    post = Post2.objects.filter(personal_code=personal_id)
    post_data={}
    post_title=""
    post_title2={}
    post_text=""
    post_text2={}

    for i in range(len(post)):
        post_title2[i]=post[i].title
    for i in range(len(post_title2)):
        post_title+=post_title2[i]+","
    post_data['title2']=post_title

    for i in range(len(post)):
        post_text2[i]=post[i].text
    for i in range(len(post_text2)):
        post_text+=post_text2[i]+","
    post_data['text2']=post_text
    return JsonResponse(post_data,safe=False,json_dumps_params={'ensure_ascii': False})



