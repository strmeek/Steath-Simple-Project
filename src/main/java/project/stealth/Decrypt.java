package project.stealth;

public class Decrypt {
        /*
    Esta classe contém todos os métodos que implementam os algoritmos de
    Decriptação, nesse programa contém os seguintes:
    1- Caesar cipher
    2- XOR Cipher
    3- Rail Fence Cipher
    4- Polybius Square Cipher
    5- One Time Pad Cipher
    6- Hill Cipher
     */

    /*
    Caesar Cipher
    Método faz a decriptação da caesar cipher
    @param String encryptedText, int shift
    @return String
     */
    public String caesarDecipher(String encryptedText, int shift){
        var decrypted = new StringBuilder();
        encryptedText.toLowerCase();

        for (char letter : encryptedText.toCharArray()) {
            //Verifica se é uma letra do Alfabeto
            if (Character.isLetter(letter)) {
                // adiciona o deslocamento (shift)
                letter = (char) ('a' + (letter - 'a' - shift + 26) % 26);
            }
            //Concatena ou junta as letras
            decrypted.append(letter);
        }
        //retorna a String completa
        return decrypted.toString();
    }

    /*
    Decrypt XOR Cipher
    Método faz Decriptação da XOR cipher (é o mesmo método da classe encrypt, pois
    esse algoritmo basta ser rodado denovo com a chave certa, está aqui com fins de organização)
    @param String encryptedText, String key
    @return String
    */
    public String xorDecipher(String encryptedText, String key){
        //Cria StringBuilder
        var decrypted = new StringBuilder();
        //tamanho da mensagem
        int textLen = encryptedText.length();
        //tamanho da chave
        int keyLen = key.length();

        //percorre cada letra da mensagem
        for(int i = 0; i < textLen; i++) {
            /*
            faz a operação de XOR da lógica com a letra da mensagem
            e a letra da chave correspondente
             */
            char letter = (char) (encryptedText.charAt(i) ^ key.charAt(i % keyLen));
            decrypted.append(letter);
        }
        //retorna a String completa
        return decrypted.toString();
    }

    /*
    Rail Fence Cipher
    Método é responsavel por revelar a mensagem escondida em uma
    Rail Fence Cipher
    @param String encyptText, int rail
    @return String
     */
    public String railFenceDecipher(String encyptText, int rail) {
        // Cria um Array que serve como "cerca" ou limite, com o tamanho do trilho
        StringBuilder[] fence = new StringBuilder[rail];
        // Adiciona um objeto StringBuilder em cada espaço do Array
        for (int i = 0; i < rail; i++) {
            fence[i] = new StringBuilder();
        }
        // Mantem controle da posição do trilho
        int railPosition = 0;
        // Mantem controle se o vetor muda de direção
        boolean down = true;
        // Percorre cada letra da mensagem
        for (int i = 0; i < encyptText.length(); i++) {
            // Atribui a letra correspondente a posição i
            char letter = encyptText.charAt(i);
            // Adiciona no array na posição especificada
            fence[railPosition].append(letter);

            // Adiciona ou diminui posições, para fazer o zigue-zague
            if (down) {
                railPosition++;
            } else {
                railPosition--;
            }

            // Faz o "Zigue-Zague" das letras mudando a variável down
            if (railPosition == rail - 1) {
                down = false;
            } else if (railPosition == 0) {
                down = true;
            }
        }

        // Calcula o tamanho de cada trilho
        int[] railSize = new int[rail];
        int fullBlockSize = 2 * (rail - 1);
        int remainderSize = encyptText.length() % fullBlockSize;

        for (int i = 0; i < railSize.length; i++) {
            int fullBlocks = encyptText.length() / fullBlockSize;
            int blockSize = fullBlocks * 2;
            if (i > 0 && i < rail - 1) {
                blockSize += fullBlocks * 2;
            }
            if (i < remainderSize) {
                blockSize++;
            } else if (i < fullBlockSize) {
                blockSize += remainderSize;
            }
            railSize[i] = blockSize;
        }

        // Constroi um array de índices para reorganizar os caracteres na ordem original
        int[] indexes = new int[encyptText.length()];
        int currentIndex = 0;
        for (int i = 0; i < railSize.length; i++) {
            for (int j = 0; j < railSize[i]; j++) {
                int fenceIndex = j % (2 * (rail - 1));
                if (fenceIndex >= rail) {
                    fenceIndex = 2 * (rail - 1) - fenceIndex;
                }
                if (fenceIndex == i) {
                    indexes[currentIndex] = j;
                    currentIndex++;
                }
            }
        }
        // Usa o array de índices para reorganizar os caracteres na ordem original
        char[] decrypted = new char[encyptText.length()];
        for (int i = 0; i < indexes.length; i++) {
            int fenceIndex = indexes[i] % (2 * (rail - 1));
            if (fenceIndex >= rail) {
                fenceIndex = 2 * (rail - 1) - fenceIndex;
            }
            if (fence[fenceIndex].length() > 0) {
                decrypted[indexes[i]] = fence[fenceIndex].charAt(0);
                fence[fenceIndex].deleteCharAt(0);
            }
        }

        // Retorna o texto decriptado
        return new String(decrypted);
    }

