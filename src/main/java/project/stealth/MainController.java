package project.stealth;

public class MainController {
    public String testingEncrypt;
    public Encrypt encrypt = new Encrypt();
    public Decrypt decrypt = new Decrypt();
    public String testingCaesarEncrypt(){
        testingEncrypt = encrypt.caesarCipher("i love sarah santos", 7);
        return testingEncrypt;
    }
    public String testingCaesarDecrypt(){
        return decrypt.caesarDecipher(testingEncrypt, 7);
    }

    public String testingXorEncrypt(){
        testingEncrypt = encrypt.xorCipher("i love sarah santos", "amor da minha vida");
        return testingEncrypt;
    }
    public String testingXorDecrypt(){
        return decrypt.xorDecipher(testingEncrypt, "amor da minha vida");
    }

    public String testingRailFenceEncrypt(){
        testingEncrypt = encrypt.railFenceCipher("i love Sarah Santos", 19);
        return testingEncrypt;
    }
    public String testingRailFenceDecrypt(){
        return decrypt.railFenceDecipher(testingEncrypt, 19);
    }

    public String testingPolybiusSquareEncrypt(){
        testingEncrypt = encrypt.polybiusSquareCipher("i love Sarah Santos", "quero beijos");
        return testingEncrypt;
    }
    public String testingPolybiusSquareDecrypt(){
        return decrypt.polybiusSquareDecipher(testingEncrypt, "quero beijos");
    }
    public String testingOneTimePadEncrypt(){
        testingEncrypt = encrypt.oneTimePadCipher("i love my princess");
        return testingEncrypt;
    }
    public String testingOneTimePadDecrypt(){
        return decrypt.oneTimePadDecipher(testingEncrypt, encrypt.oneTimePadKey);
    }
    public String testingHillEncrypt(){
        int[][] key = new int[][]{{2, 3},{3, 4}};
        testingEncrypt = encrypt.hillCipher("SARASANTOSRIBEIROO", key);
        return testingEncrypt;
    }
    public String testingHillDecrypt(){
        int[][] key = new int[][] {{2, 3}, {3, 4}};
        return decrypt.hillDecipher(testingEncrypt, key);
    }
}
