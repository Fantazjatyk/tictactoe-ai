/* 
 * The MIT License
 *
 * Copyright 2017 Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.michal.szymanski.ai.tictactoe.model;

import pl.michal.szymanski.ai.tictactoe.model.Board;
import pl.michal.szymanski.ai.tictactoe.model.BoardField;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class BoardSelector {

    private Board board;

    public BoardSelector(Board board) {
        this.board = board;
    }

    public List<BoardField> getAllFields() {
        List<BoardField> fields = new ArrayList();
        Stream.of(board.getWrapped()).forEach(el -> fields.addAll(Stream.of(el).collect(Collectors.toList())));
        return fields;
    }

    public List<BoardField[]> getRows() {
        return Stream.of(this.board.getWrapped()).collect(Collectors.toList());
    }

    public List<BoardField[]> getColumns() {
        List<BoardField[]> rows = new ArrayList();

        for (int i = 0; i < board.getSize(); i++) {
            rows.add(getColumn(i));
        }
        return rows;
    }

    private BoardField[] getDiagonal(int x1, int y1, int x2, int y2) {

        int a = board.getSize() - 1 - x1;
        a = a == 0 && x1 == board.getSize() - 1 ? (board.getSize() - 1) : (x1 == 0 ? a - 1 : a);

        a = a < 0 ? a + (2 * a) : a;

        boolean incrementX = x2 > x1;
        boolean incrementY = y2 > y1;

        List<BoardField> fields = new ArrayList();

        for (int x = x1, y = y1; incrementX && x <= x2 || !incrementX && x >= 0;) {
            fields.add(board.getWrapped()[x][y]);

            x = incrementX ? x + 1 : x - 1;
            y = incrementY ? y + 1 : y - 1;
        }
        return fields.toArray(new BoardField[0]);
    }

    public List<BoardField[]> getDiagonals() {
        List<BoardField[]> diagonals = new ArrayList();
        diagonals.add(getDiagonal(0, 0, board.getSize() - 1, board.getSize() - 1));
        diagonals.add(getDiagonal(0, board.getSize() - 1, board.getSize() - 1, 0));
        return diagonals;
    }

    public BoardField[] getColumn(int id) {
        List<BoardField[]> rows = getRows();
        List<BoardField> column = new ArrayList();

        for (int i = 0; i < rows.size(); i++) {
            BoardField[] row = rows.get(i);
            column.add(row[id]);
        }

        return column.toArray(new BoardField[0]);
    }

    public BoardField getCenter() {
        int x = (int) ((board.getSize() * Math.sqrt(2)) / 2) - 1;
        int y = x;
        return board.getWrapped()[y][x];
    }

    public List<BoardField> getCorners() {
        return board.getSelector().getAllFields().stream()
                .filter(el ->
                   (el.getX() == 0 && el.getY() == 0)
                || (el.getX() == board.getSize() - 1 && el.getY() == 0)
                || (el.getX() == 0 && el.getY() == board.getSize() - 1)
                || (el.getX() == board.getSize() - 1 && el.getY() == board.getSize() - 1))
                .collect(Collectors.toList());
    }
}
