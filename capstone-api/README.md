# Traveloka Chatbot API
Welcome to our Chatbot API

## What you need
1. Cloud Environment: Google Cloud Platform (VM Instances, Cloud Storage, VPC Network)
2. Programming Language: Python
3. Web Server: Flask API
4. Server: VM Instances

# How to setup Locally
1. clone the project first
2. checkout to branch `develop-cc` 
3. install python3 (>3.7 or latest)
4. install pip (>18.1 or latest)
5. install requirement.txt with `pip install -r requirement.txt`
6. run `app.py` for running the API script with `py app.py` or `flask run`
7. open the link from Flask with `Postman` and change method to `POST` with `/predict` route and body JSON `{ "input" : "is it children under 6 years?"}`

# How to setup with Google Cloud Platform
## Create VM Instances
1. Pick a name for VM Instance by yourself
2. Choose `asia-southeast2(Jakarta)` region and `asia-southeast2-a` zone or by default zone
3. Choose N1 series and machine tipe `n1-standard-2 (2vCPU 7,5GB)`
4. Boot disk section
    - Operation system : Ubuntu
    - Version : 18.04 LTS
    - Boot disk type : SSD Persistent Disk
    - Size : 10 GB
5. Access scopes : Allow full access to all Cloud APIs
6. Firewall : Allow HTTP Traffic
7. Create the VM Instances

## Create Cloud Storage
1. Choose Cloud Storage on navigation menu
2. Click `Create Bucket`
3. Name your bucket as you wish
4. Location data : Region and choose `asia-southeast2 (Jakarta)`
5. Create the Bucket
6. Upload the [final-model.h5](https://drive.google.com/file/d/1ftbi-3q988SxfFOrnr12FRT_xIZ3pGFA/view?usp=sharing) to the bucket

## Create Firewall Rules
1. Choose VPC Network > Firewall
2. Click `Create a Firewall Rule`
3. Name your firewall by yourself
4. Target Tags : http-server
5. source IPv4 ranges = 0.0.0.0/0
6. Protocols and port > tcp = 8080
7. Create the Firewall rule

###  Setting up VM Instances for Production Server
1. Click `SSH` on your VM Instances
2. After your already inside the SSH, you have to install some depedencies
    - `sudo apt-get update`
    - `sudo apt-get install bzip2 libxml2-dev libsm6 libxrender1 libfontconfig1`
    - `sudo add-apt-repository ppa:deadsnakes/ppa`
    - `sudo apt-get update`
    - `sudo apt install python3.9`
    - check the version first after install python3.9 with `python3 --version`, if the result still `3.6.x` you do below
    - `sudo update-alternatives --install /usr/bin/python3 python3 /usr/bin/python3.6 1`
    - `sudo update-alternatives --install /usr/bin/python3 python3 /usr/bin/python3.9 2`
    - `sudo update-alternatives --config python3` choose 2 for pick python 3.9
    - check the python version again, right now should be python 3.9.x
    - install pip3 with `sudo apt install python3-pip`
    - upgrade pip3 with `sudo pip3 install --upgrade pip`
    - install all pip depedencies with `pip3 install -r requirements.txt`
    - clone the Github repository `git clone https://github.com/yudaa48/traveloka-chatbot.git`
    - copy your uploaded model from cloud storage to your VM with `gsutil cp gs://YOUR_BUCKET_NAME/final-model.h5 traveloka-chatbot/capstone-api`
    - go to directory capstone-api and then running the script with `python3 app.py`