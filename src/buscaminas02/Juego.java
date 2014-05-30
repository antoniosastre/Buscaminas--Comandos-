package buscaminas02;

import java.util.Scanner;

/**
 *
 * @author Antonio Sastre Vázquez.
 */
public class Juego {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int filas; //Para el total de filas
        int columnas; // Para el total de columnas
        int minas; //Para el total de minas
        int fila; // Una fila en concreto, valor en Juego
        int columna; //Una columna en concreto. Valor en juego
        String confirmacion; //S/N
        String sn = ""; //S/N
        String sn2 = ""; //S/N

        Scanner teclado = new Scanner(System.in);
        System.out.println("Bienvenido al programa...\n");
        System.out.println("#############################################################################################");
        System.out.println("#                                                                                           #");
        System.out.println("#  BBBB    UU   UU   SSSSS   CCCCC   AAAAA    MM   MM   IIIIII  NN     NN   AAAAA    SSSSS  #");
        System.out.println("#  B   B   UU   UU  SS      CC      AA   AA  MM M M MM    II    NNN    NN  AA   AA  SS      #");
        System.out.println("#  B   B   UU   UU  SS      CC      AA   AA  MM M M MM    II    NN N   NN  AA   AA  SS      #");
        System.out.println("#  BBBBB   UU   UU   SSSS   CC      AAAAAAA  MM  M  MM    II    NN  N  NN  AAAAAAA   SSSS   #");
        System.out.println("#  B    B  UU   UU      SS  CC      AA   AA  MM     MM    II    NN   N NN  AA   AA      SS  #");
        System.out.println("#  B    B  UU   UU      SS  CC      AA   AA  MM     MM    II    NN    NNN  AA   AA      SS  #");
        System.out.println("#  BBBBB    UUUUU   SSSSS    CCCCC  AA   AA  MM     MM  IIIIII  NN     NN  AA   AA  SSSSS   #");
        System.out.println("#                                                                                           #");
        System.out.println("#############################################################################################");

        System.out.println("\n¿Desea leer la introducción al juego? [S/N]");
        sn2 = teclado.nextLine();
        if (sn2.equalsIgnoreCase("s")) {
            System.out.println("\nTodo el código de este programa es original, hecho sólo\n"
                    + "con las funciones aprendidas en clase.");
            System.out.println("\nEl juego es una fiel reproducción del clásico juego del sistema\n"
                    + "operativo Windows e incluye todas sus características.\n\n"
                    + "La principal de ellas es que si al descubrir una casilla, ésta\n"
                    + "no tiene una mina alrededor, aparecerá \"MWM\" en la casilla central\n"
                    + "y el programa marcará automáticamente las de alrededor.\n"
                    + "Esto es una característica del juego original y ahorra muchas pulsaciones de teclado.\n\n"
                    + "Otra característica que lo diferencia, es que la primera acción que se hace siempre\n"
                    + "es marcar una casilla que en ningún caso será una mina. Y de ahí en adelante comienza el juego.\n\n"
                    + "El número de minas que se pueden colocar varía en función del tamaño del tablero. Y es el\n"
                    + "resultado de la operación \"(Filas x Columnas)-2\" puesto que la primera casilla que se\n"
                    + "marca no contiene mina y luego debe haber como mínimo otra casilla sin mina para jugar.\n\n"
                    + "El número de banderas está limitado al número de minas, y junto a las opciones del\n"
                    + "menú relativas a las banderas, se especifican cuantas se han colocado y cuantas están disponibles.\n\n"
                    + "Una bandera proteje una casilla de ser marcada accidentalmente con una segunda confirmación.\n\n"
                    + "El número de casillas que faltan por marcar aparece siempre debajo del tablero de juego\n"
                    + "y es la operación \"Nº de casillas totales - Nº de minas\"\n\n"
                    + "Al ganar o perder, se muestra un tablero que revela las posiciones de las minas y las\n"
                    + "posiciones de las banderas colocadas de forma errónea.\n\n"
                    + "La opción \"Rendirse\" finaliza la partida actual.\n\n"
                    + "                                               Ahora estás listo para jugar.\n"
                    + "                                                             Antonio Sastre.\n\n"
                    + "Comienza el juego... \n");

        }

