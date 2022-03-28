package Twillio;

public class FourthBit {
    public static int fourthBit(int num) {
        return (num & (1 << 3)) != 0 ? 1 : 0;
    }
}
