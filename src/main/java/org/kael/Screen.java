package org.kael;

/**
 * Kontrak dasar untuk semua layar yang dapat ditampilkan di aplikasi terminal.
 */
public interface Screen {
  /**
   * Render layar ke terminal dan jalankan alur interaksinya.
   *
   * @throws Exception jika terjadi kegagalan ketika menampilkan layar.
   */
  void show() throws Exception;
}
