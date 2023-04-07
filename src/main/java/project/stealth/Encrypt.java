package project.stealth;

import java.util.BitSet;

public class Encrypt {
    /*
    Método faz a encriptação baseado no método de Cesar
    que transforma o caractere em outro de acordo com a ordem no alfabeto.
    @param String encryptText, int shift
    @return String
     */
    public String caesarCipher(String encryptText, int shift){
        var encrypted = new StringBuilder();

        for (int i = 0; i < encryptText.length(); i++) {
            char letter = encryptText.charAt(i);
            //Verifica se é uma letra do Alfabeto
            if (Character.isLetter(letter)) {
                //Verifica se a Letra é Maiuscula
                if (Character.isUpperCase(letter)) {
                    // adiciona o deslocamento (shift)
                    letter = (char) ('A' + (letter - 'A' + shift) % 26);
                } else {
                    // adiciona o deslocamento (shift)
                    letter = (char) ('a' + (letter - 'a' + shift) % 26);
                }
            }
            //Concatena ou junta as letras
            encrypted.append(letter);
        }
        //retorna a String completa
        return encrypted.toString();
    }

    /*
    Método faz encriptação baseado na XOR cipher que utiliza
    ou exclusivo para cifrar os dados
    @param String encryptText, String key
    @return String
     */
    public String xorCipher(String encryptText, String key){
        //Cria StringBuilder
        var stringBuilder = new StringBuilder();
        //tamanho da mensagem
        int textLen = encryptText.length();
        //tamanho da chave
        int keyLen = key.length();

        //percorre cada letra da mensagem
        for(int i = 0; i < textLen; i++) {
            /*
            faz a operação de XOR da lógica com a letra da mensagem
            e a letra da chave correspondente
             */
            char letter = (char) (encryptText.charAt(i) ^ key.charAt(i % keyLen));
            stringBuilder.append(letter);
        }
        //retorna a String completa
        return stringBuilder.toString();
    }
}
