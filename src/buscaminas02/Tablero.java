package buscaminas02;

import java.util.Random;

/**
 *
 * @author Antonio Sastre Vázquez.
 */
class Tablero {

    private int filas;
    private int columnas;
    private int minas;
    int[][] posicion;

    Tablero(int filas, int columnas, int minas) {
        this.setFilas(filas);
        this.setColumnas(columnas);
        this.setMinas(minas);
        this.posicion = new int[this.getFilas()][this.getColumnas()];
    }

    public String toString() {// Esto muestra el tablero de juego.
        String nancho;
        String nalto;
        String nalto2;
        String nancho2;

        String mesa = "";
        mesa += "   ";
        for (int l = 0; l < this.getColumnas(); l++) {
            if (l < 9) {
                nancho = " " + (l + 1) + " ";
            } else {
                nancho = " " + (l + 1);
            }
            mesa += " " + nancho;
        }
        mesa += "\n";
        mesa += "   ";
        for (int j = 0; j < this.getColumnas(); j++) {
            mesa += " ___";
        }

        for (int k = 0; k < this.getFilas(); k++) {
            if (k < 9) {
                nalto = " " + (k + 1);
            } else {
                nalto = "" + (k + 1);
            }
            mesa += "\n " + nalto;
            for (int i = 0; i < this.getColumnas(); i++) {
                mesa += "|";
                String mostrar;
                if (this.posicion[k][i] == -1) {
                    mostrar = "___";
                } else if (this.posicion[k][i] == -2) { //Las minas
                    mostrar = "___";
                } else if (this.posicion[k][i] == -3) { // Las banderas sin minas.
                    mostrar = "_⚑_";
                } else if (this.posicion[k][i] == -4) { // Las banderas con minas.
                    mostrar = "_⚑_";
                } else if (this.posicion[k][i] == -5) { // Una casilla destapada que ya ha sido rodeada.
                    mostrar = "MWM";
                } else {
                    mostrar = "_" + this.posicion[k][i] + "_";
                }

                mesa += "" + mostrar + "";

            }
            for (int j = 0; j < this.getFilas(); j++) {
                nalto2 = ""+k+1;
                mesa += "|"+(nalto2);
            }
           /*
            for (int m = 0; m < this.getColumnas(); m++) {
            if (m < 9) {
                nancho = " " + (m + 1) + " ";
            } else {
                nancho = " " + (m + 1);
            }
            mesa += " " + nancho;
            }

            *
            */
        

        }

        return mesa;
    }

