# wb-reactive-web
wb reactive web

This project is build on java reactive streams and spring webflux with mongodb reactive repository on netty

I have tried to cover all curd opertaion in webflux 

There is a user profile entity and we have performed curd operation on this entity with webflux

curl -v -X POST  http://localhost:8081/profiles -H "content-type: application/json" -d'{"name":"Saurabh Singh","username":"saurabh","password":"saurabh","email":"saurabhsingh@worldbuild.com","phone":"8948698899","website":"worldbulid.com"}' |json_pp

 curl -v -X PUT  http://localhost:8081/profiles/5d5997a5291aa140094c67d1 -H "content-type: application/json" -d'{"name":"Shivam Singh","username":"shivam","password":"shivam","email":"shivamsingh@worldbuild.com","phone":"7893949833","website":"worldbulid.com"}' |json_pp
 
 curl -v -X GET localhost:8081/profiles |json_pp
 
 curl -v -X GET localhost:8081/profiles/5d59979a291aa140094c67d0 |json_pp
 
 curl -v -X DELETE localhost:8081/profiles/5d59979a291aa140094c67d0
 

And We have also a SSE that notify all the events
#localhost:8081/profiles/event

[{"data":{"action":"CREATE","profile":{"id":"5d5997a5291aa140094c67d1","added":"2019-08-18T18:23:33.190+0000","updated":"2019-08-18T18:23:33.190+0000","name":"saurabh","email":"saurabh@worldbuild.com","phone":"8948698899","website":"worldbulid.com","company":null,"address":null,"username":null,"password":null},"timestamp":"2019-08-18T18:23:33.196+0000"}},
{"data":{"action":"UPDATE","profile":{"id":"5d5997a5291aa140094c67d1","added":"2019-08-18T18:23:33.190+0000","updated":"2019-08-18T18:23:57.369+0000","name":"Shivam","email":"shivam@worldbuild.com","phone":"74389303002","website":"worldbulid.com","company":null,"address":null,"username":null,"password":null},"timestamp":"2019-08-18T18:23:57.377+0000"}},
{"data":{"action":"DELETE","profile":{"id":"5d59979a291aa140094c67d0","added":"2019-08-18T18:23:22.657+0000","updated":"2019-08-18T18:23:22.657+0000","name":"saurabh","email":"saurabh@worldbuild.com","phone":"8948698899","website":"worldbulid.com","company":null,"address":null,"username":null,"password":null},"timestamp":"2019-08-18T18:25:11.404+0000"}}]


And We have also websocket as well for messeging all the event fired of server

ws://localhost:8081/ws/profiles
