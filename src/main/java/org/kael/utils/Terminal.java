package org.kael.utils;

import java.util.Scanner;

import org.kael.Screen;

/**
 * Utilitas pembungkus interaksi terminal (printing, input, screen navigation).
 * Menyediakan method untuk menampilkan divider, teks terpusat, membaca input,
 * serta mengganti layar aktif.
 */
public class Terminal {
  private int width = 100;
  private final Scanner sc;
  private Screen screen;

  private static final String CLEAR_SCREEN = "\u001B[2J";
  private static final String CURSOR_HOME = "\u001B[H";

  /**
   * Membuat Terminal dengan lebar tampilan tertentu.
   *
   * @param width lebar terminal (karakter) yang dipakai untuk menghitung padding.
   */
  public Terminal(int width) {
    this.width = width;
    this.sc = new Scanner(System.in);
  }

  /**
   * Mengatur layar aktif yang akan dirender.
   *
   * @param screen instans layar yang mengimplementasikan {@link Screen}.
   */
  public void setScreen(Screen screen){
    this.screen = screen;
  }

  /**
   * Menghapus layar dan menampilkan layar aktif yang sudah di-set.
   *
   * @throws Exception jika render layar gagal.
   */
  public void showScreen() throws Exception{
    this.clear();
    this.screen.show();
  }

  /**
   * Membersihkan tampilan terminal.
   */
  public void clear() {
    System.out.print(CLEAR_SCREEN + CURSOR_HOME);
    System.out.flush();
  }

  /**
   * Menunggu input ENTER atau baris baru dengan pesan tertentu.
   *
   * @param text teks yang dicetak sebagai prompt sebelum menunggu input.
   */
  public void waitForInput(String text) {
    this.getLine(text);
  }

  /**
   * Mencetak garis pembatas menggunakan karakter plus dan minus.
   */
  public void printDivider() {
    System.out.print("+");
    for (int i = 0; i < this.width - 2; i++) {
      System.out.print("-");
    }
    System.out.println("+");
  }

  /**
   * Mencetak satu baris teks di tengah-tengah layar dengan padding spasi.
   *
   * @param line teks yang ingin ditampilkan.
   */
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

  /**
   * Membaca satu baris input dari pengguna.
   *
   * @param prompt teks prompt yang ditampilkan sebelum input.
   * @return string penuh yang dimasukkan pengguna.
   */
  public String getLine(String prompt) {
    System.out.print(prompt);

    return this.sc.nextLine();
  }

  /**
   * Membaca bilangan bulat dari pengguna dengan validasi.
   *
   * @param prompt teks prompt.
   * @return nilai integer yang valid.
   */
  public int getInt(String prompt) {
    while (true) {
      String line = this.getLine(prompt);
      try {
        return Integer.parseInt(line.trim());
      } catch (NumberFormatException e) {
        System.out.println(Text.error("Input should be a number!"));
      }
    }
  }

  /**
   * Menampilkan daftar opsi dan membaca pilihan yang valid.
   *
   * @param options array label opsi yang akan ditampilkan.
   * @param prompt  teks prompt.
   * @return indeks opsi yang dipilih.
   */
  public int getOption(String[] options, String prompt) {
    while (true) {
      for (int i = 0; i < options.length; i++) {
        System.out.println(i + ". " + options[i]);
      }

      String line = this.getLine(prompt);
      try {
        int choose = Integer.parseInt(line.trim());
        if (choose < 0 || choose > options.length - 1) {
          System.out.println(Text.error("Input out of range!"));
          continue;
        }
        return Integer.parseInt(line.trim());
      } catch (NumberFormatException e) {
        System.out.println(Text.error("Input should be a number!"));
      }
    }
  }
}
