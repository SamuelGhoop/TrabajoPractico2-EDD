import javax.swing.*;
import java.awt.*;

public class InterfazReinas extends JFrame {

    private static final int CELL_SIZE = 70;
    private int[][] tablero = new int[NReinas.getN()][NReinas.getN()];
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JButton btnModo, btnResolver, btnLimpiar;
    private JSpinner spinnerSolucion;
    private JLabel lblTotal;
    private JPanel selectorPanel;
    private boolean modoColocar = false;

    public InterfazReinas() {
        int n = NReinas.getN();

        setTitle("8 Reinas - Backtracking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarTablero(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(n * CELL_SIZE, n * CELL_SIZE));
        boardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (modoColocar) {
                    int col = e.getX() / CELL_SIZE;
                    int fil = e.getY() / CELL_SIZE;
                    if (fil >= 0 && fil < NReinas.getN() && col >= 0 && col < NReinas.getN()) {
                        colocarReinaInicial(fil, col);
                    }
                }
            }
        });

        // Panel de controles
        JPanel controlPanel = new JPanel();
        btnModo = new JButton("Colocar reina inicial");
        btnResolver = new JButton("Resolver");
        btnLimpiar = new JButton("Limpiar tablero");

        btnModo.addActionListener(e -> activarModoColocar());
        btnResolver.addActionListener(e -> resolver());
        btnLimpiar.addActionListener(e -> limpiarTablero());

        controlPanel.add(btnModo);
        controlPanel.add(btnResolver);
        controlPanel.add(btnLimpiar);

        // Panel selector de solución
        selectorPanel = new JPanel();
        selectorPanel.add(new JLabel("Solución:"));
        spinnerSolucion = new JSpinner(new SpinnerNumberModel(1, 1, 1, 1));
        spinnerSolucion.setPreferredSize(new Dimension(60, 25));
        spinnerSolucion.addChangeListener(e -> mostrarSolucion((int) spinnerSolucion.getValue() - 1));
        selectorPanel.add(spinnerSolucion);
        lblTotal = new JLabel("de 0");
        selectorPanel.add(lblTotal);
        selectorPanel.setVisible(false);

        // Panel superior con status y selector
        JPanel topPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Caso 1: Tablero vacío. Presione Resolver.", SwingConstants.CENTER);
        topPanel.add(statusLabel, BorderLayout.NORTH);
        topPanel.add(selectorPanel, BorderLayout.SOUTH);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void dibujarTablero(Graphics g) {
        int n = NReinas.getN();
        boolean[][] fijaActual = NReinas.getFija();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(240, 217, 181));
                } else {
                    g.setColor(new Color(181, 136, 99));
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                if (tablero[i][j] == 1) {
                    g.setColor(fijaActual[i][j] ? new Color(0, 100, 200) : new Color(200, 30, 30));
                    g.setFont(new Font("Serif", Font.BOLD, 45));
                    g.drawString("♛", j * CELL_SIZE + 15, i * CELL_SIZE + 52);
                }
            }
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, n * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE, n * CELL_SIZE, i * CELL_SIZE);
        }
    }

    private void activarModoColocar() {
        limpiarTablero();
        modoColocar = true;
        statusLabel.setText("Caso 2: Haga clic en una casilla para ubicar la reina inicial.");
        btnModo.setEnabled(false);
    }

    private void colocarReinaInicial(int fila, int col) {
        int n = NReinas.getN();
        tablero = new int[n][n];
        NReinas.setFija(new boolean[n][n]);
        tablero[fila][col] = 1;
        NReinas.getFija()[fila][col] = true;
        modoColocar = false;
        statusLabel.setText("Reina fija en (" + fila + ", " + col + "). Presione Resolver.");
        boardPanel.repaint();
    }

    private void limpiarTablero() {
        int n = NReinas.getN();
        tablero = new int[n][n];
        NReinas.setFija(new boolean[n][n]);
        NReinas.setContadorSoluciones(0);
        NReinas.limpiarSoluciones();
        modoColocar = false;
        btnModo.setEnabled(true);
        selectorPanel.setVisible(false);
        statusLabel.setText("Caso 1: Tablero vacío. Presione Resolver.");
        boardPanel.repaint();
        pack();
    }

    private void resolver() {
        int n = NReinas.getN();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (!NReinas.getFija()[i][j]) tablero[i][j] = 0;

        NReinas.setContadorSoluciones(0);
        NReinas.limpiarSoluciones();
        NReinas.resolverReinas(tablero, 0);

        int total = NReinas.getContadorSoluciones();
        if (total == 0) {
            statusLabel.setText("No se encontró solución con esa configuración.");
            selectorPanel.setVisible(false);
        } else {
            statusLabel.setText("Se encontraron " + total + " soluciones.");
            spinnerSolucion.setModel(new SpinnerNumberModel(1, 1, total, 1));
            lblTotal.setText("de " + total);
            selectorPanel.setVisible(true);
            mostrarSolucion(0);
        }
        pack();
    }

    private void mostrarSolucion(int indice) {
        int[][] solucion = NReinas.getSoluciones().get(indice);
        int n = NReinas.getN();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tablero[i][j] = solucion[i][j];
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazReinas().setVisible(true));
    }
}