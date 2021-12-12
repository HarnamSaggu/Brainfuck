package brainfuck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Brainfuck {
   public static void main(String[] args) throws FileNotFoundException {
      Brainfuck brainfuck = new Brainfuck();
      StringBuilder code = new StringBuilder();
      Scanner scanner = new Scanner(new File("code.txt"));
      while (scanner.hasNextLine()) code.append(scanner.nextLine());
      scanner.close();
      int[] tape = brainfuck.run(code.toString());
      System.out.println("\n" + Arrays.toString(tape).replaceAll("[\\[\\],]", ""));
   }

   public int[] run(String code) {
      char[] commands = code.toCharArray();
      int[] tape = new int[100];
      int head = 0;
      Stack<Integer> whileIndexes = new Stack<>();
      Scanner scanner = new Scanner(System.in);
      for (int i = 0; i < commands.length; i++) {
         switch (commands[i]) {
            case '<' -> head--;
            case '>' -> head++;
            case '+' -> tape[head]++;
            case '-' -> tape[head]--;
            case '[' -> {
               if (tape[head] > 0) {
                  whileIndexes.push(i);
               } else {
                  int index = i + 1;
                  int open = 1;
                  int close = 0;
                  while (open != close) {
                     if (commands[index] == '[') {
                        open++;
                     } else if (commands[index] == ']') {
                        close++;
                     }
                     index++;
                  }
                  i = index - 1;
               }
            }
            case ']' -> {
               if (tape[head] > 0) i = whileIndexes.peek() - 1;
               whileIndexes.pop();
            }
            case '.' -> System.out.print((char) tape[head]);
            case ',' -> tape[head] = scanner.nextInt();
         }
         tape[head] = (char) (tape[head] % 255);

      }
      return tape;
   }
}
