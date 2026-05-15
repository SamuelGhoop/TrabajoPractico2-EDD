import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class NReinas {

    private static final int N = 8;
    private static int contadorSoluciones = 0;
    private static boolean[][] fija = new boolean[N][N];

    private static List<int[][]> soluciones = new ArrayList<>();

    public static List<int[][]> getSoluciones() { return soluciones; }
    public static void limpiarSoluciones() { soluciones.clear(); }

    // Getters y Setters
    public static int getN() { return N; }
    public static int getContadorSoluciones() { return contadorSoluciones; }
    public static void setContadorSoluciones(int valor) { contadorSoluciones = valor; }
    public static boolean[][] getFija() { return fija; }
    public static void setFija(boolean[][] nuevaFija) { fija = nuevaFija; }

    static void imprimirTablero(int[][] tablero) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tablero[i][j] == 1 && fija[i][j]) {
                    System.out.print("F ");
                } else if (tablero[i][j] == 1) {
                    System.out.print("R ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean esSeguro(int[][] tablero, int fila, int columna) {
        for (int j = 0; j < columna; j++) {
            if (tablero[fila][j] == 1) return false;
        }
        for (int i = fila - 1, j = columna - 1; i >= 0 && j >= 0; i--, j--) {
            if (tablero[i][j] == 1) return false;
        }
        for (int i = fila + 1, j = columna - 1; i < N && j >= 0; i++, j--) {
            if (tablero[i][j] == 1) return false;
        }
        return true;
    }

    static void resolverReinas(int[][] tablero, int columna) {
        if (columna == N) {
            contadorSoluciones++;
            // Guardar copia del tablero
            int[][] copia = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    copia[i][j] = tablero[i][j];
            soluciones.add(copia);

            System.out.println("Solucion #" + contadorSoluciones + ":");
            imprimirTablero(tablero);
            return;
        }

        for (int i = 0; i < N; i++) {
            if (fija[i][columna]) {
                if (esSeguro(tablero, i, columna)) {
                    resolverReinas(tablero, columna + 1);
                }
                return;
            }
        }

        for (int i = 0; i < N; i++) {
            if (esSeguro(tablero, i, columna)) {
                tablero[i][columna] = 1;
                resolverReinas(tablero, columna + 1);
                tablero[i][columna] = 0;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] tablero = new int[N][N];

        System.out.println("Seleccione modo:");
        System.out.println("1. Tablero vacio");
        System.out.println("2. Tablero con una reina ya ubicada");
        int modo = scanner.nextInt();

        if (modo == 2) {
            System.out.print("Ingrese fila de la reina (0-7): ");
            int filaInicial = scanner.nextInt();
            System.out.print("Ingrese columna de la reina (0-7): ");
            int colInicial = scanner.nextInt();

            if (filaInicial < 0 || filaInicial >= N || colInicial < 0 || colInicial >= N) {
                System.out.println("Posicion invalida.");
                return;
            }

            tablero[filaInicial][colInicial] = 1;
            fija[filaInicial][colInicial] = true;
            System.out.println("Reina fija en (" + filaInicial + ", " + colInicial + ")\n");
        }

        contadorSoluciones = 0;
        resolverReinas(tablero, 0);

        if (contadorSoluciones == 0) {
            System.out.println("No se encontro solucion con esa configuracion.");
        } else {
            System.out.println("Cantidad de soluciones: " + contadorSoluciones);
        }
    }
}