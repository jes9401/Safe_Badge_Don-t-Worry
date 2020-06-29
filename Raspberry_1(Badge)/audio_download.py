from requests import get  
import json
import requests
import time

def download(url, file_name = None):
    if not file_name:
        file_name = url.split('/')[-1]
        with open(file_name, "wb") as file:
            response = get(url)
            file.write(response.content)      

def main():
    past_file_data={}
    while True:
        rs = requests.get('http://jes9401.pythonanywhere.com/audio/getFile/')
        new_file_data=rs.json()
        new_data=new_file_data['audio_file']
        new_data=new_data.split(',')
        if new_file_data == past_file_data or past_file_data=={}:
            print('same')
        elif len(new_data)>1:
            print('data change')
            print(new_data[-1])
            if __name__ == '__main__':
                url = "http://jes9401.pythonanywhere.com/media/"+new_data[-2]
                print(url)
                download(url)
        past_file_data=new_file_data
        
        time.sleep(3)
main()
