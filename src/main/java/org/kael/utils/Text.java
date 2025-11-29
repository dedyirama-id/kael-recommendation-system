package org.kael.utils;

/**
 * Utility untuk memformat teks dengan kode warna dan gaya ANSI agar output terminal lebih informatif.
 */
public final class Text {
  // Reset
  public static final String RESET = "\u001B[0m";

  // Styles
  public static final String BOLD          = "\u001B[1m";
  public static final String DIM           = "\u001B[2m";
  public static final String ITALIC        = "\u001B[3m";
  public static final String UNDERLINE     = "\u001B[4m";
  public static final String BLINK         = "\u001B[5m";
  public static final String INVERT        = "\u001B[7m";
  public static final String STRIKETHROUGH = "\u001B[9m";

  // Foreground colors (normal)
  public static final String BLACK   = "\u001B[30m";
  public static final String RED     = "\u001B[31m";
  public static final String GREEN   = "\u001B[32m";
  public static final String YELLOW  = "\u001B[33m";
  public static final String BLUE    = "\u001B[34m";
  public static final String MAGENTA = "\u001B[35m";
  public static final String CYAN    = "\u001B[36m";
  public static final String WHITE   = "\u001B[37m";

  // Foreground colors (bright)
  public static final String BRIGHT_BLACK   = "\u001B[90m";
  public static final String BRIGHT_RED     = "\u001B[91m";
  public static final String BRIGHT_GREEN   = "\u001B[92m";
  public static final String BRIGHT_YELLOW  = "\u001B[93m";
  public static final String BRIGHT_BLUE    = "\u001B[94m";
  public static final String BRIGHT_MAGENTA = "\u001B[95m";
  public static final String BRIGHT_CYAN    = "\u001B[96m";
  public static final String BRIGHT_WHITE   = "\u001B[97m";

  // Background colors (normal)
  public static final String BG_BLACK   = "\u001B[40m";
  public static final String BG_RED     = "\u001B[41m";
  public static final String BG_GREEN   = "\u001B[42m";
  public static final String BG_YELLOW  = "\u001B[43m";
  public static final String BG_BLUE    = "\u001B[44m";
  public static final String BG_MAGENTA = "\u001B[45m";
  public static final String BG_CYAN    = "\u001B[46m";
  public static final String BG_WHITE   = "\u001B[47m";

  // Background colors (bright)
  public static final String BG_BRIGHT_BLACK   = "\u001B[100m";
  public static final String BG_BRIGHT_RED     = "\u001B[101m";
  public static final String BG_BRIGHT_GREEN   = "\u001B[102m";
  public static final String BG_BRIGHT_YELLOW  = "\u001B[103m";
  public static final String BG_BRIGHT_BLUE    = "\u001B[104m";
  public static final String BG_BRIGHT_MAGENTA = "\u001B[105m";
  public static final String BG_BRIGHT_CYAN    = "\u001B[106m";
  public static final String BG_BRIGHT_WHITE   = "\u001B[107m";

  /**
   * Utility class; mencegah instansiasi.
   */
  private Text() {
    // utility class
  }

  /**
   * Menambahkan sekumpulan kode ANSI ke teks dan mereset di akhir.
   *
   * @param text   teks asli.
   * @param styles daftar kode ANSI yang akan diterapkan.
   * @return teks terformat siap dicetak.
   */
  public static String format(String text, String... styles) {
    StringBuilder sb = new StringBuilder();
    for (String style : styles) {
      sb.append(style);
    }
    sb.append(text).append(RESET);
    return sb.toString();
  }

  /**
   * Memberi warna hitam pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna hitam.
   */
  public static String black(String text)   { return format(text, BLACK); }

  /**
   * Memberi warna merah pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna merah.
   */
  public static String red(String text)     { return format(text, RED); }

  /**
   * Memberi warna hijau pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna hijau.
   */
  public static String green(String text)   { return format(text, GREEN); }

  /**
   * Memberi warna kuning pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna kuning.
   */
  public static String yellow(String text)  { return format(text, YELLOW); }

  /**
   * Memberi warna biru pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna biru.
   */
  public static String blue(String text)    { return format(text, BLUE); }

  /**
   * Memberi warna magenta pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna magenta.
   */
  public static String magenta(String text) { return format(text, MAGENTA); }

  /**
   * Memberi warna cyan pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna cyan.
   */
  public static String cyan(String text)    { return format(text, CYAN); }

  /**
   * Memberi warna putih pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna putih.
   */
  public static String white(String text)   { return format(text, WHITE); }

  /**
   * Memberi warna hitam terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna hitam terang.
   */
  public static String brightBlack(String text)   { return format(text, BRIGHT_BLACK); }

  /**
   * Memberi warna merah terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna merah terang.
   */
  public static String brightRed(String text)     { return format(text, BRIGHT_RED); }

  /**
   * Memberi warna hijau terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna hijau terang.
   */
  public static String brightGreen(String text)   { return format(text, BRIGHT_GREEN); }

  /**
   * Memberi warna kuning terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna kuning terang.
   */
  public static String brightYellow(String text)  { return format(text, BRIGHT_YELLOW); }

  /**
   * Memberi warna biru terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna biru terang.
   */
  public static String brightBlue(String text)    { return format(text, BRIGHT_BLUE); }

  /**
   * Memberi warna magenta terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna magenta terang.
   */
  public static String brightMagenta(String text) { return format(text, BRIGHT_MAGENTA); }

