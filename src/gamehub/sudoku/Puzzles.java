package gamehub.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Puzzles {

    
    private static final String[][] PUZZLES = {
        {
            "--74916-5",
            "2---6-3-9",
            "-----7-1-",
            "-586----4",
            "--3----9-",
            "--62--187",
            "9-4-7---2",
            "67-83----",
            "81--45---"
        },
        {
            "53--7----",
            "6--195---",
            "-98----6-",
            "8---6---3",
            "4--8-3--1",
            "7---2---6",
            "-6----28-",
            "---419--5",
            "----8--79"
        },
        {
            "-2-6-8---",
            "58---97--",
            "----4----",
            "37----5--",
            "6-------4",
            "--8----13",
            "----2----",
            "--98---36",
            "---3-6-9-"
        },
        {
            "1--9-4---",
            "--2---6--",
            "-----1-3-",
            "--1-----7",
            "4--2-3--9",
            "3-----5--",
            "-6-3-----",
            "--8---1--",
            "---7-5--2"
        },
        {
            "--4--1---",
            "8--2--5--",
            "-9--8-2--",
            "---7--6--",
            "3---9---7",
            "--2--5---",
            "--5-6--4-",
            "--7--1--2",
            "---3--7--"
        },
        {
            "----5----",
            "-1--2--6-",
            "--3-----1",
            "--92-7--5",
            "--6---3--",
            "8--1-64--",
            "4-----2--",
            "-2--8--7-",
            "----9----"
        },
        {
            "---7--6--",
            "1---9----",
            "--2--8--7",
            "7--5-----",
            "------5--",
            "----7--3-",
            "--4--1---",
            "-6--3--9-",
            "9--8--1--"
        },
        {
            "-7--6--3-",
            "--5---7--",
            "3--9--1--",
            "----2--6-",
            "9--8--4--",
            "5--3-----",
            "-3--5--9-",
            "--7---6--",
            "-1--7--5-"
        },
        {
            "----7-2--",
            "9---6--5-",
            "----9---3",
            "1-6-----7",
            "--2-5-4--",
            "7-----8-2",
            "3---4----",
            "-8--7---6",
            "--5-2----"
        },
        {
            "--1--7--6",
            "7---3----",
            "--9---8--",
            "5-----1--",
            "---4-6---",
            "--3-----9",
            "--2---3--",
            "----1---4",
            "3--6--2--"
        }
    };

    
    public static String[] getPuzzle() {
        int index = new Random().nextInt(PUZZLES.length);
        return PUZZLES[index];
    }

   
    public static String[] getSolution(String[] puzzle) {
        int[][] grid = stringArrayToGrid(puzzle);
        solve(grid);
        return gridToStringArray(grid);
    }

   
    public static boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false; 
                }
            }
        }
        return true; 
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num ||
                board[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

   
    public static int[][] stringArrayToGrid(String[] strGrid) {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            char[] row = strGrid[i].toCharArray();
            for (int j = 0; j < 9; j++) {
                grid[i][j] = (row[j] == '-' || row[j] == '0') ? 0 : row[j] - '0';
            }
        }
        return grid;
    }

   
    public static String[] gridToStringArray(int[][] grid) {
        String[] result = new String[9];
        for (int i = 0; i < 9; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 9; j++) {
                sb.append(grid[i][j] == 0 ? '-' : grid[i][j]);
            }
            result[i] = sb.toString();
        }
        return result;
    }
}
