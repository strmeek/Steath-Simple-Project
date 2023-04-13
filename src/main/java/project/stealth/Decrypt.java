package project.stealth;

public class Decrypt {
        /*
    Esta classe contém todos os métodos que implementam os algoritmos de
    Decriptação, nesse programa contém os seguintes:
    1- Caesar cipher
    2- XOR Cipher
    3- Polybius Square Cipher
    4- One Time Pad Cipher
    5- Hill Cipher
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
    Polybius Square Cipher
    Método responsavel por decifrar mensagens criptografas por Polybius Square Cipher
    @param String encryptedText, String key
    @return String
     */
    public static String polybiusSquareDecipher(String encryptedText) {
        // Matriz do alfabeto, incluindo a letra J
        char[][] polybiusSquare = {{'A', 'B', 'C', 'D', 'E'},
                {'F', 'G', 'H', 'I', 'J'},
                {'K', 'L', 'M', 'N', 'O'},
                {'P', 'Q', 'R', 'S', 'T'},
                {'U', 'V', 'W', 'X', 'Y'},
                {'Z', ' ', '.', ',', ';'}};

        // Cria um StringBuilder para armazenar a mensagem decifrada
        StringBuilder revealedText = new StringBuilder();

        // Itera por cada par de números na mensagem cifrada
        for (int i = 0; i < encryptedText.length(); i += 2) {
            int row = Character.getNumericValue(encryptedText.charAt(i)) - 1;
            int col = Character.getNumericValue(encryptedText.charAt(i+1)) - 1;
            // Adiciona a letra correspondente à posição decifrada à mensagem decifrada
            revealedText.append(polybiusSquare[row][col]);
        }

        // Retorna a mensagem decifrada
        return revealedText.toString();
    }

    /*
    Decrypt One Time Pad Cipher
    Método é responsável por fazer a Decriptação da One Time Pad Cipher
    @param String encryptedText, String key
    @return String
     */
    public String oneTimePadDecipher(String encryptedText, String key){
        var decrypted = new StringBuilder();

        //garante que a chave é do mesmo tamanho que a mensagem
        if(key.length() < encryptedText.length()){
            throw new IllegalArgumentException();
        }

        /*faz a operação de decriptação, se a chave estiver correta
        a mensagem sairá correta.
        */

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

