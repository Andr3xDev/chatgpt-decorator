# AIintegration
## ESCUELA COLOMBIANA DE INGENIERÍA - AYGO

# Diagrama de Arquitectura

 ![image](https://github.com/ginnko2019/aaintegration/blob/master/Assets/ArquitecturaAiintegrationS.jpg)

## AWS S3: 
Servicio de AWS empleado para el despliegue del front-end. Este servicio se emplea comúnmente para alojar documentos. Cómo el front-end posee tecnologías simples es perfecto para poderlo alojar y desplegar.
## AWS EC2: 
Máquina virtual de AWS que permite desplegar aplicaciones back-end. Se seleccionó una instancia
T2-MICRO.
## AWS Elastic IP: 
En AWS, el servicio EC2 utiliza direcciones IP dinámicas por razones de seguridad, lo que puede ocasionar problemas de integración con el front-end,
ya que estas direcciones pueden cambiar en determinados momentos, provocando desconexiones entre el back-end y el front-end. Para solucionar este inconveniente, AWS
ofrece Elastic IP, que proporciona una direccián IP estática y permite una conexión transparente entre el back-end y el
front-end.
Azure OpenAI Service: Este servicio proporciona herramientas y APIs que facilitan la integración de capacidades de procesamiento del lenguaje natural, generación de texto,
comprensión de lenguaje y otras funcionalidades de IA en aplicaciones y sistemas.
## OpenAI ChatGPT: Este servicio contiene un conjunto
de herramientas proporcionadas por OpenAI que permite a desarrolladores y empresas acceder, integrar y utilizar modelos de inteligencia artificial avanzados en sus aplicaciones y
servicios. Esta plataforma ofrece una variedad de productos y APIs, facilitando el desarrollo de soluciones basadas en
inteligencia artificial.

# Diagrama de Clases

 ![image](https://github.com/ginnko2019/aaintegration/blob/master/Assets/AIIntegrationDiagramClass.jpg)

# Patrones de Software empleados
Para garantizar una integración adecuada en el prototipo propuesto se emplearon diferentes patrones investigados en el estado del arte de este documento, cada patrón se detallará
a continuación junto con la forma en que se implementó dentro del software.
## Patrón de Two-Phase Predictions
Dentro del prototipo en el front-end que es la aplicación del lado del cliente, se creó un módulo desarrollado en JavaScript que realiza una validación previa de la entrada
del usuario, con el objetivo de responder preguntas triviales con respuestas pre-programadas, que garantizan el cuidado
y la optimización de los recursos. Dicho módulo posee internamente un dataSet con datos de preguntas y respuestas
típicas.
## Patrón de Componentes Independientes (Independent Components)
Dentro del back-end del prototipo se implementó un esquema de adaptadores abstractos, que permite adicionar
nuevos controladores de herramientas de IA sin necesidad de realizar modificaciones mayores en el código. Esto permite
que el software sea escalable y fácilmente mantenible en el tiempo.
## Patrón de Validación de Entradas 
Cuando la petición llega al back-end desde la aplicación de Cliente, la entrada se procesa mediante una clase llamada
InputAnalizer, esta clase se encarga de analizar el contenido de la respuesta para determinar si esta debe ser mejorada
(se utiliza otro controlador de IA para lograrlo), si contiene caracteres inválidos que necesiten ser ajustados o si está lista
para ser procesada por los modelos de IA. 
## Patrón Adaptador
Este patrón va muy de la mano con el patrón de com- ponentes independientes, dentro de la solución propuesta
se empleó como un medio de estandarización que permite los controladores de las IA se puedan adaptar fácilmente al
software cumpliendo un contrato funcional, que garantiza que cada uno de estos implemente los métodos necesarios para poder integrarse efectivamente con la aplicación. 
