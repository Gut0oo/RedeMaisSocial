package org.example.boundary;

import java.util.Scanner;

public class MainScreen {

    public void start(){
        Scanner sc = new Scanner(System.in);
        int opc;

        do{
            System.out.println("=== REDE MAIS SOCIAL ===");
            System.out.println("1 - Iniciar Cadastro");
            System.out.println("2 - Realizar Login");
            System.out.println("0 - Sair");
            System.out.print(">> ");
            opc = sc.nextInt();

            switch (opc) {
                case 1:
                    new RegisterScreen().start();
                    break;
                case 2:
                    new LoginScreen().start();
                    break;
            }
        }while (opc != 0);
    }
}
