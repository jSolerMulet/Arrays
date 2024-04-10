package cuatroenraya;

import java.util.Scanner;

public class JuegoNoEjecutable {
    
    // Definicion de constantes para el tamaño del tablero y el numero de fichas para ganar
    private static final int NUM_FILAS = 3;
    private static final int NUM_COLUMNAS = 3;
    private static final int FICHAS_PARA_GANAR = 3;
    
    // Variables de instancia para el tablero y el jugador actual
    private String tablero;
    private char jugadorActual;

    // Constructor para inicializar el juego
    
    public JuegoNoEjecutable() {
        // Inicializacion del tablero y del jugador
        tablero = inicializarTablero();
        jugadorActual = 'X';
    }

    // Metodo para inicializar el tablero con espacios en blanco
    
    private String inicializarTablero() {
        // Inicializar una cadena vacÃ­a
        String tableroInicial = "";
        // Rellenar el tablero con espacios en blanco
        for (int filas = 0; filas < NUM_FILAS * NUM_COLUMNAS; filas++) {
            tableroInicial += " ";
        }
        return tableroInicial; // Devolver el tablero inicializado
    }

    // Metodo para mostrar el tablero en la consola
    
    private void mostrarTablero() {
        // Iterar sobre las filas y columnas del tablero
        for (int fila = 0; fila < NUM_FILAS; fila++) {
            for (int columna = 0; columna < NUM_COLUMNAS; columna++) {
                // Imprimir la ficha en la posicion (i, j) del tablero
                System.out.print("|" + obtenerFicha(fila, columna));
            }
            // Imprimir separador de fila
            System.out.println("|");
            System.out.println("-------"); // Imprimir separador de columna
        }
    }

    // Metodo para obtener el caracter de una ficha en una posicion especifica del tablero
    
    private char obtenerFicha(int fila, int columna) {
        // Calcular el indice correspondiente en la cadena del tablero
        return tablero.charAt(fila * NUM_COLUMNAS + columna);
    }

    // Metodo principal para iniciar el juego
    
    public void iniciarJuego() {
        // Crear un objeto Scanner para la entrada del usuario
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Mostrar el tablero actual
            mostrarTablero();

            // Solicitar al jugador actual que elija una columna
            System.out.println("Jugador " + jugadorActual + ", elige una columna (1-3): ");
            // Leer la columna seleccionada por el jugador
            int columna = sc.nextInt() - 1;

            // Verificar si la columna seleccionada es valida y esta disponible
            if (columna >= 0 && columna < NUM_COLUMNAS && obtenerFicha(0, columna) == ' ') {
                // Colocar la ficha en la columna seleccionada
                int fila = colocarFicha(columna);

                // Verificar si el jugador actual ha ganado
                if (esGanador(fila, columna)) {
                    mostrarTablero();
                    System.out.println("¡Jugador " + jugadorActual + " gana!");
                    break; // Salir del bucle mientras
                }

                // Verificar si hay un empate
                if (tableroCompleto()) {
                    mostrarTablero();
                    System.out.println("¡Empate!");
                    break; // Salir del bucle mientras
                }

                // Cambiar al siguiente jugador
                cambiarJugador();
            } else {
                // Si la columna no es valida o esta llena, imprimir mensaje de error
                System.out.println("Columna invalida. Intentalo de nuevo.");
            }
        }
    }

    // Metodo para colocar una ficha en la columna seleccionada
    
    private int colocarFicha(int columna) {
        // Iterar sobre las filas desde abajo hacia arriba
        int fila;
        for (fila = NUM_FILAS - 1; fila >= 0; fila--) {
            // Verificar si la casilla estÃ¡ vacÃ­a
            if (obtenerFicha(fila, columna) == ' ') {
                // Actualizar el tablero con la nueva ficha en la posicion correcta
                tablero = tablero.substring(0, fila * NUM_COLUMNAS + columna) +
                          jugadorActual +
                          tablero.substring(fila * NUM_COLUMNAS + columna + 1);
                return fila; // Devolver la fila donde se coloca la ficha
            }
        }
        return -1; // Devolver -1 si la columna esta llena (no deberia ocurrir)
    }

    // Metodo para cambiar al siguiente jugador
    
    private void cambiarJugador() {
        jugadorActual = (jugadorActual == 'X') ? 'O':'X'; // Cambiar entre 'X' y 'O'
    }

    // Metodo para verificar si el jugador actual ha ganado
    
    private boolean esGanador(int fila, int columna) {
        char ficha = jugadorActual;

        // Verificar fila
        int contador = 1;
        // horizontal derecha
        contador += contarFichasEnDireccion(fila, columna, 0, 1); 
        // horizontal derecha
        contador += contarFichasEnDireccion(fila, columna, 0, -1); 
        if (contador >= FICHAS_PARA_GANAR) return true;

        // Verificar columna
        contador = 1;
        // vertical abajo
        contador += contarFichasEnDireccion(fila, columna, 1, 0); 
        if (contador >= FICHAS_PARA_GANAR) return true;

        // Verificar diagonal \
        contador = 1;
        // diagonal inferior derecha
        contador += contarFichasEnDireccion(fila, columna, 1, 1); 
        // diagonal superior izquierda
        contador += contarFichasEnDireccion(fila, columna, -1, -1); 
        if (contador >= FICHAS_PARA_GANAR) return true;

        // Verificar diagonal /
        contador = 1;
        // diagonal inferior izquierda
        contador += contarFichasEnDireccion(fila, columna, 1, -1); 
        // diagonal superior derecha
        contador += contarFichasEnDireccion(fila, columna, -1, 1); 
        return contador >= FICHAS_PARA_GANAR;
    }

    // Metodo para contar el numero de fichas en una direccion especifica
    
    private int contarFichasEnDireccion(int fila, int columna, int dirFila, int dirColumna) {
        char ficha = jugadorActual;
        int contador = 0;
        // Calcular las coordenadas de la siguiente casilla en la direccion dada
        int filasTot = fila + dirFila;
        int columTot = columna + dirColumna;
        // Contar las fichas del jugador actual en la direcciÃ³n dada
        while (filasTot >= 0 && filasTot < NUM_FILAS && columTot >= 0 && columTot < NUM_COLUMNAS 
        		&& obtenerFicha(filasTot, columTot) == ficha) {
            contador++;
            filasTot += dirFila; // Moverse a la siguiente casilla en la direcciÃ³n dada
            columTot += dirColumna;
        }
        return contador; // Devolver el numero de fichas encontradas en la direcciÃ³n dada
    }

    // Metodo para verificar si el tablero estÃ¡ completo (empate)
    
    private boolean tableroCompleto() {
        // Iterar sobre todas las casillas del tablero
        for (int table = 0; table < NUM_FILAS * NUM_COLUMNAS; table++) {
            // Verificar si hay al menos una casilla vacia
            if (tablero.charAt(table) == ' ') {
            	// Si hay una casilla vacia, el juego no esta completo
            	return false; 
            }
        }
        // Si no hay casillas vacias, el juego esta completo (empate)
        return true; 
    }
}