  /**
   * Memberi warna cyan terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna cyan terang.
   */
  public static String brightCyan(String text)    { return format(text, BRIGHT_CYAN); }

  /**
   * Memberi warna putih terang pada teks (foreground).
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan warna putih terang.
   */
  public static String brightWhite(String text)   { return format(text, BRIGHT_WHITE); }

  /**
   * Memberi warna latar hitam pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar hitam.
   */
  public static String bgBlack(String text)   { return format(text, BG_BLACK); }

  /**
   * Memberi warna latar merah pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar merah.
   */
  public static String bgRed(String text)     { return format(text, BG_RED); }

  /**
   * Memberi warna latar hijau pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar hijau.
   */
  public static String bgGreen(String text)   { return format(text, BG_GREEN); }

  /**
   * Memberi warna latar kuning pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar kuning.
   */
  public static String bgYellow(String text)  { return format(text, BG_YELLOW); }

  /**
   * Memberi warna latar biru pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar biru.
   */
  public static String bgBlue(String text)    { return format(text, BG_BLUE); }

  /**
   * Memberi warna latar magenta pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar magenta.
   */
  public static String bgMagenta(String text) { return format(text, BG_MAGENTA); }

  /**
   * Memberi warna latar cyan pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar cyan.
   */
  public static String bgCyan(String text)    { return format(text, BG_CYAN); }

  /**
   * Memberi warna latar putih pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar putih.
   */
  public static String bgWhite(String text)   { return format(text, BG_WHITE); }

  /**
   * Memberi warna latar hitam terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar hitam terang.
   */
  public static String bgBrightBlack(String text)   { return format(text, BG_BRIGHT_BLACK); }

  /**
   * Memberi warna latar merah terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar merah terang.
   */
  public static String bgBrightRed(String text)     { return format(text, BG_BRIGHT_RED); }

  /**
   * Memberi warna latar hijau terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar hijau terang.
   */
  public static String bgBrightGreen(String text)   { return format(text, BG_BRIGHT_GREEN); }

  /**
   * Memberi warna latar kuning terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar kuning terang.
   */
  public static String bgBrightYellow(String text)  { return format(text, BG_BRIGHT_YELLOW); }

  /**
   * Memberi warna latar biru terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar biru terang.
   */
  public static String bgBrightBlue(String text)    { return format(text, BG_BRIGHT_BLUE); }

  /**
   * Memberi warna latar magenta terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar magenta terang.
   */
  public static String bgBrightMagenta(String text) { return format(text, BG_BRIGHT_MAGENTA); }

  /**
   * Memberi warna latar cyan terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar cyan terang.
   */
  public static String bgBrightCyan(String text)    { return format(text, BG_BRIGHT_CYAN); }

  /**
   * Memberi warna latar putih terang pada teks.
   *
   * @param text teks yang akan diwarnai.
   * @return teks dengan latar putih terang.
   */
  public static String bgBrightWhite(String text)   { return format(text, BG_BRIGHT_WHITE); }

  /**
   * Menambahkan gaya tebal pada teks.
   *
   * @param text teks yang akan diformat.
   * @return teks dengan gaya bold.
   */
  public static String bold(String text)          { return format(text, BOLD); }

  /**
   * Menambahkan gaya redup/dim pada teks.
   *
   * @param text teks yang akan diformat.
   * @return teks dengan gaya dim.
   */
  public static String dim(String text)           { return format(text, DIM); }

  /**
   * Menambahkan gaya italic pada teks.
   *
   * @param text teks yang akan diformat.
   * @return teks italic.
   */
  public static String italic(String text)        { return format(text, ITALIC); }

  /**
   * Menambahkan garis bawah pada teks.
   *
   * @param text teks yang akan diformat.
   * @return teks bergaris bawah.
   */
  public static String underline(String text)     { return format(text, UNDERLINE); }

  /**
   * Menambahkan efek blink pada teks.
   *
   * @param text teks yang akan diformat.
   * @return teks dengan efek blink.
   */
  public static String blink(String text)         { return format(text, BLINK); }

  /**
   * Membalik warna (foreground dan background) teks.
   *
   * @param text teks yang akan diformat.
   * @return teks dengan efek invert.
   */
  public static String invert(String text)        { return format(text, INVERT); }

  /**
   * Menambahkan efek coret pada teks.
   *
   * @param text teks yang akan diformat.
   * @return teks dengan efek coret.
   */
  public static String strikethrough(String text) { return format(text, STRIKETHROUGH); }

  /**
   * Template pesan error (tebal + merah).
   *
   * @param text teks pesan.
   * @return teks terformat untuk pesan error.
   */
  public static String error(String text)   { return format(text, BOLD, RED); }

  /**
   * Template pesan sukses (tebal + hijau).
   *
   * @param text teks pesan.
   * @return teks terformat untuk pesan sukses.
   */
  public static String success(String text) { return format(text, BOLD, GREEN); }

  /**
   * Template pesan peringatan (tebal + kuning).
   *
   * @param text teks pesan.
   * @return teks terformat untuk peringatan.
   */
  public static String warning(String text) { return format(text, BOLD, YELLOW); }

  /**
   * Template pesan informasi (tebal + biru).
   *
   * @param text teks pesan.
   * @return teks terformat untuk informasi.
   */
  public static String info(String text)    { return format(text, BOLD, BLUE); }
}
