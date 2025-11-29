package org.kael.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kael.Screen;
import org.kael.algorithms.Personalization;
import org.kael.algorithms.Scored;
import org.kael.algorithms.Sort;
import org.kael.entities.User;
import org.kael.models.Graph;
import org.kael.utils.Terminal;
import org.kael.utils.Text;

/**
 * Layar untuk memberikan rekomendasi user lain
 * yang memiliki minat event/tag yang serupa.
 */
public class RecommendUserScreen implements Screen {
    private final Terminal terminal;
    private final Graph<Object> graph;

    /**
     * Membuat layar rekomendasi user.
     *
     * @param terminal utilitas terminal untuk I/O.
     * @param graph    graf objek pengguna, event, dan tag.
     */
    public RecommendUserScreen(Terminal terminal, Graph<Object> graph) {
        this.terminal = terminal;
        this.graph = graph;
    }

    /**
     * Menjalankan alur rekomendasi: menerima deskripsi profil,
     * menghitung skor kedekatan dengan BFS personalisasi, mengurutkan,
     * dan menampilkan beberapa user teratas.
     *
     * @throws Exception jika proses penghitungan atau penampilan layar gagal.
     */
    @Override
    public void show() throws Exception {
        this.terminal.clear();
        this.terminal.printDivider();
        System.out.println(Text.bold("# Recommend User"));
        this.terminal.printDivider();

        String[] tokens = this.terminal.getLine("Describe your event: ").split("\\s+");

        Map<Object, Integer> scores = new HashMap<>();

        for (String token : tokens) {
            if (token.isBlank()) continue;
            Set<Object> matches = this.graph.search(token);
            if (matches == null || matches.isEmpty()) {
                continue;
            }

            for (Object vertex : new LinkedHashSet<>(matches)) {
                Personalization.bfsScoring(this.graph, vertex, 1, scores);
            }
        }

        List<Scored<Object>> scoredList = Personalization.toScoredList(scores);
        Scored<Object>[] scoredArray = scoredList.toArray(Scored[]::new);
        Sort.selectionSort(scoredArray);

        final int MAX_USERS = 5;
        List<Scored<User>> users = new ArrayList<>();

        for (Scored<Object> scored : scoredArray) {
            Object value = scored.getValue();
            if (value instanceof User user) {
                users.add(Scored.of(user, scored.getScore()));
                if (users.size() >= MAX_USERS) {
                    break;
                }
            }
        }

        for (Scored<User> scoredUser : users) {
            User user = scoredUser.getValue();
            terminal.printDivider();
            System.out.println("ID          : " + user.getId());
            System.out.println("Name        : " + user.getName());
            System.out.println("Relv. Score : " + scoredUser.getScore());
        }

        if (users.isEmpty()) {
            System.out.println(Text.warning("Maaf, belum ada rekomendasi user yang cocok dengan deskripsi event yang anda berikan."));
        }

        terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
        this.terminal.clear();
    }
}
