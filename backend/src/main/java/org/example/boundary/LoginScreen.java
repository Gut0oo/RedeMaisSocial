package org.example.boundary;

import java.util.Scanner;

public class LoginScreen {

    public void start(){
        Scanner sc = new Scanner(System.in);
        int opc;
        String senha, cp; //cp = cadastro de pessoas(fisicas ou juridicas)
        System.out.println("=== REDE MAIS SOCIAL ===");
        System.out.println("CPF (XXX.XXX.XXX-YY) / CNPJ (XX.XXX.XXX/0001-YY): ");
        System.out.print(">> ");

        System.out.println("Senha: ");
        System.out.print(">> ");
        senha = sc.nextLine();
        cp = sc.nextLine();

        //colocar um Controlador para verificar o login, caso seja validado, ir para a tela de inicio do app

    }
}
