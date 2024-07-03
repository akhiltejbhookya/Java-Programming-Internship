import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private static final int BOARD_SIZE = 3; // Size of the board (3x3)
    private static final int CELL_SIZE = 100; // Size of each cell in pixels

    private JButton[][] cells; // Array to store the buttons/cells of the board
    private char currentPlayer; // Current player - 'X' or 'O'
    private JLabel statusLabel; // Label to show status - whose turn or who wins

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        setLocationRelativeTo(null); // Center the window

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        cells = new JButton[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = 'X';

        // Initialize cells and add action listener to each
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j] = new JButton("");
                cells[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                cells[i][j].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cells[i][j].addActionListener(this); // Add ActionListener
                boardPanel.add(cells[i][j]);
            }
        }

        statusLabel = new JLabel("Player " + currentPlayer + "'s turn");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
    }

    // Action listener for cell buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedCell = (JButton) e.getSource();

        // Check all cells to find which one was clicked
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (cells[i][j] == clickedCell && clickedCell.getText().isEmpty()) {
                    cells[i][j].setText(Character.toString(currentPlayer));
                    cells[i][j].setEnabled(false); // Disable button after click
                    if (checkWin(currentPlayer)) {
                        statusLabel.setText("Player " + currentPlayer + " wins!");
                        disableAllCells();
                    } else if (checkDraw()) {
                        statusLabel.setText("It's a draw!");
                    } else {
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        statusLabel.setText("Player " + currentPlayer + "'s turn");
                    }
                    return;
                }
            }
        }
    }

    // Method to check if the current player has won
    private boolean checkWin(char player) {
        String mark = Character.toString(player);

        // Check rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (cells[i][0].getText().equals(mark) && cells[i][1].getText().equals(mark) && cells[i][2].getText().equals(mark))
                return true;
            if (cells[0][i].getText().equals(mark) && cells[1][i].getText().equals(mark) && cells[2][i].getText().equals(mark))
                return true;
        }

        // Check diagonals
        if (cells[0][0].getText().equals(mark) && cells[1][1].getText().equals(mark) && cells[2][2].getText().equals(mark))
            return true;
        if (cells[0][2].getText().equals(mark) && cells[1][1].getText().equals(mark) && cells[2][0].getText().equals(mark))
            return true;

        return false;
    }

    // Method to check if the game is a draw
    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (cells[i][j].getText().isEmpty()) {
                    return false; // Found an empty cell, game is not a draw yet
                }
            }
        }
        return true; // All cells are filled, game is a draw
    }

    // Method to disable all cells after the game ends
    private void disableAllCells() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TicTacToeGUI ticTacToe = new TicTacToeGUI();
                ticTacToe.setVisible(true);
            }
        });
    }
}
