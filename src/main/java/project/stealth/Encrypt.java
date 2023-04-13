package project.stealth;

import java.util.Random;

public class Encrypt {
    /*
    Esta classe contém todos os métodos que implementam os algoritmos de
    Encriptação, nesse programa contém os seguintes:
    1- Caesar cipher
    2- XOR Cipher
    3- Polybius Square Cipher
    4- One Time Pad Cipher
    5- Hill Cipher
     */

    /*
    -Variáveis-
     */
    public String oneTimePadKey;
    /*
    Caesar Cipher
    Método faz a encriptação baseado no método de Cesar
    que transforma o caractere em outro de acordo com a ordem no alfabeto.
    @param String encryptText, int shift
    @return String
     */
    public String caesarCipher(String encryptText, int shift){
        var encrypted = new StringBuilder();
        encryptText.toLowerCase();

        for (char letter : encryptText.toCharArray()) {
            //Verifica se é uma letra do Alfabeto
            if (Character.isLetter(letter)) {
                // adiciona o deslocamento (shift)
                letter = (char) ('a' + (letter - 'a' + shift + 26) % 26);
                }
            //Concatena ou junta as letras
            encrypted.append(letter);
        }
        //retorna a String completa
        return encrypted.toString();
    }

    /*
    XOR Cipher
    Método faz encriptação baseado na XOR cipher que utiliza
    ou exclusivo para cifrar os dados
    @param String encryptText, String key
    @return String
     */
    public String xorCipher(String encryptText, String key){
        //Cria StringBuilder
        var encrypted = new StringBuilder();
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
            encrypted.append(letter);
        }
        //retorna a String completa
        return encrypted.toString();
    }

    /*
    Polybius Square Cipher
    Método faz a encriptação utilizando a Polybius Square Cipher que usa
    uma grade 5x5 para cifrar as letras do alfabeto, exceto o J e converte
    as letras em posições da grade
    @param String encyptText, String key
    @return String
     */
    public String polybiusSquareCipher(String encryptText){
        // Matriz do alfabeto, incluindo a letra J
        char[][] polybiusSquare = {{'A', 'B', 'C', 'D', 'E'},
                {'F', 'G', 'H', 'I', 'J'},
                {'K', 'L', 'M', 'N', 'O'},
                {'P', 'Q', 'R', 'S', 'T'},
                {'U', 'V', 'W', 'X', 'Y'},
                {'Z', ' ', '.', ',', ';'}};

        // Pré-processamento da mensagem em letras maiúsculas e removendo caracteres não-alfabéticos
        encryptText = encryptText.toUpperCase().replaceAll("[^A-Z]", "");

        // Cria um StringBuilder para armazenar a mensagem cifrada
        StringBuilder ciphertext = new StringBuilder();

        // Itera por cada letra da mensagem
        for (int i = 0; i < encryptText.length(); i++) {
            char letter = encryptText.charAt(i);
            // Itera por cada letra na matriz de Polybius para encontrar a posição da letra atual
            for (int row = 0; row < polybiusSquare.length; row++) {
                for (int col = 0; col < polybiusSquare[row].length; col++) {
                    if (polybiusSquare[row][col] == letter) {
                        // Adiciona a posição cifrada (linha e coluna) à mensagem cifrada
                        ciphertext.append(row+1).append(col+1);
                    }
                }
            }
        }
        // Retorna a mensagem cifrada
        return ciphertext.toString();
    }
    /*
    One Time Pad Cipher
    Método faz a encriptação usando One time Pad que utiliza uma key
    random do tamanho do texto a ser encriptado e a chave é misturada
    com o texto utilizando módulo
    @param String encryptText
    @return String
     */
    public String oneTimePadCipher(String encryptText){
        //Gera chave aleatória
        var key = new StringBuilder();
        var random = new Random();
        //faz ela ter o tamanho do texto a ser encriptado
        while(key.length() < encryptText.length()){
            key.append((char) random.nextInt(256));
        }
        //Mistura a chave com o texto
        var encrypted = new StringBuilder();
        for (int i = 0; i < encryptText.length(); i++) {
            //Faz a operação para cada letra do texto
            char letter = (char) (encryptText.charAt(i) ^ key.charAt(i));
            encrypted.append(letter);
        }
        oneTimePadKey = key.toString();
        return encrypted.toString();
    }

    /*
    Hill Cipher
    Os Métodos abaixo fazem a encriptação utilizando Hill Cipher, que separa o texto em blocos
    e multiplica por uma matriz(key), para obter o texto encriptado
    @param String encryptText, int[][] key
    @return String
     */
    /*
    Para que a encriptação tenha sucesso, deve-se entregar um texto em CAPSLOCK
    e uma matriz que contenha inversa
     */
    public String hillCipher(String encryptText, int[][] key) {
        int keyLen = key.length; // dimensão da matriz chave
        int textLen = encryptText.length();
        int[][] encryptTextMatrix = new int[keyLen][textLen/keyLen]; // matriz do texto a ser encriptado(encryptText)
        for (int i = 0; i < textLen; i+=keyLen) {
            for (int j = 0; j < keyLen; j++) {
                encryptTextMatrix[j][i/keyLen] = encryptText.charAt(i+j) - 'A';
            }
        }
        int[][] encryptedTextMatrix = new int[keyLen][textLen/keyLen]; // matriz do encryptedText
        for (int i = 0; i < keyLen; i++) {
            for (int j = 0; j < textLen/keyLen; j++) {
                int sum = 0;
                for (int k = 0; k < keyLen; k++) {
                    sum += key[i][k] * encryptTextMatrix[k][j];
                }
                encryptedTextMatrix[i][j] = sum % 26;
            }
        }
        StringBuilder encryptedText = new StringBuilder(); // encryptedText em string
        for (int i = 0; i < textLen/keyLen; i++) {
            for (int j = 0; j < keyLen; j++) {
                encryptedText.append((char)(encryptedTextMatrix[j][i] + 'A'));
            }
        }
        return encryptedText.toString();
    }
}

