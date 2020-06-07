# greeting-openliberty

## To build the Greeting.war file 
```
$ ./gradlew clean build
$ ./gradlew libertyStop
```

## To build the docker image
```
$ docker build -t awiradarma/greeting:1.0-OL -f src/main/docker/Dockerfile .
```

## To run the image
```
$ docker run -d --rm --name xyz -p 9080:9080 awiradarma/greeting:1.0-OL

$ docker ps
CONTAINER ID        IMAGE                        COMMAND                  CREATED             STATUS              PORTS                              NAMES
3c8620aad12c        awiradarma/greeting:1.0-OL   "/opt/ol/helpers/run…"   23 seconds ago      Up 21 seconds       0.0.0.0:9080->9080/tcp, 9443/tcp   xyz
```

## Try to hit the endpoint
```
$ curl localhost:9080/greeting
hello ( 3c8620aad12c/172.17.0.2 ) 
```

## To see the Open J9 shared class data cache, exec into the running container
``` 
$ docker exec -it xyz /bin/sh

sh-4.4$ java -Xshareclasses:name=liberty,cacheDir=/output/.classCache/,listAllCaches

Listing all caches in cacheDir /output/.classCache/

Cache name      	level         cache-type      feature         layer       OS shmid       OS semid       last detach time

Compatible shared caches
liberty         	Java11 64-bit  persistent      cr              0                                         In use         
liberty         	Java11 64-bit  persistent      cr              1                                         In use         

sh-4.4$ java -Xshareclasses:name=liberty,cacheDir=/output/.classCache/,printAllStats 2>&1 | egrep "Greeting"
	/opt/ol/wlp/usr/servers/defaultServer/apps/Greeting.war!/WEB-INF/classes/id/bogor/quarkus/GreetingApplication.class
2: 0x00007FA4DBB54EFC ROMCLASS: id/bogor/quarkus/GreetingApplication at 0x00007FA4DA945DB0.
	/opt/ol/wlp/usr/servers/defaultServer/apps/Greeting.war!/WEB-INF/classes/id/bogor/quarkus/GreetingService.class
2: 0x00007FA4DBAC12F0 ROMCLASS: id/bogor/quarkus/GreetingService at 0x00007FA4DAC710F8.
	ROMMETHOD: getRemoteGreeting Signature: ()Ljava/lang/String; Address: 0x00007FA4DAC711F8
	/opt/ol/wlp/usr/servers/defaultServer/apps/Greeting.war!/WEB-INF/classes/id/bogor/quarkus/Greeting.class
2: 0x00007FA4DBAC1134 ROMCLASS: id/bogor/quarkus/Greeting at 0x00007FA4DAC71348.
```
