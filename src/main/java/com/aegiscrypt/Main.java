package com.aegiscrypt;

import com.aegiscrypt.service.EncryptionService;
import com.aegiscrypt.service.impl.AegisCryptService;

import java.io.Console;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EncryptionService encryptionService = new AegisCryptService();
        Scanner scanner = new Scanner(System.in);

        Console console = System.console();

        if (console == null) {
            System.err.println("Atenção: Console não disponível. A senha será exibida durante a digitação.");
        }

        System.out.println("--- BEM-VINDO AO AEGISCRYPT ---");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Criptografar Texto");
            System.out.println("2. Descriptografar Texto");
            System.out.println("3. Sair");
            System.out.print(">> ");

            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    handleEncrypt(scanner, console, encryptionService);
                    break;
                case "2":
                    handleDecrypt(scanner, console, encryptionService);
                    break;
                case "3":
                    System.out.println("Encerrando AegisCrypt. Mantenha sua chave segura!");
                    scanner.close();
                    return;
                default:
                    System.err.println("Opção inválida. Por favor, tente novamente.\n");
                    break;
            }
        }
    }

    private static void handleEncrypt(Scanner scanner, Console console, EncryptionService service) {
        System.out.print("Digite o texto a ser criptografado: ");
        String textoPlano = scanner.nextLine();

        String senha = readPassword(scanner, console, "Digite a senha para a criptografia: ");

        try {
            String textoCriptografado = service.encrypt(senha, textoPlano);
            System.out.println("\n--- TEXTO CRIPTOGRAFADO (em Base64) ---");
            System.out.println(textoCriptografado);
            System.out.println("---------------------------------------");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante a criptografia: " + e.getMessage());
        }
    }

    private static void handleDecrypt(Scanner scanner, Console console, EncryptionService service) {
        System.out.print("Cole o texto criptografado (em Base64): ");
        String textoCriptografado = scanner.nextLine();

        String senha = readPassword(scanner, console, "Digite a senha para a descriptografia: ");

        try {
            String textoDescriptografado = service.decrypt(senha, textoCriptografado);
            System.out.println("\n--- TEXTO DESCRIPTOGRAFADO ---");
            System.out.println(textoDescriptografado);
            System.out.println("------------------------------");
        } catch (Exception e) {
            System.err.println("\nFalha ao descriptografar. Verifique se a senha está correta e se o texto criptografado não foi alterado.\n");
        }
    }

    private static String readPassword(Scanner scanner, Console console, String prompt) {
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

}