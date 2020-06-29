from django.shortcuts import render, redirect
import json
from django.views import View
from django.http import JsonResponse
from accounts.serializers import ProfileSerializer
from .models import Profile
from django.contrib.auth.models import User
from rest_framework import viewsets
from django.contrib import auth
from django.contrib import messages
from django.contrib.auth.decorators import login_required
from .forms import UserRegisterForm


#def signup(request):
#    if request.method=="POST":
#        if request.POST["password1"] == request.POST["password2"]:
#            user = User.objects.create_user(
#                username=request.POST["username"], password=request.POST["password1"])
#            auth.login(request,user)
#            return redirect('accounts:home')
#        return render(request, 'accounts/signup.html')
#    return render(request, 'accounts/signup.html')




#def login(request):
#    if request.method == "POST":
#        username = request.POST['username']
#        password = request.POST['password']
#        user = auth.authenticate(request,username=username, password=password)
#        if user is not None:
#            auth.login(request, user)
#            return redirect('accounts:home')
#       else:
#           return render(request, 'accounts/login.html', {'error' : 'username or password is incorrect'})
#    else:
#        return render(request, 'accounts/login.html')

#def logout(request):
#   auth.logout(request)
#    return redirect('accounts:home')

class ProfileViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    def post(self, request, format=None):
        return Response("ok")


def home(request):
    return render(request, 'accounts/home.html')


def register(request):
    if request.method == 'POST':
        form = UserRegisterForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            messages.success(request, f'Your account has been created! You are now able to log in')
            return redirect('accounts:home')
    else:
        form = UserRegisterForm()
    return render(request, 'accounts/register.html', {'form': form})

def getUser(request):
    if request.method == 'GET':
        #profile = Profile.objects.order_by('created_date').reverse()
        user=User.objects.all().order_by('id')
        profile_data = {}
        for i in range(len(user)):
            profile_data[i]=user[i].username
        #profile_data['user'] = user
        return JsonResponse(profile_data) # json으로 응답
        #return HttpResponse(json.dumps(home_data), content_type="application/json")
        #return render(request, 'uleung/getHomeInfo.html', json.dumps(home_data))
    elif request.method == 'POST':
        pass
