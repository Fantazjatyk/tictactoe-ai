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

import pl.michal.szymanski.ai.tictactoe.model.BoardField;
import pl.michal.szymanski.ai.tictactoe.exceptions.UndenfinedBoardFieldException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class BoardReader<T> {

    private Predicate<T> notMe;
    private Predicate<T> unknown;
    private Predicate<T> me;


    /*
    Also, BoardReader can interpretate null value, if thera are no wrappers (Boolean, Integer, Character), itp.
    */

    public void configure(Predicate<T> notMe, Predicate<T> unknown, Predicate<T> me) {
        this.unknown = unknown;
        this.notMe = notMe;
        this.me = me;
    }

    private BoardField[] readRow(T[] row, int rowId) {
        BoardField[] result = new BoardField[row.length];

        for (int i = 0; i < row.length; i++) {
            T field = row[i];
            BoardField f = new BoardField(i, rowId);
            FieldOwner state = evaluateFieldState(field);
            f.setValue(state);
            result[i] = f;
        }
        return result;
    }

    private FieldOwner evaluateFieldState(T field) {

        if (me.test(field)) {
            return FieldOwner.Me;
        } else if (notMe.test(field)) {
            return FieldOwner.NotMe;
        }
        else if (unknown.test(field)) {
                return FieldOwner.Unknown;
            }
            else{
                throw new UndenfinedBoardFieldException();
            }

    }


    public BoardField[][] read(T[][] board) {
        List<BoardField[]> rows = new ArrayList();

        for (int i = 0; i < board.length; i++) {
            rows.add(readRow(board[i], i));
        }

        return rows.toArray(new BoardField[0][0]);
    }

}
