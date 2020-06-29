from requests import get

def download(url, file_name):
    with open(file_name,"wb") as file:
        response = get(url)
        file.write(response.content)

if __name__ == '__main__':
    url="http://jes9401.pythonanywhere.com/meida/audio/jsg.m4a"
    download(url,"test.m4a")
