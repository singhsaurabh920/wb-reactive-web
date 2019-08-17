#!/bin/bash
port=${1:-8080}

curl -H "content-type: application/json" -d'{"name":"saurabh","email":"saurabh@worldbuild.com","phone":"8948698899","website":"worldbulid.com"}' http://localhost:${port}/profiles # <1>
