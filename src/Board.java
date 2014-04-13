
/**
 * File: Board.java
 * Author: Brian Borowski
 * Date created: April 9, 2012
 * Date last modified: September 2, 2012
 */
public class Board {
    public static final char MARK_RED = 'X', MARK_BLACK = 'O', UNMARKED = ' ';
    public static final String RED = "Red", BLACK = "Black";
    public static final int ROWS = 6, COLUMNS = 7;
    public static final int[] INCREMENT = {0, 1, 4, 32, 128, 512}; 

    private final char[][] board;
    private final int[] firstAvailableRow;
    private boolean redWinFound, blackWinFound;

    public Board() {
        board = new char[ROWS][COLUMNS];
        firstAvailableRow = new int[COLUMNS];
        reset();
    }

    public void reset() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = UNMARKED;
            }
        }
        for (int col = 0; col < COLUMNS; col++) {
            firstAvailableRow[col] = ROWS - 1;
        }
    }

    public boolean isColumnAvailable(int column) {
        return firstAvailableRow[column] != -1;
    }

    public int getFirstAvailableRow(int column) {
        return firstAvailableRow[column];
    }

    public char get(int row, int column) {
        return board[row][column];
    }

    public char[][] getGrid() {
        char[][] grid = new char[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                grid[row][col] = board[row][col];
            }
        }
        return grid;
    }

    public void display() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print("|" + board[row][col]);
            }
            System.out.println("|");
        }
        System.out.println("---------------");
    }

    public void mark(int col, char mark) throws IllegalArgumentException {
        int row = firstAvailableRow[col];
        if (row < 0) {
            throw new IllegalArgumentException(
                "Column " + (col + 1) + " is already full.");
        }
        board[row][col] = mark;
        --firstAvailableRow[col];
    }

    public void set(int col, char mark) throws IllegalArgumentException {
        mark(col, mark);
    }

    public void unset(int col) throws IllegalArgumentException {
        int row = firstAvailableRow[col];
        if (row >= ROWS) {
            throw new IllegalArgumentException(
                "Column " + (col + 1) + " is already empty.");
        }
        row = ++firstAvailableRow[col];
        board[row][col] = UNMARKED;
    }

    public static String getColorOfPlayer(char player)
            throws IllegalArgumentException {

        if (player == MARK_RED) {
            return RED;
        } else if (player == MARK_BLACK) {
            return BLACK;
        } else {
            throw new IllegalArgumentException(
                "Unknown player " + player + " received.");
        }
    }

    public char getWinner() {
        // Check rows
        for (int row = 0; row < ROWS; row++) {
            for (int col = 3; col < COLUMNS; col++) {
                int redCount = 0, blackCount = 0;
                for (int val = 0; val < 4; val++) {
                    char mark = board[row][col - val];
                    if (mark == MARK_RED) {
                        redCount++;
                    } else if (mark == MARK_BLACK) {
                        blackCount++;
                    }
                }
                if (redCount == 4) {
                    return MARK_RED;
                } else if (blackCount == 4) {
                    return MARK_BLACK;
                }
            }
        }
        // Check columns
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 3; row < ROWS; row++) {
                int redCount = 0, blackCount = 0;
                for (int val = 0; val < 4; val++) {
                    char mark = board[row - val][col];
                    if (mark == MARK_RED) {
                        redCount++;
                    } else if (mark == MARK_BLACK) {
                        blackCount++;
                    }
                }
                if (redCount == 4) {
                    return MARK_RED;
                } else if (blackCount == 4) {
                    return MARK_BLACK;
                }
            }
        }
        // Check major diagonals
        for (int row = ROWS - 4; row >= 0; row--) {
            for (int col = COLUMNS - 4; col >= 0; col--) {
                int redCount = 0, blackCount = 0;
                for (int val = 3; val >= 0; val--) {
                    char mark = board[row + val][col + val];
                    if (mark == MARK_RED) {
                        redCount++;
                    } else if (mark == MARK_BLACK) {
                        blackCount++;
                    }
                }
                if (redCount == 4) {
                    return MARK_RED;
                } else if (blackCount == 4) {
                    return MARK_BLACK;
                }
            }
        }
        // Check minor diagonals
        for (int row = ROWS - 4; row >= 0; row--) {
            for (int col = COLUMNS - 4; col >= 0; col--) {
                int redCount = 0, blackCount = 0;
                for (int val = 3; val >= 0; val--) {
                    char mark = board[row + val][col - val + 3];
                    if (mark == MARK_RED) {
                        redCount++;
                    } else if (mark == MARK_BLACK) {
                        blackCount++;
                    }
                }
                if (redCount == 4) {
                    return MARK_RED;
                } else if (blackCount == 4) {
                    return MARK_BLACK;
                }
            }
        }
        return UNMARKED;
    }

    private int getScoreIncrement(int redCount, int blackCount, char player) {
        if (redCount == blackCount) {
            if (player == Board.MARK_RED) {
                return -1;
            }
            return 1;
        } else if (redCount < blackCount) {
            if (player == Board.MARK_RED) {
                return INCREMENT[blackCount] - INCREMENT[redCount];
            }
            return INCREMENT[blackCount + 1] - INCREMENT[redCount];
        } else {
            if (player == Board.MARK_RED) {
                return -INCREMENT[redCount + 1] + INCREMENT[blackCount];
            }
            return -INCREMENT[redCount] + INCREMENT[blackCount];
        }
    }
    
    public boolean redWinFound() {
        return redWinFound;
    }
    
    public boolean blackWinFound() {
        return blackWinFound;
    }

    public int getHeuristicScore(char player, int col, int depth, int maxDepth) {
        int score = 0,
            row = firstAvailableRow[col] + 1,
            redCount, blackCount;
        redWinFound = blackWinFound = false;

        ///////////////////////////////////////////////////////////////////////
        // Check row
        ///////////////////////////////////////////////////////////////////////
        redCount = blackCount = 0;
        char[] boardRow = board[row];
        int cStart = col - 3,
            colStart = cStart >= 0 ? cStart : 0,        
            colEnd = COLUMNS - 3 - (colStart - cStart);
        for (int c = colStart; c < colEnd; c++) {
            redCount = blackCount = 0;
            for (int val = 0; val < 4; val++) {
                char mark = boardRow[c + val];
                if (mark == MARK_RED) {
                    redCount++;
                } else if (mark == MARK_BLACK) {
                    blackCount++;
                }
            }
            if (redCount == 4) {
                redWinFound = true;
                if (depth <= 2) {
                    return Integer.MIN_VALUE + 1;
                }
            } else if (blackCount == 4) {
                blackWinFound = true;
                if (depth <= 2) {
                    return Integer.MAX_VALUE - 1;
                }
            }
            score += getScoreIncrement(redCount, blackCount, player);
        }

        ///////////////////////////////////////////////////////////////////////
        // Check column
        ///////////////////////////////////////////////////////////////////////
        redCount = blackCount = 0;
        int rowEnd = Math.min(ROWS, row + 4);
        for (int r = row; r < rowEnd; r++) {
            char mark = board[r][col];
            if (mark == MARK_RED) {
                redCount++;
            } else if (mark == MARK_BLACK) {
                blackCount++;
            }            
        }
        if (redCount == 4) {
            redWinFound = true;
            if (depth <= 2) {
                return Integer.MIN_VALUE + 1;
            }
        } else if (blackCount == 4) {
            blackWinFound = true;
            if (depth <= 2) {
                return Integer.MAX_VALUE - 1;
            }
        }
        score += getScoreIncrement(redCount, blackCount, player);

        ///////////////////////////////////////////////////////////////////////
        // Check major diagonal
        ///////////////////////////////////////////////////////////////////////
        int minValue = Math.min(row, col),
            rowStart = row - minValue;
        colStart = col - minValue;
        for (int r = rowStart, c = colStart; r <= ROWS - 4 && c <= COLUMNS - 4; r++, c++) {
            redCount = blackCount = 0;
            for (int val = 0; val < 4; val++) {
                char mark = board[r + val][c + val];
                if (mark == MARK_RED) {
                    redCount++;
                } else if (mark == MARK_BLACK) {
                    blackCount++;
                }
            }
            if (redCount == 4) {
                redWinFound = true;
                if (depth <= 2) {
                    return Integer.MIN_VALUE + 1;
                }
            } else if (blackCount == 4) {
                blackWinFound = true;
                if (depth <= 2) {
                    return Integer.MAX_VALUE - 1;
                }
            }
            score += getScoreIncrement(redCount, blackCount, player);
        }

        ///////////////////////////////////////////////////////////////////////
        // Check minor diagonal
        ///////////////////////////////////////////////////////////////////////
        minValue = Math.min(ROWS - 1 - row, col);
        rowStart = row + minValue;
        colStart = col - minValue;
        for (int r = rowStart, c = colStart; r >= 3 && c <= COLUMNS - 4; r--, c++) {
            redCount = blackCount = 0;
            for (int val = 0; val < 4; val++) {
                char mark = board[r - val][c + val];
                if (mark == MARK_RED) {
                    redCount++;
                } else if (mark == MARK_BLACK) {
                    blackCount++;
                }
            }
            if (redCount == 4) {
                redWinFound = true;
                if (depth <= 2) {
                    return Integer.MIN_VALUE + 1;
                }
            } else if (blackCount == 4) {
                blackWinFound = true;
                if (depth <= 2) {
                    return Integer.MAX_VALUE - 1;
                }
            }
            score += getScoreIncrement(redCount, blackCount, player);
        }
        return score;
    }

    public boolean isFull() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == Board.UNMARKED) {
                    return false;
                }
            }
        }
        return true;
    }
}
