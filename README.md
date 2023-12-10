## Para inicializar Kafka local
Para inicializar kafka local se debe ejecutar el siguiente comando, estando en la 
raíz de este proyecto donde se encuentra en docker-compose.yml:
```
# sudo service docker start
# docker-compose up -d
``` 

## Para verificar salud de la aplicación
Con la siguiente API se procede a comprobar la salud de la aplicación -> [Health](http://localhost:6094/api/actuator/health)
```
http://localhost:6091/api/actuator/health
```

## Endpoints
Todos los endpoints se pueden verificar y ejecutar entrando al siguiente enlace luego de ejecutar el proyecto

[Swagger-UI](http://localhost:6091/api/swagger-ui.html)

```
http://localhost:6091/api/swagger-ui.html
```