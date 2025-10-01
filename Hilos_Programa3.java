package hilos_programa3;

//Importar librerias para manejar tiempos
import java.time.LocalTime; //Representar horas, por hora, minuto, segundo, nanosegundos
import java.time.format.DateTimeFormatter; //Para dar formato a la hora


public class Hilos_Programa3{

    // Método para crear el hilo que muestra la hora
    public static Thread crearHiloHora() {
        Runnable tareaHora = new Runnable() {
            @Override
            public void run() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                long inicio = System.currentTimeMillis();
                while (System.currentTimeMillis() - inicio < 60000) {
                    LocalTime horaActual = LocalTime.now();
                    System.out.println("Hora actual: " + horaActual.format(formatter));
                    try {
                        Thread.sleep(5000); // Espera 5 segundos
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return new Thread(tareaHora);
    }

    // Método para crear el hilo que muestra "Trabajando..."
    public static Thread crearHiloTrabajando() {
        Runnable tareaTrabajando = new Runnable() {
            @Override
            public void run() {
                long inicio = System.currentTimeMillis();
                while (System.currentTimeMillis() - inicio < 60000) {
                    System.out.println("Trabajando");
                    try {
                        Thread.sleep(1000); // Espera 1 segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return new Thread(tareaTrabajando);
    }

    // Método principal
    public static void main(String[] args) {
        // Crear los hilos
        Thread hilo1 = crearHiloHora();
        Thread hilo2 = crearHiloTrabajando();

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();

        // Esperar a que los hilos terminen
        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Programa finalizado despues de 1 minuto.");
    }
}