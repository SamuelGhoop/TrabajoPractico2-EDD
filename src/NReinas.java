public class NReinas {

    private static final int N = 8;
    private static int contadorSoluciones = 0;

    static void imprimirTablero(int[][] tablero) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print((tablero[i][j] == 1 ? "R " : ". "));
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean esSeguro(int[][] tablero, int fila, int columna) {
        boolean condicionSeguridad = true;
        int i = 0;
        while ((condicionSeguridad == true) && i < columna ) { // revisa misma fila hacia la izquierda
            if (tablero[fila][i] == 1) { // si hay reina es inseguro poner la otra
                condicionSeguridad = false;
            }
            i++;

        }
        i = fila; //indicar fila
        int j = columna;
        while ((condicionSeguridad == true) && i >= 0 && j >= 0) { // diagonal superior izquierda
            if (tablero[i][j] == 1) {
                condicionSeguridad = false;
            }
            i--;
            j--;
        }
        i = fila;
        j = columna;
        while ((condicionSeguridad == true) && i < N && j >= 0) { // diagonal inferior izquierda
            if (tablero[i][j] == 1) {
                condicionSeguridad = false;
            }
            i++;
            j--;
        }
        return condicionSeguridad; //si no hay riesgo, devuelve true
    }


    static void resolverReinas(int[][] tablero, int columna) { // Metodo backtracking
        if (columna == N) { // Condicion de parada / Goal: Encontramos una solucion tablero lleno
            contadorSoluciones++; //para llevar la cuenta
            System.out.println("Solucion #" + contadorSoluciones + ":");
            imprimirTablero(tablero); // visualizamos como quedan las reinas en el tablero

        } else {
            for (int i = 0; i < N; i++) { // Recorremos todas las filas (saltamos entre ellas en la recursion)
                if (esSeguro(tablero, i, columna)) { // Validamos si es posible poner reina ahi
                    tablero[i][columna] = 1; // si si la ponemos
                    resolverReinas(tablero, columna + 1); // llamado recursivo que avanza la columna (ya pusimos reina en esta columna)
                    tablero[i][columna] = 0; // backtracking que devuelve despues de hacer los llamados recursivos
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] tablero = new int[N][N];
        resolverReinas(tablero, 0);
        System.out.println("cantidad de solucione: " + contadorSoluciones);
    }
}