    /*
    Polybius Square Cipher
    Método responsavel por decifrar mensagens criptografas por Polybius Square Cipher
    @param String encryptedText, String key
    @return String
     */
    public String polybiusSquareDecipher(String encryptedText, String key) {
        // grade 5x5
        char[][] grid = new char[5][5];
        // concatena a chave com o alfabeto
        String keyAlphabet = key + "abcdefghiklmnopqrstuvwxyz";
        // mantem controle da posição atual
        int index = 0;
        // percorre a matriz
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // posiciona as letras no grid
                grid[i][j] = keyAlphabet.charAt(index);
                // atualiza o index;
                index += 1;
            }
        }
        // cria o string builder da mensagem final
        var decrypted = new StringBuilder();
        // percorre a mensagem criptografada
        for (int i = 0; i < encryptedText.length() - 1; i += 2) {
            char firstChar = encryptedText.charAt(i);
            char secondChar = encryptedText.charAt(i + 1);
            // verifica se o valor de firstChar e secondChar estão no intervalo esperado
            if (firstChar < '1' || firstChar > '5' || secondChar < '1' || secondChar > '5') {
                decrypted.append("?");
                continue;
            }
            // encontra a letra correspondente à posição na matriz
            char letter = grid[firstChar - '1'][secondChar - '1'];
            // adiciona a letra descriptografada ao string builder
            decrypted.append(letter);
        }
        // retorna a mensagem descriptografada
        return decrypted.toString();
    }

    /*
    Decrypt One Time Pad Cipher
    Método é responsável por fazer a Decriptação da One Time Pad Cipher
    @param String encryptedText, String key
    @return String
     */
    public String oneTimePadDecipher(String encryptedText, String key){
        var decrypted = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            char letter = (char) (encryptedText.charAt(i) ^ key.charAt(i));
            decrypted.append(letter);
        }
        return decrypted.toString();
    }

    /*
    Decrypt Hill Cipher
    Método faz a Decriptação de Hill Ciphers
    @param String encryptedText, int[][] key
    @return String
     */
    public String hillDecipher(String encryptedText, int[][] key) {
        // arredonda o tamanho do texto, se for necessário
        if (encryptedText.length() % 2 != 0) {
            encryptedText += "$";
        }

        // converte o texto cifrado em uma matriz
        int columns = encryptedText.length() / 2;
        int[][] cipherMatrix = new int[2][columns];
        for (int i = 0; i < columns; i++) {
            cipherMatrix[0][i] = encryptedText.charAt(2 * i) - 'A';
            cipherMatrix[1][i] = encryptedText.charAt(2 * i + 1) - 'A';
        }

        // calcula a matriz inversa da chave
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        det = Math.floorMod(det, 26);
        int[][] inverseKey = new int[2][2];
        inverseKey[0][0] = key[1][1];
        inverseKey[1][1] = key[0][0];
        inverseKey[0][1] = - key[0][1];
        inverseKey[1][0] = - key[1][0];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverseKey[i][j] = Math.floorMod(inverseKey[i][j] * modInverse(det, 26), 26);
            }
        }

        // decripta o texto
        int[][] decryptedMatrix = new int[2][columns];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < columns; j++) {
                int sum = 0;
                for (int k = 0; k < 2; k++) {
                    sum += inverseKey[i][k] * cipherMatrix[k][j];
                }
                decryptedMatrix[i][j] = Math.floorMod(sum, 26);
            }
        }

        // converte a matriz de volta para texto
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < columns; i++) {
            decryptedText.append((char) (decryptedMatrix[0][i] + 'A'));
            decryptedText.append((char) (decryptedMatrix[1][i] + 'A'));
        }
        return decryptedText.toString();
    }

    // método para calcular o inverso modular de um número
    public int modInverse(int a, int m) {
        a = Math.floorMod(a, m);
        for (int x = 1; x < m; x++) {
            if (Math.floorMod(a * x, m) == 1) {
                return x;
            }
        }
        throw new ArithmeticException("Inverso modular não existe.");
    }
}

