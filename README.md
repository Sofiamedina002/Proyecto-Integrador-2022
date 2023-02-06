### Proyecto Integrador II
***
<h1 align = "center">DIGITAL HOUSE MONEY</h1>


## Introducción del proyecto ✒️
Nuestro proyecto consiste en el desarrollo del Backend de una billetera virtual en la cual un 
usuario podrá registrarse, iniciar sesión y cerrar sesión. También crear una cuenta, cargarle
dinero a la misma, cargar tarjetas tanto de débito como de crédito y realizar operaciones como
transferencias a otras cuentas o ingresos de dinero.
Enmarcados en los lineamientos de trabajo propuestos, utilizamos metodologías del desarrollo 
ágil de software, más específicamente Scrum, con sus eventos, roles y artefactos.
Asistimos a lecturas de sprint, sprint plannings, weeklys, reviews y retrospectives, además de 
los encuentros diarios (dailys) en los que en equipo cada integrante exponía las tareas realizadas 
el día anterior, las del día en curso y los obstáculos encontrados.
El trabajo se dividió en cuatro sprints de dos semanas de duración cada uno, durante las cuáles 
se desarrollaron los eventos antes mencionados, con la presencia del Product Owner y Scrum Master.


## Equipo de trabajo y roles 👨‍💻
* Capone, Julieta: Backend
* Corredor, Jorge: Backend
* Kessy, Santiago: Backend
* Izaguirre, Liam: Backend
* Gutierrez, Martin: Infraestructura
* Medina, Sofía: Backend y Testing


## Tecnologías y herramientas empleadas 🛠️
* Backend: IDE IntelliJ Idea, lenguaje Java enmarcado dentro de Spring framework y Spring Boot, Maven, Amazon Cognito.
* Base de Datos: MySQL Workbench.
* Testing: Postman, RestAssured, IDE Visual Studio Code.
* Infraestructura: Docker, AWS.


## Documentación Técnica
### Backend 🖇
Para el desarrollo del Backend, utilizamos una estructura de microservicios con sus principales componentes: Eureka, Configuration Service y Api Gateway.
Creamos dos microservicios, uno de usuario y otro de cuentas.
Dentro del servicio de usuario tenemos una entidad de usuario, un service, un repository y un controller, también tenemos la configuración a nuestro proveedor de autenticación, Cognito, quien permite el registro, la verificación, el inicio de sesión y el cierre de sesión del usuario.
Dentro del servicio de cuentas tenemos cuatro entidades: cuenta, tarjeta, transacción y transferencia; para cada una de ellas tenemos un service y un repository. Tenemos un solo controller con todos los métodos.
Además tenemos la configuración de Spring Security, nuestro proveedor de seguridad.
La comunicación entre los microservicios se realiza a través de Feign.

![UML](https://gitlab.ctd.academy/ctd/proyecto-integrador-2/proyecto-integrador-1022/0321-ft-c2-back/grupo-08/-/blob/develop-01-14-15/images/uml.jpeg)

#### API y documentación correspondiente en los siguientes links:
- [API User](http://ec2-3-238-159-100.compute-1.amazonaws.com:8081/swagger-ui/index.html#/)
  ![User Swagger](https://gitlab.ctd.academy/ctd/proyecto-integrador-2/proyecto-integrador-1022/0321-ft-c2-back/grupo-08/-/blob/develop-01-14-15/images/UserSwagger.jpeg)

- [API Wallet](http://ec2-3-238-159-100.compute-1.amazonaws.com:8082/swagger-ui/index.html#/)
  ![Account Swagger](https://gitlab.ctd.academy/ctd/proyecto-integrador-2/proyecto-integrador-1022/0321-ft-c2-back/grupo-08/-/blob/develop-01-14-15/images/AccountSwagger.jpeg)


### Testing ✔
- [Casos de Prueba](https://docs.google.com/spreadsheets/d/1yOWyszelE4gts06_Eopfhzrqh4g8B9xj/edit?rtpof=true#gid=1988637572)
- [Reporte de Defectos](https://docs.google.com/spreadsheets/d/1iPIvYp_rbsInkdLQlAVHfaIZD2qYJyxX/edit?rtpof=true#gid=1575197008)
- [Plan de Pruebas](https://drive.google.com/drive/u/0/folders/1inK7pjzRr_LZnZD-HtLTvSVpBZYH0OlX)


### Infraestructura 🔨
La Infraestructura para esta App se concibió en la nube de AWS, plataforma Cloud para alojar servidores y crear infraestructuras dedicadas para todos los tipos de necesidades, esta fue recomendada por DH.

Inicialmente se realizó un diagrama con la mayor robustez posible, tanto en temas de seguridad como de velocidad y escalado.
Esto se puede apreciar en el siguiente diagrama:

![Infraestructura Inicial](https://gitlab.ctd.academy/ctd/proyecto-integrador-2/proyecto-integrador-1022/0321-ft-c2-back/grupo-08/-/blob/develop-01-14-15/images/infrainicial.png)

Finalmente, el MVP al que llegamos, condicionados por el tiempo, las prioridades y los accesos de la cuenta fue el siguiente:

![Infraestructura Final](https://gitlab.ctd.academy/ctd/proyecto-integrador-2/proyecto-integrador-1022/0321-ft-c2-back/grupo-08/-/blob/develop-01-14-15/images/infrafinal.png)

Se compone únicamente de una VPC con una instancia EC2 t3 Medium con Ubuntu 20.04 y una base de datos RDS con motor MySQL.

Dentro de la instancia se encuentran los microservicios que componen a Digital Money, cada uno Dockerizado y orquestado por el Docker Compose.

[Repositorios Docker](https://hub.docker.com/repositories/mgo02)


### Base de Datos 📊

Nos apoyamos en una base de datos con motor MySQL alojada en AWS, esta alojaba los datos de los microservicios de Cuentas y de Usuarios, a continuación el DER de nuestra base de Datos:

![DER](https://gitlab.ctd.academy/ctd/proyecto-integrador-2/proyecto-integrador-1022/0321-ft-c2-back/grupo-08/-/blob/develop-01-14-15/images/bbd.jpeg)