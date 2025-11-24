package org.kael.utils;

/**
 * Text Formatter Class
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

  private Text() {
    // utility class
  }

  // Generic formatter
  public static String format(String text, String... styles) {
    StringBuilder sb = new StringBuilder();
    for (String style : styles) {
      sb.append(style);
    }
    sb.append(text).append(RESET);
    return sb.toString();
  }

  // Text color helpers (normal)
  public static String black(String text)   { return format(text, BLACK); }
  public static String red(String text)     { return format(text, RED); }
  public static String green(String text)   { return format(text, GREEN); }
  public static String yellow(String text)  { return format(text, YELLOW); }
  public static String blue(String text)    { return format(text, BLUE); }
  public static String magenta(String text) { return format(text, MAGENTA); }
  public static String cyan(String text)    { return format(text, CYAN); }
  public static String white(String text)   { return format(text, WHITE); }

  // Text color helpers (bright)
  public static String brightBlack(String text)   { return format(text, BRIGHT_BLACK); }
  public static String brightRed(String text)     { return format(text, BRIGHT_RED); }
  public static String brightGreen(String text)   { return format(text, BRIGHT_GREEN); }
  public static String brightYellow(String text)  { return format(text, BRIGHT_YELLOW); }
  public static String brightBlue(String text)    { return format(text, BRIGHT_BLUE); }
  public static String brightMagenta(String text) { return format(text, BRIGHT_MAGENTA); }
  public static String brightCyan(String text)    { return format(text, BRIGHT_CYAN); }
  public static String brightWhite(String text)   { return format(text, BRIGHT_WHITE); }

  // Background helpers (normal)
  public static String bgBlack(String text)   { return format(text, BG_BLACK); }
  public static String bgRed(String text)     { return format(text, BG_RED); }
  public static String bgGreen(String text)   { return format(text, BG_GREEN); }
  public static String bgYellow(String text)  { return format(text, BG_YELLOW); }
  public static String bgBlue(String text)    { return format(text, BG_BLUE); }
  public static String bgMagenta(String text) { return format(text, BG_MAGENTA); }
  public static String bgCyan(String text)    { return format(text, BG_CYAN); }
  public static String bgWhite(String text)   { return format(text, BG_WHITE); }

  // Background helpers (bright)
  public static String bgBrightBlack(String text)   { return format(text, BG_BRIGHT_BLACK); }
  public static String bgBrightRed(String text)     { return format(text, BG_BRIGHT_RED); }
  public static String bgBrightGreen(String text)   { return format(text, BG_BRIGHT_GREEN); }
  public static String bgBrightYellow(String text)  { return format(text, BG_BRIGHT_YELLOW); }
  public static String bgBrightBlue(String text)    { return format(text, BG_BRIGHT_BLUE); }
  public static String bgBrightMagenta(String text) { return format(text, BG_BRIGHT_MAGENTA); }
  public static String bgBrightCyan(String text)    { return format(text, BG_BRIGHT_CYAN); }
  public static String bgBrightWhite(String text)   { return format(text, BG_BRIGHT_WHITE); }

  // Style helpers
  public static String bold(String text)          { return format(text, BOLD); }
  public static String dim(String text)           { return format(text, DIM); }
  public static String italic(String text)        { return format(text, ITALIC); }
  public static String underline(String text)     { return format(text, UNDERLINE); }
  public static String blink(String text)         { return format(text, BLINK); }
  public static String invert(String text)        { return format(text, INVERT); }
  public static String strikethrough(String text) { return format(text, STRIKETHROUGH); }

  // Combination Template
  public static String error(String text)   { return format(text, BOLD, RED); }
  public static String success(String text) { return format(text, BOLD, GREEN); }
  public static String warning(String text) { return format(text, BOLD, YELLOW); }
  public static String info(String text)    { return format(text, BOLD, BLUE); }
}
