#!/bin/bash
port=${1:-8081}

curl -H "content-type: application/json" -d'{"name":"Saurabh Singh","username":"saurabh","password":"saurabh","email":"saurabhsingh@worldbuild.com","phone":"8948698899","website":"worldbulid.com"}' http://localhost:${port}/profiles
