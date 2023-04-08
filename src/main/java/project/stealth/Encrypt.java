package project.stealth;

import java.util.Random;

public class Encrypt {
    /*
    Esta classe contém todos os métodos que implementam os algoritmos de
    Encriptação, nesse programa contém os seguintes:
    1- Caesar cipher
    2- XOR Cipher
    3- Rail Fence Cipher
    4- Polybius Square Cipher
    5- One Time Pad Cipher
    6- Hill Cipher
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
    Rail Fence Cipher
    Método faz a encriptação baseado na Rail Fence Cipher que
    move cada letra da mensagem em zigue-zague de acordo com um numero especificado
    chamado de "trilho"
    @param String encyptText, int rail
    @return String
     */
    public String railFenceCipher(String encyptText, int rail){
        //Cria um Array que serve como "cerca" ou limite, com o tamanho do trilho
        StringBuilder[] fence = new StringBuilder[rail];
        //adiciona um objeto StringBuilder em cada espaço do Array
        for(int i = 0; i<rail; i++){
            fence[i] = new StringBuilder();
        }
        //mantem controle da posição do trilho
        int railPosition = 0;
        //mantem controle se o vetor muda de direção
        boolean down = true;
        //percorre cada letra da mensagem
        for (int i = 0; i < encyptText.length(); i++) {
            //atribui a letra correspondente a posição i
            char letter = encyptText.charAt(i);
            //adiciona no array na posição especificada
            fence[railPosition].append(letter);

            //adiciona ou diminui posições, para fazer o zigue-zague
            if (down){
                railPosition++;
            } else {
                railPosition--;
            }

            //faz o "Zigue-Zague" das letras mudando a variavel down
            if(railPosition == rail - 1){
                down = false;
            } else if (railPosition == 0) {
                down = true;
            }
        }
        //hora de juntar os resultados das operações em uma string somente
        var encrypted = new StringBuilder();
        //percorre o Array de StringBuilders
        for (int i = 0; i < rail; i++) {
            //concatena em uma string somente
            encrypted.append(fence[i]);
        }
        //retorna o texto encriptado
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
    public String polybiusSquareCipher(String encryptText, String key){
        //grade 5x5
        char[][] grid = new char[5][5];
        //concatena a chave com o alfabeto
        String keyAlphabet = key + "abcdefghiklmnopqrstuvwxyz";
        //mantem controle da posição atual
        int index = 0;
        //percorre a matriz
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //posiciona as letras no grid
                grid[i][j] = keyAlphabet.charAt(index);
                //atualiza o index;
                index++;
            }
        }
        //cria o string builder da mensagem final
        var encrypted = new StringBuilder();
        //percorre o texto a ser encriptado
        for (int i = 0; i < encryptText.length(); i++) {
            char letter = encryptText.charAt(i);
            //adiciona os espaços caso existam
            if(letter == ' '){
                encrypted.append(' ');
                continue;
            }
            //para cada letra encontrada, retorna sua posição correspondente
            int[] position = findPosition(grid, letter);
            //adiciona na String final a posição especificada da letra
            encrypted.append(position[0]).append(position[1]);
        }
        //string final
        return encrypted.toString();
    }
    /*
    Método acha a posição no grid em polybiusSquareCipher
    @param char[][] grid, char letter
    @return int[] position
    */
    private int[] findPosition(char[][] grid, char letter) {
        //numero da posição da letra
        int[] position = new int[2];
        //percorre a matriz atrás da letra em questão
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //acha a letra no grid
                if (grid[i][j] == letter) {
                    //salva a posição da linha
                    position[0] = i + 1;
                    //salva a posição da coluna
                    position[1] = j + 1;
                    return position;
                }
            }
        }
        //retorna a posição
        return position;
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
    public String hillCipher(String encryptText, int[][] key){
        //arredonda o tamanho do texto, se for necessário
        while(encryptText.length() % 2 != 0){
            encryptText += "$";
        }
        //converte o texto em uma matriz
        int columns = encryptText.length() / 2;
        //matriz com o texto puro, não encriptado ainda
        int[][] encryptThisMatrix = new int[2][columns];
        for (int i = 0; i < columns; i++) {
            encryptThisMatrix[0][i] = encryptText.charAt(2*i) - 'A';
            encryptThisMatrix[1][i] = encryptText.charAt(2*i+1) - 'A';
        }
        //Matriz com o texto encriptado
        int[][] encryptedMatrix = new int[2][columns];
        //percorre matriz
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < columns; j++) {
                int sum = 0;
                for (int k = 0; k < 2; k++) {
                    //multiplica as 2 matrizes
                    sum += key[i][j] * encryptThisMatrix[k][j];
                }
                encryptedMatrix[i][j] = sum % 26;
            }
        }
        //Convertendo de volta para texto
        var encrypted = new StringBuilder();
        for (int i = 0; i < columns; i++) {
            encrypted.append((char) (encryptedMatrix[0][i] + 'A'));
            encrypted.append((char) (encryptedMatrix[1][i] + 'A'));
        }
        //texto final
        return encrypted.toString();
    }
}
