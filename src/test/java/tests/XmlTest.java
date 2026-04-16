package tests;

import utils.XmlUtils;

public class XmlTest {

    public static void main(String[] args) {

        String nome = XmlUtils.lerTag(
            "src/test/resources/data/cliente.xml",
            "nome"
        );

        String email = XmlUtils.lerTag(
            "src/test/resources/data/cliente.xml",
            "email"
        );

        System.out.println("Nome XML: " + nome);
        System.out.println("Email XML: " + email);
    }
}