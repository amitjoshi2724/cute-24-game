import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
public class DriverProgrammingExercise22_13{
   public static void main(String[] args){
      JFrame frame = new JFrame();
      frame.setSize(800, 500);
      frame.setResizable(true);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      double width = screenSize.getWidth();
      double height = screenSize.getHeight();
      frame.setLocation((int)(width / 2) - (frame.getWidth() / 2),(int)(height / 2) - (frame.getHeight() / 2));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.getContentPane().add(new ProgrammingExercise22_13Panel(frame));
      frame.setTitle("Simple 24-Point Card Game");
      frame.setVisible(true);
   }
}
class ProgrammingExercise22_13Panel extends JPanel{
   private static JButton refresh, verify;
   private static ImageIcon[] card;
   private static JTextField enterTextField;
   private static JLabel label;
   private static Container south;
   private static JPanel north;
   private static int[] array;
   private static double[] solutionArray;
   private static Stack<Double> operandStackCheck = new Stack<Double>();
   private static JFrame frame;
   public ProgrammingExercise22_13Panel(JFrame f){
      setLayout(new BorderLayout());
      this.frame = f;
      refresh = new JButton("Refresh");
      refresh.setFont(new Font("Times New Roman", Font.PLAIN, 24));
      refresh.addActionListener(new RefreshListener());
      verify = new JButton("Verify");
      verify.setFont(new Font("Times New Roman", Font.PLAIN, 24));
      verify.addActionListener(new VerifyListener());
      card = new ImageIcon[4];
      label = new JLabel("Enter an expression: ");
      label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
      enterTextField = new JTextField(20);
      //enterTextField.setLineWrap(true);
      enterTextField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
      south = new Container();
      south.setLayout(new FlowLayout());
      south.add(label);
      south.add(enterTextField);
      south.add(verify);
      add(south, BorderLayout.SOUTH);
      north = new JPanel();
      north.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      north.setLayout(new BorderLayout(10, 10));
      north.add(refresh, BorderLayout.EAST);
      add(north, BorderLayout.NORTH);
      array = new int[52];
      solutionArray = new double[4]; 
      for (int i = 0; i < array.length; i++) {
         array[i] = i;
      }
      card = new ImageIcon[4];
   }
   private class RefreshListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         enterTextField.setText("");
         operandStackCheck.removeAllElements();//
         repaint();
      }
   }
   private class VerifyListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         if(!verify(enterTextField)){
            JOptionPane.showMessageDialog(frame, "Incorrect result");
         }   
         else{
            JOptionPane.showMessageDialog(frame, "Correct");
         }
         refresh.doClick();
      }
   }
   private static boolean verify(JTextField jta){
      System.out.println(jta.getText().replaceAll("\\s+",""));
      if(evaluateExpression(jta.getText().replaceAll("\\s+","")) == 24.0){
         if(operandStackCheck.size() != 4){
            System.out.println("The size was " + operandStackCheck.size());
            return false;
         }
         System.out.println(solutionArray);
         for(int i = 0; i < solutionArray.length; i++){
            if(!operandStackCheck.contains(solutionArray[i])){
               System.out.println("It didn't contain " + solutionArray[i]);
               return false;
            }
            operandStackCheck.remove(operandStackCheck.indexOf(solutionArray[i]));
         }
         return true;
      }
      System.out.println("It wasn't 24, it was " + evaluateExpression(jta.getText()));
      jta.setText("");
      return false;
   }
   public static double evaluateExpression(String expression){
      Stack<Double> operandStack = new Stack<Double>();
      Stack<Character> operatorStack = new Stack<Character>();
      expression = insertBlanks(expression);
      String[] tokens = expression.split(" ");
      for(String token: tokens){
         if(token.length() == 0)
            continue;
         else if(token.charAt(0) == '+' || token.charAt(0) == '-'){
            while(!operatorStack.isEmpty() &&
            (operatorStack.peek() == '+' ||
            operatorStack.peek() == '-' ||
            operatorStack.peek() == '*' ||
            operatorStack.peek() == '/' )){
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.push(token.charAt(0));
         }
         else if(token.charAt(0) == '*' || token.charAt(0) == '/'){
            while(!operatorStack.isEmpty() &&
            (operatorStack.peek() == '*' ||
            operatorStack.peek() == '/' )){
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.push(token.charAt(0));
         }
         else if(token.trim().charAt(0) == '('){
            operatorStack.push('(');
         }
         else if(token.trim().charAt(0) == ')'){
            while(operatorStack.peek() != '('){
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.pop();
         }
         else{
            operandStack.push(new Double(Double.parseDouble(token)));
            operandStackCheck.push(new Double(Double.parseDouble(token)));
         }
      }
      while(!operatorStack.isEmpty()){
         processAnOperator(operandStack, operatorStack);
      }
      return operandStack.pop();
   }
   public static void processAnOperator(Stack<Double> operandStack, Stack<Character> operatorStack){
      char op = operatorStack.pop();
      double op1 = operandStack.pop();
      double op2 = operandStack.pop();
      if(op == '+')
         operandStack.push(op2 + op1);
      else if(op == '-')
         operandStack.push(op2 - op1);
      else if(op == '*')
         operandStack.push(op2 * op1);
      else if(op == '/')
         operandStack.push(op2 / op1);
   }
   public static String insertBlanks(String s){
      String result = "";
      for(int i = 0; i < s.length(); i++){
         if(s.charAt(i) == '(' || s.charAt(i) == ')' ||
          s.charAt(i) == '*' || s.charAt(i) == '-' ||
          s.charAt(i) == '+' || s.charAt(i) == '/')
            result += " " + s.charAt(i) + " ";
         else
            result += s.charAt(i);
      }
      return result;
   }
   public void paintComponent(Graphics g){
      int[] shuffledArray = shuffle(array);
      /*int[] shuffledArray = {4, 8, 1, 5};
      if(Math.random() < .5){
         for(int i = 0; i < 4; i++){
            shuffledArray[i] = 5;
         }
      }*/
      String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
         	"j", "q", "k" };
      String[] books = { "c", "s", "h", "d" };
      for (int j = 0, x = 10, y = 60; j < 4; j++, x += 200, y += 0) {
         if (j == 5) {
            y += 100;
            x = 10;
         }
         String cardName;
         String cardNumber = numbers[shuffledArray[j] % 13];
         switch(cardNumber){
            case "j":
               solutionArray[j] = 11.0;
               break;
            case "q":
               solutionArray[j] = 12.0;
               break;
            case "k":
               solutionArray[j] = 13.0;
               break;
            default:
               solutionArray[j] = Double.parseDouble(cardNumber);
               break;
         }
         String cardBook = books[shuffledArray[j] / 13];
         cardName = cardBook + cardNumber;
         card[j] = new ImageIcon("images/" + cardName
            	+ ".gif");
         g.drawImage(card[j].getImage(), x, y, 160, 240, this);
      }
   }
   public int[] shuffle(int[] array) {
      for (int i = 0; i < array.length; i++) {
         int rNumber = ((int) (Math.random() * array.length));
         int temp = array[i];
         array[i] = array[rNumber];
         array[rNumber] = temp;
      }
      return array;
   }
}
/*
   import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
public class DriverProgrammingExercise22_13{
   public static void main(String[] args){
      JFrame frame = new JFrame();
      frame.setSize(400, 300);
      frame.setResizable(true);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      double width = screenSize.getWidth();
      double height = screenSize.getHeight();
      frame.setLocation((int)(width / 2) - (frame.getWidth() / 2),(int)(height / 2) - (frame.getHeight() / 2));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.getContentPane().add(new ProgrammingExercise22_13Panel(frame));
      frame.setTitle("Simple 24-Point Card Game");
      frame.setVisible(true);
   }
}
class ProgrammingExercise22_13Panel extends JPanel{
   private static JButton refresh, verify;
   private static ImageIcon[] card;
   private static JTextField enterTextField;
   private static JLabel label;
   private static Container south;
   private static JPanel north;
   private static int[] array, solutionArray;
   private static Stack<Integer> operandStackCheck = new Stack<Integer>();
   private static JFrame frame;
   public ProgrammingExercise22_13Panel(JFrame f){
      setLayout(new BorderLayout());
      this.frame = f;
      refresh = new JButton("Refresh");
      refresh.addActionListener(new RefreshListener());
      verify = new JButton("Verify");
      verify.addActionListener(new VerifyListener());
      card = new ImageIcon[4];
      label = new JLabel("Enter an expression: ");
      enterTextField = new JTextField();
      enterTextField.setLineWrap(true);
      south = new Container();
      south.setLayout(new FlowLayout());
      south.add(label);
      south.add(enterTextField);
      south.add(verify);
      add(south, BorderLayout.SOUTH);
      north = new JPanel();
      north.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      north.setLayout(new BorderLayout(10, 10));
      north.add(refresh, BorderLayout.EAST);
      add(north, BorderLayout.NORTH);
      array = new int[52];
      solutionArray = new int[4]; 
      for (int i = 0; i < array.length; i++) {
         array[i] = i;
      }
      card = new ImageIcon[4];
   }
   private class RefreshListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         enterTextField.setText("");
         operandStackCheck.removeAllElements();//
         repaint();
      }
   }
   private class VerifyListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         if(!verify(enterTextField)){
            JOptionPane.showMessageDialog(frame, "Incorrect result");
         }   
         else{
            JOptionPane.showMessageDialog(frame, "Correct");
         }
         refresh.doClick();
      }
   }
   private static boolean verify(JTextField jta){
      if(evaluateExpression(jta.getText()) == 24){
         if(operandStackCheck.size() != 4){
            System.out.println("The size was " + operandStackCheck.size());
            return false;
         }
         for(int i = 0; i < solutionArray.length; i++){
            if(!operandStackCheck.contains(solutionArray[i])){
               System.out.println("It didn't contain " + solutionArray[i]);
               return false;
            }
            operandStackCheck.remove(operandStackCheck.indexOf(solutionArray[i]));
         }
         return true;
      }
      System.out.println("It wasn't 24, it was " + evaluateExpression(jta.getText()));
      jta.setText("");
      return false;
   }
   public static int evaluateExpression(String expression){
      Stack<Integer> operandStack = new Stack<Integer>();
      Stack<Character> operatorStack = new Stack<Character>();
      expression = insertBlanks(expression);
      String[] tokens = expression.split(" ");
      for(String token: tokens){
         if(token.length() == 0)
            continue;
         else if(token.charAt(0) == '+' || token.charAt(0) == '-'){
            while(!operatorStack.isEmpty() &&
            (operatorStack.peek() == '+' ||
            operatorStack.peek() == '-' ||
            operatorStack.peek() == '*' ||
            operatorStack.peek() == '/' )){
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.push(token.charAt(0));
         }
         else if(token.charAt(0) == '*' || token.charAt(0) == '/'){
            while(!operatorStack.isEmpty() &&
            (operatorStack.peek() == '*' ||
            operatorStack.peek() == '/' )){
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.push(token.charAt(0));
         }
         else if(token.trim().charAt(0) == '('){
            operatorStack.push('(');
         }
         else if(token.trim().charAt(0) == ')'){
            while(operatorStack.peek() != '('){
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.pop();
         }
         else{
            operandStack.push(new Integer(Integer.parseInt(token)));
            operandStackCheck.push(new Integer(Integer.parseInt(token)));
         }
      }
      while(!operatorStack.isEmpty()){
         processAnOperator(operandStack, operatorStack);
      }
      return operandStack.pop();
   }
   public static void processAnOperator(Stack<Integer> operandStack, Stack<Character> operatorStack){
      char op = operatorStack.pop();
      int op1 = operandStack.pop();
      int op2 = operandStack.pop();
      if(op == '+')
         operandStack.push(op2 + op1);
      else if(op == '-')
         operandStack.push(op2 - op1);
      else if(op == '*')
         operandStack.push(op2 * op1);
      else if(op == '/')
         operandStack.push(op2 / op1);
   }
   public static String insertBlanks(String s){
      String result = "";
      for(int i = 0; i < s.length(); i++){
         if(s.charAt(i) == '(' || s.charAt(i) == ')' ||
          s.charAt(i) == '*' || s.charAt(i) == '-' ||
          s.charAt(i) == '+' || s.charAt(i) == '/')
            result += " " + s.charAt(i) + " ";
         else
            result += s.charAt(i);
      }
      return result;
   }
   public void paintComponent(Graphics g){
      int[] shuffledArray = shuffle(array);
      /*int[] shuffledArray = {4, 8, 1, 5};
      if(Math.random() < .5){
         for(int i = 0; i < 4; i++){
            shuffledArray[i] = 5;
         }
      }*/
      /*String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
         	"j", "q", "k" };
      String[] books = { "c", "s", "h", "d" };
      for (int j = 0, x = 10, y = 40; j < 4; j++, x += 100, y += 0) {
         if (j == 5) {
            y += 100;
            x = 10;
         }
         String cardName;
         String cardNumber = numbers[shuffledArray[j] % 13];
         switch(cardNumber){
            case "j":
               solutionArray[j] = 11;
               break;
            case "q":
               solutionArray[j] = 12;
               break;
            case "k":
               solutionArray[j] = 13;
               break;
            default:
               solutionArray[j] = Integer.parseInt(cardNumber);
               break;
         }
         String cardBook = books[shuffledArray[j] / 13];
         cardName = cardBook + cardNumber;
         card[j] = new ImageIcon("images/" + cardName
            	+ ".gif");
         g.drawImage(card[j].getImage(), x, y, this);
      }
   }
   public int[] shuffle(int[] array) {
      for (int i = 0; i < array.length; i++) {
         int rNumber = ((int) (Math.random() * array.length));
         int temp = array[i];
         array[i] = array[rNumber];
         array[rNumber] = temp;
      }
      return array;
   }
}*/