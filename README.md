# Problema de las 8 Reinas - Backtracking

## Explicación del problema

El problema de las 8 reinas consiste en ubicar 8 reinas en un tablero de ajedrez de 8x8 de forma que ninguna reina ataque a otra. En ajedrez, una reina puede moverse en horizontal, vertical y diagonal, por lo que el desafío es encontrar una configuración donde las 8 reinas coexistan sin amenazarse entre sí.

Este problema se resuelve usando **backtracking** porque no existe una fórmula directa para encontrar las posiciones válidas. El algoritmo debe explorar posibilidades, y cuando detecta que una configuración parcial no puede llevar a una solución válida, retrocede e intenta otra opción.

## Definición de las restricciones

Dos reinas **no pueden** estar ubicadas en:

- **Misma fila:** no puede haber dos reinas en la misma fila del tablero.
- **Misma columna:** no puede haber dos reinas en la misma columna del tablero.
- **Misma diagonal principal:** la diagonal que va de arriba-izquierda a abajo-derecha.
- **Misma diagonal secundaria:** la diagonal que va de abajo-izquierda a arriba-derecha.

## Pseudocódigo del algoritmo

```
procedimiento resolverReinas(tablero, columna):

    // Caso base: si ya ubicamos reina en todas las columnas, encontramos solución
    si columna == 8:
        guardar copia del tablero como solución
        retornar

    // Si hay una reina fija en esta columna (caso 2):
    para cada fila i de 0 a 7:
        si la posición (i, columna) es fija:
            si esSeguro(tablero, i, columna):
                resolver reinas(tablero, columna + 1)
            retornar  // no probar otras filas en esta columna

    // Si no hay reina fija, probar todas las filas
    para cada fila i de 0 a 7:
        si esSeguro(tablero, i, columna):
            tablero[i][columna] = 1           // colocar reina
            resolverReinas(tablero, columna + 1)  // avanzar a siguiente columna
            tablero[i][columna] = 0           // backtracking: quitar reina


procedimiento esSeguro(tablero, fila, columna):

    // Revisar misma fila hacia la izquierda
    para cada columna j desde 0 hasta columna - 1:
        si tablero[fila][j] tiene reina: retornar falso

    // Revisar diagonal superior izquierda
    i = fila - 1, j = columna - 1
    mientras i >= 0 y j >= 0:
        si tablero[i][j] tiene reina: retornar falso
        i--, j--

    // Revisar diagonal inferior izquierda
    i = fila + 1, j = columna - 1
    mientras i < 8 y j >= 0:
        si tablero[i][j] tiene reina: retornar falso
        i++, j--

    retornar verdadero  // la posición es segura
```

## Preguntas orientadoras

### ¿Qué representa una decisión en este problema?

Una decisión es elegir en qué fila colocar la reina de la columna actual. El algoritmo avanza columna por columna (de la 0 a la 7), y en cada columna prueba todas las filas posibles (de la 0 a la 7) buscando una posición segura.

### ¿Cómo se valida si una reina puede ubicarse en una posición?

Se verifica que no haya otra reina en la misma fila (recorriendo hacia la izquierda), ni en la diagonal superior izquierda, ni en la diagonal inferior izquierda. No se revisan las columnas a la derecha porque aún no se han colocado reinas allí. Esto se implementa en el método `esSeguro()`.

### ¿Cuál es el caso base del algoritmo?

El caso base se alcanza cuando `columna == 8`, es decir, cuando se han colocado reinas exitosamente en las 8 columnas. En ese momento se tiene una solución válida y se guarda una copia del tablero.

### ¿Cuándo debe hacerse backtracking?

El backtracking ocurre cuando ninguna fila de la columna actual es segura para colocar una reina, o después de explorar una rama recursiva. Cuando el algoritmo retorna de una llamada recursiva, quita la reina que había puesto (`tablero[i][columna] = 0`) y prueba la siguiente fila. Si ninguna fila funciona, retorna automáticamente a la columna anterior para cambiar la decisión previa.
