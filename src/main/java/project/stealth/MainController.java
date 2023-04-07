package project.stealth;

public class MainController {
    public String testing(){
        var encrypt = new Encrypt();
        return encrypt.caesarCipher("aleatory", 2);
    }
}
