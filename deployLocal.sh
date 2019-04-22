#!/bin/bash

TAG_NAME=$(<LOCAL)
COMPUTER_NAME=$(cat /proc/sys/kernel/hostname)

if [ $COMPUTER_NAME = "maso34" ]; then
<<<<<<< HEAD
    ./deploy.sh EBFC0D6EB6ED7C6334D2 RpJgjrsVdCmTFEmqbP1NPFdUhCWAckbff2p3zsJX http://localhost:8080/v2-beta/projects/1a5 lcl $TAG_NAME
=======
    ./deploy.sh EBFC0D6EB6ED7C6334D2 RpJgjrsVdCmTFEmqbP1NPFdUhCWAckbff2p3zsJX http://localhost:8080/v2-beta/projects/1a5 lcl
elif [ $COMPUTER_NAME = "maso89" ]; then
    ./deploy.sh 0C0636EC95524674AABF YVLbqbL14gcq2yQPJvbwz3MeJK5UAWNgKoYe9UCL http://localhost:8080/v2-beta/projects/1a5 lcl
>>>>>>> c3b2a3ff76dd178a5faf171498b8987e44f21de8
else
    ./deploy.sh C3EB1019A856653D3D7A WcF7KbNgrUStCYryH7YWKPvKf3VkfV3rnvtb4QnF http://localhost:8080/v2-beta/projects/1a5 lcl
fi