    public boolean ganado() {//Boolean que dice si se ha ganado.
        int ganado = 0;

        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                if (this.posicion[i][j] == -1 || this.posicion[i][j] == -3) {//Mientras haya minas sin levantar, o banderas colocadas sin mina debajo, return false.
                    ganado++;
                }
            }
        }
        if (ganado == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String resultado() {//Muestra el tablero de resultado que aparece cuando se gana o se pierde.
        String nancho;
        String nalto;

        String mesa = "";
        mesa += "   ";
        for (int l = 0; l < this.getColumnas(); l++) {
            if (l < 9) {
                nancho = "  " + (l + 1);
            } else {
                nancho = " " + (l + 1);
            }
            mesa += " " + nancho;
        }
        mesa += "\n";
        mesa += "   ";
        for (int j = 0; j < this.getColumnas(); j++) {
            mesa += " ___";
        }

        for (int k = 0; k < this.getFilas(); k++) {
            if (k < 9) {
                nalto = " " + (k + 1);
            } else {
                nalto = "" + (k + 1);
            }
            mesa += "\n " + nalto;
            for (int i = 0; i < this.getColumnas(); i++) {
                mesa += "|";
                String mostrar;
                if (this.posicion[k][i] == -1) {//Casillas no levantadas.
                    mostrar = "_";
                } else if (this.posicion[k][i] == -2) {//Casillas con una mina.
                    mostrar = "*";
                } else if (this.posicion[k][i] == -3) {//Banderas sin mina.
                    mostrar = "X";
                } else if (this.posicion[k][i] == -4) {//Banderas con una mina debajo.
                    mostrar = "*";
                } else {
                    mostrar = "_";// El resto se muestra con "_".
                }

                mesa += "_" + mostrar + "_";

            }
            mesa += "|";
        }
        mesa += "\n\n\"*\" -> Mina.\n\"X\" -> Bandera sin mina debajo.";
        return mesa;
    }

    public void rellenar() {//Este método pone todos los datos del array a -1 (agua).

        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                this.posicion[i][j] = -1;
            }
        }

    }

     public void colocarMinas(int fila, int columna) {//Coloca el número de minas en todas las casillas menos en la primera que se marca.

        Random aleatorio = new Random();
        int ubicadas = 0;
        this.posicion[fila - 1][columna - 1] = -6;//Primera casilla marcada. A -6 para que no sea convertida.
        while (ubicadas < this.getMinas()) {//Mientras las minas ubicadas sean menos que las que hay que colocar, repite.

            int alefila = aleatorio.nextInt(this.getFilas());
            int alecolumna = aleatorio.nextInt(this.getColumnas());
            if (this.posicion[alefila][alecolumna] == -1) {//Recibe los números aleatorios entre 0 y y las filas y columnas. Si hay un -1
                this.posicion[alefila][alecolumna] = -2;//Coloca mina
                ubicadas++;//Y añade una a el n-umero de ubicadas.
            }
        }
        this.posicion[fila - 1][columna - 1] = -1;//Al final, quita el -6 para dejarla como estaba. (En el juego, inmediatamente después esta es marcada.
    }

    public void marcar(int fila, int columna) {//Marcar cambia el valor de una posición del array por el número de minas que tiene alrededor.
        if (this.posicion[fila - 1][columna - 1] != -5 && this.posicion[fila - 1][columna - 1] != -3 && this.posicion[fila - 1][columna - 1] != -4) {
//En caso de que no haya sido levantada y rodeada antes, o de que sea una bandera.
            if (fila == 1 && columna == 1) {
                int numeromostrado = 0;
                for (int i = -1; i <= 0; i++) {
                    for (int j = -1; j <= 0; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (fila == 1 && columna == this.getColumnas()) {
                int numeromostrado = 0;
                for (int i = -1; i <= 0; i++) {
                    for (int j = -2; j <= -1; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (fila == this.getFilas() && columna == 1) {
                int numeromostrado = 0;
                for (int i = -2; i <= -1; i++) {
                    for (int j = -1; j <= 0; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (fila == this.getFilas() && columna == this.getColumnas()) {
                int numeromostrado = 0;
                for (int i = -2; i <= -1; i++) {
                    for (int j = -2; j <= -1; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (fila == 1) {
                int numeromostrado = 0;
                for (int i = -1; i <= 0; i++) {
                    for (int j = -2; j <= 0; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (fila == this.getFilas()) {
                int numeromostrado = 0;
                for (int i = -2; i <= -1; i++) {
                    for (int j = -2; j <= 0; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (columna == 1) {
                int numeromostrado = 0;
                for (int i = -2; i <= 0; i++) {
                    for (int j = -1; j <= 0; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else if (columna == this.getColumnas()) {
                int numeromostrado = 0;
                for (int i = -2; i <= 0; i++) {
                    for (int j = -2; j <= -1; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;
            } else {
                int numeromostrado = 0;
                for (int i = -2; i <= 0; i++) {
                    for (int j = -2; j <= 0; j++) {
                        if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                            numeromostrado += 1;
                        }
                    }
                }
                this.posicion[fila - 1][columna - 1] = numeromostrado;

            }
        }
    }

    public void levantar(int fila, int columna) {//Levantar implica todo el proceso de levantar y el del método redundar al final.
        if (fila == 1 && columna == 1) {
            int numeromostrado = 0;
            for (int i = -1; i <= 0; i++) {
                for (int j = -1; j <= 0; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (fila == 1 && columna == this.getColumnas()) {
            int numeromostrado = 0;
            for (int i = -1; i <= 0; i++) {
                for (int j = -2; j <= -1; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (fila == this.getFilas() && columna == 1) {
            int numeromostrado = 0;
            for (int i = -2; i <= -1; i++) {
                for (int j = -1; j <= 0; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (fila == this.getFilas() && columna == this.getColumnas()) {
            int numeromostrado = 0;
            for (int i = -2; i <= -1; i++) {
                for (int j = -2; j <= -1; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (fila == 1) {
            int numeromostrado = 0;
            for (int i = -1; i <= 0; i++) {
                for (int j = -2; j <= 0; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (fila == this.getFilas()) {
            int numeromostrado = 0;
            for (int i = -2; i <= -1; i++) {
                for (int j = -2; j <= 0; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (columna == 1) {
            int numeromostrado = 0;
            for (int i = -2; i <= 0; i++) {
                for (int j = -1; j <= 0; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else if (columna == this.getColumnas()) {
            int numeromostrado = 0;
            for (int i = -2; i <= 0; i++) {
                for (int j = -2; j <= -1; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;
        } else {
            int numeromostrado = 0;
            for (int i = -2; i <= 0; i++) {
                for (int j = -2; j <= 0; j++) {
                    if (this.posicion[fila + i][columna + j] == -2 || this.posicion[fila + i][columna + j] == -4) {
                        numeromostrado += 1;
                    }
                }
            }
            this.posicion[fila - 1][columna - 1] = numeromostrado;

        }
        this.redundar();//Aquí está redundar.
    }

    public boolean hayceros() {//Indica si en el array hay algún valor que sea cero.
        int numero = 0;
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                if (this.posicion[i][j] == 0) {
                    numero += 1;
                }

            }
        }
        if (numero == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void redundar() {//Esto rodea todos los valores 0 del array y los convierte a -5 (no se toca más).
        do {
            for (int i = 0; i < this.getFilas(); i++) {
                for (int j = 0; j < this.getColumnas(); j++) {
                    if (this.posicion[i][j] == 0) {
                        this.rodear(i + 1, j + 1);
                        this.posicion[i][j] = -5;

                    }

                }

            }
        } while (this.hayceros());//Y lo hace mientras haya ceros en el array.
    }

    public void rodear(int fila, int columna) {//Rodear, si una casilla es cero, marca las de alrededor que seguro no tienen mina.


        if (this.posicion[fila - 1][columna - 1] == 0) {

            if (fila == 1 && columna == 1) {

                this.marcar(fila, columna + 1);
                this.marcar(fila + 1, columna);
                this.marcar(fila + 1, columna + 1);


            } else if (fila == 1 && columna == this.getColumnas()) {
                this.marcar(fila, columna - 1);
                this.marcar(fila + 1, columna - 1);
                this.marcar(fila + 1, columna);


            } else if (fila == this.getFilas() && columna == 1) {
                this.marcar(fila - 1, columna);
                this.marcar(fila - 1, columna + 1);
                this.marcar(fila, columna + 1);


            } else if (fila == this.getFilas() && columna == this.getColumnas()) {
                this.marcar(fila - 1, columna - 1);
                this.marcar(fila - 1, columna);
                this.marcar(fila, columna - 1);


            } else if (fila == 1) {
                this.marcar(fila, columna - 1);
                this.marcar(fila, columna + 1);
                this.marcar(fila + 1, columna - 1);
                this.marcar(fila + 1, columna);
                this.marcar(fila + 1, columna + 1);


            } else if (fila == this.getFilas()) {
                this.marcar(fila - 1, columna - 1);
                this.marcar(fila - 1, columna);
                this.marcar(fila - 1, columna + 1);
                this.marcar(fila, columna - 1);
                this.marcar(fila, columna + 1);


            } else if (columna == 1) {
                this.marcar(fila - 1, columna);
                this.marcar(fila - 1, columna + 1);
                this.marcar(fila, columna + 1);
                this.marcar(fila + 1, columna);
                this.marcar(fila + 1, columna + 1);


            } else if (columna == this.getColumnas()) {
                this.marcar(fila - 1, columna - 1);
                this.marcar(fila - 1, columna);
                this.marcar(fila, columna - 1);
                this.marcar(fila + 1, columna - 1);
                this.marcar(fila + 1, columna);


            } else {
                this.marcar(fila - 1, columna - 1);
                this.marcar(fila - 1, columna);
                this.marcar(fila - 1, columna + 1);
                this.marcar(fila, columna - 1);
                this.marcar(fila, columna + 1);
                this.marcar(fila + 1, columna - 1);
                this.marcar(fila + 1, columna);
                this.marcar(fila + 1, columna + 1);


            }
        }

    }

   

    public int casillasSinVer() {//Cuenta las casillas que no han sido jugadas.
        int casillas = 0;
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                if (this.posicion[i][j] == -1 || this.posicion[i][j] == -2 || this.posicion[i][j] == -3 || this.posicion[i][j] == -4) {
                    casillas += 1;
                }
            }
        }
        casillas = casillas - this.getMinas();//El total de casillas que no ha sido jugadas menos el número de minas.
        return casillas;
    }

    public int numerobanderas() {//El número de banderas que contiene el tablero.
        int numero = 0;
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                if (this.posicion[i][j] == -3 || this.posicion[i][j] == -4) {
                    numero += 1;
                }
            }
        }

        return numero;
    }

    public boolean colocarBandera(int fila, int columna) {//Coloca una bandera. Si no tiene mina, coloca un -3, si sí la contiene, coloca un -4.
        if (this.posicion[fila - 1][columna - 1] == -1) {//Además es un boolean que confirma que se ha colocado. Aunque no se usa en el juego.
            this.posicion[fila - 1][columna - 1] = -3;
            return true;
        } else if (this.posicion[fila - 1][columna - 1] == -2) {
            this.posicion[fila - 1][columna - 1] = -4;
            return true;
        } else {
            return false;
        }
    }

    public boolean quitarBandera(int fila, int columna) {//Igual que "Colocar bandera" pero inviertiendo los números.
        if (this.posicion[fila - 1][columna - 1] == -3) {
            this.posicion[fila - 1][columna - 1] = -1;
            return true;
        } else if (this.posicion[fila - 1][columna - 1] == -4) {
            this.posicion[fila - 1][columna - 1] = -2;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }

    /**
     * @return the columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the minas
     */
    public int getMinas() {
        return minas;
    }

    /**
     * @param minas the minas to set
     */
    public void setMinas(int minas) {
        this.minas = minas;
    }
}
