import javax.swing.*;
public class GUI {

public static void main(String[] args)
{
//JOptionPane.showMessageDialog(null, "Hello , World!");

    String agetext = JOptionPane.showInputDialog(null, "How old are you?");
    int age = 0;
    try {
     age = Integer.parseInt(agetext);
    } catch (NumberFormatException numberFormatException){ JOptionPane.showMessageDialog(
            null, "maybe use numbers next time");}

    String moneytext = JOptionPane.showInputDialog(null, "How  much money do you have?");
    int money = 0;
    try {
        money = Integer.parseInt(moneytext);
    } catch (NumberFormatException numberFormatException){ JOptionPane.showMessageDialog(
            null, "invalid input");}

    JOptionPane.showMessageDialog(null, "If you can double your money each year, \n" +
           "You'll have " + (money * 32) + "dollars at age " + ( age + 5) + "!");


}
}
