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
    -Variáveis-
     */

    /*
    Decrypt One Time Pad Cipher
    Método é responsável por fazer a Decriptação da One Time Pad Cipher
    @param String encryptedText, String key
    @return String
     */
    public String oneTimePadCipher(String encryptedText, String key){
        var decrypted = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            char letter = (char) (encryptedText.charAt(i) ^ key.charAt(i));
            decrypted.append(letter);
        }
        return decrypted.toString();
    }

    /*
   Decrypt XOR Cipher
   Método faz Decriptação da XOR cipher (é o mesmo método da classe encrypt, pois
   esse algoritmo basta ser rodado denovo com a chave certa, está aqui com fins de organização)
   @param String encryptedText, String key
   @return String
    */
    public String xorCipher(String encryptedText, String key){
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
    Decrypt Hill Cipher
    Método faz a Decriptação de Hill Ciphers
    @param String encryptedText, int[][] key
    @return String
     */

    public String hillCipher(String encryptedText, int[][] key){
        // Converte o texto encriptado em uma matriz
        int n = encryptedText.length() / 2;
        int[][] encryptedTextMatrix = new int[2][n];
        for (int i = 0; i < n; i++) {
            encryptedTextMatrix[0][i] = encryptedText.charAt(2*i) - 'A';
            encryptedTextMatrix[1][i] = encryptedText.charAt(2*i+1) - 'A';
        }

        // Acha o inverso na chave
        int determinant = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        int invertDeterminat = -1;
        for (int i = 0; i < 26; i++) {
            if ((i * determinant) % 26 == 1) {
                invertDeterminat = i;
                break;
            }
        }
        //confere se a matriz chave realmente tem inversa
        if (invertDeterminat == -1) {
            throw new IllegalArgumentException("Key matrix is not invertible");
        }
        int[][] invertKey = new int[2][2];
        invertKey[0][0] = key[1][1] * invertDeterminat % 26;
        invertKey[0][1] = -key[0][1] * invertDeterminat % 26;
        invertKey[1][0] = -key[1][0] * invertDeterminat % 26;
        invertKey[1][1] = key[0][0] * invertDeterminat % 26;

        //percorre as matrizes e multiplica pela chave para revelar a mensagem
        int[][] decryptedTextMatrix = new int[2][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    decryptedTextMatrix[j][i] += invertKey[j][k] * encryptedTextMatrix[k][i];
                }
                decryptedTextMatrix[j][i] = (decryptedTextMatrix[j][i] % 26 + 26) % 26;
            }
        }

        // converte o resultado em uma string
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < n; i++) {
            decrypted.append((char) (decryptedTextMatrix[0][i] + 'A'));
            decrypted.append((char) (decryptedTextMatrix[1][i] + 'A'));
        }
        return decrypted.toString();
    }
}

