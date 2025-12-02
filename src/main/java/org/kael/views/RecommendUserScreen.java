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
 * Layar untuk memberikan rekomendasi user berdasarkan deskripsi event
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
     * Menjalankan alur rekomendasi: menerima deskripsi event,
     * menghitung skor kedekatan dengan algoritma yang dipilih pengguna,
     * mengurutkan, dan menampilkan beberapa user teratas.
     *
     * @throws Exception jika proses penghitungan atau penampilan layar gagal.
     */
    @Override
    public void show() throws Exception {
        final int MAX_RECOMMENDATION_COUNT = 5;

        terminal.clear();
        terminal.printDivider();
        System.out.println(Text.bold("# Recommend User"));
        terminal.printDivider();

        String[] tokens = terminal.getLine("Describe your event: ").split("\\s+");
        terminal.printDivider();

        int choice = terminal.getOption(
            new String[] {
                "Cancel [x]",
                "Recommend using BFS",
                "Recommend using Personalized PageRank"
            },
            "Choose recommendation algorithm (0-2): "
        );

        if (choice == 0) {
            System.out.println(Text.brightBlack("Cancelled. No recommendation generated."));
            terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
            terminal.clear();
            return;
        }

        Set<Object> seeds = new LinkedHashSet<>();
        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }

            Set<Object> matches = graph.search(token);
            if (matches != null && !matches.isEmpty()) {
                seeds.addAll(matches);
            }
        }

        if (seeds.isEmpty()) {
            System.out.println(Text.warning("Maaf, belum ada node yang cocok dengan deskripsi event Anda."));
            terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
            terminal.clear();
            return;
        }

        Map<Object, Double> scores = switch (choice) {
            case 1 -> {
                yield Personalization.bfsRecommendation(
                    this.graph,
                    seeds,
                    1
                );
            }
            case 2 -> {
                Map<Object, Double> personalization = new HashMap<>();
                for (Object seed : seeds) {
                    personalization.merge(seed, 1.0, Double::sum);
                }

                yield Personalization.personalizedPageRank(
                    this.graph,
                    personalization,
                    0.15,
                    50,
                    1e-6
                );
            }
            default -> {
                System.out.println(Text.warning("Pilihan algoritma tidak valid."));
                terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
                terminal.clear();
                yield Map.of();
            }
        };

        if (scores.isEmpty()) {
            System.out.println(
                Text.warning("Maaf, belum ada node yang cocok dengan deskripsi event Anda.")
            );
            terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
            terminal.clear();
            return;
        }

        List<Scored<Object>> scoredList = Personalization.toScoredList(scores);
        Scored<Object>[] scoredArray = scoredList.toArray(Scored[]::new);
        Sort.selectionSort(scoredArray);

        List<Scored<User>> users = new ArrayList<>();

        // Ambil hanya node bertipe User
        for (Scored<Object> scored : scoredArray) {
            Object value = scored.getValue();
            if (value instanceof User user) {
                users.add(Scored.of(user, scored.getScore()));
                if (users.size() >= MAX_RECOMMENDATION_COUNT) {
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
            System.out.println(
                Text.warning("Maaf, belum ada rekomendasi user yang cocok dengan deskripsi event yang Anda berikan.")
            );
        }

        terminal.waitForInput(Text.brightBlack("Press ENTER to continue..."));
        terminal.clear();
    }

}
