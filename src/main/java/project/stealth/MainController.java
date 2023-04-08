package project.stealth;

public class MainController {
    public String testingEncrypt;
    public Encrypt encrypt = new Encrypt();
    public Decrypt decrypt = new Decrypt();
    public String testingEncrypt(){
        testingEncrypt = encrypt.caesarCipher("i love sarah santos", 7);
        return testingEncrypt;
    }
    public String testingDecrypt(){
        return decrypt.caesarCipher(testingEncrypt, 7);
    }
}
