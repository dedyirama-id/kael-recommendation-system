package org.kael.utils;

import java.util.Scanner;

import org.kael.Screen;

public class Terminal {
  private int width = 100;
  private final Scanner sc;
  private Screen screen;

  private static final String CLEAR_SCREEN = "\u001B[2J";
  private static final String CURSOR_HOME = "\u001B[H";

  public Terminal(int width) {
    this.width = width;
    this.sc = new Scanner(System.in);
  }

  public void setScreen(Screen screen){
    this.screen = screen;
  }

  public void showScreen() throws Exception{
    this.clear();
    this.screen.show();
  }

  public void clear() {
    System.out.print(CLEAR_SCREEN + CURSOR_HOME);
    System.out.flush();
  }

  public void waitForInput(String text) {
    this.getLine(text);
  }

  public void printDivider() {
    System.out.print("+");
    for (int i = 0; i < this.width - 2; i++) {
      System.out.print("-");
    }
    System.out.println("+");
  }

  public void printCenter(String line) {
    int leftSize = (this.width - line.length()) / 2;
    int rightSize = (this.width - line.length()) / 2;

    for (int i = 0; i < leftSize; i++) {
      System.out.print(" ");
    }
    System.out.print(line);
    for (int i = 0; i < rightSize; i++) {
      System.out.print(" ");
    }
    System.out.println("");
  }

  public String getLine(String prefix) {
    System.out.print(prefix);

    return this.sc.nextLine();
  }

  public int getInt(String prefix) {
    while (true) {
      String line = this.getLine(prefix);
      try {
        return Integer.parseInt(line.trim());
      } catch (NumberFormatException e) {
        System.out.println(Text.error("Input should a number!"));
      }
    }
  }

  public int getOption(String[] options, String prefix) {
    while (true) {
      for (int i = 0; i < options.length; i++) {
        System.out.println(i + ". " + options[i]);
      }

      String line = this.getLine(prefix);
      try {
        int choose = Integer.parseInt(line.trim());
        if (choose < 0 || choose > options.length - 1) {
          System.out.println(Text.error("Input out of range!"));
          continue;
        }
        return Integer.parseInt(line.trim());
      } catch (NumberFormatException e) {
        System.out.println(Text.error("Input should a number!"));
      }
    }
  }
}
