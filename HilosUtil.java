// Clase HilosUtil.java modificada
package hilos_programa3;

//Importacion de librerias
import java.time.LocalTime; //Para manejar la hora actual.
import java.time.format.DateTimeFormatter; //Para dar formato a la hora
import javax.swing.JTextArea; //Para el componente de texto de area de la GUI
/*
NOTA: La razon por la que se utiliza el JtextArea es que en el metodo creado anteriormente,
sabia donde se iban a imprimir los datos a traves de sout
 */
public class HilosUtil {
    // Metodo H1 para mostrar hora
    public static Thread crearHiloHora(JTextArea areaTexto) {
        Runnable tareaHora = new Runnable() {
            @Override
            public void run() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); //Creacion del formato para la hora
                long inicio = System.currentTimeMillis(); //Capturar el tiempo de inicio.
                //Tiempo actual-tiempo de inicio y si es menor a 1 minutos (60000 s)
                while (System.currentTimeMillis() - inicio < 60000) {
                    LocalTime horaActual = LocalTime.now();
                    String mensaje = "Hora actual: " + horaActual.format(formatter);

                    //Actualizar el JTextArea en vez del sout
                    actualizarAreaTexto(areaTexto, mensaje);

                    try {
                        Thread.sleep(5000); //Esperar 5 segundos para que se vuelva a mostrar la hora
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Mensaje final cuando se pasa el minuto
                actualizarAreaTexto(areaTexto, "Hilo de hora finalizado");
            }
        };
        return new Thread(tareaHora); //Retornar el hilo creado
    }

    //Metodo para mostrar "Trabjando"
    public static Thread crearHiloTrabajando(JTextArea areaTexto) {
        //Creacion de objeto en Runnable ->Tarea que ejecuta el hilo
        Runnable tareaTrabajando = new Runnable() {
            @Override
            public void run() {
                long inicio = System.currentTimeMillis(); //Capturar hora de inicio
                while (System.currentTimeMillis() - inicio < 60000) {
                    // Actualizar el JTextArea
                    actualizarAreaTexto(areaTexto, "Trabajando...");

                    try {
                        Thread.sleep(1000); //Pausar cada segundo
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                //Mensaje final
                actualizarAreaTexto(areaTexto, "Hilo trabajando finalizado");
            }
        };
        return new Thread(tareaTrabajando); //Retorna el hilo creado
    }

    private static int contadorTrabajando = 0; //Contador para ver cuantas veces se mostro "Trabajando"

    private static void actualizarAreaTexto(javax.swing.JTextArea areaTexto, String mensaje) {
    /* Ejecuta la actualización dentro del hilo gráfico principal (EDT)
    Event Dispatch Thread (EDT).Este hilo es el encargado de manejar todos los eventos y actualizaciones graficas.    
    invokeLater Para programar una tarea que debe ejecutarse */
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (mensaje.contains("Trabajando")) {
                    contadorTrabajando++; // Incrementa el contador global
                    String simbolo;
                    // Alterna el símbolo según si el contador es par o impar
                    if (contadorTrabajando % 2 == 0) {
                        simbolo = "+ ";
                    } else {
                        simbolo = "* ";
                    }
                    // Agrega el simbolo y el mensaje al area de texto
                    areaTexto.append(simbolo + mensaje + "\n");
                } else {
                    // Si no contiene "Trabajando", solo agrega el mensaje
                    areaTexto.append(mensaje + "\n");
                }
            }
        });
    }
}
