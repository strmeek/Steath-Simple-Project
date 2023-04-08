package project.stealth;

public class MainController {
    public String testingEncrypt;
    public Encrypt encrypt = new Encrypt();
    public Decrypt decrypt = new Decrypt();
    public String testingEncrypt(){
        testingEncrypt = encrypt.xorCipher("i love sarah", "juggernaut");
        return testingEncrypt;
    }
    public String testingDecrypt(){
        return decrypt.xorCipher(testingEncrypt, "juggernaut");
    }
}
