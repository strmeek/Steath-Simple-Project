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

    /*
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
}