        do {//este do l contiene todo.

            do {
                System.out.print("Indique el número de filas del tablero: [2-99]");//Comienza el juego. Número de Filas del tablero.
                filas = teclado.nextInt();
            } while (filas > 99 || filas < 2);
            do {
                System.out.print("Indique el número de columnas del tablero: [2-99]");//Número de columnas.
                columnas = teclado.nextInt();
            } while (columnas > 99 || columnas < 2);
            do {
                System.out.print("Indique el número de minas ocultas: [1-" + ((filas * columnas) - 2) + "]");//Número de minas.
                minas = teclado.nextInt();
            } while (minas < 1 || minas > ((filas * columnas) - 2));
            Tablero tablero = new Tablero(filas, columnas, minas);
            tablero.rellenar();  //Rellenar todo el array de arrays con el número -1 (no mina)

            System.out.println("\n" + tablero + "\n");
            System.out.println("Indique la primera casilla a marcar:");//Primera función. Marcar primera casilla que nunca será mina.
            do {
                System.out.print("Fila: [1-" + filas + "]");
                fila = teclado.nextInt();
            } while (fila < 1 || fila > filas);
            do {
                System.out.print("Columna: [1-" + columnas + "]");
                columna = teclado.nextInt();
            } while (columna < 1 || columna > columnas);

            tablero.colocarMinas(fila, columna); //Colocas las minas (-2 en el array) excepto en la primera casilla.
            tablero.levantar(fila, columna); //Marcar la primera casilla.

//Ahora se muestra el tablero y el menú.
            int opcion;
            do {//Este do es el del menú del juego + el tablero.
                System.out.println("\n" + tablero + "\n");

                System.out.println("Quedan por descubrir " + tablero.casillasSinVer() + " casillas.\n");

                System.out.println("¿Qué operación desea?");
                System.out.println("1. Marcar Casilla.");
                System.out.println("2. Colocar Bandera. (Hay " + (minas - tablero.numerobanderas()) + " banderas disponibles.).");
                System.out.println("3. Quitar bandera. (Hay " + tablero.numerobanderas() + " banderas colocadas).");
                System.out.println("4. Rendirse.");
                opcion = teclado.nextInt();

                if (opcion == 1) {//Opción 1 - Marcar Casilla.
                    do {
                        System.out.print("Fila: [1-" + filas + "]");
                        fila = teclado.nextInt();
                    } while (fila < 1 || fila > filas);
                    do {
                        System.out.print("Columna: [1-" + columnas + "]");
                        columna = teclado.nextInt();
                    } while (columna < 1 || columna > columnas);
                    if (tablero.posicion[fila - 1][columna - 1] == -1) {
                        tablero.levantar(fila, columna); //levantar

                        if (tablero.ganado()) { // Se compureba si se ha ganado
                            System.out.println("\n");
                            System.out.println("##########################################################################");
                            System.out.println("#                                                                        #");
                            System.out.println("#  VV   VV  IIIIII   CCCCC  TTTTTTTT   OOOOO   RRRRRR   IIIIII   AAAAA   #");
                            System.out.println("#  VV   VV    II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                            System.out.println("#  VV   VV    II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                            System.out.println("#  VV   VV    II    CC         TT     OO   OO  RRRRRR     II    AAAAAAA  #");
                            System.out.println("#   V   V     II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                            System.out.println("#    V V      II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                            System.out.println("#     V     IIIIII   CCCCC     TT      OOOOO   RR   RR  IIIIII  AA   AA  #");
                            System.out.println("#                                                                        #");
                            System.out.println("##########################################################################");
                            System.out.println("\n");
                            System.out.println(tablero.resultado());
                            break; //Si se ha ganado, break;
                        }


                    } else if (tablero.posicion[fila - 1][columna - 1] == -2) {//Si se ha levantado una mina se pierde el juego.
                        System.out.println("\n");
                        System.out.println("###################################################################################");
                        System.out.println("#                                                                                 #");
                        System.out.println("#   GGGGGG   AAAAA    MM   MM    EEEEE         OOOOO   VV   VV   EEEEE   RRRRRR   #");
                        System.out.println("#  GG       AA   AA  MM M M MM  EE            OO   OO  VV   VV  EE       RR   RR  #");
                        System.out.println("#  GG       AA   AA  MM M M MM  EE            OO   OO  VV   VV  EE       RR   RR  #");
                        System.out.println("#  GG  GGG  AAAAAAA  MM  M  MM  EEEEE         OO   OO  VV   VV  EEEEE    RRRRRR   #");
                        System.out.println("#  GG   GG  AA   AA  MM     MM  EE            OO   OO   V   V   EE       RR   RR  #");
                        System.out.println("#  GG   GG  AA   AA  MM     MM  EE            OO   OO    V V    EE       RR   RR  #");
                        System.out.println("#   GGGGG   AA   AA  MM     MM   EEEEE         OOOOO      V      EEEEE   RR   RR  #");
                        System.out.println("#                                                                                 #");
                        System.out.println("###################################################################################");
                        System.out.println("\n");
                        System.out.println(tablero.resultado());
                        break;//Y se acaba.

                    } else if (tablero.posicion[fila - 1][columna - 1] == -3) {//Si se levanta una bandera sin mina se pide confirmación
                        System.out.println("Esta casilla contiene una Bandera. ¿Desea marcarla de todos modos? [S/N]");//Confirmación
                        teclado.nextLine();
                        confirmacion = teclado.nextLine();
                        if (confirmacion.equalsIgnoreCase("s")) {
                            tablero.quitarBandera(fila, columna);
                            tablero.marcar(fila, columna);
                            tablero.redundar();
                            if (tablero.ganado()) {//Si es la última pues se gana.
                                System.out.println("\n");
                                System.out.println("##########################################################################");
                                System.out.println("#                                                                        #");
                                System.out.println("#  VV   VV  IIIIII   CCCCC  TTTTTTTT   OOOOO   RRRRRR   IIIIII   AAAAA   #");
                                System.out.println("#  VV   VV    II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#  VV   VV    II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#  VV   VV    II    CC         TT     OO   OO  RRRRRR     II    AAAAAAA  #");
                                System.out.println("#   V   V     II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#    V V      II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#     V     IIIIII   CCCCC     TT      OOOOO   RR   RR  IIIIII  AA   AA  #");
                                System.out.println("#                                                                        #");
                                System.out.println("##########################################################################");
                                System.out.println("\n");
                                System.out.println(tablero.resultado());
                                break;//Y se caba.
                            }
                        }

                    } else if (tablero.posicion[fila - 1][columna - 1] == -4) {//Si se levanta una bandera que tiene una mina, se pide confirmación.
                        System.out.println("Esta casilla contiene una Bandera. ¿Desea marcarla de todos modos? [S/N]");
                        teclado.nextLine();
                        confirmacion = teclado.nextLine();
                        if (confirmacion.equalsIgnoreCase("s")) {//Si la confirmación es positiva, pues se pierde.
                            System.out.println("\n");
                            System.out.println("###################################################################################");
                            System.out.println("#                                                                                 #");
                            System.out.println("#   GGGGGG   AAAAA    MM   MM    EEEEE         OOOOO   VV   VV   EEEEE   RRRRRR   #");
                            System.out.println("#  GG       AA   AA  MM M M MM  EE            OO   OO  VV   VV  EE       RR   RR  #");
                            System.out.println("#  GG       AA   AA  MM M M MM  EE            OO   OO  VV   VV  EE       RR   RR  #");
                            System.out.println("#  GG  GGG  AAAAAAA  MM  M  MM  EEEEE         OO   OO  VV   VV  EEEEE    RRRRRR   #");
                            System.out.println("#  GG   GG  AA   AA  MM     MM  EE            OO   OO   V   V   EE       RR   RR  #");
                            System.out.println("#  GG   GG  AA   AA  MM     MM  EE            OO   OO    V V    EE       RR   RR  #");
                            System.out.println("#   GGGGG   AA   AA  MM     MM   EEEEE         OOOOO      V      EEEEE   RR   RR  #");
                            System.out.println("#                                                                                 #");
                            System.out.println("###################################################################################");
                            System.out.println("\n");
                            System.out.println(tablero.resultado());
                            break;//Y se acaba el juego.
                        }
                    }



                }

                if (opcion == 2) {//Colocar bandeera.

                    if (tablero.numerobanderas() < minas) {

                        do {
                            System.out.print("Fila: [1-" + filas + "]");
                            fila = teclado.nextInt();
                        } while (fila < 1 || fila > filas);
                        do {
                            System.out.print("Columna: [1-" + columnas + "]");
                            columna = teclado.nextInt();
                        } while (columna < 1 || columna > columnas);
                        if (tablero.posicion[fila - 1][columna - 1] == -1 || tablero.posicion[fila - 1][columna - 1] == -2) {
                            tablero.colocarBandera(fila, columna);
                            if (tablero.ganado()) {//Si al colocar una bandera, es la última casilla, se gana.
                                System.out.println("\n");
                                System.out.println("##########################################################################");
                                System.out.println("#                                                                        #");
                                System.out.println("#  VV   VV  IIIIII   CCCCC  TTTTTTTT   OOOOO   RRRRRR   IIIIII   AAAAA   #");
                                System.out.println("#  VV   VV    II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#  VV   VV    II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#  VV   VV    II    CC         TT     OO   OO  RRRRRR     II    AAAAAAA  #");
                                System.out.println("#   V   V     II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#    V V      II    CC         TT     OO   OO  RR   RR    II    AA   AA  #");
                                System.out.println("#     V     IIIIII   CCCCC     TT      OOOOO   RR   RR  IIIIII  AA   AA  #");
                                System.out.println("#                                                                        #");
                                System.out.println("##########################################################################");
                                System.out.println("\n");
                                System.out.println(tablero.resultado());
                                break;
                            }
                        } else if (tablero.posicion[fila - 1][columna - 1] == -3 || tablero.posicion[fila - 1][columna - 1] == -4) {
                            System.out.println("Esa casilla ya contiene una bandera.");//Diferentes posibilidades respecto a las banderas.
                        } else {
                            System.out.println("Esa casilla ya ha sido destapada.");
                        }
                    } else {
                        System.out.println("No te quedan banderas para colocar.");
                    }


                }

                if (opcion == 3) {//Quitar banderas.
                    if (tablero.numerobanderas() > 0) {


                        do {
                            System.out.print("Fila: [1-" + filas + "]");
                            fila = teclado.nextInt();
                        } while (fila < 1 || fila > filas);
                        do {
                            System.out.print("Columna: [1-" + columnas + "]");
                            columna = teclado.nextInt();
                        } while (columna < 1 || columna > columnas);
                        if (tablero.posicion[fila - 1][columna - 1] == -3 || tablero.posicion[fila - 1][columna - 1] == -4) {
                            tablero.quitarBandera(fila, columna);
                        } else if (tablero.posicion[fila - 1][columna - 1] == -1 || tablero.posicion[fila - 1][columna - 1] == -2) {
                            System.out.println("Esa casilla no contiene una bandera.");//Diferentes posibilidades.
                        } else {
                            System.out.println("Esa casilla ya ha sido destapada.");
                        }
                    } else {
                        System.out.println("No hay banderas para quitar.");
                    }


                }



            } while (opcion != 4);//Al rendirse o encontrarse un break durante el juego, se aparece aquí.
            System.out.println("\n¿Desea volver a empezar? [S/N]");
            teclado.nextLine();
            sn = teclado.nextLine();
        } while (sn.equalsIgnoreCase("s"));
    }
}
