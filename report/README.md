# Report

In this directory you can find a LaTeX template aimed to develop the report of your assignment.

# Diseño del Programa

## Overview

Nuestro objetivo es crear un simulador que emule el comportamiento de una **Random Access Machine** o en su forma abreviada **RAM**. Esta es una máquina abstracta perteneciente a las **register machines**, muy similar a la **counter machine** y **Turing-equivalente**.
Consta de un número infinito de registros y un número infinito de instrucciones ( condición que no llevaremos a la práctica pues nos es imposible reprensentar programas infinitos ), estas se cargan en la **memoria de programa** mientras que los registros se encuentran en una **memoria de datos**. A estas memorias accede una **Unidad Aritmética, Lógica y de Control** que se encarga de procesar las instrucciones y operar con los datos que se almacenan en los registros y los que se leen a partir de la **cinta de entrada** por medio de una cabeza lectora. El resultado de estas operaciones se almacena en la **cinta de salida** por medio de una cinta de escritura.

![Foto RAM](../misc/ram_machine.png)

 Para empezar a diseñar el programa vamos a descomponer la máquina en una _jerarquía de dependencia_ a fin de establecer qué componentes son dependientes entre sí:

![](../misc/jerarquia_inicial_.png)

 Podemos ver que:

 +  La _cinta de entrada_ es directamente requerida por la _cabeza lectora_ de la máquina y ambas conforman la _unidad de entrada_.

 +  Ocurre lo mismo de manera análoga en el caso de la _unidad de salida_.

 +  A pesar de que la existencia de ambas memorias es indispensable para el funcionamiento de la máquina ninguna está contenida en la otra y no se comunican entre sí de forma directa sino por medio de la Unidad Aritmética, Lógica y de Control.

 + El registro IP a pesar de ser un registro no está contenido en la memoria de datos, se implementa de forma independiente.


 +  Por último ambas unidades, las dos memorias y el _registro apuntador de instrucción (IP)_ son requeridas por la Unidad Aritmético - Lógica y de Control y por lo tanto esta está conformada por todos ellos.


## Diseño del código

### Clases

#### ReadingHead

La clase ReadingHead se encarga de leer los datos de la **cinta de entrada** a partir de un fichero que se pasa por parámetro, por medio de un objeto de la clase **BufferedReader**.

#### WritingHead

La clase WritingHead se encarga de escribr los datos resultantes de la ejecución del programa en un **fichero de salida** que representa la **cinta de salida** por medio de un objeto de la clase **FileOutputStream**.
